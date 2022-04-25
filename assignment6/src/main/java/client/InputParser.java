package client;

import util.Command;

public class InputParser {
  private String userName;
  public InputParser(String userName){
    this.userName = userName;

  }

  //TODO: check the Input cannot be Empty.
  public void inputParse(String Input){
    String lowerCaseInput = Input.toLowerCase();
    String firstWord = lowerCaseInput.split(" ",2)[0];
    //logOff
    if(lowerCaseInput.equals(Command.LOG_OFF)){

    }
    //who
    else if (lowerCaseInput.equals(Command.WHO)) {

    }
    else if(lowerCaseInput.equals(Command.HELP)){

    }
    //@user
    else if(firstWord.charAt(0)!=Command.AT_USER && firstWord.length()>1){

    }
    //@insult
    else if(firstWord.charAt(0)!=Command.INSULT_USER && firstWord.length()>1){

    }
    //@all
    else{

    }
  }




}
