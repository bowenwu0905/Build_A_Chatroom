package util;

import java.util.List;

public class PrintLogUtil {
  public static void errorMessage(String userName, String backMessage){
    System.err.println("ERROR: user: "+ userName +" Issue:"+ backMessage +"\n");
  }

  public static void successMessage(String userName, String backMessage){
    System.out.println("Success: user: "+userName+ " message:"+backMessage+"\n");
  }

  public static void queryMessage(String userName,List<String> allUser){
    System.out.println("Success: user: "+userName+ " All online user list:"+String.join(", ", allUser)+"\n");
  }

  public static void oneOnOneMessage(String senderName,String receiverName, String text){
    System.out.println(senderName+" -> "+receiverName+ " :"+text+"\n");
  }

  public static void groupMessage(String senderName,String text){
    System.out.println("From "+senderName+" to everyone:"+text+"\n");
  }

}
