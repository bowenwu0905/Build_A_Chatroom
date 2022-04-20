package protocol;

/**
 * implement of State
 *
 * @author xiaochong
 */
public enum ProtocolImp implements Protocol {
  CONNECT_MESSAGE,
  CONNECT_RESPONSE,
  DISCONNECT_MESSAGE,
  DISCONNECT_RESPONSE,
  QUERY_USERS,
  QUERY_RESPONSE,
  BROADCAST_MESSAGE,
  DIRECT_MESSAGE,
  FAILED_MESSAGE,
  SEND_INSULT;

  @Override
  public String encode(String message) {
    ProtocolImp messageType = getMessageType(message);
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
  public String decode(String message) {
    ProtocolImp messageType = getMessageType(message);
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

  private ProtocolImp getMessageType(String message) {

    return ProtocolImp.BROADCAST_MESSAGE;
  }
}
