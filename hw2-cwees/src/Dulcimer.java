import java.util.ArrayList;


/**
 * Class that models a dulcimer with a base, treble1 and treble2 string.
 * 
 * @author Chris He
 * @version 9/28/2022
 */
public class Dulcimer {
    public ArrayList<DulcimerString> baseStrings; //change to private?
    public ArrayList<DulcimerString> treble1Strings;
    public ArrayList<DulcimerString> treble2Strings;

    /**
     * Constructs a Dulcimer with the bass, treble1 and treble2 strings.
     *   @param bassNotes a String specifying the bass notes, from bottom to top
     *   @param	treble1Notes a string specifying the treble1 notes, from bot to top
     *   @param treble2Notes a string specifying the treble2 notes, from bot to top
     */
    public Dulcimer(String bassNotes, String treble1Notes, String treble2Notes ) {
        this.baseStrings = new ArrayList<DulcimerString>();
        this.treble1Strings = new ArrayList<DulcimerString>();
        this.treble2Strings = new ArrayList<DulcimerString>();
        
        for (String str : bassNotes.split("\\s+")) {
            this.baseStrings.add(new DulcimerString(str));
        } 
        for (String str : treble1Notes.split("\\s+")) {
            this.treble1Strings.add(new DulcimerString(str));
        }
        for (String str : treble2Notes.split("\\s+")) {
            this.treble2Strings.add(new DulcimerString(str));
        }
    }
    
    
    /**
     * Strikes the bass string and sets it to vibrating.
     *   @param stringNum the string number (starting at the bottom with 0)
     */
    public void hammerBass(int stringNum) {
    	if (stringNum >= 0 && stringNum < this.baseStrings.size()) {
            this.baseStrings.get(stringNum).strike();
        }
    }
    
    /**
     * Strikes the treble 1 string and sets it to vibrating.
     *   @param stringNum the string number (starting at the bottom with 0)
     */
    
    public void hammerTreb1(int stringNum) {
    	if (stringNum >= 0 && stringNum < this.treble1Strings.size()) {
            this.treble1Strings.get(stringNum).strike();
        }
    }
    
    /**
     * Strikes the treble 2 string and sets it to vibrating.
     *   @param stringNum the string number (starting at the bottom with 0)
     */
    
    public void hammerTreb2(int stringNum) {
    	if (stringNum >= 0 && stringNum < this.treble2Strings.size()) {
            this.treble2Strings.get(stringNum).strike();
        }
    }

    /**
     * Plays the sounds corresponding to all of the struck strings.
     */
    public void play() {
        double combinedFrequencies = 0.0;
        for (int i = 0; i < this.baseStrings.size(); i++) {
            combinedFrequencies += this.baseStrings.get(i).sample();
        }
        for (int i = 0; i < this.treble1Strings.size(); i++) {
            combinedFrequencies += this.treble1Strings.get(i).sample();
        }
        for (int i = 0; i < this.treble2Strings.size(); i++) {
            combinedFrequencies += this.treble2Strings.get(i).sample();
        }
        StdAudio.play(combinedFrequencies);
    }
}
