package sequentialSolution;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * CSVProcessor class of sequential solution, process the existed CVS files and generate a hashMap
 *
 * @author bowen
 */
public class CSVProcessor {
  private final static String courseName = "courses.csv";
  private final static String studentVle = "studentVle.csv";
  String inputPath;
  private Map<String, Map<String, Integer>> res;

  /**
   *
   * @param inputPath, the path taken in as a string
   */
  public CSVProcessor(String inputPath) {
    this.inputPath = inputPath;
    this.res = new HashMap<String, Map<String, Integer>>();
  }

  /**
   *
   * @return inputPath as String
   */
  public String getPath() {
    return inputPath;
  }

  /**
   *
   * @param inputPath, set the inputPath
   */
  public void setPath(String inputPath) {
    this.inputPath = inputPath;
  }

  /**
   *
   * @return CSVMap generated
   */
  public Map<String, Map<String, Integer>> getRes() {
    return res;
  }

  /**
   *
   * @param res, set csvMap
   */
  public void setRes(
      Map<String, Map<String, Integer>> res) {
    this.res = res;
  }

  /**
   *
   * @return the courseFile in certain path
   */
  public File getCourseFile(){
    File dir = new File(inputPath);
    File course = null;
    for(File f: dir.listFiles()){
      if(f.getName().equals(courseName)){
        course = f;
      }}
    return course;
  }

  /**
   *
   * @return the studentFile in certain path
   */
  public File getStudentFile(){
    File dir = new File(inputPath);
    File student = null;
    for(File f: dir.listFiles()){
      if(f.getName().equals(studentVle)){
        student = f;
      }
    }
    return student;
  }

  /**
   *
   * @return Map as the csvMap generated
   * @throws CsvValidationException when the csv is not valid
   * @throws IOException when certain error happens
   */
  public Map<String, Map<String,Integer>> process() throws CsvValidationException, IOException {
    readCourse();
    countCourse();
    return res;
  }

  /**
   * read the course file and generate the key of csvMap
   * @throws IOException when certain error happens
   * @throws CsvValidationException when the csv file is not valid
   */
  public void readCourse() throws IOException, CsvValidationException {
    FileReader fileReader = new FileReader(this.getCourseFile());
    CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
    String[] nextLine;
    while((nextLine = csvReader.readNext()) != null){
      String key = nextLine[0]+"_"+nextLine[1];
      res.put(key, new HashMap<String, Integer>());
    }
  }

  /**
   * count the courseFile and generate the key of the csvMap
   * @throws IOException when certain error happens
   * @throws CsvValidationException when the csv file is not valid
   */
  public void countCourse() throws IOException, CsvValidationException {
    FileReader fileReader = new FileReader(this.getStudentFile());
    CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
    String[] nextLine;
    while((nextLine = csvReader.readNext()) != null){
      String key = nextLine[0] + "_" + nextLine[1];
      Map<String, Integer> record = res.get(key);
      String date = nextLine[4];
      record.put(date, record.getOrDefault(date, 0) + Integer.parseInt(nextLine[5]));
    }
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CSVProcessor)) {
      return false;
    }
    CSVProcessor processor = (CSVProcessor) o;
    return Objects.equals(getPath(), processor.getPath()) && Objects.equals(
        getRes(), processor.getRes());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPath(), getRes());
  }

  @Override
  public String toString() {
    return "CSVProcessor{" +
        "inputPath='" + inputPath + '\'' +
        ", res=" + res +
        '}';
  }
}
