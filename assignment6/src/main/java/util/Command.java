package util;

/**
 * The command class
 */
public class Command {

  /**
   * logoff
   */
  public static final String LOG_OFF = "logoff";

  /**
   * query users
   */
  public static final String WHO = "who";

  /**
   * send user
   */
  public static final char AT_USER = '@';

  /**
   * Group message
   */
  public static final String AT_ALL = "@all";

  /**
   * Send  Insult
   */
  public static final char INSULT_USER = '!';

  /**
   * ask for help
   */
  public static final String HELP = "?";

  /**
   * help menu
   */
  public static final String HELP_MENU =
      """
          usage: Usage
           ?                             open help menu. It should alone on a line
           logoff                        logoff client connection from the server. It should alone
                                         on a line
           who                           Query all the online users. It should alone on a line
           @user                         Send direct message to user. It should list at the start
                                         of the input
           !user                         Send insult message and broadcast. 
           @all                          Send group message to all online users. It should list at
                                         the start of the input
          Note: 1. if there are multiple commands (for @user,!user and @all), only the command at the
                   top of the input will be use
                2. if the user's input doesn't match any command, the program will treat it as "@all"
          """;

  /**
   * The constructor
   */
  public Command() {
  }

  /**
   * to string
   *
   * @return the string format
   */
  @Override
  public String toString() {
    return "Command{}";
  }


}
