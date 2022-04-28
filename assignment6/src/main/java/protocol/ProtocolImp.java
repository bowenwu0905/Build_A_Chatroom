package protocol;


import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * implement of State
 *
 * @author Bowen
 */
public class ProtocolImp implements Protocol {


  /**
   * The constant emptyString.
   */
  public final static char space = ' ';

  /**
   * The constant first index.
   */
  public final static Integer first_index = 0;

  /**
   * The constant second index.
   */
  public final static Integer second_index = 1;

  /**
   * The constant third index.
   */
  public final static Integer third_index = 2;

  /**
   * @param msg, the message passed in as String
   * @return byte[] converted
   */
  public byte[] convertStringTobytes(String msg) {
    return msg.getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public void encode(MessageType messageType, List<String> message,
      DataOutputStream dataOutputStream) throws IOException {
    switch (messageType) {
      case CONNECT_MESSAGE -> {
        // only pass in userName (one element)
        int type = messageToIdr.get(MessageType.CONNECT_MESSAGE);
        String userName = message.get(first_index);
        int userNameLength = userName.length();
        byte[] toBytes = convertStringTobytes(userName);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(userNameLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(toBytes);
      }

      case CONNECT_RESPONSE -> {
        // pass in boolean and message, both as String
        int type = messageToIdr.get(MessageType.CONNECT_RESPONSE);
        String success = message.get(first_index);
        String msg = message.get(second_index);
        int msgLength = msg.length();
        byte[] toBytes = convertStringTobytes(msg);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeBoolean(Boolean.parseBoolean(success));
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(msgLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(toBytes);
      }

      case DISCONNECT_MESSAGE -> {
        // pass in userName
        int type = messageToIdr.get(MessageType.DISCONNECT_MESSAGE);
        String userName = message.get(first_index);
        int userNameLength = userName.length();
        byte[] toBytes = convertStringTobytes(userName);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(userNameLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(toBytes);
      }

      case DISCONNECT_RESPONSE -> {
        int type = messageToIdr.get(MessageType.DISCONNECT_RESPONSE);
        String success = message.get(first_index);
        String msg = message.get(second_index);
        int msgLength = msg.length();
        byte[] toBytes = convertStringTobytes(msg);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeBoolean(Boolean.parseBoolean(success));
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(msgLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(toBytes);
      }

      case QUERY_USERS -> {
        // pass in userName
        int type = messageToIdr.get(MessageType.QUERY_USERS);
        String userName = message.get(first_index);
        int userNameLength = userName.length();
        byte[] toBytes = convertStringTobytes(userName);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(userNameLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(toBytes);
      }

      case QUERY_RESPONSE -> {
        // only pass in all users' name
        int type = messageToIdr.get(MessageType.QUERY_RESPONSE);
        int userNumber = message.size();
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(userNumber);
        for (String s : message) {
          dataOutputStream.writeChar(space);
          byte[] toBytes = convertStringTobytes(s);
          int userNameLength = s.length();
          dataOutputStream.writeInt(userNameLength);
          dataOutputStream.writeChar(space);
          dataOutputStream.write(toBytes);
        }
      }

      case BROADCAST_MESSAGE -> {
        // pass in sender userName, message
        int type = messageToIdr.get(MessageType.BROADCAST_MESSAGE);
        String senderUserName = message.get(first_index);
        byte[] nameToBytes = convertStringTobytes(senderUserName);
        int userNameLength = senderUserName.length();
        String msg = message.get(second_index);
        byte[] msgToBytes = convertStringTobytes(msg);
        int msgLength = msg.length();
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(userNameLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(nameToBytes);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(msgLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(msgToBytes);

      }
      case DIRECT_MESSAGE -> {
        // pass in sender userName, recipient userName and message as String (three elements)
        int type = messageToIdr.get(MessageType.DIRECT_MESSAGE);
        String senderUserName = message.get(first_index);
        byte[] senderToBytes = convertStringTobytes(senderUserName);
        int senderLength = senderUserName.length();
        String recipientUserName = message.get(second_index);
        byte[] recipientToBytes = convertStringTobytes(recipientUserName);
        int recipientLength = recipientUserName.length();
        String msg = message.get(third_index);
        int msgLength = msg.length();
        byte[] msgToBytes = convertStringTobytes(msg);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(senderLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(senderToBytes);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(recipientLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(recipientToBytes);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(msgLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(msgToBytes);

      }
      case FAILED_MESSAGE -> {
        int type = messageToIdr.get(MessageType.FAILED_MESSAGE);
        String msg = message.get(first_index);
        int msgLength = msg.length();
        byte[] toByte = convertStringTobytes(msg);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(msgLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(toByte);

      }
      case SEND_INSULT -> {
        int type = messageToIdr.get(MessageType.SEND_INSULT);
        String senderName = message.get(first_index);
        int senderLength = senderName.length();
        byte[] senderToByte = convertStringTobytes(senderName);
        String recipientName = message.get(second_index);
        int recipientLength = recipientName.length();
        byte[] recipientToByte = convertStringTobytes(recipientName);
        dataOutputStream.writeInt(type);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(senderLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(senderToByte);
        dataOutputStream.writeChar(space);
        dataOutputStream.writeInt(recipientLength);
        dataOutputStream.writeChar(space);
        dataOutputStream.write(recipientToByte);
      }

    }
  }


}
