package udpclient;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UDPClient {

    static final int BUFFER_SIZE = 1024;
    static int PORT = 9988;
    static String name;
    static Scanner sc = new Scanner(System.in);
    static DatagramSocket socket;
    
    
    static Map<String, InetAddress> hosts = new HashMap<>();
    
    static void buildHosts() throws UnknownHostException {
        hosts.put("Bruno", Inet4Address.getByName("172.16.2.206"));
        hosts.put("Gilson", Inet4Address.getByName("172.16.2.205"));
        hosts.put("André", Inet4Address.getByName("172.16.2.204"));
    }
    
    private static void chatWith(InetAddress host) throws IOException {
        byte[] sendBuffer = new byte[BUFFER_SIZE];
        
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length);
        String fromClient = null;
        
        System.out.printf("Conexão iniciada com %s", host.getAddress());
        sendMessage("Oi aqui é o Gilson. Alguma questao?", host);
        while(true) {
            fromClient = readConsole("");
            if(fromClient.equalsIgnoreCase("nao")) break;
            
            receiveFromServer();
            sendMessage(fromClient, host);
            
        }
        
        socket.close();
        socket = new DatagramSocket();
    }
    
    private static void receiveFromServer() throws IOException {
         byte[] readBuffer = new byte[BUFFER_SIZE];
         DatagramPacket receivePacket = new DatagramPacket(readBuffer, readBuffer.length);
         socket.receive(receivePacket);
         
         System.out.println("Server: " + new String(receivePacket.getData()));
    }
    
    public static void main(String args[]) throws Exception {
        socket = new DatagramSocket();
        buildHosts();
        
        //String serverName = readConsole("Deseja perguntar algo para alguém?");
        
        while(true) {
            String serverName = readConsole("Deseja perguntar algo para alguém?");
            InetAddress host = hosts.get(serverName);
            if (host == null) throw new RuntimeException("Host não encontrado");
            if (serverName.equalsIgnoreCase("nao")) break;
            chatWith(host);
        }
    }
    
    private static void sendMessage(String message, InetAddress host) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);
        socket.send(sendPacket);
    }
    
    private static String readConsole(String message) {
        System.out.println(message);
        return sc.nextLine();
    }
}
