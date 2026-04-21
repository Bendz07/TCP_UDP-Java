package TCP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class TCPClientGUI extends JFrame {
    private JTextField hostField;
    private JSpinner portSpinner;
    private JTextArea messageArea;
    private JTextField inputField;
    private JButton connectButton;
    private JButton sendButton;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected = false;

    public TCPClientGUI() {
        super("TCP Client GUI");
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
        portSpinner = new JSpinner(new SpinnerNumberModel(5000, 1, 65535, 1));
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
        String host = hostField.getText().trim();
        int port = (Integer) portSpinner.getValue();

        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            connected = true;
            connectButton.setText("Disconnect");
            inputField.setEnabled(true);
            sendButton.setEnabled(true);
            hostField.setEnabled(false);
            portSpinner.setEnabled(false);
            messageArea.append("Connected to server at " + host + ":" + port + "\n");

            // Start reader thread
            new Thread(this::readFromServer).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to connect: " + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            disconnect();
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

        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromServer() {
        String line;
        try {
            while (connected && (line = in.readLine()) != null) {
                String finalLine = line;
                SwingUtilities.invokeLater(() -> messageArea.append("Server: " + finalLine + "\n"));
            }
        } catch (IOException e) {
            if (connected) {
                SwingUtilities.invokeLater(() -> {
                    messageArea.append("Connection lost: " + e.getMessage() + "\n");
                    disconnect();
                });
            }
        }
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty() && connected) {
            out.println(message);
            messageArea.append("You: " + message + "\n");
            inputField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TCPClientGUI::new);
    }
}
