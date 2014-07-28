package jautenim;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class LeWorker implements Runnable {
    int id;
    ServerSocket server;

    public LeWorker(int id, ServerSocket server) {
        this.id = id;
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            processARequest();
        }
    }

    private void processARequest() {
        try {
            // Accepta una conexio i crea lobjecte socket
            Socket socket = server.accept();

            // Llegeix linput
            String input = readAString(socket.getInputStream());

            // Prepara resposta i logala
            String resposta = "Handled from worker " + id + " - " + input;
            System.out.println(resposta);

            // Contesta
            OutputStream os = socket.getOutputStream();
            os.write(resposta.getBytes());

            // Acaba la comunicacio
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readAString(InputStream is) {
        // http://stackoverflow.com/a/5445161/1729742
        Scanner s = new Scanner(is).useDelimiter(new String(new byte[]{0x00}));
        return s.hasNext() ? s.next() : null;
    }
}
