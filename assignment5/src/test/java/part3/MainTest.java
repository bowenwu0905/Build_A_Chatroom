package part3;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author xiaochong
 */
class MainTest {

  @Test
  void main() throws InterruptedException, IOException {
    String[] args = new String[]{"data", "output_part2", "5000"};
    Main.main(args);
    File f = new File("output_part3/activity-threshold.csv");
    BufferedReader br = new BufferedReader(new FileReader(f));
    Assertions.assertNotNull(br.readLine());
  }
}