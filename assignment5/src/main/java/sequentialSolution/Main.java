package sequentialSolution;

import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException, CsvValidationException {
    String inputPath = args[0].trim();
    System.out.println("Please enter the output directory: ");
    Scanner sc = new Scanner(System.in);
    String outputDir = sc.nextLine();
    Processor processor = new Processor(inputPath);
    Map<String, Map<Integer, Integer>> csvMap = processor.process();
    Generator g = new Generator(inputPath);
    g.generate(csvMap, outputDir);

  }

}
