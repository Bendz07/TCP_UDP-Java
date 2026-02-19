package TCP;

import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("TCP Server listening on port " + port);

			while (true) {
			    Socket clientSocket = serverSocket.accept(); // blocks until client connects
			    System.out.println("Client connected: " + clientSocket.getInetAddress());

			    // Handle client in a new thread
			    new Thread(() -> {
			        try (
			            BufferedReader in = new BufferedReader(
			                new InputStreamReader(clientSocket.getInputStream()));
			            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
			        ) {
			            String message;
			            while ((message = in.readLine()) != null) {
			                System.out.println("Received: " + message);
			                out.println("Echo: " + message); // send response
			            }
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			    }).start();
			}
		}
    }
}