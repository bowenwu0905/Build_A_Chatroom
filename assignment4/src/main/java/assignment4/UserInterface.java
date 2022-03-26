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
  private JsonReader jsonReader;

  /**
   * Constructor for UserInterface
   */
  public UserInterface() {
    jsonReader = new JsonReader();
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
  public void setFileDictionary() {
    this.fileDictionary = new ArrayList<>();
    this.fileMap = new HashMap<>();
    File f = new File(absolutePath("").concat(templateFolder));
    File[] listOfFiles = f.listFiles();

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].getName().endsWith(json)) {
        String fileName = listOfFiles[i].getName();
        this.fileDictionary.add(fileName.replaceAll(json, ""));
        this.fileMap.put(i+1, listOfFiles[i].getPath());
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
  public Map<Integer, String> getFileMap() {
    return fileMap;
  }

  /**
   * display the UserInterface
   */
  public void display() throws Exception {
    Scanner in = new Scanner(System.in);
    setFileDictionary();
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
      } else if (Integer.parseInt(line) >= 1 && Integer.parseInt(line) <= this.fileDictionary.size()) {
        String tmp = new String();
        for(int i =0; i< this.fileDictionary.size(); i++){
          if(line.equals(Integer.toString(i+1))){
            tmp = fileMap.get(i + 1);
            System.out.println(jsonReader.jsonProcessor(tmp));
          }
        }
        System.out.println("Would you like another? (y/n)");
        line = in.nextLine();
        while(line.equalsIgnoreCase("y")){
          System.out.println(jsonReader.jsonProcessor(tmp));
          System.out.println("Would you like another? (y/n)");
          line = in.nextLine();
        }
        if(line.equalsIgnoreCase("n")){
          continue;
        }
      } else {
        throw new Exception("input is invalid");
      }
    }
  }

}