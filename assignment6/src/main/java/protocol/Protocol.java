package protocol;

/**
 * message state of protocol
 *
 * @author xiaochong
 */
public interface Protocol {

  String encode(String message);

  String decode(String message);

}
