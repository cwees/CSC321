/**
 * This driver class initializes an object from SentimentsCalculator class that takes user input 
 * to choose file to train * the program to identify words with positive and negative sentiments. 
 * The user can then input a word or phrase and the * sentiment score of the input is returned.
 *   @author Chris and Ashley
 *   @version 9/7/2022 
 */
import java.util.Scanner;

public class ReviewAnalysisDriver {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in); // creates new Scanner object
		System.out.println("Enter name of file to be evaluated:"); // user prompt
		String filename = in.nextLine(); // captures inputted filename
		SentimentsCalculator sent = new SentimentsCalculator(filename); // creates SentimentsCalculator object
		
		while(true) { // allows user to continue to input phrases until user decides to quit
			System.out.println("Enter phrase to evaluate sentiment:"); // user prompt
			String inputPhrase = in.nextLine(); // captures inputted phrase

			if(inputPhrase.equalsIgnoreCase("quit")) { // user option to break prompt loop
				break; 
			}
			
			int inputPhraseScore = sent.sentimentScore(inputPhrase); // captures sentiment score of inputted phrase
			String str="";
			if(inputPhraseScore>0) { // assigns String positive to positive score
				str="positive";
			}
			else {
				str="negative"; // assigns String negative to negative or 0 score
			}
			System.out.println("The sentiment of the phrase, "+inputPhrase+", is "+str+" with a score of "+inputPhraseScore); // return input phrase, sentiment, and evaluated score
		}
		in.close(); // close program when user breaks loop
	}
}

