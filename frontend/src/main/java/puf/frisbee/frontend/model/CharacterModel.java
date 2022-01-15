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
        System.out.println("move");
        socketClient.sendMessageToServer(position);
    }

    // TODO: use enums and stuff
    public String getOtherCharacterMovement() {
        return "left";
        // TODO: right now the character is running left until he is out of the window, we need to fix that with
        // TODO: getting the response and return that
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
