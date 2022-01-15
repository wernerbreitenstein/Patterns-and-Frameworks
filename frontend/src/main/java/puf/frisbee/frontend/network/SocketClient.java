package puf.frisbee.frontend.network;

import io.github.cdimascio.dotenv.Dotenv;
import puf.frisbee.frontend.model.PlayerPosition;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public SocketClient(){
        Dotenv dotenv = Dotenv.load();
        String socketIP = dotenv.get("SOCKET_IP");
        int socketPort = Integer.parseInt((dotenv.get("SOCKET_PORT")));

        try {
            this.socket = new Socket(socketIP, socketPort);

            InputStream input = this.socket.getInputStream();
            OutputStream output = this.socket.getOutputStream();

            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(output));
            this.bufferedReader = new BufferedReader(new InputStreamReader(input));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToServer(String message){
        try {
            this.bufferedWriter.write(message + "\n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readMessageFromServer(){
        try {
            if (this.bufferedReader.ready() && this.bufferedReader.readLine() != null) {
                // TODO: get this working, readlLine is blocking
                String message = this.bufferedReader.readLine();
                return this.bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    // TODO: stop connection - super important!! some tear down or so method
}