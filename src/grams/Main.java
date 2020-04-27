package grams;

import java.io.IOException;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) throws IOException {


        //Create scanner for input
        Scanner myScanner = new Scanner(System.in);
        //Ask user for input
        System.out.println("Please type 1 word:");
        //Store user input (only 1 word wanted, so we can't use nextLine)
        String yourInput = myScanner.next();
        //Close scanner when done
        myScanner.close();

        txtReader textR = new txtReader("src/messages.txt");

        textR.printPairsByValue();
        //Read File to create BiGram
        //TODO:Put file reader methods in another class



        //Print result 1
    System.out.println("Your next word will be + " + "\n");


    }
}
