package jautenim;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class LeWorker implements Runnable {
    private Socket socket;

    public LeWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Consegueix el nom del thread
            String name;
            synchronized (this) {
                name = Thread.currentThread().getName();
            }

            // Llegeix linput del socket
            String input = readAString(this.socket.getInputStream());

            // Prepara resposta i logala
            String resposta = "Handled by worker " + name + " - " + input;
            // System.out.println(resposta);

            // Contesta
            this.socket.getOutputStream().write(resposta.getBytes());

            // Acaba la comunicacio
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Request handling fuckup", e);
        }
    }

    private String readAString(InputStream is) {
        // http://stackoverflow.com/a/5445161/1729742
        Scanner s = new Scanner(is).useDelimiter(new String(new byte[]{0x00}));
        return s.hasNext() ? s.next() : null;
    }
}
