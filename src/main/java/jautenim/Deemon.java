package jautenim;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

import java.io.IOException;
import java.net.ServerSocket;

public class Deemon implements Daemon {

    @Override
    public void init(DaemonContext context) throws DaemonInitException, Exception {
        String[] args = context.getArguments();
    }

    @Override
    public void start() throws Exception {
        ServerSocket myServer = null;

        try {
            // Crea el servidor que escoltara a localhost:8888
            myServer = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
            destroy();
        }

        int cores = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < cores; i++) {
            Thread t = new Thread(new LeWorker(i, myServer));
            t.start();
        }
    }

    @Override
    public void stop() throws Exception {
    }

    @Override
    public void destroy() {
    }
}
