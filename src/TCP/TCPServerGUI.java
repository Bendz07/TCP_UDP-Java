package TCP;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TCPServerGUI extends JFrame {
    private JSpinner portSpinner;
    private JButton startButton;
    private JTextArea logArea;
    private ServerSocket serverSocket;
    private AtomicBoolean running = new AtomicBoolean(false);

    public TCPServerGUI() {
        super("TCP Server GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Top panel with port and start button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Port:"));
        portSpinner = new JSpinner(new SpinnerNumberModel(5000, 1, 65535, 1));
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
            serverSocket = new ServerSocket(port);
            running.set(true);
            startButton.setText("Stop Server");
            portSpinner.setEnabled(false);
            logArea.append("TCP Server started on port " + port + "\n");

            // Accept clients in a separate thread
            new Thread(this::acceptClients).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to start server: " + e.getMessage(),
                    "Server Error", JOptionPane.ERROR_MESSAGE);
            stopServer();
        }
    }

    private void acceptClients() {
        while (running.get()) {
            try {
                Socket clientSocket = serverSocket.accept();
                logArea.append("Client connected: " + clientSocket.getInetAddress() + "\n");

                // Handle each client in a separate thread
                new Thread(() -> handleClient(clientSocket)).start();

            } catch (IOException e) {
                if (running.get()) {
                    logArea.append("Error accepting client: " + e.getMessage() + "\n");
                }
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String message;
            while ((message = in.readLine()) != null) {
                String finalMessage = message;
                SwingUtilities.invokeLater(() -> 
                    logArea.append("Received from " + clientSocket.getInetAddress() + ": " + finalMessage + "\n"));
                
                String response = "Echo: " + message;
                out.println(response);
            }

        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> 
                logArea.append("Client disconnected: " + clientSocket.getInetAddress() + "\n"));
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopServer() {
        running.set(false);
        startButton.setText("Start Server");
        portSpinner.setEnabled(true);
        logArea.append("TCP Server stopped\n");

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TCPServerGUI::new);
    }
}
