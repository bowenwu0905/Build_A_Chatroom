package protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * implement of State
 *
 * @author xiaochong
 */
public class ProtocolImp implements Protocol {

  public final static Map<ProtocolImp,Integer> messageToIdr= new HashMap<>(){{
    put(ProtocolImp.CONNECT_MESSAGE,19);
    put(ProtocolImp.CONNECT_RESPONSE,20);
    put(ProtocolImp.DISCONNECT_MESSAGE,21);
    put(ProtocolImp.QUERY_USERS,22);
    put(ProtocolImp.QUERY_RESPONSE,23);
    put(ProtocolImp.BROADCAST_MESSAGE,24);
    put(ProtocolImp.DIRECT_MESSAGE,25);
    put(ProtocolImp.FAILED_MESSAGE,26);
    put(ProtocolImp.SEND_INSULT,27);
  }};

//TODO: 1. add messange to ID and ID to messange map
//  2. encoding can be break down to piece it will reaturn a list od string: connectMessange(type,Int,Sting)->list<string>
// 3.Return type <[8,int],[]
  @Override
  public String encode(MessageType messageType, String message) {
    switch (messageType) {
      case CONNECT_MESSAGE -> {

      }

      case QUERY_USERS -> {

      }

      case SEND_INSULT -> {

      }
      case DIRECT_MESSAGE -> {

      }
      case FAILED_MESSAGE -> {

      }
      case QUERY_RESPONSE -> {

      }
      case CONNECT_RESPONSE -> {

      }
      case BROADCAST_MESSAGE -> {

      }
      case DISCONNECT_MESSAGE -> {

      }
      case DISCONNECT_RESPONSE -> {

      }
    }
    return null;
  }

  @Override
  public String decode(MessageType messageType, String message) {
    switch (messageType) {
      case CONNECT_MESSAGE -> {

      }

      case QUERY_USERS -> {

      }

      case SEND_INSULT -> {

      }
      case DIRECT_MESSAGE -> {

      }
      case FAILED_MESSAGE -> {

      }
      case QUERY_RESPONSE -> {

      }
      case CONNECT_RESPONSE -> {

      }
      case BROADCAST_MESSAGE -> {

      }
      case DISCONNECT_MESSAGE -> {

      }
      case DISCONNECT_RESPONSE -> {

      }
    }
    return null;
  }

  @Override
  public MessageType getMessageType(String message) {

    return MessageType.CONNECT_RESPONSE;
  }
}