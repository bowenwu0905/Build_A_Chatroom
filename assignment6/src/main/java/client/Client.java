package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;



/**
 * Client class. You need to run the main for starting the class. Two threads, for reading the
 * command and sending the command, will start.
 *
 * Note: Client will try to reconnect server when server closed during the phase of login. After login,
 * if the server closed, only error message will be thrown and reconnection will not happen
 *
 * @author Zitao Shen
 */
public class Client {

  private String userName;

  private boolean logOff = false;
  private boolean connectStatus = false;
  private Socket client = null;

  private static final int ARG_LENGTH = 2;
  private final static int READER_NUM = 1;
  private final static int HOSTNAME_ARG_INDEX = 0;
  private final static int PORT_ARG_INDEX = 1;
  private final static int SLEEP_TIME = 3;

  private final static int STREAM_TIME_OUT = 3000;

  /**
   * The constructor
   */
  public Client() {
  }

  /**
   * The start function. Two threads, for reading the command and sending the command, will start.
   *
   * @param args hostname and port
   * @throws IOException the exception when socket has issue
   */
  private void start(String[] args) throws IOException {

    //check if there are enough args
    if (args.length < ARG_LENGTH) {
      System.err.println("Wrong number of args! Usage: Client <server IP> <server port>");
      System.exit(1);
    }

    //Try to connect to server
    Scanner sc = new Scanner(System.in);
    String hostname = args[HOSTNAME_ARG_INDEX];
    int port = Integer.parseInt(args[PORT_ARG_INDEX]);

    try {
      client = new Socket(hostname, port);
    } catch (Exception e) {
      System.err.println("Could not connect to " + hostname + ":" + port + ", has it started?");
      System.exit(1);
    }

    while (!this.logOff) {
      try {
        DataOutputStream toServer;
        //if server closed ahead
        try {
          toServer = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
          System.out.println("Server closed, retry connecting in 3 seconds.");
          TimeUnit.SECONDS.sleep(SLEEP_TIME);
          //Continue to check and connect server if server closed
          try {
            client = new Socket(hostname, port);
          } catch (Exception e2) {
            System.err.println(
                "Could not connect to " + hostname + ":" + port + ", has it started?");
          }
          continue;
        }
        String input = "";
        DataInputStream fromServer = new DataInputStream(client.getInputStream());

        //Ask user to login
        while (!connectStatus) {
          System.out.println(">>> Enter username to login as <username> \n");
          input = sc.nextLine();
          //if user didn't input anything, continue ask
          while (input.trim().equals("")) {
            System.out.println(">>> Input is empty, please ENTER an username as <username> \n");
            input = sc.nextLine();
          }
          System.out.println(">>> Hi," + input.trim());
          //This username is case-sensitive
          this.setUserName(input.trim());
          InputHandler inputHandler = new InputHandler(this.userName, toServer);
          OutputHandler outputHandler = new OutputHandler(this.userName, fromServer);
          inputHandler.connectServer();
          client.setSoTimeout(STREAM_TIME_OUT);
          fromServer.readInt();
          connectStatus = outputHandler.connectStatusResponseHandle();
        }

        //Start thread to receive and send message
        CountDownLatch readLatch = new CountDownLatch(this.READER_NUM);
        new ReaderThread(this, readLatch, client).start();
        new WriterThread(this, readLatch, client).start();
        readLatch.await();
      } catch (SocketTimeoutException e) {
        System.err.println("Timeout error. Server not responding.");
      } catch (Exception e) {
        if (client != null) {
          client.close();
        }
      }
    }
    //LogOut
    System.exit(0);
  }

  /**
   * The main function, you need to run this function to start the client
   *
   * @param args hostname and port
   * @throws IOException throw when stream broke
   */
  public static void main(String[] args) throws IOException {
    Client client = new Client();
    client.start(args);
  }

  /**
   * check the user wants to log off
   *
   * @return boolean
   */
  public boolean isLogOff() {
    return logOff;
  }

  /**
   * set the logoff status for user
   *
   * @param logOff boolean
   */
  public void setLogOff(boolean logOff) {
    this.logOff = logOff;
  }


  /**
   * get the username
   *
   * @return the user's name
   */
  public String getUserName() {
    return userName;
  }

  /**
   * set the username
   *
   * @param userName new user's name
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * check if two objects are equal
   *
   * @param o the other object
   * @return boolean
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Client client = (Client) o;
    return Objects.equals(userName, client.userName);
  }

  /**
   * calculate the hashcode of the object
   *
   * @return the hashcode of object
   */
  @Override
  public int hashCode() {
    return Objects.hash(userName);
  }

  /**
   * to string
   *
   * @return the string representation
   */
  @Override
  public String toString() {
    return "Client{}";
  }
}