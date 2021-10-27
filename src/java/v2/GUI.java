package v2;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Executable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GUI {
    public JPanel Screen;
    public JTextField Input;
    public JButton sendButton;
    public JTextArea Chat;
    public JButton ConnectButton;
    public JTextField URI_Input;
    public JScrollPane ChatScroll;
    public JTextArea username;
    public static PeerConnectionHandler handler;

    public static Thread speakThread;

    public GUI() {



        Chat.setLineWrap(true);

        Input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getExtendedKeyCode() == 10){
                    handler.handleSend(Input.getText());
                    Input.setText("");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        sendButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                handler.handleSend(Input.getText());
                Input.setText("");
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
        speakThread = new Thread (new FreeTTS(data), "Speak Thread");
        speakThread.start();
        Chat.append("\n" + message);
        Chat.setCaretPosition(Chat.getDocument().getLength());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Client_GUI");

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                speakThread.interrupt();
                handler.handleShutdown();
                FreeTTS.handleShutdown();
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
