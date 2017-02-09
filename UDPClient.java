package udpclient;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UDPClient {

    static String name;
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String args[]) throws Exception {
        String host = Inet4Address.getLocalHost().getHostAddress();
        System.out.println("Por favor, digite seu nome:");
        name = sc.nextLine();
        
        BufferedReader inFromUser
                = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        //String sentence = inFromUser.readLine();
        String sentence = String.format("“Oi, aqui é o %s. Alguma questão? (IP: %s)", name, host);
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9988);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println("DO SERVIDOR:" + modifiedSentence);
        clientSocket.close();
    }
}
