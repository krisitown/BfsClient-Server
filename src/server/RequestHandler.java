package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable {
    private Socket socket;
    private ArgumentParser argumentParser;
    private BfsSolver bfsSolver;

    public RequestHandler(Socket socket) {
        this.socket = socket;
        argumentParser = new ArgumentParser();
        bfsSolver = new BfsSolver();
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            List<String> inputLines = new ArrayList<>();
            String inputLine;
            while (!(inputLine = in.readLine()).isBlank()) {
                inputLines.add(inputLine);
            }
            Arguments arguments = argumentParser.parse(inputLines);
            out.println(String.join(", ", bfsSolver.solve(arguments)));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
