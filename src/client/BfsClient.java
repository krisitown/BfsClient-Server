package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BfsClient {
    private static final int SERVER_PORT = 4444;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", SERVER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server.");

            System.out.println("Enter message in the following format:");
            System.out.println("Line 1: NUMBER_OF_THREADS");
            System.out.println("Line 2: STARTING_NODE");
            System.out.println("Line 3+: <NODE>, <CHILD_1>, <CHILD_2>, ..., <CHILD_N>");

            String message = scanner.nextLine();
            StringBuffer sb = new StringBuffer();
            while (!message.isBlank()) {
                sb.append(message + '\n');
                message = scanner.nextLine();
            }

            writer.println(sb);
            long time = System.currentTimeMillis();

            String reply = reader.readLine();
            long delta = System.currentTimeMillis() - time;

            System.out.println("Response from server:");
            System.out.println(reply);
            System.out.println("Response time: " + delta + "ms.");
        } catch (IOException e) {
            System.out.println("There is a problem with the network communication");
            e.printStackTrace();
        }
    }
}
