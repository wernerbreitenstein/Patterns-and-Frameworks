package puf.frisbee.frontend.network;

import puf.frisbee.frontend.model.PlayerPosition;

public class SocketClientFactory {

    private SocketClient socketClient;

    public SocketClient getSocketClient() {
        if (socketClient == null) {
            socketClient = new SocketClient();
        }

        return socketClient;
    }
}
