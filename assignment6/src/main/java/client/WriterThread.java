package client;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;


public class WriterThread extends Thread{

  private InputHandler inputHandler;
  private Client client;
  private Socket socket;
  private Scanner sc;
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
    while (!socket.isClosed() || this.readerLatch.getCount() > 0) {

      try {
        String line;
        System.out.println(">>> Enter your command \n");
        line = sc.nextLine();
        while (line.trim().equals("")) {
          System.out.println(">>> Input is empty, please ENTER your command");
          line = sc.nextLine();
        }
        this.inputHandler.inputParse(line.trim());
      }catch (IOException e){
        System.err.println("Error writing to server: " + e.getMessage());
      }
    }
  }
}
