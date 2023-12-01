package chat;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/*
Создать окно клиента чата. Окно должно содержать JtextField для ввода логина, пароля,
IP-адреса сервера, порта подключения к серверу, область ввода сообщений, JTextArea
область просмотра сообщений чата и JButton подключения к серверу и отправки сообщения в чат.
Желательно сразу сгруппировать компоненты, относящиеся к серверу сгруппировать на JPanel
сверху экрана, а компоненты, относящиеся к отправке сообщения – на JPanel снизу
 */
public class Chat extends JFrame {
    private static final int WINDOW_HEIGHT = 700;
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;
    JButton btnSend = new JButton("Отправить.");
    JButton btnConnect = new JButton("Соединиться с сервером");
    JLabel lblLogin = new JLabel("Login:");
    JLabel lblPassword = new JLabel("Password:");
    JLabel lblIP = new JLabel("IP:");
    JLabel lblMessage = new JLabel("Сообщение:");
    JTextField txtFieldLogin = new JTextField(22);
    JTextField txtFieldPassword = new JTextField(22);
    JTextField txtFieldIP = new JTextField(16);
    JTextField txtFieldMessage = new JTextField();
    JTextArea areaMessage = new JTextArea(30, 40);
    JPanel panServer;
    JPanel panMessage;
    JPanel panClient;
    String login;
    String password;
    String IP;
    String message;
    String file = "./chat.txt";

    Server server = new Server();

    public Chat() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("ChatWindow");
        setResizable(false);
        areaMessage.setLineWrap(true);
        areaMessage.setWrapStyleWord(true);
        panServer = new JPanel(new GridLayout(3, 2, 5, 10));
        panServer.add(lblLogin);
        panServer.add(txtFieldLogin);
        panServer.add(lblPassword);
        panServer.add(txtFieldPassword);
        panServer.add(lblIP);
        panServer.add(txtFieldIP);
        panMessage = new JPanel();
        panMessage.add(lblMessage);
        panMessage.add(new JScrollPane(areaMessage));
        panClient = new JPanel(new GridLayout(3, 1, 5, 10));
        panClient.add(txtFieldMessage);
        panClient.add(btnSend);
        panClient.add(btnConnect);

        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.serverStatus) {
                    try {
                        Map<String, String> messages = server.getMessages();
                        StringBuilder data = new StringBuilder();
                        for (Map.Entry<String, String> entry : messages.entrySet()) {
                            data.append(entry.getKey());
                            data.append(": ");
                            data.append(entry.getValue());
                            data.append("\n");
                        }
                        String messagesLine = data.toString();
                        System.out.printf(messagesLine);
                        areaMessage.append(messagesLine);

                    } catch (IOException f) {
                        throw new RuntimeException(f);
                    }
                }else {
                    JOptionPane.showMessageDialog(new JFrame(),"Сервер не доступен");
                }
            }
        });
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        txtFieldMessage.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        add(panServer, BorderLayout.NORTH);
        add(panMessage, BorderLayout.CENTER);
        add(panClient, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void sendMessage() {
        Map<String, String> message = new HashMap<>();
        message.put(txtFieldLogin.getText(), txtFieldMessage.getText());
        server.sendMessage(message);
        areaMessage.append( LocalDateTime.now()+" "+txtFieldLogin.getText() + ": " + txtFieldMessage.getText() + "\n");
    }


}