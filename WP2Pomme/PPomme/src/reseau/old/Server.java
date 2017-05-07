package reseau.old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Server implements Runnable{

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Attributs
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static PrintWriter out;
    private static Scanner scanner;
    private static Thread thread_input;
    private static Thread thread_scanner;
    private static boolean isConnected = false;

    //Main
    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(2009);
            socket = serverSocket.accept();

            isConnected = true;

            System.out.println("User connected");

            out = new PrintWriter(socket.getOutputStream());
            out.println("You are connected -Server");
            out.flush();

            start_thread_input();
            start_thread_scanner();

            /*
            scanner = new Scanner(System.in);
            String line;
            while (isConnected) {
                //System.out.println(in.readLine());
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    System.out.println("Server : " + line);
                    out.println(line);
                    out.flush();
                    if (line.equals("end")) {
                        isConnected = false;
                    }
                }
            }*/

            thread_input.join();
            scanner.close();
            serverSocket.close();
            System.out.println("Server Socket closed");
            socket.close();
            System.out.println("Socket closed");

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
                        System.out.println("Server : " + line);
                        out.println(line);
                        out.flush();
                        if (line.equals("end")) {
                            isConnected = false;
                            scanner.close();
                            return;
                        }
                    }
                }
            }
        };

        thread_scanner = new Thread(runnable);
        thread_scanner.start();
    }

    private static void start_thread_input() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (isConnected) {
                    try {
                        String line = in.readLine();
                        System.out.println("Client : " + line);
                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread_input = new Thread(runnable);
        thread_input.start();
    }

    @Override
    public void run() {
        try {
            socket = serverSocket.accept();
            isConnected = true;
            System.out.println("User connected");
            out = new PrintWriter(socket.getOutputStream());
            out.println("You are connected -Server");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        start_thread_input();
        //start_thread_scanner();
    }
}
