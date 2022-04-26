package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import util.Command;

public class WriterThread extends Thread{

  private InputHandler inputHandler;
  private Client client;
  private Socket socket;
  private Scanner sc =null;
  private CountDownLatch readerLatch;
  private DataOutputStream toServer;

  public WriterThread(Client client,  CountDownLatch readerLatch, Socket socket)
      throws IOException {
    this.client = client;
    this.readerLatch = readerLatch;
    this.socket = socket;
    this.toServer = new DataOutputStream(this.socket.getOutputStream());
    this.inputHandler = new InputHandler(client.getUserName(), toServer);
    sc = new Scanner(System.in);
  }



  public void run() {
    while (this.readerLatch.getCount() > 0) {

//      while(!this.client.isConnectStatus()){

//        try {
//          String input = "";
//          System.out.println(">>> Enter username to login as <username> \n");
//          input = sc.nextLine();
//
//          //user didn't input anything
//          while (input.trim().equals("")){
//            System.out.println(">>> Input is empty, please ENTER an username as <username> \n");
//            input = sc.nextLine();
//          }
//          System.out.println(">>> Hi," + input.trim());
//          //This username is case seneitive
//          this.client.setUserName(input.trim());
//          this.inputHandler = new InputHandler(client.getUserName(), toServer);
//          this.inputHandler.connectServer();
//          this.socket.setSoTimeout(4000);
//          System.out.println("stats+"+this.client.isConnectStatus());
//        } catch (IOException e) {
//          System.err.println("Error writing to server: " + e.getMessage());
//        }
//      }

      try {
        String line = "";
        System.out.println(">>> Enter your command \n");
        line = sc.nextLine();
        while (line.trim().equals("")) {
          System.out.println(">>> Input is empty, please ENTER your command");
          line = sc.nextLine();
        }
//        if (line.trim().equals(Command.HELP)) {
//          continue;
//        }
        this.inputHandler.inputParse(line.trim());
      }catch (IOException e){
        System.err.println("Error writing to server: " + e.getMessage());
      }
    }
  }
}
