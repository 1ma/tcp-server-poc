package jautenim;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

import java.io.IOException;
import java.net.ServerSocket;

public class Deemon implements Daemon {
    private ServerSocket server;
    private MahServer runner;
    private Thread leThread;

    /**
     * Initializes this <code>Daemon</code> instance.
     * <p>
     * This method gets called once the JVM process is created and the
     * <code>Daemon</code> instance is created thru its empty public
     * constructor.
     * </p>
     * <p>
     * Under certain operating systems (typically Unix based operating
     * systems) and if the native invocation framework is configured to do
     * so, this method might be called with <i>super-user</i> privileges.
     * </p>
     * <p>
     * For example, it might be wise to create <code>ServerSocket</code>
     * instances within the scope of this method, and perform all operations
     * requiring <i>super-user</i> privileges in the underlying operating
     * system.
     * </p>
     * <p>
     * Apart from set up and allocation of native resources, this method
     * must not start the actual operation of the <code>Daemon</code> (such
     * as starting threads calling the <code>ServerSocket.accept()</code>
     * method) as this would impose some serious security hazards. The
     * start of operation must be performed in the <code>start()</code>
     * method.
     * </p>
     *
     * @param context A <code>DaemonContext</code> object used to
     *                communicate with the container.
     * @throws org.apache.commons.daemon.DaemonInitException An exception that prevented
     *                                                       initialization where you want to display a nice message to the user,
     *                                                       rather than a stack trace.
     * @throws Exception                                     Any exception preventing a successful
     *                                                       initialization.
     */
    @Override
    public void init(DaemonContext context) throws DaemonInitException, Exception {
        try {
            this.server = new ServerSocket(8888);
        } catch (IOException e) {
            throw new DaemonInitException("Could not bind server to port 8888", e);
        }
    }

    /**
     * Starts the operation of this <code>Daemon</code> instance. This
     * method is to be invoked by the environment after the init()
     * method has been successfully invoked and possibly the security
     * level of the JVM has been dropped. Implementors of this
     * method are free to start any number of threads, but need to
     * return control after having done that to enable invocation of
     * the stop()-method.
     */
    @Override
    public void start() throws Exception {
        // Inicialitza el Runnable
        this.runner = new MahServer(this.server);

        // Crea un thread que executara el runnable
        this.leThread = new Thread(this.runner);

        // I a correr
        this.leThread.start();
    }

    /**
     * Stops the operation of this <code>Daemon</code> instance. Note
     * that the proper place to free any allocated resources such as
     * sockets or file descriptors is in the destroy method, as the
     * container may restart the Daemon by calling start() after
     * stop().
     */
    @Override
    public void stop() throws Exception {
        // Indica al Runnable que ha danar acabant
        this.runner.stopeet();

        // Espera que el thread es mori
        this.leThread.join();
    }

    /**
     * Frees any resources allocated by this daemon such as file
     * descriptors or sockets. This method gets called by the container
     * after stop() has been called, before the JVM exits. The Daemon
     * can not be restarted after this method has been called without a
     * new call to the init() method.
     */
    @Override
    public void destroy() {
    }
}
