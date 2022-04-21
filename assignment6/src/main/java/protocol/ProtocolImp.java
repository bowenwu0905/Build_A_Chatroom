package protocol;

/**
 * implement of State
 *
 * @author xiaochong
 */
public class ProtocolImp implements Protocol {

  @Override
  public String encode(MessageType messageType, String message) {
    switch (messageType) {
      case CONNECT_MESSAGE:
      case QUERY_USERS:
      case SEND_INSULT:
      case DIRECT_MESSAGE:
      case FAILED_MESSAGE:
      case QUERY_RESPONSE:
      case CONNECT_RESPONSE:
      case BROADCAST_MESSAGE:
      case DISCONNECT_MESSAGE:
      case DISCONNECT_RESPONSE:
    }
    return null;
  }

  @Override
  public String decode(MessageType messageType, String message) {
    switch (messageType) {
      case CONNECT_MESSAGE:
      case QUERY_USERS:
      case SEND_INSULT:
      case DIRECT_MESSAGE:
      case FAILED_MESSAGE:
      case QUERY_RESPONSE:
      case CONNECT_RESPONSE:
      case BROADCAST_MESSAGE:
      case DISCONNECT_MESSAGE:
      case DISCONNECT_RESPONSE:
    }
    return null;
  }

  @Override
  public MessageType getMessageType(String message) {

    return MessageType.CONNECT_RESPONSE;
  }
}
