package protocol;

/**
 * message state of protocol
 *
 * @author xiaochong
 */
public interface Protocol {

  String encode(MessageType messageType, String message);

  String decode(MessageType messageType, String message);

  MessageType getMessageType(String message);

}
