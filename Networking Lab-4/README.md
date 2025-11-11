# Networking Lab-4: File Transfer using Socket Programming

This lab builds on the concepts of socket programming to create a file transfer application. It also touches upon handling HTTP-like commands for client-server interaction.

## Key Concepts

### File Transfer over Sockets
Transferring files over a network using sockets involves reading the file into a byte stream on the sender's side and writing it to the socket's output stream. The receiver reads from the socket's input stream and writes the bytes to a file.

### Handling Commands
The client and server communicate using a simple command-based protocol. For example, the client can send an "ls" command to list files on the server, or a filename to request a download.

## Lab Implementation

The lab consists of a client and a server that communicate to transfer files.

- **`Roll50_Server.java`**: This server application listens for connections on port 5000. When a client connects, it can:
    - Respond to a `ls` command by sending a list of available files in the `server_files` directory.
    - Respond to a file name by sending the requested file to the client.

- **`Roll22_Client.java`**: This client application connects to the server. It can:
    - Send the `ls` command to the server and display the list of files.
    - Send a file name to the server to request a download, and then save the received file.

## Lab Files

- **`Roll50_Server.java`**: The source code for the file server.
- **`Roll22_Client.java`**: The source code for the file client.
- **`server_files/`**: A directory containing the files that are available for download from the server.
- **`downloaded_doreamon.jpg`**: An example of a file downloaded by the client.
- **`CSE_3111__Lab_4_Lab_Manual_CSEDU.pdf`**: The lab manual with detailed instructions.
- **`Networking_Lab4.pdf`**: A supplementary PDF document, likely a report or presentation.
- **`Report.tex`**: The LaTeX source for a report.
