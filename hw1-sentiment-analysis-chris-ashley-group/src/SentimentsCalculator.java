/**
 * This class models a SentimentsCalculator that calculates the sentiment score   
 * for individual words as well the sentiment score for a phrase.
 *   @author Chris and Ashley
 *   @version 9/7/2022 
 */

 // import necessary classes
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class SentimentsCalculator {

	private List<WordObject> wordArrayList = new ArrayList <WordObject>(); // initalize ArrayList that stores words as objects along with sentiment score
	
	/**
	 * Creates an object that can be used to determine the sentiments of 
	 * words and phrases based on a set of training data.
	 * @param filename the name of the file that contains the training data
	 * to determine the sentiments of different words
	 */
	public SentimentsCalculator(String filename) {
		String str = "";// where to store the string
		Scanner infile; // creates Scanner object

		try { // try loop to ensure that user input is valid
			infile = new Scanner(new File(filename));
			while(infile.hasNextLine()) { // while unread line exists
				str=infile.nextLine();	//read next line
				
				int reviewScore; // stores review score form file
			
				String[] stringArray = str.split(" "); // separates words in line with " " delimiter
				reviewScore =Integer.parseInt(stringArray[0]);	 // assigns score as int at beginning of line
				
				List<String> cleanedStringArray = new ArrayList <String>(); //cleans stringArray and stores as an arraylist
				for (int i = 1; i<stringArray.length; i++) {
					String temp=stringCleaner(stringArray[i]);
					cleanedStringArray.add(temp);
				}
				
				for(int i=0;i<cleanedStringArray.size();i++) { // goes through every item within cleanedstringarray
					int indexword=indexOfWord(cleanedStringArray.get(i));// gets index of word, if dne it returns -1
					if(indexword==-1) {//if doesn't exist
						WordObject word = new WordObject(cleanedStringArray.get(i), reviewScore); //create new wordObject w/ reviewscore
						wordArrayList.add(word);//add wordObject to wordarraylist						
					}
					else { //if does exist
						wordArrayList.get(indexword).addWordScore(reviewScore);//add reviewscore to the wordObject within wordArrayList
					}
				}
			}
			infile.close(); // close scanner
		}
		catch (java.io.FileNotFoundException e) {
			System.out.println("No such file: " + filename); // catches if file exists or not
		}
	}

	
	/**
	 * Determines the sentiments of the provided text by analyzing the 
	 * sentiments of the individual words. The text can be determined to 
	 * be positive, negative, or neutral/unknown.
	 * @param phrase the word or phrase to determine the sentiments of
	 * @return a positive number if the sentiments are positive, 
	 * 		   a negative number if the sentiments are negative,
	 * 		   zero if the sentiments are neutral or unknown
	 */
	public int sentimentScore(String phrase) {
		int phraseScore = 0;
		String [] phraseStringArray = phrase.split(" "); // separates phrase by delimiter, " ", then stores each word/unit in String array
		List<String> cleanedPhraseStringArray = new ArrayList <String>(); // initialize String Array List
		for (int i = 0; i<phraseStringArray.length; i++) { // iterates through each word/unit in String array
			String temp = stringCleaner(phraseStringArray[i]); // calls stringCleaner method on each word/unit in array
			cleanedPhraseStringArray.add(temp); // adds each "cleaned" word in String Array List
		}	
		for (int i=0; i<cleanedPhraseStringArray.size();i++) { // iterates through String Array List
			phraseScore = sentimentWordScore(cleanedPhraseStringArray.get(i))+phraseScore; // retrieves word score from wordArrayList through sentimentWordScore method and sums overall sentiment score of phrase
		}
			
		return phraseScore; // return sentiment score of phrase
	}

	/**
	 * Cleans a string of all punctuation before and after the characters or numbers. 
	 * Returns an empty string if the string is just punctuation
	 * @param messy uncleaned string
	 * @return messyLower which will be the cleaned string
	 */
	public String stringCleaner(String messy) {
		String messyLower = messy.toLowerCase(); //turns everything to lowercase
		int cleanerCounter= 0; // counter for how many characters to remove at the front/back
		int stringLength=messyLower.length(); // counter for length of string
		if(stringLength==0) {	//breaks method and returns blank string if the length of the string is 0
			return "";
			
		}
		while(Character.isLetter(messyLower.charAt(stringLength-1))==false) { //cleans back end 
															//as long as it isn't a letter , breaks while loop if a letter
			cleanerCounter++; // +1 to count of characters to be removed
			stringLength--; //reduces string length counter
			if(stringLength==0) {	//breaks method and returns blank string if the length of the string is 0
				return "";
				
			}
		}
		messyLower=messyLower.substring(0,messyLower.length()-cleanerCounter); //creates string with cleaned backend
		cleanerCounter=0; //resets how many characters needs to be cleaned, now cleans front part of string
		
		while(Character.isLetter(messyLower.charAt(cleanerCounter))==false) { //cleans front part of string
			cleanerCounter++; //counts how many characters to be removed
		}
		messyLower=messyLower.substring(cleanerCounter,stringLength); //creates final cleaned string
		return messyLower; //returns cleaned string
	}
		
		
	
	/**
	 * Determines the sentiments of the provided word by analyzing the 
	 * frequency that the word appears in a positive, negative or neutral
	 * review. The text can be determined to be positive, negative, 
	 * or neutral/unknown.
	 * @param word the word to determine the sentiments of
	 * @return a positive number if the sentiments are positive, 
	 * 		   a negative number if the sentiments are negative,
	 * 		   zero if the sentiments are neutral or unknown
	 */
	public int sentimentWordScore(String word) { 

		if(word.equals("")) {
			return 0; //returns score of 0 if string is empty
		}
		int indexword=indexOfWord(word);
		if(indexword==-1) { // if word doesn't exist within wordStringArray returns score of 0
			return 0;
		}
		else {
			if(wordArrayList.get(indexword).getScore()>0){
				return 1;} //returns positive wordscore if score of wordObject is positive
			else if(wordArrayList.get(indexword).getScore()<0) {
				return -1; //returns negative wordscore if score of wordobject is negative
			}
			else {
				return 0; //returns 0 if score of WordObject is 0
			}
		}
		

	}
	
	/**
	 * Determines the index of the word within wordArrayList 
	 * by comparing it to the wordobject.getWord(). 
	 * If it does not exist, it returns a negative value
	 * @param word desired to be searched within the wordArrayList
	 * @return the index that the WordObject of word exists at. 
	 * If the object DNE, it returns -1
	 */
	public int indexOfWord(String word) {
	    for(int k=0; k < wordArrayList.size(); k++) { //searches through word arraylist
	        if (word.equals(wordArrayList.get(k).getWord())) {     
	            return k; //returns index where the word WordObject exists
	        }
	    }
	    return -1; //if WordObject word doesn't exist within the wordArrayList
	} 
}	
