package puf.frisbee.frontend.network;

public class SocketClientFactory {
    /**
     * The instance of the socket client.
     */
    private SocketClient socketClient;

    /**
     * Initializes an instance of the socket client if it doesn't exist and
     * returns it.
     *
     * @return the instance of the socket client.
     */
    public SocketClient getSocketClient() {
        if (socketClient == null) {
            socketClient = new SocketClient();
        }

        return socketClient;
    }
}
