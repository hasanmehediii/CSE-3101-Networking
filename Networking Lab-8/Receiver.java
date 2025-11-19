import java.io.*;
import java.net.*;
import java.util.*;

public class Receiver {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("The server started on port 5000");

        Socket socket = server.accept();
        System.out.println("Client connected: " + socket.getInetAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Random rand = new Random();

        while (true) {
            String msg = in.readLine();
            if (msg == null) break;

            String[] packets = msg.split(",");
            int lossIndex = rand.nextInt(packets.length);

            for (int i = 0; i < packets.length; i++) {
                if (i == lossIndex) {
                    if (i == 0) {
                        out.println("ACK:NA");
                        out.println("ACK:NA");
                        out.println("ACK:NA");
                    } else {
                        out.println("ACK:" + packets[i - 1]);
                        out.println("ACK:" + packets[i - 1]);
                        out.println("ACK:" + packets[i - 1]);
                    }
                } else {
                    out.println("ACK:" + packets[i]);
                }
            }
        }

        socket.close();
        server.close();
        System.out.println("Client disconnected.");
    }
}
