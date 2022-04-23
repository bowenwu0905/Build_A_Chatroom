package util;

public class PrintLogUtil {
  public static void errorMessage(String userName, String backMessage){
    System.err.println("ERROR: user: "+ userName +" Issue:"+ backMessage +"\n");
  }

  public static void successMessage(String userName, String backMessage){
    System.out.println("Success: user: "+userName+ " message:"+backMessage);
  }

}
