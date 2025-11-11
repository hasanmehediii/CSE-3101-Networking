# Networking Lab-7: TCP Layer Analysis with Wireshark

This lab returns to Wireshark for a deep dive into the Transmission Control Protocol (TCP), one of the core protocols of the Internet protocol suite. The focus is on understanding TCP's mechanisms for reliable, ordered, and error-checked delivery of a stream of octets.

## Key Concepts

### TCP (Transmission Control Protocol)
TCP provides reliable, connection-oriented communication over an IP network. Key features analyzed in this lab include:
- **Three-Way Handshake:** The process of establishing a TCP connection, involving SYN, SYN-ACK, and ACK packets.
- **Data Transfer:** How TCP segments data and uses sequence and acknowledgment numbers to ensure reliable delivery.
- **Connection Termination:** The process of gracefully closing a TCP connection using FIN and ACK packets.
- **Flow Control:** Mechanisms like the sliding window protocol that prevent a sender from overwhelming a receiver.
- **Congestion Control:** How TCP deals with network congestion.

### UDP (User Datagram Protocol)
While the focus is on TCP, the lab also includes a manual for UDP (`Wireshark_UDP_v9.pdf`). UDP is a simpler, connectionless protocol that does not provide reliability, ordering, or error checking. It is often used for time-sensitive applications like video streaming or online gaming.

## Lab Activities

Using Wireshark, students capture and analyze TCP traffic to:
- Visualize the three-way handshake and connection termination.
- Track the sequence and acknowledgment numbers in a TCP stream.
- Observe how TCP handles packet loss and retransmissions.
- Compare and contrast TCP with UDP.

## Lab Files

- **`Wireshark_TCP_v9.pdf`**: The primary lab manual for the TCP analysis exercises.
- **`Wireshark_UDP_v9.pdf`**: A supplementary lab manual for analyzing the UDP protocol.
- **`tcp-wireshark-trace1-1.pcapng` and `tcp-wireshark-trace1-2.pcapng`**: Wireshark capture files containing TCP traffic for analysis.
- **`dns-wireshark-trace1-1.pcapng`**: A DNS trace file, likely for comparison or to generate the initial traffic.
- **`Roll22_50.pdf`**: A student report or submission for this lab.
