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
    System.out.println("Please type 1 word:");
    // Store user input (only 1 word wanted, so we can't use nextLine)
    String yourInput = myScanner.next();
    // Close scanner when done
    myScanner.close();

    FileMapper textR = new FileMapper("src/messages.txt");

    Boolean pop = textR.checkSet(yourInput);
    System.out.println(pop);
    textR.printPairsByValue();
    // Read File to create BiGram

    // Print result 1
    System.out.println("Your next word will be + " + "\n");
  }
}
