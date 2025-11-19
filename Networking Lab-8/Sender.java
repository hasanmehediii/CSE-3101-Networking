import java.io.*;
import java.net.*;
import java.util.*;

public class Sender {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String mode = args.length > 0 ? args[0].toUpperCase() : "TAHOE";
        int cwnd = 1, ssthresh = 8, dupACK = 0, round = 1;
        String lastACK = "";
        int pktCounter = 1;

        System.out.println("== TCP " + mode + " Mode ==");

        while (round <= 10) {
            System.out.println("Round " + round + ": cwnd=" + cwnd + ", ssthresh=" + ssthresh);

            List<String> packets = new ArrayList<>();
            for (int i = 0; i < cwnd; i++) {
                packets.add("pkt" + pktCounter++);
            }

            System.out.println("Sent packets: " + String.join(",", packets));
            out.println(String.join(",", packets));

            dupACK = 0;

            for (int i = 0; i < packets.size(); i++) {
                String ack = in.readLine();
                System.out.println("Received: " + ack);

                if (ack.equals(lastACK)) {
                    dupACK++;
                    if (dupACK == 3) {
                        System.out.println("==> 3 Duplicate ACKs: Fast Retransmit Triggered");
                        ssthresh = Math.max(cwnd / 2, 1);

                        if (mode.equals("RENO")) {
                            cwnd = ssthresh;
                            System.out.println("TCP RENO Fast Recovery: cwnd=" + cwnd);
                        } else {
                            cwnd = 1;
                            System.out.println("TCP TAHOE Reset: cwnd=1");
                        }

                        lastACK = ack;
                        round++;
                        continue;
                    }
                } else {
                    dupACK = 1;
                    lastACK = ack;
                }
            }

            if (cwnd < ssthresh) cwnd *= 2;
            else cwnd += 1;

            round++;
        }

        socket.close();
        System.out.println("Client disconnected.");
    }
}
