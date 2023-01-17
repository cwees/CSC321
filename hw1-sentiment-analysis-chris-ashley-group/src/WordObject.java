/**
 * This class creates a WordObject object that has an int score and a String that stores the word.
 * @author Chris & Ashley
 * @version 9/7/2022
 */
public class WordObject {
	private String wordStr;
	private int score;
	private int frequency;
	private static final int SCORE_OFFSET= -2; //might have to make this public?
	
	/**
	 * Constructs a WordObject object with the word and sentiment score.
	 * @param word that object is being created for
	 * @param score first score of the object
	 */
	public WordObject(String word, int score) {
		this.wordStr = word;
		this.score = score+SCORE_OFFSET;;
		this.frequency++;
	}

	/**
	 * Accessor method for the score of the word
	 * @return the sentiment score of the word
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * The accessor method for word string
	 * @return the word in a string
	 */
	public String getWord() {
		return this.wordStr;
	}
	
	/**
	 * Accessor method for frequency of word
	 * @return the frequency of the word
	 */
	public int getFreq() {
		return this.frequency;
	}
	
	/**
	 * Mutator method that increases the current score by wordScore
	 * @param wordScore amount to increase the stored score by
	 */
	public void addWordScore(int wordScore) {
		this.score=wordScore+this.score+SCORE_OFFSET;
	}
}