package puf.frisbee.frontend.network;

public class SocketClientFactory {

    private SocketClient socketClient;

    public SocketClient getSocketClient() {
        if (socketClient == null) {
            socketClient = new SocketClient();
        }

        return socketClient;
    }
}
