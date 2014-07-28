package jautenim;

import java.io.IOException;
import java.net.ServerSocket;

public class MahServer {
    public static void main(String[] args) {
        ServerSocket myServer = null;

        try {
            // Crea el servidor que escoltara a localhost:8888
            myServer = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        int cores = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < cores; i++) {
            Thread t = new Thread(new LeWorker("Worker " + i, myServer));
            t.start();
        }
    }
}
