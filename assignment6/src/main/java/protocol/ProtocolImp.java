package protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  @Override
  public List<byte[]> encode(MessageType messageType, List<String> message) {
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

    return MessageType.CONNECT_RESPONSE;
  }
}
