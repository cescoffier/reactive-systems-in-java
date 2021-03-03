package org.acme.blocking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Examples of a single-threaded blocking server.
 */
public class BlockingEchoServer {

    public static void main(String[] args) throws IOException {
        int port = 9999;

        // Create a server socket
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {

                // Wait for the next connection from a client
                Socket client = server.accept();

                PrintWriter response = new PrintWriter(client.getOutputStream(), true);
                BufferedReader request = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));

                String line;
                while ((line = request.readLine()) != null) {
                    System.out.println("Server received message from client: " + line);
                    // Echo the request
                    response.println(line);

                    // Add a way to stop the application.
                    if ("done".equalsIgnoreCase(line)) {
                        break;
                    }
                }
                client.close();
            }
        }
    }
}
