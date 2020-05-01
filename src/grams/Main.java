package grams;

import java.io.IOException;
import java.util.Scanner;

public class Main {
  /**
   * The main method, which runs the whole program.
   *
   * @param args args
   * @throws IOException if there isn't a file to read from, this is thrown
   */
  public static void main(String[] args) throws IOException {

    // Create scanner for input
    Scanner myScanner = new Scanner(System.in);
    // Ask user for input
    System.out.println(
        "Please type 1 word, and "
            + "I'll try to predict what three words you might say next: (Case sensitive)");
    // Store user input (only 1 word wanted, so we can't use nextLine)
    String yourInput = myScanner.next();
    // Close scanner when done
    myScanner.close();

    // First load the file, and make a list of words contained.
    TextReader textR = new TextReader("src/messages.txt");
    // Next, pass the created list into the Predictor class to perform analytics.
    Predictor mystic = new Predictor(textR.textWords);
    // After the mystic has prepared her mind with word pairs, she reads your mind.
    // She calls upon the createPrediction method to generate word suggestions
    mystic.createPrediction(yourInput);

    // Print results, one by one
    for (int i = 0; i < mystic.wordPredicts.size(); i++) {
      System.out.printf("You next word might be " + "%s.\n", mystic.wordPredicts.get(i));
    }
  }
}
