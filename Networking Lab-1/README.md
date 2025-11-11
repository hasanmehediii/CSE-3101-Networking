# Networking Lab-1: Basic Network Commands

This lab introduces fundamental networking commands used for diagnostics and troubleshooting. The primary tools covered are `ping`, `traceroute`, and `nslookup`.

## Key Concepts

### `ping`
The `ping` command is used to test the reachability of a host on an IP network. It sends ICMP (Internet Control Message Protocol) Echo Request messages to the target host and waits for an ICMP Echo Reply. This helps determine if the host is online and the round-trip time for packets.

### `traceroute`
The `traceroute` command (or `tracert` on Windows) is used to map the pathway of a packet from the source to the destination. It shows each "hop" (router) the packet passes through, providing a better understanding of the network path and identifying potential points of failure or latency.

### `nslookup`
The `nslookup` command is used to query the Domain Name System (DNS) to find the IP address corresponding to a domain name (or vice versa). It's an essential tool for diagnosing DNS resolution issues.

## Lab Files

- **`CSE 3111 - Comp Net Lab Experiment_1.docx`**: The lab manual document for this experiment.
- **`Networking Lab-1.pdf`**: A PDF version of the lab manual or report.
