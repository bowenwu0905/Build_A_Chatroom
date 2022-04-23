package protocol;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * message state of protocol
 *
 * @author xiaochong
 */
public interface Protocol {


  /**
   * The constant CONNECT_MESSAGE.
   */
  int CONNECT_MESSAGE = 19;
  /**
   * The constant CONNECT_RESPONSE.
   */
  int CONNECT_RESPONSE = 20;
  /**
   * The constant DISCONNECT_MESSAGE.
   */
  int DISCONNECT_MESSAGE = 21;
  /**
   * The constant QUERY_CONNECTED_USERS.
   */
  int QUERY_CONNECTED_USERS = 22;
  /**
   * The constant QUERY_USER_RESPONSE.
   */
  int QUERY_USER_RESPONSE = 23;
  /**
   * The constant BROADCAST_MESSAGE.
   */
  int BROADCAST_MESSAGE = 24;
  /**
   * The constant DIRECT_MESSAGE.
   */
  int DIRECT_MESSAGE = 25;
  /**
   * The constant FAILED_MESSAGE.
   */
  int FAILED_MESSAGE = 26;
  /**
   * The constant SEND_INSULT.
   */
  int SEND_INSULT = 27;

  /**
   * The constant hashMap messageToIdr
   */
  Map<MessageType,Integer> messageToIdr= new HashMap<>(){{
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
  Map<Integer, MessageType> idrToMessage = new HashMap<>(){{
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

  void encode(MessageType messageType, List<String> message, DataOutputStream dataOutputStream) throws IOException;

  List<String> decode(List<byte[]> message);

  MessageType getMessageType(List<byte[]> message);

}
