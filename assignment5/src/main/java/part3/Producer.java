package part3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Producer implements Runnable {

  private BlockingQueue<Map<String, String>> buffer;
  private String folderPath;
  private CsvProcessor processor = new CsvProcessor();
  private CountDownLatch latch;

  private static final String CSV = ".csv";
  private Set<String> fileNameSet;

  public Producer(BlockingQueue<Map<String, String>> buffer, String folderPath,
      CountDownLatch latch, Set<String> fileNameSet) {
    this.buffer = buffer;
    this.folderPath = folderPath;
    this.latch = latch;
    this.fileNameSet = fileNameSet;
  }

  public String getFolderPath() {
    return folderPath;
  }

  public void setFolderPath(String folderPath) {
    this.folderPath = folderPath;
  }

  public BlockingQueue<Map<String, String>> getBuffer() {
    return buffer;
  }

  @Override
  public void run() {
    String line;
    try {
      File f = new File(this.processor.absolutePathChange(folderPath));
      File[] listOfFiles = f.listFiles();
      for (int i = 0; i < listOfFiles.length; i++) {
        String currFileName = listOfFiles[i].getName().replaceAll(CSV, "");
        // contains in set is not thread safe
        if (this.fileNameSet.contains(currFileName)) {
          BufferedReader br = null;
          br = new BufferedReader(new FileReader(listOfFiles[i]));
          String[] fieldList = br.readLine().split(CsvProcessor.csvSplit);
          while (true) {
            //When the buffer is full, the producer will stop reading CSV
            if ((line = br.readLine()) != null) {
              Map<String, String> record = this.processor.csvToHashMap(currFileName, line);
              try {
                this.buffer.put(record);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            } else {
              //Break when there is no file, and producer exit
              break;
            }
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    latch.countDown();
  }

}
