import java.io.*;
import java.net.*;

public class Roll22_Client {
    static final String SERVER_IP = "10.101.198.21";
    static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        DataInputStream dtinp = null;
        BufferedReader bfr = null;

        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to server!");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            dtinp = new DataInputStream(socket.getInputStream());
            bfr = new BufferedReader(new InputStreamReader(System.in));

            String welcomeMsg = reader.readLine();
            System.out.println(welcomeMsg);

            while (true) {
                System.out.print("Enter from heree(ls / filename.ext / exit): ");
                String command = bfr.readLine();

                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection...");
                    break;
                }

                writer.println(command);

                if (command.equalsIgnoreCase("ls")) {
                    System.out.println("Files available on server:");
                    String fileName;
                    while (!(fileName = reader.readLine()).equals("END")) {
                        System.out.println(fileName);
                    }
                } else {
                    String serverResponse = reader.readLine();

                    if (serverResponse.equals("FOUND")) {
                        long fileSize = dtinp.readLong();
                        System.out.println("File size: " + fileSize + " bytes");

                        FileOutputStream fos = new FileOutputStream("downloaded_" + command);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        long totalRead = 0;

                        while (totalRead < fileSize && (bytesRead = dtinp.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                            totalRead += bytesRead;
                        }

                        fos.close();
                        System.out.println("Downloaded done: downloaded_" + command);
                    } else if (serverResponse.equals("NOT_FOUND")) {
                        System.out.println("File not found on server!");
                    } else {
                        System.out.println("Server says: " + serverResponse);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
