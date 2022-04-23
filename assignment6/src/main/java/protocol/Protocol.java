package protocol;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * message state of protocol
 *
 * @author xiaochong
 */
public interface Protocol {

  void encode(MessageType messageType, List<String> message, DataOutputStream dataOutputStream) throws IOException;

  List<String> decode(List<byte[]> message);

  MessageType getMessageType(List<byte[]> message);

}
