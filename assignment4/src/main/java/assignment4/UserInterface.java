package assignment4;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;
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
  private Grammar grammar;
  private static final int startIndex = 1;
  private static final String startField = "start";

  /**
   * Constructor for UserInterface
   */
  public UserInterface() {
    jsonReader = new JsonReader();
    grammar = new Grammar();
  }

  /**
   * Generating the absoulte path
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
    try {
      File f = new File(absolutePath("").concat(templateFolder));
      File[] listOfFiles = f.listFiles();
      for (int i = 0; i < listOfFiles.length; i++) {
        if (listOfFiles[i].getName().endsWith(json)) {
          String fileName = listOfFiles[i].getName();
          this.fileDictionary.add(fileName.replaceAll(json, ""));
          this.fileMap.put(i + startIndex, listOfFiles[i].getPath());
        }
      }
    }catch (NullPointerException e){
      e.printStackTrace();
    }
  }

  /**
   * get the file dictionary
   * @return fileDictionary with all json file names
   */
  public List<String> getFileDictionary() {
    return this.fileDictionary;
  }

  /**
   * Generating the files in hashmap format
   * @return FilePathDictionary, with key: index of the file, value: path of the file
   */
  public Map<Integer, String> getFileMap() {
    return this.fileMap;
  }

  /**
   * display the UserInterface
   */
  public void display(){
    Scanner in = new Scanner(System.in);
    setFileDictionary();
    System.out.println("Loading grammars...\n");
    while (true) {
      String line;
      System.out.println("\nThe following grammars are available: ");
      for (int i = 0; i < this.fileDictionary.size(); i++) {
        System.out.println(i + 1 + ". " + this.fileDictionary.get(i));
      }
      System.out.println("\nWhich would you like to use? (q to quit)");
      line = in.nextLine();
      try {
        if (line.equalsIgnoreCase("q")) {
          System.exit(0);
        } else if (Integer.parseInt(line) >= 1
            && Integer.parseInt(line) <= this.fileDictionary.size()) {
          String tmp = "";
          for (int i = 0; i < this.fileDictionary.size(); i++) {
            if (line.equals(Integer.toString(i + 1))) {
              tmp = fileMap.get(i + startIndex);
              System.out.println(grammar.textGenerator(startField, jsonReader.jsonProcess(tmp)) + "\n");
            }
          }

          while(true) {
            System.out.println("\nWould you like another? (y/n)");
            line = in.nextLine();
            if (line.equalsIgnoreCase("y")) {
              System.out.println(grammar.textGenerator(startField, jsonReader.jsonProcess(tmp)));
            }
            else if(line.equalsIgnoreCase("n")) {
              break;
            }
            else{
              try {
                throw new InvalidInputException("Wrong Input!");
              }catch (InvalidInputException ignored){
              }
            }
          }
        } else {
          throw new NumberFormatException("Input is out of range!");
        }
      }
      catch (NumberFormatException e){
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public String toString() {
    return "UserInterface{" +
        "fileDictionary=" + fileDictionary +
        ", fileMap=" + fileMap +
        ", jsonReader=" + jsonReader +
        ", grammar=" + grammar +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserInterface)) {
      return false;
    }
    UserInterface that = (UserInterface) o;
    return Objects.equals(getFileDictionary(), that.getFileDictionary())
        && Objects.equals(getFileMap(), that.getFileMap()) && Objects.equals(
        jsonReader, that.jsonReader) && Objects.equals(grammar, that.grammar);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFileDictionary(), getFileMap(), jsonReader, grammar);
  }
}