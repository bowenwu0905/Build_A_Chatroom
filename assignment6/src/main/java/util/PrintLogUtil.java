package util;

import java.util.List;

/**
 * The class for format the terminal output
 */
public class PrintLogUtil {


  /**
   * The constructor
   */
  public PrintLogUtil() {
  }

  /**
   * Generate the error message
   *
   * @param userName    the name of client
   * @param backMessage the message received
   */
  public static void errorMessage(String userName, String backMessage) {
    System.err.println("ERROR: user: " + userName + " Issue:" + backMessage + "\n");
  }

  /**
   * Generate the success message
   *
   * @param userName    the name of client
   * @param backMessage the received message
   */
  public static void successMessage(String userName, String backMessage) {
    System.out.println("Success: user: " + userName + " message:" + backMessage + "\n");
  }

  /**
   * Generate the query message
   *
   * @param userName the name of client
   * @param allUser  the list of online users
   */
  public static void queryMessage(String userName, List<String> allUser) {
    System.out.println(
        "Success: user: " + userName + " All online user list:" + String.join(", ", allUser)
            + "\n");
  }

  /**
   * Generate the one to one message
   *
   * @param senderName   the name of client
   * @param receiverName the name of receiver
   * @param text         the message
   */
  public static void oneOnOneMessage(String senderName, String receiverName, String text) {
    System.out.println(senderName + " -> " + receiverName + " :" + text + "\n");
  }


  /**
   * to string
   *
   * @return the string format
   */
  @Override
  public String toString() {
    return "PrintLogUtil{}";
  }
}
