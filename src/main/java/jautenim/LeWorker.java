package jautenim;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class LeWorker implements Runnable {
    Thread runner;
    ServerSocket server;

    private String readNextString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter(new String(new byte[]{0x00}));
        return s.hasNext() ? s.next() : null;
    }

    public LeWorker(String threadName, ServerSocket server) {
        this.server = server;

        this.runner = new Thread(this, threadName);
        this.runner.start();
    }

    private void processARequest() {
        try {
            // Accepta una conexio i crea l'objecte socket
            Socket socket = server.accept();

            // Llegeix linput
            String input = readNextString(socket.getInputStream());

            String resposta = "Handled from worker " + runner.getName() + " - " + input;

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

    @Override
    public void run() {
        while (true) {
            processARequest();
        }
    }
}
