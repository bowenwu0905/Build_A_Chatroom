package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import protocol.MessageType;
import protocol.Protocol;

/**
 * client class
 *
 * @author xiaochong,Zitao Shen
 */
public class Client {
  private String userName;

  private InputHandler inputPareser;
  private boolean logOff = false;
  Scanner sc = null;
  Socket client = null;

  private void start(String[] args) throws IOException {
    if (args.length<2)  {
      System.err.println("Wrong number of args! Usage: Client <server IP> <server port>");
      System.exit(1);
    }

    //Try to connect to server
    sc = new Scanner(System.in);
    String hostname = args[0];
    int port =  Integer.parseInt(args[1]);

    try {
      client = new Socket(hostname, port);
    }catch(Exception e){
      System.err.println("Could not connect to "+hostname+":"+port+ ", has it started?");
      System.exit(1);
    }

    String serverMessage;
    int i = 0;

    while (!this.logOff) {
      try {
        DataOutputStream toServer = null;
        //if server closed ahead
        try {
          toServer = new DataOutputStream(client.getOutputStream());
        }catch(Exception e){
          System.out.println("Server closed, retry connecting in 3 seconds.");
          TimeUnit.SECONDS.sleep(3);
          //Continue to check and connect server if server closed
          try {
            client = new Socket(hostname, port);
          }catch(Exception e2){
            System.err.println("Could not connect to "+hostname+":"+port+ ", has it started?");
          }
          continue;
        }


        String input = "";
        System.out.println("Enter username to login as <username>");
        input = sc.nextLine();

        //user didn't input anything
        while (input.trim().equals("")){
          System.out.println("Input is empty, please ENTER an username as <username>");
          input = sc.nextLine();
          //TODO: add protocal signature for CONNECT_MESSAGE

        }
        System.out.println("Hi," + input.trim());

        //Send to server
        toServer.writeUTF(input.trim());
        client.setSoTimeout(3000);
        DataInputStream fromServer = new DataInputStream(client.getInputStream());
        serverMessage = fromServer.readUTF();
        //Incloude success or not
//        if (serverMessage.contains("SUCCESS")) LOGGER.info("Server " + serverMessage);
//        else LOGGER.severe("Server " + serverMessage);
        while(true){

          String line = "";
          System.out.println("Enter your command");
          line = sc.nextLine();
          inputPareser.checkInput(line);

        }



        switch(state){
//          case MessageType.CONNECT_RESPONSE -> {
//
//          }
          case MessageType.DISCONNECT_RESPONSE -> {



          }
          case MessageType.QUERY_RESPONSE -> {

          }
          case MessageType.DIRECT_MESSAGE -> {

          }
          case MessageType.BROADCAST_MESSAGE -> {

          }
          case MessageType.FAILED_MESSAGE -> {

          }
          case MessageType.SEND_INSULT -> {

          }
        }



      }catch(SocketTimeoutException e){
        System.err.println("Timeout error. Server not responding.");
      }catch(Exception e){
        if (client != null) client.close();
      }
    }

  }

  public static void main(String[] args) throws IOException{
    Client client = new Client();
    client.start(args);
  }

  public boolean isLogOff() {
    return logOff;
  }

  public void setLogOff(boolean logOff) {
    this.logOff = logOff;
  }
}