package v2;

import javax.swing.*;
import java.awt.event.*;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GUI {
    public JPanel Screen;
    public JTextField Input;
    public JButton sendButton;
    public JTextArea Chat;
    public JButton ConnectButton;
    public JTextField URI_Input;
    private JLabel yourIPIsLabel;
    private JScrollPane ChatScroll;
    public static PeerConnectionHandler handler;

    public GUI() {



        Chat.setLineWrap(true);

        try {
            yourIPIsLabel.setText("Your IP is " + NetworkTools.getPrivateIP().toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        sendButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                handler.handleSend(Input.getText());
            }
        });
        ConnectButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                handler.handleAddConnection(URI_Input.getText());
            }
        });
    }

    public void write(String data){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        String message = format.format(today) + " - " + data;
        Chat.append("\n" + message);
        Chat.setCaretPosition(Chat.getDocument().getLength());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Client_GUI");

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                handler.handleShutdown();
            }
        });

        GUI gui = new GUI();
        frame.setContentPane(gui.Screen);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        handler = new PeerConnectionHandler(gui);
    }
}
