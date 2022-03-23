package assignment4;

import java.util.HashMap;

/**
 * processing json template file
 *
 * @author xiaochong
 */
public class Template {

  public Template() {
  }

  public String jsonReader(String path) {

    return "";
  }

  public HashMap converter(String jsonFile) {

    return new HashMap();
  }

  public String textGenerator(HashMap jsonMap) throws UndefinedTerminalException {
    if (jsonMap.isEmpty()) {
      throw new UndefinedTerminalException();
    }
    return "";
  }


}