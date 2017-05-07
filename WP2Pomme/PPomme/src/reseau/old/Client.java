package reseau.old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    // Attributs
    private Socket socket;
    BufferedReader in = null;

    //Constructeur
    public Client(String ipAdversaire, int port) {
        try {
            socket = new Socket(ipAdversaire, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            isConnected = true;
            start_thread_input(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PrintWriter out;

    public static void main(String[] args) {
        // write your code here
        Socket socket;
        BufferedReader in = null;
        //PrintWriter out = null;
        try {
            socket = new Socket(InetAddress.getLocalHost(), 2009);

            isConnected = true;
            //System.out.println("Connected to srv");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message_du_srv = in.readLine();
            System.out.println(message_du_srv);
            out = new PrintWriter(socket.getOutputStream());
            out.println("Hello -client");
            out.flush();

            //start_thread_input(socket);
            start_thread_scanner();
            /*scanner = new Scanner(System.in);
            String line;
            while (isConnected) {
                //System.out.println(in.readLine());

                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    System.out.println("Client : " + line);
                    out.println(line);
                    out.flush();
                }
            }*/

            thread_input.join();
            scanner.close();
            out.close();
            socket.close();
            System.out.println("Socket closed");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Threads
    private static void start_thread_scanner() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                scanner = new Scanner(System.in);
                String line;
                while (isConnected) {
                    if (scanner.hasNextLine()) {
                        line = scanner.nextLine();
                        System.out.println("Client : " + line);
                        out.println(line);
                        out.flush();
                    }
                }
            }
        };

        thread_scanner = new Thread(runnable);
        thread_scanner.start();
    }

    private void start_thread_input(final Socket socket) {
        //String idCarte = "";

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    while (isConnected) {
                        String idCarte = in.readLine();
                        System.out.println("Server : " + idCarte);
                    }
                    in.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread_input = new Thread(runnable);
        thread_input.start();
    }

    private static boolean isConnected = false;
    private static Thread thread_input;
    private static Thread thread_scanner;
    private static Scanner scanner;
}

