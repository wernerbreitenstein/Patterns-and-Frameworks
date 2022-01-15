package puf.frisbee.frontend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import puf.frisbee.frontend.network.SocketClient;

// TODO: create interface
public class CharacterModel {
    SocketClient socketClient;

    public CharacterModel(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    // send message to socket as soon as own position is moved, so the other client knows
    // TODO: use enums and stuff
    public void moveOwnCharacter(String position) {
        socketClient.sendMessageToServer(position);
    }

    // TODO: use enums and stuff
    public String getOtherCharacterMovement() {
        // TODO: return real values with enums and all, right now we are just running in the
        // TODO: oposite direction of the own character
        if (socketClient.readMessageFromServer() != null && socketClient.readMessageFromServer().equals("left")) {
            return "right";
        }

        if (socketClient.readMessageFromServer() != null && socketClient.readMessageFromServer().equals("right")) {
            return "left";
        }

        return null;

        // TODO: see what we can use here
//        ObjectMapper objectMapper = new ObjectMapper();
//        String playerMotionJSON;
//        try {
//            playerMotionJSON = objectMapper.writeValueAsString(PlayerMotion.LEFT);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            playerMotionJSON = "";
//        }
//        socketClientFactory.getSocketClient(PlayerPosition.LEFT).sendMessageToServer(playerMotionJSON);
//        String msg = socketClientFactory.getSocketClient().readMessageFromServer();
//        System.out.println(msg);
    }
}
