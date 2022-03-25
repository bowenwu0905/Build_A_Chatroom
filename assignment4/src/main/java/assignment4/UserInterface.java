package assignment4;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

/**
 * user interface class
 *
 * @author bowen
 */
public class UserInterface {
  private static final String json = ".json";
  private static final String templateFolder = "/templates";
  private List<String> fileDictionary;
  private Map<Integer, String> fileMap;
  private Template template;

  /**
   * Constructor for UserInterface
   */
  public UserInterface() {
    template = new Template();
  }

  /**
   * @param path, string
   * @return absolutePath of the path
   */
  public String absolutePath(String path) {
    return new File(path).getAbsolutePath();
  }

  /**
   * Get all the json file and add them to fileDictionary
   */
  public void setfileDictionary() {
    this.fileDictionary = new ArrayList<>();
    this.fileMap = new HashMap<>();
    File f = new File(absolutePath("").concat(templateFolder));
    File[] listofFiles = f.listFiles();

    for (int i = 0; i < listofFiles.length; i++) {
      if (listofFiles[i].getName().endsWith(json)) {
        String fileName = listofFiles[i].getName();
        this.fileDictionary.add(fileName.replaceAll(json, ""));
        this.fileMap.put(i+1, listofFiles[i].getPath());
      }
    }
  }

  /**
   * @return fileDictionary with all json file names
   */
  public List<String> getFileDictionary() {
    return fileDictionary;
  }

  /**
   * @return FilePathDictionary, with key: index of the file, value: path of the file
   */
  public Map<Integer, String> getfileMap() {
    return fileMap;
  }

  /**
   * display the UserInterface
   */
  public void display(){
    Scanner in = new Scanner(System.in);
    setfileDictionary();
    System.out.println("Loading grammars...");

    while (true) {
      String line;
      System.out.println("The following grammars are available: ");
      for (int i = 0; i < this.fileDictionary.size(); i++) {
        System.out.println(Integer.toString(i + 1) + ". " + this.fileDictionary.get(i));
      }
      System.out.println("Which would you like to use? (q to quit)");
      line = in.nextLine();
      if(line.equalsIgnoreCase("q")){
        System.exit(0);
      }
      else{
        for(int i =0; i< this.fileDictionary.size(); i++){
          if(line.equals(Integer.toString(i+1))){
            // TODO: generate the grammar of certain file
          }
        }
        System.out.println("Would you like another? (y/n)");
        line = in.nextLine();
        while(line.equals("y")){
          // TODO: generate the same grammar of certain file
          System.out.println("Would you like another? (y/n)");
          line = in.nextLine();
        }
        if(line.equals("n")){
          continue;
        }
      }


      //template.jsonReader();
      //template.converter();
      //template.textGenerator();
    }
  }

}