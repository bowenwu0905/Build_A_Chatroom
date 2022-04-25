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
import protocol.ProtocolImp;
import util.Command;

/**
 * client class
 *
 * @author xiaochong,Zitao Shen
 */
public class Client {
  private String userName;
  private Protocol protocol  = new ProtocolImp();

  private InputHandler inputHandler;
  private OutputHandler outputHandler;
  private boolean logOff = false;
  private Scanner sc = null;
  private Socket client = null;

  public Client() {
  }

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

    while (!this.logOff) {
      try {
        DataOutputStream toServer;
        //if server closed ahead
//        try {
          toServer = new DataOutputStream(client.getOutputStream());
//        }catch(Exception e){
//          System.out.println("Server closed, retry connecting in 3 seconds.");
//          TimeUnit.SECONDS.sleep(3);
//          //Continue to check and connect server if server closed
//          try {
//            client = new Socket(hostname, port);
//          }catch(Exception e2){
//            System.err.println("Could not connect to "+hostname+":"+port+ ", has it started?");
//          }
//          continue;
//        }

        boolean connectStatus = false;
        String input = "";
        DataInputStream fromServer = new DataInputStream(client.getInputStream());;
       while(!connectStatus){

          System.out.println("Enter username to login as <username> \n");
          input = sc.nextLine();

          //user didn't input anything
          while (input.trim().equals("")){
            System.out.println("Input is empty, please ENTER an username as <username> \n");
            input = sc.nextLine();
          }
          System.out.println("Hi," + input.trim());
          //This username is case seneitive
          this.setUserName(input.trim());
          this.inputHandler = new InputHandler(this.userName,toServer);


          this.outputHandler = new OutputHandler(this.userName,fromServer);

          this.inputHandler.connectServer();
          client.setSoTimeout(3000);
          fromServer.readInt();
          connectStatus = outputHandler.connectStatusResponseHandle();
        }


        while(true){
          String line;
          System.out.println("Enter your command \n");
          line = sc.nextLine();
          while (input.trim().equals("")){
            System.out.println("Input is empty, please ENTER your command");
            input = sc.nextLine();
          }
          this.inputHandler.inputParse(line.trim());
          if (line.trim().equals(Command.HELP)){
            continue;
          }
          client.setSoTimeout(3000);
          int messageType = fromServer.readInt();
          if(this.protocol.idrToMessage.get(messageType) == MessageType.DISCONNECT_MESSAGE){
            boolean isDisconnect = outputHandler.connectStatusResponseHandle();
            if(isDisconnect){
              this.setLogOff(isDisconnect);
              break;
            }
          }else{
            this.outputHandler.outPuthandle(this.protocol.idrToMessage.get(messageType));
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

  private boolean isLogOff() {
    return logOff;
  }

  private void setLogOff(boolean logOff) {
    this.logOff = logOff;
  }

  public String getUserName() {
    return userName;
  }

  private void setUserName(String userName) {
    this.userName = userName;
  }
}