package org.acme.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * Examples of a single-threaded blocking client.
 */
public class EchoClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 9999;
        try {
            Socket client = new Socket(host, port);
            PrintWriter request = new PrintWriter(client.getOutputStream(), true);
            BufferedReader response = new BufferedReader(new InputStreamReader(client.getInputStream()));

            List<String> requests = Arrays.asList("hello", "world", "done");

            for(String r : requests) {
                request.println(r);
                System.out.println("echo: " + response.readLine()); // Wait for the server to respond
            }
        } catch (Exception e) {
            System.err.println("Couldn't get I/O for the connection to " + host + ".." + e.toString());
        }
    }
}
