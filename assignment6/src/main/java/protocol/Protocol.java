package protocol;

import java.io.IOException;
import java.util.List;

/**
 * message state of protocol
 *
 * @author xiaochong
 */
public interface Protocol {

  List<byte[]> encode(MessageType messageType, List<String> message) throws IOException;

  List<String> decode(List<byte[]> message);

  MessageType getMessageType(List<byte[]> message);

}
