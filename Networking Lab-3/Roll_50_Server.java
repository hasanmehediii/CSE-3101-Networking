import java.io.*;
import java.net.*;
import java.util.*;

public class Roll_50_Server {
    private static Map<String, String> accountPin = Collections.synchronizedMap(new HashMap<>());
    private static Map<String, Integer> accountBalance = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        loadDatabase();
        try {
            ServerSocket server = new ServerSocket(7777);
            System.out.println("Server started on port 7777");
            while (true) {
                Socket socket = server.accept();
                System.out.println("New client connected: " + socket.getInetAddress());
                new ClientHandler(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadDatabase() {
        try (BufferedReader br = new BufferedReader(new FileReader("database.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                accountPin.put(parts[0], parts[1]);
                accountBalance.put(parts[0], Integer.parseInt(parts[2]));
            }
            System.out.println("Database loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading database: " + e.getMessage());
        }
    }

    private static synchronized void saveDatabase() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("database.txt"))) {
            for (String card : accountBalance.keySet()) {
                bw.write(card + "," + accountPin.get(card) + "," + accountBalance.get(card));
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving database: " + e.getMessage());
        }
    }

    static class ClientHandler extends Thread {
        Socket socket;
        DataInputStream inputDS;
        DataOutputStream outputDS;
        String authenticatedCard = null;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                inputDS = new DataInputStream(socket.getInputStream());
                outputDS = new DataOutputStream(socket.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                while (true) {
                    String message = inputDS.readUTF();

                    if (message.startsWith("AUTH:")) {
                        String[] parts = message.split(":");
                        if (parts.length == 3) {
                            String card = parts[1];
                            String pin = parts[2];
                            if (accountPin.containsKey(card) && accountPin.get(card).equals(pin)) {
                                authenticatedCard = card;
                                outputDS.writeUTF("AUTH_OK");
                                System.out.println("Client authenticated: " + card);
                            } else {
                                outputDS.writeUTF("AUTH_FAIL");
                                System.out.println("Failed authentication attempt for card: " + card);
                            }
                        } else {
                            outputDS.writeUTF("INVALID_FORMAT");
                            System.out.println("Invalid AUTH format received");
                        }

                    } else if (message.equals("BALANCE_REQ")) {
                        if (authenticatedCard != null) {
                            int balance = accountBalance.get(authenticatedCard);
                            outputDS.writeUTF("BALANCE_RES:" + balance);
                            System.out.println("Balance request for " + authenticatedCard + ": " + balance);
                        } else {
                            outputDS.writeUTF("AUTH_REQUIRED");
                            System.out.println("Balance request without authentication");
                        }

                    } else if (message.startsWith("WITHDRAW:")) {
                        if (authenticatedCard != null) {
                            int amount = Integer.parseInt(message.split(":")[1]);
                            int balance = accountBalance.get(authenticatedCard);
                            if (balance >= amount) {
                                accountBalance.put(authenticatedCard, balance - amount);
                                saveDatabase();
                                outputDS.writeUTF("WITHDRAW_OK");
                                System.out.println("Withdrawal of " + amount + " for " + authenticatedCard
                                        + ". New balance: " + (balance - amount));
                            } else {
                                outputDS.writeUTF("INSUFFICIENT_FUNDS");
                                System.out.println("Failed withdrawal (insufficient funds) for " + authenticatedCard
                                        + ": " + amount);
                            }
                        } else {
                            outputDS.writeUTF("AUTH_REQUIRED");
                            System.out.println("Withdrawal request without authentication");
                        }

                    } else if (message.equals("EXIT")) {
                        outputDS.writeUTF("EXIT");
                        System.out.println("Client disconnected: " + socket.getInetAddress());
                        socket.close();
                        break;

                    } else {
                        outputDS.writeUTF("UNKNOWN_COMMAND");
                        System.out.println("Unknown command received: " + message);
                    }
                }
            } catch (Exception e) {
                System.out.println("Connection lost with client: " + socket.getInetAddress());
            }
        }
    }
}
