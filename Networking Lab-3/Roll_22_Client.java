import java.io.*;
import java.net.*;
import java.util.*;

class MessageReceiver extends Thread {
    DataInputStream inputDS;

    public MessageReceiver(DataInputStream inputDS) {
        this.inputDS = inputDS;
    }

    public void run() {
        try {
            String message;
            while (true) {
                message = inputDS.readUTF();
                System.out.println("\nServer: " + message);
                if (message.equals("EXIT"))
                    break;
                System.out.print("You: ");
            }
        } catch (Exception e) {
        }
    }
}

public class Roll_22_Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            Socket socket = new Socket("10.170.251.21", 7777);
            DataInputStream inputDS = new DataInputStream(socket.getInputStream());
            DataOutputStream outputDS = new DataOutputStream(socket.getOutputStream());
            MessageReceiver receiver = new MessageReceiver(inputDS);
            receiver.start();
            System.out.print("Enter card number: ");
            String card = sc.nextLine();
            System.out.print("Enter PIN: ");
            String pin = sc.nextLine();
            outputDS.writeUTF("AUTH:" + card + ":" + pin);
            outputDS.flush();
            while (true) {
                System.out.print("You: ");
                String text = sc.nextLine();
                if (text.equalsIgnoreCase("EXIT")) {
                    outputDS.writeUTF("EXIT");
                    outputDS.flush();
                    break;
                }
                if (text.equalsIgnoreCase("BALANCE_REQ")) {
                    outputDS.writeUTF("BALANCE_REQ");
                } else if (text.toUpperCase().startsWith("WITHDRAW")) {
                    String[] parts = text.split(" ");
                    if (parts.length != 2) {
                        System.out.println("Usage: WITHDRAW <amount>");
                        continue;
                    }
                    outputDS.writeUTF("WITHDRAW:" + parts[1]);
                } else {
                    System.out.println("Ue BALANCE_REQ, WITHDRAW <amount> EXIT");
                }
                outputDS.flush();
            }
            receiver.join();
            socket.close();
            sc.close();
        } catch (Exception e) {
        }
    }
}
