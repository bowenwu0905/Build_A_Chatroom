package assignment4;

/**
 * The exception for incorrect input
 */
public class InvalidInputException extends Exception{

  /**
   * The function will print out the message
   * @param message the error message
   */
  public InvalidInputException(String message) {
    super(message);
  }
}
