package concurrentSolution;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * The class for processing the CSV
 */
public class CsvProcessor {

  public static final String csvSplit = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
  public static final String courseModule = "code_module";
  public static final String coursePresentation = "code_presentation";
  public static final String time = "date";
  public static final String sumClick = "sum_click";
  private static final String removeQuotation = "^\"|\"$";

  /**
   * constructor for the class
   */
  public CsvProcessor() {

  }

  /**
   * transform the csv to Hashmap
   *
   * @param csvLine   the row of csv file
   * @param fieldList the top row of the csv file
   */
  public Map<String, String> csvToHashMap(String csvLine, String[] fieldList) {
    String[] recordInfo = csvLine.split(csvSplit, -1);
    Map<String, String> record = new HashMap<>();
    for (int i = 0; i < fieldList.length; i++) {
      record.put(fieldList[i].replaceAll(removeQuotation, ""),
          recordInfo[i].replaceAll(removeQuotation, ""));
    }
    return record;
  }


  /**
   * Generating the absolute file path
   *
   * @param path the relative file path
   * @return the absolute file path
   */
  public String absolutePathChange(String path) {
    File file = new File(path);
    if (file.isAbsolute()) {
      return path;
    } else {
      String filePath = new File("").getAbsolutePath();
      return filePath.concat("/" + path);
    }
  }


  /**
   * convert the object to string
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "CsvProcessor{}";
  }

}
