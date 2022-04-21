package protocol;

/**
 * message type
 *
 * @author xiaochong
 */
public enum MessageType {
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
}
