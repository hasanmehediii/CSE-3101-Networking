import java.io.*;
import java.net.*;

public class Client3 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));
        String fileName;
        while ((fileName = br.readLine()) != null) {
            File file = new File(fileName);
            if (!file.exists())
                continue;
            new Thread(() -> {
                try {
                    Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    FileInputStream fis = new FileInputStream(file);
                    dos.writeUTF(file.getName());
                    dos.writeLong(file.length());
                    byte[] buffer = new byte[4096];
                    int read;
                    while ((read = fis.read(buffer)) > 0) {
                        dos.write(buffer, 0, read);
                    }
                    fis.close();
                    dos.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        br.close();
    }
}
