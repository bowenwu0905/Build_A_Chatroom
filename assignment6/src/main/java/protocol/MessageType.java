package protocol;

/**
 * message type
 *
 * @author xiaochong
 */
public enum MessageType {
  /**
   * The message type of CONNECT_MESSAGE
   */
  CONNECT_MESSAGE,
  /**
   * The message type of CONNECT_RESPONSE
   */
  CONNECT_RESPONSE,
  /**
   * The message type of DISCONNECT_MESSAGE
   */
  DISCONNECT_MESSAGE,
  /**
   * The message type of DISCONNECT_RESPONSE
   */
  DISCONNECT_RESPONSE,
  /**
   * The message type of QUERY_USERS
   */
  QUERY_USERS,
  /**
   * The message type of QUERY_RESPONSE
   */
  QUERY_RESPONSE,
  /**
   * The message type of BROADCAST_MESSAGE
   */
  BROADCAST_MESSAGE,
  /**
   * The message type of DIRECT_MESSAGE
   */
  DIRECT_MESSAGE,
  /**
   * The message type of FAILED_MESSAGE
   */
  FAILED_MESSAGE,
  /**
   * The message type of SEND_INSULT
   */
  SEND_INSULT;
}
