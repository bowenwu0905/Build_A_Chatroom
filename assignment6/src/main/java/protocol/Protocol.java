package protocol;

import java.util.List;

/**
 * message state of protocol
 *
 * @author xiaochong
 */
public interface Protocol {

  List<byte[]> encode(MessageType messageType, List<String> message);

  List<String> decode(List<byte[]> message);

  MessageType getMessageType(List<byte[]> message);

}
