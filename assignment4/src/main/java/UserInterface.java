/**
 * user interface clss
 *
 * @author xiaochong
 */
public class UserInterface {

  //private static final String ;

  private Template template;

  public UserInterface() {
    template = new Template();
  }

  public void dictionaryRead() {

  }

  public void display() {
    dictionaryRead();
    while (true) {
      template.jsonReader();
      template.converter();
      template.textGenerator();
    }
  }
}