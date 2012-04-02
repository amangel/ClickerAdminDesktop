package com.clicker.admin.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;


public class CommunicationHub {
    
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private String username = "frederis";
    private String password = "testpw";
    private Reconnecter reconnecter;
    private boolean waitingForHeartbeat = false;
    private Timer heartbeatTimer;
    private int heartbeatSeconds = 15;
    private InputManagementThread inputManager;
    
    private static CommunicationHub _instance = null;
    public  static String SEMI_COLON_SEPARATOR  = "`/;";
    public  static String COLON_SEPARATOR       = "`/:";
    public  static String GRAVE_SEPARATOR       = "`/`";
    public  static String AMPERSAND_SEPARATOR   = "`/&";
    public  static String AT_SEPARATOR          = "`/@";
    public  static String COMMA_SEPARATOR       = "`/,";
    
    private CommunicationHub() {}
    
    public static synchronized CommunicationHub getInstance() {
        if(_instance == null) {
            _instance = new CommunicationHub();
        }
        return _instance;
    }
    
    public void setIp(String ip) {
        reconnecter = new Reconnecter(ip);
        new Thread(reconnecter).start();
    }
    
    public void sendMessage(String text) {
        writer.println(text);
        writer.flush();
    }
    
    public void sendQuestion(String question) {
        sendMessage("ClientCommand" + GRAVE_SEPARATOR + question + AMPERSAND_SEPARATOR + AMPERSAND_SEPARATOR +
                "Everyone" + COMMA_SEPARATOR);
    }
    
    public String readMessage() throws IOException {
        return reader.readLine();
    }
    
    public void gotDisconnected() {
        inputManager.stop();
        waitingForHeartbeat = false;
        heartbeatTimer.cancel();      
    }
    
    private class Reconnecter implements Runnable {
        String ip;
        int adminPort = 7700;
        int timeout = 5000;
        boolean isConnected;
        
        public Reconnecter(String ip) {
            this.ip = ip;
            isConnected = false;
        }
        
        public void run() {
            int retryCount = 0;
            while (!isConnected && retryCount < 2) {
                try {               
                    SocketAddress sockaddr = new InetSocketAddress(ip, adminPort);
                    Socket newSocket = new Socket();
                    newSocket.connect(sockaddr, timeout);
                    newSocket.setKeepAlive(true);
                    socket = newSocket;
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new PrintWriter(socket.getOutputStream(), false);
                    sendMessage(username);
                    System.out.println("sent username ");
                    sendMessage(password);
                    System.out.println("sent pw ");
                    isConnected = true;
                    startListening();
                } catch (Exception e) {
                    try {
                        System.out.println("failed to connect, waiting and trying again");
                        retryCount++;
                        Thread.sleep(100);
                    } catch (InterruptedException ie) {}
                }
            }
            //     Log.d("RECONNECT", "Out of reconnection loop");
            //     reconnectingDialog.dismiss();
            if (isConnected) {
                //         Log.d("RECONNECT", "Calling success message");
                System.out.println("connected");
                //startListening();
                //subHandler.sendEmptyMessage(RECONNECT_SUCCESS);     
            } else {
                System.out.println("not connected");
                //          Log.d("RECONNECT", "Calling failed message");
                //subHandler.sendEmptyMessage(RECONNECT_FAILED);
            }
        }
        
    }
    
    public void receivedHeartbeat() {
        waitingForHeartbeat = false;
    }
    
    
    public void startListening() {
        heartbeatTimer = new Timer();
        heartbeatTimer.scheduleAtFixedRate(new HeartbeatTask(), 15000, heartbeatSeconds * 1000);
        inputManager = new InputManagementThread();
    }
    
    
    private class HeartbeatTask extends TimerTask {
        public void run() {
            //Log.d("hb","In heartbeattask");
            if (waitingForHeartbeat) {
                // Log.d("hb","Got no heartbeat response");
                try {
                    socket.close();
                } catch (IOException e) {}
                //gotDisconnected();
            } else {
                waitingForHeartbeat = true;
                //Log.d("hb","Sending heartbeat message");
                sendMessage("AreYouStillThere");
            }
        }
    }

    public void updateGroupsInClassModel(String string) {
        ClassModel.getInstance().setClientsIntoGroups(string);
    }
}
