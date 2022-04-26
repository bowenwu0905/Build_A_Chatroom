package client;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import util.Command;

public class WriterThread extends Thread{

  private InputHandler inputHandler;
  private Scanner sc =null;
  private CountDownLatch readerLatch;

  public WriterThread(InputHandler inputHandler, CountDownLatch readerLatch){
    this.inputHandler = inputHandler;
    this.readerLatch = readerLatch;
    sc = new Scanner(System.in);
  }



  public void run() {
    while (this.readerLatch.getCount() > 0) {
      try {
        String line = "";
        System.out.println(">>> Enter your command \n");
        line = sc.nextLine();
        while (line.trim().equals("")) {
          System.out.println(">>> Input is empty, please ENTER your command");
          line = sc.nextLine();
        }
        if (line.trim().equals(Command.HELP)) {
          continue;
        }
        this.inputHandler.inputParse(line.trim());
      }catch (IOException e){
        System.err.println("Error writing to server: " + e.getMessage());
      }
    }
  }
}
