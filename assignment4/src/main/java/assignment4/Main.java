package assignment4;

/**
 * main class
 *
 * @author xiaochong
 */
public class Main {

  /**
   * the main function
   * @param args the inputs
   */
  public static void main(String[] args){
    run();
  }

  /**
   * The function for running the UI
   */
  public static void run(){
    UserInterface userInterface = new UserInterface();
    userInterface.display();
  }

  /**
   * generate the string of the object
   * @return the string
   */
  @Override
  public String toString() {
    return "Main{}";
  }
}