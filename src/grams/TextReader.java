package grams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class will read a file from a given path. And if it exists, a list of words contained in
 * that file will be generated.
 */
public class TextReader {

  public List<String> textWords;

  /**
   * Reads a txtFile. First it checks to see if the file path passed in exists If true, then it
   * reads the text line by line and puts it into a usable structure.
   */
  public TextReader(String location) throws IOException {

    if (checkFile(location)) {
      loadFile(location);
    }
  }

  /**
   * Loads text file into a list of words.
   *
   * <p>Uses a stream to load each line sequentially first
   *
   * @param textFile string containing the location of the file
   */
  protected void loadFile(String textFile) throws IOException {

    // Create a path based on the passed in file location
    Path textPath = Paths.get(textFile);

    // Stream to load file into a single list of every word
    Stream<String> textLines = Files.lines(textPath).filter(line -> !line.isBlank());

    // Split the lines into words, KEEP LETTER CASES
    // Then create a list using the stream
    textWords =
        textLines
            .map(line -> line.split("\\s+"))
            .flatMap(Arrays::stream)
            .collect(Collectors.toList());
  }

  /**
   * checkFile - checks to ensure the file exists.
   *
   * @return false if file not found, true if found
   */
  private boolean checkFile(String txtFile) {

    if (!Files.exists(Paths.get(txtFile))) {
      System.out.println("File does not exist");
      return false;
    }
    return true;
  }
}
