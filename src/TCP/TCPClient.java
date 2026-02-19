package TCP;
import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 5000;

        try (
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(
                new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to server. Type messages:");
            String line;
            while ((line = userInput.readLine()) != null) {
                out.println(line);                      // send to server
                System.out.println(in.readLine());      // read response
            }
        }
    }
}