package udpserver;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UDPServer {
    
    static int PORT = 9988;
    static DatagramSocket serverSocket;

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        serverSocket = new DatagramSocket(9988);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            
            //chatWith(getClient(receivePacket));
            String sentence = new String(receivePacket.getData()).trim();
            receiveData = new byte[1024];
            System.out.println("RECEBIDO: " + sentence);
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sc.nextLine();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket
                    = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }
    
    private static void chatWith(InetAddress host) {
        do {
            
        } while(true);
    }
    
    private static void sendMessage(String message, InetAddress host) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);
        serverSocket.send(sendPacket);
    }
    
    private static InetAddress getClient(DatagramPacket packet) {
        return packet.getAddress();
    }
}
