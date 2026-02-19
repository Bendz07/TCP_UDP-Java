# Java TCP & UDP Client-Server

A demonstration of client-server networking in Java using both **TCP** (reliable, connection-oriented) and **UDP** (fast, connectionless) protocols.

---

## Project Structure

```
java-client-server/
├── tcp/
│   ├── TCPServer.java
│   └── TCPClient.java
├── udp/
│   ├── UDPServer.java
│   └── UDPClient.java
└── README.md
```

---

## Prerequisites

- Java JDK 8 or higher
- A terminal / command prompt

---

## Getting Started

### Clone or download the project

```bash
git clone https://github.com/your-username/java-client-server.git
cd java-client-server
```

---

## TCP — Echo Server

TCP provides a **reliable, ordered, connection-oriented** communication channel. The server accepts a client connection, echoes back every message it receives, and handles multiple clients concurrently using threads.

### Compile

```bash
javac tcp/TCPServer.java
javac tcp/TCPClient.java
```

### Run

Open two terminals:

**Terminal 1 — Start the server:**
```bash
java tcp.TCPServer
# TCP Server listening on port 5000
```

**Terminal 2 — Start the client:**
```bash
java tcp.TCPClient
# Connected to server. Type messages:
Hello
# Echo: Hello
```

### How it works

| Step | Description |
|------|-------------|
| 1 | `ServerSocket` binds to port 5000 and blocks on `accept()` |
| 2 | Client creates a `Socket` and connects to the server |
| 3 | Server spawns a new `Thread` per client connection |
| 4 | Client sends messages; server echoes them back |
| 5 | Connection closes when the client disconnects |

---

## UDP — Echo Server

UDP provides a **fast, connectionless** communication channel with no guarantee of delivery or ordering. Each message is an independent `DatagramPacket`.

### Compile

```bash
javac udp/UDPServer.java
javac udp/UDPClient.java
```

### Run

Open two terminals:

**Terminal 1 — Start the server:**
```bash
java udp.UDPServer
# UDP Server listening on port 6000
```

**Terminal 2 — Start the client:**
```bash
java udp.UDPClient
# Type messages:
Hello
# Echo: Hello
```

### How it works

| Step | Description |
|------|-------------|
| 1 | Server creates a `DatagramSocket` bound to port 6000 |
| 2 | Client creates an unbound `DatagramSocket` |
| 3 | Client wraps data in a `DatagramPacket` with the server's address and sends it |
| 4 | Server reads the packet, extracts sender address, and sends a response back |
| 5 | No connection is maintained between messages |

---

## TCP vs UDP Comparison

| Feature | TCP | UDP |
|---|---|---|
| Connection | Connection-oriented | Connectionless |
| Reliability | Guaranteed delivery | No guarantee |
| Ordering | Messages arrive in order | May arrive out of order |
| Speed | Slower (handshake + ACKs) | Faster (no overhead) |
| Use cases | HTTP, file transfer, email | Video streaming, DNS, gaming |
| Java socket | `Socket` / `ServerSocket` | `DatagramSocket` / `DatagramPacket` |

---

## Configuration

Default ports can be changed at the top of each file:

| File | Variable | Default |
|------|----------|---------|
| `TCPServer.java` | `port` | `5000` |
| `TCPClient.java` | `port` | `5000` |
| `UDPServer.java` | `port` | `6000` |
| `UDPClient.java` | `port` | `6000` |

---

## Key Java Classes

- **`ServerSocket`** — listens for incoming TCP connections
- **`Socket`** — represents a TCP connection endpoint
- **`DatagramSocket`** — sends and receives UDP packets
- **`DatagramPacket`** — a UDP packet containing data and addressing info
- **`InetAddress`** — represents an IP address

---

## Extending This Project

Ideas for further development:

- **Thread pool** — Replace `new Thread()` per client with `ExecutorService` for better resource management
- **Chat application** — Broadcast messages from one client to all connected clients
- **File transfer** — Stream file bytes over TCP with progress tracking
- **Non-blocking I/O** — Use Java NIO (`Selector`, `Channel`) for high-performance servers
- **Protocol buffer** — Replace plain text with structured serialization (e.g., JSON, Protobuf)

---

## License

MIT License. See `LICENSE` for details.
