package UDP;

import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 6000;

        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(host);
        byte[] buffer = new byte[1024];
        Scanner scanner = new Scanner(System.in);

        System.out.println("Connected to UDP server. Type messages:");

        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();

            // Send packet
            byte[] data = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, port);
            socket.send(sendPacket);

            // Receive response
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);
            System.out.println(new String(receivePacket.getData(), 0, receivePacket.getLength()));
        }

        socket.close();
    }
}