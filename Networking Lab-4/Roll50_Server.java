import java.io.*;
import java.net.*;

public class Roll50_Server {
    private static final int PORT = 5000;
    private static final String Folder = "server_files";

    public static void main(String[] args) {
        File folder = new File(Folder);
        if (!folder.exists()) {
            folder.mkdir();
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                new ClientHandler(socket, folder).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private File folder;

    public ClientHandler(Socket socket, File folder) {
        this.socket = socket;
        this.folder = folder;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream())) {
            writer.println("Connected to File Server. Type 'ls' to see files, or enter a file name to download.");

            String command;
            while ((command = reader.readLine()) != null) {
                if (command.equalsIgnoreCase("ls")) {

                    File[] files = folder.listFiles();
                    if (files != null && files.length > 0) {
                        for (File f : files) {
                            if (f.isFile()) {
                                writer.println(f.getName());
                            }
                        }
                    }
                    writer.println("END");

                } else {

                    File file = new File(folder, command);

                    if (file.exists() && file.isFile()) {
                        writer.println("FOUND");
                        long fileSize = file.length();
                        dataOut.writeLong(fileSize);

                        try (FileInputStream fis = new FileInputStream(file)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = fis.read(buffer)) != -1) {
                                dataOut.write(buffer, 0, bytesRead);
                            }
                        }
                        System.out.println("File " + command + " sent successfully.");
                    } else {
                        writer.println("NOT_FOUND");
                        System.out.println("File " + command + " not found.");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
