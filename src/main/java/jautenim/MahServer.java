package jautenim;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MahServer implements Runnable {
    private ServerSocket server;
    private ExecutorService threadPool;

    public MahServer(ServerSocket server) {
        this.server = server;
        this.threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = this.server.accept();

                this.threadPool.execute(new LeWorker(socket));
            } catch (IOException e) {
                if (this.server.isClosed()) {
                    break;
                } else {
                    throw new RuntimeException("Could not accept client connection", e);
                }
            }
        }

        this.threadPool.shutdown();
    }

    public synchronized void stopeet() {
        try {
            this.server.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not unbind server from port 8888", e);
        }
    }
}
