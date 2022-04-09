package assignment4;

import java.security.PrivilegedActionException;

/**
 * handle undefined terminal exception
 *
 * @author xiaochong
 */
public class UndefinedTerminalException extends Exception{

  /**
   * Constructs a new exception with {@code null} as its detail message. The cause is not
   * initialized, and may subsequently be initialized by a call to {@link #initCause}.
   */
  public UndefinedTerminalException() {
  }

  /**
   * Constructs a new exception with the specified detail message.  The cause is not initialized,
   * and may subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *                {@link #getMessage()} method.
   */
  public UndefinedTerminalException(String message) {
    super(message);
  }
}