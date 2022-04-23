package protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Integer;

/**
 * implement of State
 *
 * @author xiaochong
 */
public class ProtocolImp implements Protocol {


  /**
   *
   * @param message, the message passed in as String
   * @return List<byte[]> generated, the first element is the length of message converted to byteArray,
   * the second element is the message itself converted to byteArray
   * @throws IOException when certain error happens
   */
  public List<byte[]> genByteArray(String message) throws IOException {
    List<byte[]> res = new ArrayList<>();
    Integer msgSize = message.length();
    byte[] size = new byte[1];
    size[0] = msgSize.byteValue();
    res.add(size);
    res.add(message.getBytes(StandardCharsets.UTF_8));
    return res;
  }

  @Override
  public void encode(MessageType messageType, List<String> message, DataOutputStream dataOutputStream) throws IOException {
    switch (messageType) {
      case CONNECT_MESSAGE, QUERY_USERS, FAILED_MESSAGE, DISCONNECT_MESSAGE -> {
        // only pass in userName (one element)
        String userName = message.get(0);
        List<byte[]> encoder = genByteArray(userName);
        return encoder;
      }


      case SEND_INSULT, BROADCAST_MESSAGE -> {
        // pass in sender userName, recipient userName
        String senderUserName = message.get(0);
        String recipientUserName = message.get(1);
        List<byte[]> sender = genByteArray(senderUserName);
        List<byte[]> recipient = genByteArray(recipientUserName);
        sender.addAll(recipient);
        return sender;

      }
      case DIRECT_MESSAGE -> {
        // pass in sender userName, recipient userName and message as String (three elements)
        // return byte array with each element's length and itself converted to byte[] (six elements in total)

        String senderUserName = message.get(0);
        String recipientUserName = message.get(1);
        String msg = message.get(1);
        List<byte[]> sender = genByteArray(senderUserName);
        List<byte[]> recipient = genByteArray(recipientUserName);
        List<byte[]> msgByteArray = genByteArray(msg);
        sender.addAll(recipient);
        sender.addAll(msgByteArray);
        return sender;

      }
      case QUERY_RESPONSE -> {
        // take in all names of users (only name)
        List<byte[]> encoder = new ArrayList<>();
        Integer numberOfUsers = message.size();
        byte[] number = new byte[1];
        number[1] = numberOfUsers.byteValue();
        encoder.add(number);
        for(int i = 0; i < message.size(); i ++){
          List<byte[]> nameToByteArray = genByteArray(message.get(i));
          encoder.addAll(nameToByteArray);
        }
        return encoder;


      }
      case CONNECT_RESPONSE, DISCONNECT_RESPONSE -> {
        // pass in success, message as String (two elements)
        // convert success: String -> Boolean -> byte[]
        // convert message: messageSize -> byte[], String -> byte[]
        List<byte[]> encoder = new ArrayList<>();
        Boolean success = Boolean.parseBoolean(message.get(0));
        byte[] vOut = new byte[]{(byte) (success?1:0)};
        String msg = message.get(1);
        List<byte[]> msgByteArray = genByteArray(msg);
        encoder.add(vOut);
        encoder.addAll(msgByteArray);
        return encoder;

      }
    }
    return null;
  }

  @Override
  public List<String> decode(List<byte[]> message) {
    MessageType messageType = getMessageType(message);
    switch (messageType) {
      case CONNECT_MESSAGE -> {

      }

      case QUERY_USERS -> {

      }

      case SEND_INSULT -> {

      }
      case DIRECT_MESSAGE -> {

      }
      case FAILED_MESSAGE -> {

      }
      case QUERY_RESPONSE -> {

      }
      case CONNECT_RESPONSE -> {

      }
      case BROADCAST_MESSAGE -> {

      }
      case DISCONNECT_MESSAGE -> {

      }
      case DISCONNECT_RESPONSE -> {

      }
    }
    return null;
  }

  @Override
  public MessageType getMessageType(List<byte[]> message) {
    // byte - > number
    // number -> messageType

    int number = ByteBuffer.wrap(message.get(0)).getInt();
    MessageType messageType = idrToMessage.get(number);

    return messageType;
  }
}
