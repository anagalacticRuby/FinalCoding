package grams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileMapper {

  private final Map<HashSet<String>, Integer> bgrams = new HashMap<>();

  private LinkedHashMap<HashSet<String>, Integer> sortedGrams;
  private final Map<String, Integer> wordCount = new HashMap<>();
  private int totalCount;
  private List<String[]> wordResults = new ArrayList<>();

  public boolean checkSet(String predicate) {

    if (sortedGrams.entrySet().toString().contains(predicate)) {
      /*  for (HashSet<String> wordSet : sortedGrams.keySet()) {
        List<Set<String>> wordList = wordSet.stream().collect(Collectors.toSet());
        double confidence = (double) sortedGrams.get(wordList.get(0)) / sortedGrams.get(wordSet);
      }*/

      for(String[] words: wordResults){
        for(int conclusion = 0; conclusion < totalCount; conclusion++){
          if(words[conclusion] == ){

          }
        }
System.out.println("hi");
      }
      System.out.println(sortedGrams.entrySet());

      return true;
    }
    return false;
  }

  /**
   * Reads a txtFile. First it checks to see if the file path passed in exists If true, then it
   * reads the text line by line and puts it into a usable structure.
   */
  public FileMapper(String location) throws IOException {

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
    List<String> textWords =
        textLines
            .map(line -> line.split("\\s+"))
            .flatMap(Arrays::stream)
            .collect(Collectors.toList());

    // System.out.println(textWords);
    wordResults = (List<String[]>) textWords;
    // Count individual occurrences of every word
    for (int i = 1; i < textWords.size(); i++) {
      wordCount.merge((textWords.get(i)), 1, Integer::sum);
      //Total count of all words
      totalCount = wordCount.values().stream().reduce(0, Integer::sum);
    }
    // System.out.println(wordCount);
    //System.out.println(totalCount);

    // Create bgrams of word pairs
    createBgram(textWords);
    // Sort bgrams by value (highest to lowest)
    sortBgram();
  }


  /** Sort the bgram by value (highest first) using a stream. */
  private void sortBgram() {
    sortedGrams =
        bgrams.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }

  /**
   * Build bgrams for all the words found in the file.
   *
   * @param textWords The list of words retrieved from the file
   */
  private void createBgram(List<String> textWords) {
    /*    String[] allTokens = currentLine.trim().split("\\s+");
    for (String token : allTokens) {
      allWordsByKey.merge(token, 1, Integer::sum);*/
    for (int i = 1; i < textWords.size(); i++) {
      bgrams.merge(
          new HashSet<>(Arrays.asList(textWords.get(i - 1), textWords.get(i))), 1, Integer::sum);
    }
  }

  /** Print Map of word pairs, sorted by value (highest first). */
  protected void printPairsByValue() {
    sortedGrams.forEach((key, value) -> System.out.println(key + ", " + value));
  }

  /**
   * checkFile - checks to ensure the file exists.
   *
   * @return false if file not found, true if found
   */
  private boolean checkFile(String txtfile) {

    if (!Files.exists(Paths.get(txtfile))) {
      System.out.println("File does not exist");
      return false;
    }
    return true;
  }
}
