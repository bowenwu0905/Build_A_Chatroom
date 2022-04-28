package part3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
/**
 * The producer class. It is for read all the output files of part 2, which is student activities.
 * then, put into the buffer
 */
public class Producer implements Runnable {

  private BlockingQueue<Map<String, String>> buffer;
  private String folderPath;
  private CsvProcessor processor = new CsvProcessor();
  private CountDownLatch latch;

  private static final String CSV = ".csv";
  private Set<String> fileNameSet;
  /**
   * The constructor of the producer
   *
   * @param buffer          the queue for storing data passed to consumers
   * @param folderPath      the path for the output of part2
   * @param latch           the producer's latch
   * @param fileNameSet     Set of course names
   */
  public Producer(BlockingQueue<Map<String, String>> buffer, String folderPath,
      CountDownLatch latch, Set<String> fileNameSet) {
    this.buffer = buffer;
    this.folderPath = folderPath;
    this.latch = latch;
    this.fileNameSet = fileNameSet;
  }
  /**
   * Get the path for the output of part2
   *
   * @return the csv file path
   */
  public String getFolderPath() {
    return folderPath;
  }
  /**
   * Set the path for the output of part2
   *
   * @param folderPath the path for the output of part2
   */
  public void setFolderPath(String folderPath) {
    this.folderPath = folderPath;
  }
  /**
   * Get the buffer
   *
   * @return the buffer shared between producer and consumer
   */
  public BlockingQueue<Map<String, String>> getBuffer() {
    return buffer;
  }

  /**
   * get Set of course names
   * @return fileNameSet Set file name set
   */
  public Set<String> getFileNameSet() {
    return fileNameSet;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Producer)) {
      return false;
    }
    Producer producer = (Producer) o;
    return Objects.equals(getBuffer(), producer.getBuffer()) && Objects.equals(
        getFolderPath(), producer.getFolderPath()) && Objects.equals(processor,
        producer.processor) && Objects.equals(latch, producer.latch)
        && Objects.equals(fileNameSet, producer.fileNameSet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getBuffer(), getFolderPath(), processor, latch, fileNameSet);
  }

  @Override
  public String toString() {
    return "Producer{" +
        "buffer=" + buffer +
        ", folderPath='" + folderPath + '\'' +
        ", processor=" + processor +
        ", latch=" + latch +
        ", fileNameSet=" + fileNameSet +
        '}';
  }
}
