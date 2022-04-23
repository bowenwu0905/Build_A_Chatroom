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
   * The constant CONNECT_MESSAGE.
   */
  public final static int CONNECT_MESSAGE = 19;
  /**
   * The constant CONNECT_RESPONSE.
   */
  public final static int CONNECT_RESPONSE = 20;
  /**
   * The constant DISCONNECT_MESSAGE.
   */
  public final static int DISCONNECT_MESSAGE = 21;
  /**
   * The constant QUERY_CONNECTED_USERS.
   */
  public final static int QUERY_CONNECTED_USERS = 22;
  /**
   * The constant QUERY_USER_RESPONSE.
   */
  public final static int QUERY_USER_RESPONSE = 23;
  /**
   * The constant BROADCAST_MESSAGE.
   */
  public final static int BROADCAST_MESSAGE = 24;
  /**
   * The constant DIRECT_MESSAGE.
   */
  public final static int DIRECT_MESSAGE = 25;
  /**
   * The constant FAILED_MESSAGE.
   */
  public final static int FAILED_MESSAGE = 26;
  /**
   * The constant SEND_INSULT.
   */
  public final static int SEND_INSULT = 27;
  /**
   * The constant emptyString.
   */
  public final static String emptyString = " ";
  /**
   * The constant hashMap messageToIdr
   */
  public final static Map<MessageType,Integer> messageToIdr= new HashMap<>(){{
    put(MessageType.CONNECT_MESSAGE,CONNECT_MESSAGE);
    put(MessageType.CONNECT_RESPONSE,CONNECT_RESPONSE);
    put(MessageType.DISCONNECT_MESSAGE,DISCONNECT_MESSAGE);
    put(MessageType.QUERY_USERS,QUERY_CONNECTED_USERS);
    put(MessageType.QUERY_RESPONSE,QUERY_USER_RESPONSE);
    put(MessageType.BROADCAST_MESSAGE,BROADCAST_MESSAGE);
    put(MessageType.DIRECT_MESSAGE,DIRECT_MESSAGE);
    put(MessageType.FAILED_MESSAGE,FAILED_MESSAGE);
    put(MessageType.SEND_INSULT,SEND_INSULT);
  }};
  /**
   * The constant hashMap idrToMessage
   */
  public final static Map<Integer, MessageType> idrToMessage = new HashMap<>(){{
    put(CONNECT_MESSAGE, MessageType.CONNECT_MESSAGE);
    put(CONNECT_RESPONSE, MessageType.CONNECT_RESPONSE);
    put(DISCONNECT_MESSAGE, MessageType.DISCONNECT_MESSAGE);
    put(QUERY_CONNECTED_USERS, MessageType.QUERY_USERS);
    put(QUERY_USER_RESPONSE, MessageType.QUERY_RESPONSE);
    put(BROADCAST_MESSAGE, MessageType.BROADCAST_MESSAGE);
    put(DIRECT_MESSAGE, MessageType.DIRECT_MESSAGE);
    put(FAILED_MESSAGE, MessageType.FAILED_MESSAGE);
    put(SEND_INSULT, MessageType.SEND_INSULT);
  }
  };

  /**
   *
   * @param msg, the message passed in as String
   * @return byte[] converted
   */
  public byte[] convertStringTobytes(String msg){
    return msg.getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public void encode(MessageType messageType, List<String> message, DataOutputStream dataOutputStream) throws IOException {
    switch (messageType) {
      case CONNECT_MESSAGE -> {
        // only pass in userName (one element)
        int type = messageToIdr.get(MessageType.CONNECT_MESSAGE);
        String userName = message.get(0);
        int userNameLength = userName.length();
        byte[] toBytes = convertStringTobytes(userName);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(userNameLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(toBytes);
      }

      case CONNECT_RESPONSE ->{
        // pass in boolean and message, both as String
        int type = messageToIdr.get(MessageType.CONNECT_RESPONSE);
        String success = message.get(0);
        String msg = message.get(1);
        int msgLength = msg.length();
        byte[] toBytes = convertStringTobytes(msg);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeBoolean(Boolean.parseBoolean(success));
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(msgLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(toBytes);
      }

      case DISCONNECT_MESSAGE -> {
        // pass in userName
        int type = messageToIdr.get(MessageType.DISCONNECT_MESSAGE);
        String userName = message.get(0);
        int userNameLength = userName.length();
        byte[] toBytes = convertStringTobytes(userName);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(userNameLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(toBytes);
      }

      case DISCONNECT_RESPONSE ->{
        int type = messageToIdr.get(MessageType.DISCONNECT_RESPONSE);
        String success = message.get(0);
        String msg = message.get(1);
        int msgLength = msg.length();
        byte[] toBytes = convertStringTobytes(msg);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeBoolean(Boolean.parseBoolean(success));
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(msgLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(toBytes);
      }

      case QUERY_USERS -> {
        // pass in userName
        int type = messageToIdr.get(MessageType.QUERY_USERS);
        String userName = message.get(0);
        int userNameLength = userName.length();
        byte[] toBytes = convertStringTobytes(userName);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(userNameLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(toBytes);
      }

      case QUERY_RESPONSE -> {
        // only pass in all users' name
        int type = messageToIdr.get(MessageType.QUERY_RESPONSE);
        int userNumber = message.size();
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(userNumber);
        for(int i = 0; i < userNumber; i++){
          dataOutputStream.writeChars(emptyString);
          String userName = message.get(i);
          byte[] toBytes = convertStringTobytes(userName);
          int userNameLength = userName.length();
          dataOutputStream.writeInt(userNameLength);
          dataOutputStream.writeChars(emptyString);
          dataOutputStream.write(toBytes);
        }
      }


      case BROADCAST_MESSAGE -> {
        // pass in sender userName, message
        int type = messageToIdr.get(MessageType.BROADCAST_MESSAGE);
        String senderUserName = message.get(0);
        byte[] nameToBytes = convertStringTobytes(senderUserName);
        int userNameLength = senderUserName.length();
        String msg = message.get(1);
        byte[] msgToBytes = convertStringTobytes(msg);
        int msgLength = msg.length();
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(userNameLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(nameToBytes);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(msgLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(msgToBytes);

      }
      case DIRECT_MESSAGE -> {
        // pass in sender userName, recipient userName and message as String (three elements)
        int type = messageToIdr.get(MessageType.DIRECT_MESSAGE);
        String senderUserName = message.get(0);
        byte[] senderToBytes = convertStringTobytes(senderUserName);
        int senderLength = senderUserName.length();
        String recipientUserName = message.get(1);
        byte[] recipientToBytes = convertStringTobytes(recipientUserName);
        int recipientLength = recipientUserName.length();
        String msg = message.get(2);
        int msgLength = msg.length();
        byte[] msgToBytes = convertStringTobytes(msg);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(senderLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(senderToBytes);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(recipientLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(recipientToBytes);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(msgLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(msgToBytes);

      }
      case FAILED_MESSAGE -> {
        int type = messageToIdr.get(MessageType.FAILED_MESSAGE);
        String msg = message.get(0);
        int msgLength = msg.length();
        byte[] toByte = convertStringTobytes(msg);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(msgLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(toByte);

      }
      case SEND_INSULT -> {
        int type = messageToIdr.get(MessageType.SEND_INSULT);
        String senderName = message.get(0);
        int senderLength = senderName.length();
        byte[] senderToByte = convertStringTobytes(senderName);
        String recipientName = message.get(1);
        int recipientLength = recipientName.length();
        byte[] recipientToByte = convertStringTobytes(recipientName);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(senderLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(senderToByte);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.writeInt(recipientLength);
        dataOutputStream.writeChars(emptyString);
        dataOutputStream.write(recipientToByte);
      }
    }
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
