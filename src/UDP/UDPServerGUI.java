package UDP;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class UDPServerGUI extends JFrame {
    private JSpinner portSpinner;
    private JButton startButton;
    private JTextArea logArea;
    private DatagramSocket socket;
    private AtomicBoolean running = new AtomicBoolean(false);

    public UDPServerGUI() {
        super("UDP Server GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Top panel with port and start button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Port:"));
        portSpinner = new JSpinner(new SpinnerNumberModel(6000, 1, 65535, 1));
        topPanel.add(portSpinner);
        startButton = new JButton("Start Server");
        startButton.addActionListener(e -> toggleServer());
        topPanel.add(startButton);

        // Log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);

        // Layout
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void toggleServer() {
        if (!running.get()) {
            startServer();
        } else {
            stopServer();
        }
    }

    private void startServer() {
        int port = (Integer) portSpinner.getValue();
        try {
            socket = new DatagramSocket(port);
            running.set(true);
            startButton.setText("Stop Server");
            portSpinner.setEnabled(false);
            logArea.append("UDP Server started on port " + port + "\n");

            // Listen for packets in a separate thread
            new Thread(this::listenForPackets).start();

        } catch (SocketException e) {
            JOptionPane.showMessageDialog(this, "Failed to start server: " + e.getMessage(),
                    "Server Error", JOptionPane.ERROR_MESSAGE);
            stopServer();
        }
    }

    private void listenForPackets() {
        byte[] buffer = new byte[1024];
        while (running.get()) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // blocks until packet arrives

                String received = new String(packet.getData(), 0, packet.getLength());
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                SwingUtilities.invokeLater(() -> 
                    logArea.append("Received from " + clientAddress + ":" + clientPort + ": " + received + "\n"));

                // Send response back to sender
                String response = "Echo: " + received;
                byte[] responseData = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(
                        responseData, responseData.length,
                        clientAddress, clientPort);
                socket.send(responsePacket);

                SwingUtilities.invokeLater(() -> 
                    logArea.append("Sent echo to " + clientAddress + ":" + clientPort + "\n"));

            } catch (IOException e) {
                if (running.get()) {
                    SwingUtilities.invokeLater(() -> 
                        logArea.append("Error: " + e.getMessage() + "\n"));
                }
            }
        }
    }

    private void stopServer() {
        running.set(false);
        startButton.setText("Start Server");
        portSpinner.setEnabled(true);
        logArea.append("UDP Server stopped\n");

        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UDPServerGUI::new);
    }
}
