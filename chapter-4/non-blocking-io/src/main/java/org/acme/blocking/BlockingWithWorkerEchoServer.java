package org.acme.blocking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Examples of a multi-threaded blocking server.
 */
public class BlockingWithWorkerEchoServer {

    public static void main(String[] args) throws IOException {
        int port = 9999;
        ExecutorService executors = Executors.newFixedThreadPool(10);

        // Create a server socket
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {

                // Wait for the next connection from a client
                Socket client = server.accept();

                executors.submit(() -> {
                    try {
                        PrintWriter response = new PrintWriter(client.getOutputStream(), true);
                        BufferedReader request = new BufferedReader(
                                new InputStreamReader(client.getInputStream()));

                        String line;
                        while ((line = request.readLine()) != null) {
                            System.out.println(Thread.currentThread().getName() +
                                    " - Server received message from client: " + line);
                            // Echo the request
                            response.println(line);

                            // Add a way to stop the application.
                            if ("done".equalsIgnoreCase(line)) {
                                break;
                            }
                        }
                        client.close();
                    } catch (Exception e) {
                        System.err.println("Couldn't serve I/O: " + e.toString());

                    }
                });
            }
        }
    }
}
