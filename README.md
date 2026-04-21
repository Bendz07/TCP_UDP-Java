Here's the result of running `cat -n` on /workspace/README.md:
     1	# Java TCP & UDP Client-Server
     2	
     3	A demonstration of client-server networking in Java using both **TCP** (reliable, connection-oriented) and **UDP** (fast, connectionless) protocols.
     4	
     5	---
     6	
     7	## Project Structure
     8	
     9	```
    10	java-client-server/
    11	├── tcp/
    12	│   ├── TCPServer.java
    13	│   └── TCPClient.java
    14	├── udp/
    15	│   ├── UDPServer.java
    16	│   └── UDPClient.java
    17	└── README.md
    18	```
    19	
    20	---
    21	
    22	## Prerequisites
    23	
    24	- Java JDK 8 or higher
    25	- A terminal / command prompt
    26	
    27	---
    28	
    29	## Getting Started
    30	
    31	### Clone or download the project
    32	
    33	```bash
    34	git clone https://github.com/your-username/java-client-server.git
    35	cd java-client-server
    36	```
    37	
    38	---
    39	
    40	## TCP — Echo Server
    41	
    42	TCP provides a **reliable, ordered, connection-oriented** communication channel. The server accepts a client connection, echoes back every message it receives, and handles multiple clients concurrently using threads.
    43	
    44	### Compile
    45	
    46	```bash
    47	javac tcp/TCPServer.java
    48	javac tcp/TCPClient.java
    49	```
    50	
    51	### Run
    52	
    53	Open two terminals:
    54	
    55	**Terminal 1 — Start the server:**
    56	```bash
    57	java tcp.TCPServer
    58	# TCP Server listening on port 5000
    59	```
    60	
    61	**Terminal 2 — Start the client:**
    62	```bash
    63	java tcp.TCPClient
    64	# Connected to server. Type messages:
    65	Hello
    66	# Echo: Hello
    67	```
    68	
    69	### How it works
    70	
    71	| Step | Description |
    72	|------|-------------|
    73	| 1 | `ServerSocket` binds to port 5000 and blocks on `accept()` |
    74	| 2 | Client creates a `Socket` and connects to the server |
    75	| 3 | Server spawns a new `Thread` per client connection |
    76	| 4 | Client sends messages; server echoes them back |
    77	| 5 | Connection closes when the client disconnects |
    78	
    79	---
    80	
    81	## UDP — Echo Server
    82	
    83	UDP provides a **fast, connectionless** communication channel with no guarantee of delivery or ordering. Each message is an independent `DatagramPacket`.
    84	
    85	### Compile
    86	
    87	```bash
    88	javac udp/UDPServer.java
    89	javac udp/UDPClient.java
    90	```
    91	
    92	### Run
    93	
    94	Open two terminals:
    95	
    96	**Terminal 1 — Start the server:**
    97	```bash
    98	java udp.UDPServer
    99	# UDP Server listening on port 6000
   100	```
   101	
   102	**Terminal 2 — Start the client:**
   103	```bash
   104	java udp.UDPClient
   105	# Type messages:
   106	Hello
   107	# Echo: Hello
   108	```
   109	
   110	### How it works
   111	
   112	| Step | Description |
   113	|------|-------------|
   114	| 1 | Server creates a `DatagramSocket` bound to port 6000 |
   115	| 2 | Client creates an unbound `DatagramSocket` |
   116	| 3 | Client wraps data in a `DatagramPacket` with the server's address and sends it |
   117	| 4 | Server reads the packet, extracts sender address, and sends a response back |
   118	| 5 | No connection is maintained between messages |
   119	
   120	---
   121	
   122	## TCP vs UDP Comparison
   123	
   124	| Feature | TCP | UDP |
   125	|---|---|---|
   126	| Connection | Connection-oriented | Connectionless |
   127	| Reliability | Guaranteed delivery | No guarantee |
   128	| Ordering | Messages arrive in order | May arrive out of order |
   129	| Speed | Slower (handshake + ACKs) | Faster (no overhead) |
   130	| Use cases | HTTP, file transfer, email | Video streaming, DNS, gaming |
   131	| Java socket | `Socket` / `ServerSocket` | `DatagramSocket` / `DatagramPacket` |
   132	
   133	---
   134	
   135	## Configuration
   136	
   137	Default ports can be changed at the top of each file:
   138	
   139	| File | Variable | Default |
   140	|------|----------|---------|
   141	| `TCPServer.java` | `port` | `5000` |
   142	| `TCPClient.java` | `port` | `5000` |
   143	| `UDPServer.java` | `port` | `6000` |
   144	| `UDPClient.java` | `port` | `6000` |
   145	
   146	---
   147	
   148	## Key Java Classes
   149	
   150	- **`ServerSocket`** — listens for incoming TCP connections
   151	- **`Socket`** — represents a TCP connection endpoint
   152	- **`DatagramSocket`** — sends and receives UDP packets
   153	- **`DatagramPacket`** — a UDP packet containing data and addressing info
   154	- **`InetAddress`** — represents an IP address
   155	
   156	---
   157	
   158	## Extending This Project
   159	
   160	Ideas for further development:
   161	
   162	- **Thread pool** — Replace `new Thread()` per client with `ExecutorService` for better resource management
   163	- **Chat application** — Broadcast messages from one client to all connected clients
   164	- **File transfer** — Stream file bytes over TCP with progress tracking
   165	- **Non-blocking I/O** — Use Java NIO (`Selector`, `Channel`) for high-performance servers
   166	- **Protocol buffer** — Replace plain text with structured serialization (e.g., JSON, Protobuf)
   167	
   168	---
   169	
   170	## License
   171	
   172	MIT License. See `LICENSE` for details.
   173	
$ edit /workspace/README.md
edit /workspace/README.md
The file /workspace/README.md has been edited. Here's the result of running `cat -n` on a snippet of /workspace/README.md:
     1	# Java TCP & UDP Client-Server with GUI
     2	
     3	A demonstration of client-server networking in Java using both **TCP** (reliable, connection-oriented) and **UDP** (fast, connectionless) protocols. This project includes both **console-based** and **modern GUI-based** interfaces for interactive testing and learning.
     4	
     5	---
     6	
     7	## Project Structure
     8	
     9	```
    10	java-client-server/
    11	├── tcp/
    12	│   ├── TCPServer.java          # Console-based TCP server
    13	│   ├── TCPClient.java          # Console-based TCP client
    14	│   ├── TCPServerGUI.java       # GUI-based TCP server (NEW)
    15	│   └── TCPClientGUI.java       # GUI-based TCP client (NEW)
    16	├── udp/
    17	│   ├── UDPServer.java          # Console-based UDP server
    18	│   ├── UDPClient.java          # Console-based UDP client
    19	│   ├── UDPServerGUI.java       # GUI-based UDP server (NEW)
    20	│   └── UDPClientGUI.java       # GUI-based UDP client (NEW)
    21	└── README.md
    22	```
    23	
    24	---
    25	
    26	## Prerequisites
    27	
    28	- Java JDK 8 or higher
    29	- A terminal / command prompt
    30	- Graphical display environment (for GUI applications)
    31	
    32	---
    33	
    34	## Getting Started
    35	
    36	### Clone or download the project
    37	
    38	```bash
    39	git clone https://github.com/your-username/java-client-server.git
    40	cd java-client-server
    41	```
    42	
    43	---
    44	
    45	## 🖥️ GUI Applications (NEW!)
    46	
    47	The project now includes graphical user interfaces for easier interaction and testing.
    48	
    49	### Running the GUI Applications
    50	
    51	#### TCP Server GUI
    52	```bash
    53	javac tcp/*.java
    54	java tcp.TCPServerGUI
    55	```
    56	- Configure port number (default: 5000)
    57	- Click "Start Server" to begin listening
    58	- Monitor client connections in real-time
    59	- Supports multiple concurrent clients
    60	
    61	#### TCP Client GUI
    62	```bash
    63	javac tcp/*.java
    64	java tcp.TCPClientGUI
    65	```
    66	- Enter server host and port
    67	- Click "Connect" to establish connection
    68	- Send messages through the text input field
    69	- View server responses in the message area
    70	
    71	#### UDP Server GUI
    72	```bash
    73	javac udp/*.java
    74	java udp.UDPServerGUI
    75	```
    76	- Configure listening port (default: 6000)
    77	- Click "Start Server" to begin receiving packets
    78	- View incoming messages and automatic echo responses
    79	
    80	#### UDP Client GUI
    81	```bash
    82	javac udp/*.java
    83	java udp.UDPClientGUI
    84	```
    85	- Enter server address and port
    86	- Click "Connect" to initialize socket
    87	- Send datagram messages and receive responses
    88	
    89	### GUI Features
    90	
    91	- **Modern Swing Interface**: Clean, intuitive design
    92	- **Real-time Logging**: Live updates for all network events
    93	- **Thread-safe Operations**: Background threads prevent UI freezing
    94	- **Error Handling**: User-friendly error dialogs
    95	- **Resource Management**: Proper cleanup on window close
    96	
    97	---
    98	
    99	## TCP — Echo Server
   100	
   101	TCP provides a **reliable, ordered, connection-oriented** communication channel. The server accepts a client connection, echoes back every message it receives, and handles multiple clients concurrently using threads.
   102	
   103	### Compile
   104	
   105	```bash
   106	javac tcp/TCPServer.java
   107	javac tcp/TCPClient.java
   108	javac tcp/TCPServerGUI.java
   109	javac tcp/TCPClientGUI.java
   110	```
   111	
   112	### Run (Console Version)
   113	
   114	Open two terminals:
   115	
   116	**Terminal 1 — Start the server:**
   117	```bash
   118	java tcp.TCPServer
   119	# TCP Server listening on port 5000
   120	```
   121	
   122	**Terminal 2 — Start the client:**
   123	```bash
   124	java tcp.TCPClient
   125	# Connected to server. Type messages:
   126	Hello
   127	# Echo: Hello
   128	```
   129	
   130	### How it works
   131	
   132	| Step | Description |
   133	|------|-------------|
   134	| 1 | `ServerSocket` binds to port 5000 and blocks on `accept()` |
   135	| 2 | Client creates a `Socket` and connects to the server |
   136	| 3 | Server spawns a new `Thread` per client connection |
   137	| 4 | Client sends messages; server echoes them back |
   138	| 5 | Connection closes when the client disconnects |
   139	
   140	---
   141	
   142	## UDP — Echo Server
   143	
   144	UDP provides a **fast, connectionless** communication channel with no guarantee of delivery or ordering. Each message is an independent `DatagramPacket`.
   145	
   146	### Compile
   147	
   148	```bash
   149	javac udp/UDPServer.java
   150	javac udp/UDPClient.java
   151	javac udp/UDPServerGUI.java
   152	javac udp/UDPClientGUI.java
   153	```
   154	
   155	### Run (Console Version)
   156	
   157	Open two terminals:
   158	
   159	**Terminal 1 — Start the server:**
   160	```bash
   161	java udp.UDPServer
   162	# UDP Server listening on port 6000
   163	```
   164	
   165	**Terminal 2 — Start the client:**
   166	```bash
   167	java udp.UDPClient
   168	# Type messages:
   169	Hello
   170	# Echo: Hello
   171	```
   172	
   173	### How it works
   174	
   175	| Step | Description |
   176	|------|-------------|
   177	| 1 | Server creates a `DatagramSocket` bound to port 6000 |
   178	| 2 | Client creates an unbound `DatagramSocket` |
   179	| 3 | Client wraps data in a `DatagramPacket` with the server's address and sends it |
   180	| 4 | Server reads the packet, extracts sender address, and sends a response back |
   181	| 5 | No connection is maintained between messages |
   182	
   183	---
   184	
   185	## TCP vs UDP Comparison
   186	
   187	| Feature | TCP | UDP |
   188	|---|---|---|
   189	| Connection | Connection-oriented | Connectionless |
   190	| Reliability | Guaranteed delivery | No guarantee |
   191	| Ordering | Messages arrive in order | May arrive out of order |
   192	| Speed | Slower (handshake + ACKs) | Faster (no overhead) |
   193	| Use cases | HTTP, file transfer, email | Video streaming, DNS, gaming |
   194	| Java socket | `Socket` / `ServerSocket` | `DatagramSocket` / `DatagramPacket` |
   195	
   196	---
   197	
   198	## Configuration
   199	
   200	Default ports can be changed at the top of each file or through the GUI:
   201	
   202	| File | Variable | Default |
   203	|------|----------|---------|
   204	| `TCPServer.java` | `port` | `5000` |
   205	| `TCPClient.java` | `port` | `5000` |
   206	| `TCPServerGUI.java` | Configurable via UI | `5000` |
   207	| `TCPClientGUI.java` | Configurable via UI | `5000` |
   208	| `UDPServer.java` | `port` | `6000` |
   209	| `UDPClient.java` | `port` | `6000` |
   210	| `UDPServerGUI.java` | Configurable via UI | `6000` |
   211	| `UDPClientGUI.java` | Configurable via UI | `6000` |
   212	
   213	---
   214	
   215	## Key Java Classes
   216	
   217	- **`ServerSocket`** — listens for incoming TCP connections
   218	- **`Socket`** — represents a TCP connection endpoint
   219	- **`DatagramSocket`** — sends and receives UDP packets
   220	- **`DatagramPacket`** — a UDP packet containing data and addressing info
   221	- **`InetAddress`** — represents an IP address
   222	- **`JFrame`, `JPanel`, `JButton`** — Swing components for GUI (GUI versions)
   223	- **`SwingUtilities`** — ensures thread-safe UI updates (GUI versions)
   224	
   225	---
   226	
   227	## Extending This Project
   228	
   229	Ideas for further development:
   230	
   231	- **Thread pool** — Replace `new Thread()` per client with `ExecutorService` for better resource management
   232	- **Chat application** — Broadcast messages from one client to all connected clients
   233	- **File transfer** — Stream file bytes over TCP with progress tracking
   234	- **Non-blocking I/O** — Use Java NIO (`Selector`, `Channel`) for high-performance servers
   235	- **Protocol buffer** — Replace plain text with structured serialization (e.g., JSON, Protobuf)
   236	- **Enhanced GUI** — Add message history, connection statistics, or dark mode theme
   237	
   238	---
   239	
   240	## Testing Scenarios
   241	
   242	### Scenario 1: Basic TCP Communication
   243	1. Launch `TCPServerGUI` and start the server on port 5000
   244	2. Launch `TCPClientGUI` and connect to `localhost:5000`
   245	3. Send test messages and verify echo responses
   246	
   247	### Scenario 2: Multi-Client TCP
   248	1. Start `TCPServerGUI`
   249	2. Launch multiple `TCPClientGUI` instances
   250	3. Connect all clients to the same server
   251	4. Observe concurrent connection handling
   252	
   253	### Scenario 3: UDP Communication
   254	1. Launch `UDPServerGUI` on port 6000
   255	2. Launch `UDPClientGUI` and connect
   256	3. Send datagram messages and verify responses
   257	
   258	### Scenario 4: Compare Console vs GUI
   259	1. Run console and GUI versions simultaneously
   260	2. Compare functionality and user experience
   261	3. Understand the benefits of each approach
   262	
   263	---
   264	
   265	## Troubleshooting
   266	
   267	### Common Issues
   268	
   269	**"Address already in use" error:**
   270	- Another application is using the specified port
   271	- Solution: Change the port number or stop the conflicting application
   272	
   273	**GUI doesn't appear:**
   274	- Running in headless environment (no display)
   275	- Solution: Use console versions or ensure X11/display is available
   276	
   277	**Connection refused:**
   278	- Server is not running or using different port
   279	- Solution: Verify server is started and port matches
   280	
   281	**Firewall blocking connections:**
   282	- System firewall may block network access
   283	- Solution: Allow Java through firewall or use localhost for testing
   284	
   285	---
   286	
   287	## License
   288	
   289	MIT License. See `LICENSE` for details.
