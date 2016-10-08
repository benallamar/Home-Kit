package Connection;

/**
 * Created by marouanebenalla on 07/10/2016.
 */
public abstract class Server {
    private Boolean isServerMode = false;
    private int port = 1049;


    public void modeServer() {
        System.out.println(port);
    }

    public void modeClient() {
        System.out.println(port);
    }

    public void run() {
        if (isServerMode)
            modeServer();
        else
            modeClient();
    }
}
