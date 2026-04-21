package UDP;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;

public class UDPClientGUI extends JFrame {
    private JTextField hostField;
    private JSpinner portSpinner;
    private JTextArea messageArea;
    private JTextField inputField;
    private JButton connectButton;
    private JButton sendButton;
    private DatagramSocket socket;
    private InetAddress address;
    private boolean connected = false;
    private int port;
    private String host;

    public UDPClientGUI() {
        super("UDP Client GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Create components
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        topPanel.add(new JLabel("Host:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        hostField = new JTextField("localhost", 15);
        topPanel.add(hostField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        topPanel.add(new JLabel("Port:"), gbc);
        gbc.gridx = 3;
        portSpinner = new JSpinner(new SpinnerNumberModel(6000, 1, 65535, 1));
        topPanel.add(portSpinner, gbc);

        gbc.gridx = 4;
        connectButton = new JButton("Connect");
        connectButton.addActionListener(e -> toggleConnection());
        topPanel.add(connectButton, gbc);

        // Message display area
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setPreferredSize(new Dimension(0, 300));

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setEnabled(false);
        inputField.addActionListener(e -> sendMessage());
        sendButton = new JButton("Send");
        sendButton.setEnabled(false);
        sendButton.addActionListener(e -> sendMessage());

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Layout
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void toggleConnection() {
        if (!connected) {
            connect();
        } else {
            disconnect();
        }
    }

    private void connect() {
        host = hostField.getText().trim();
        port = (Integer) portSpinner.getValue();

        try {
            address = InetAddress.getByName(host);
            socket = new DatagramSocket();
            socket.setSoTimeout(5000); // 5 second timeout for receiving

            connected = true;
            connectButton.setText("Disconnect");
            inputField.setEnabled(true);
            sendButton.setEnabled(true);
            hostField.setEnabled(false);
            portSpinner.setEnabled(false);
            messageArea.append("Connected to UDP server at " + host + ":" + port + "\n");

            // Start reader thread
            new Thread(this::readFromServer).start();

        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(this, "Unknown host: " + host,
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(this, "Failed to create socket: " + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void disconnect() {
        connected = false;
        connectButton.setText("Connect");
        inputField.setEnabled(false);
        sendButton.setEnabled(false);
        hostField.setEnabled(true);
        portSpinner.setEnabled(true);
        messageArea.append("Disconnected from server\n");

        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    private void readFromServer() {
        byte[] buffer = new byte[1024];
        while (connected) {
            try {
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                SwingUtilities.invokeLater(() -> messageArea.append("Server: " + response + "\n"));
            } catch (SocketTimeoutException e) {
                // Timeout is expected, just continue listening
            } catch (IOException e) {
                if (connected) {
                    SwingUtilities.invokeLater(() -> {
                        messageArea.append("Error receiving: " + e.getMessage() + "\n");
                        disconnect();
                    });
                }
            }
        }
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty() && connected) {
            try {
                byte[] data = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, port);
                socket.send(sendPacket);
                messageArea.append("You: " + message + "\n");
                inputField.setText("");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to send: " + e.getMessage(),
                        "Send Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UDPClientGUI::new);
    }
}
