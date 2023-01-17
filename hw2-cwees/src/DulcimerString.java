import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.lang.Math;
/**
 * Class that models a dulcimer string.
 * 
 * @author Chris He
 * @version 9/28/2022
 */
public class DulcimerString {
	private String note;
	private Queue<Double> moosic = new LinkedList<Double>();
	private static final int SAMPLE_RATE= 44100;
	/**
	 * Class Constructor. initializes a queue containing zeroes 
	 * of length N which it calculates using getOffsetFromMiddleC
	 * @param note that will be played by the dulcimer
	 */
	public DulcimerString(String note) {
		this.note=note;
		double test=(22-getOffsetFromMiddleC())/12.0;
		double math = Math.pow(2, test);
		double round = (int)Math.round(math*SAMPLE_RATE/440.0);
		for(int i=1;i<=round;i++) {
			moosic.add(0.0);
		}
	}
	
	/**
	 * accessor method for note
	 * @return returns note that will be played
	 */
	public String getNote() {
		return this.note;
	}
	/**
	 * Calculates and returns the distance between the note and middle C
	 * @return returns the distance from the note to middle C as an integer
	 */
	public int getOffsetFromMiddleC() { //replace with fixed version
		int counter =0;
		int distance =0;
		int plusminus=1;
		if(note.charAt(this.note.length()-1)== '-') { //checks if it goes down an octave
			plusminus=-1;
		}
		while(this.note.charAt(this.note.length()-1-counter)=='+' ||  //updown octive counter
				this.note.charAt(this.note.length()-1-counter)== '-') {
			counter++;
		}

		String newnote=this.note.substring(0,this.note.length()-counter); //new string of cleaned note
		
		ArrayList<String> chromatic = new ArrayList<String>();
		String[] chromaticScale={"A","A#","B","C","C#","D","D#","E","F","F#","G","G#"}; 
		chromatic.addAll(Arrays.asList(chromaticScale));
		distance=chromatic.indexOf(newnote)-3;///tune notes
		
		int var= distance+plusminus*12*counter;
		return var;
			
	}

	
	/**
	 * mutator method that strikes the string.
	 * fills the queue with randomly generated values 
	 * between -.5(inclusive) and .5(exclusive)
	 */
	public void strike() {
		for(int i=0;i<moosic.size();i++) {
			moosic.remove();
			moosic.add(Math.random()-.5);
		}
	}
	/**
	 * Samples the audio from the queue. 
	 * calculates new queue value by averaging the
	 *  the first value in the queue (removed)and the new first value, 
	 *  then multiplying a decay factor constant
	 * @return first removed value, double
	 */
	public double sample() {
		double decay_factor=.996;
		double first=moosic.remove();
		double second=moosic.peek();
		moosic.add((first+second)*decay_factor*.5);
		return first;
	}


}

