package UDP;

import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        int port = 6000;
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[1024];
        System.out.println("UDP Server listening on port " + port);

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet); // blocks until packet arrives

            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received: " + received);

            // Send response back to sender
            String response = "Echo: " + received;
            byte[] responseData = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(
                responseData, responseData.length,
                packet.getAddress(), packet.getPort()
            );
            socket.send(responsePacket);
        }
    }
}