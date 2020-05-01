package grams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Predictor class generates word suggestions, given a list of words.
 *
 * <p>It creates a bi-gram of word pairs found in the list, and then checks to see if a user input
 * word exists before calculating the word suggestions.
 */
public class Predictor {

  private final Map<HashSet<String>, Integer> bgrams = new HashMap<>();
  private LinkedHashMap<HashSet<String>, Integer> sortedGrams;
  public ArrayList<String> wordPredicts = new ArrayList<>();

  /**
   * This constructor will generate word suggestions based on the list of words passed in.
   *
   * @param wordList The list of words to work with
   */
  public Predictor(List<String> wordList) {
    createBgram(wordList);
    sortBgram();
  }

  /**
   * Build bgrams for all the words found in the list.
   *
   * @param wordList The list of words retrieved from the list
   */
  private void createBgram(List<String> wordList) {
    for (int i = 1; i < wordList.size(); i++) {
      bgrams.merge(
          new HashSet<>(Arrays.asList(wordList.get(i - 1), wordList.get(i))), 1, Integer::sum);
    }
  }

  /**
   * Checks to see if the word input to console exists in a set.
   *
   * <p>Then, it will give you the next possible word if there exists one. Otherwise it outputs
   * connector words
   *
   * @param predicate the word input by the user
   */
  public void createPrediction(String predicate) {
    // If the word list doesn't contain the user's input, suggest common connector words
    if (!sortedGrams.entrySet().toString().contains(predicate)) {
      wordPredicts.add("the");
      wordPredicts.add("this");
      wordPredicts.add("of");
    } else {
      // Otherwise, perform analytics on the list to find suggestions.
      // Take our map of sorted bi-grams and filter to only the ones we care about
      Map<HashSet<String>, Integer> resultMap =
          sortedGrams.entrySet().stream()
              .filter(
                  map ->
                      map.getKey().toString().contains("[" + predicate + ",")
                          || map.getKey().toString().contains(predicate + "]"))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      // count up how many times that word appears in all those relevant pairs
      int validTotal = resultMap.values().stream().reduce(0, Integer::sum);
      for (HashSet<String> wordSet : resultMap.keySet()) {
        // Calculate the support for each valid word pair
        double support = (double) resultMap.get(wordSet) / validTotal;
        // Multiply by 100 to get percents
        support *= 100;

        // Used for debugging / data validation
        // System.out.printf("%.3f%n", support);
        if (support > 60) {
          List<String> collect =
              wordSet.stream().filter(cut -> !cut.matches(predicate)).collect(Collectors.toList());
          // If a word has a support over 60%, add it to the list of suggested words
          wordPredicts.addAll(collect);
        }
      }
      // Adding filler words
      wordPredicts.add("the");
      wordPredicts.add("this");
      wordPredicts.add("of");
      // Delete any excess suggestions
      while (wordPredicts.size() > 3) {
        wordPredicts.remove(3);
      }
    }
  }

  /** Print a Map of word pairs, sorted by value (highest first) to console. */
  protected void printPairsByValue() {
    sortedGrams.forEach((key, value) -> System.out.println(key + ", " + value));
  }

  /** Sort the bi-gram by value (highest first) using a stream. */
  private void sortBgram() {
    sortedGrams =
        bgrams.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }
}
