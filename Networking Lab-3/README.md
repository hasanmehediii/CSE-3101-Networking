# Networking Lab-3: Client-Server Socket Programming

This lab introduces the fundamentals of socket programming to create a basic client-server application. The goal is to establish a connection between a client and a server and enable them to exchange messages.

## Key Concepts

### Sockets
A socket is an endpoint for communication between two machines. By using sockets, programs can communicate across a network, regardless of the underlying network technology. This lab uses TCP sockets, which provide a reliable, stream-oriented connection.

### Client-Server Model
This is a distributed application structure that partitions tasks or workloads between the providers of a resource or service, called servers, and service requesters, called clients.

### Java Implementation
- **`ServerSocket`**: A Java class used on the server side to listen for incoming client connections.
- **`Socket`**: A Java class representing a single connection between the client and the server.
- **`DataInputStream` and `DataOutputStream`**: Used for reading from and writing to the socket's communication streams.

## Lab Implementation

The provided code implements a simple banking server (`Roll_50_Server.java`) and a corresponding client (`Roll_22_Client.java`).

- **`Roll_50_Server.java`**: This server application listens for client connections on port 7777. It handles multiple clients simultaneously using threads. The server can:
    - Authenticate a client based on a card number and PIN.
    - Check the account balance.
    - Process withdrawals.
    - The server state (account information) is persisted in `database.txt`.

- **`Roll_22_Client.java`**: This client application connects to the server, sends commands for authentication, balance inquiry, and withdrawal, and displays the server's responses.

## Lab Files

- **`Roll_50_Server.java`**: The source code for the server application.
- **`Roll_22_Client.java`**: The source code for the client application.
- **`database.txt`**: A simple text file used as a database to store user account information (card number, PIN, balance).
- **`CSE_3111__Lab_3_Lab_Manual_CSEDU (2).pdf`**: The lab manual with detailed instructions.
- **`networikg1.pdf` and `Networking_LabReport-1.pdf`**: Supporting documents, likely a report or presentation.
- **`networking3_latex.tex`**: The LaTeX source for a report.
