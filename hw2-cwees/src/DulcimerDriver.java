import java.awt.Font;

/**
 * Driver class for the keyboard-based virtual dulcimer.
 * 
 * @author Chris He
 * @version 9/28/2022
 */

public class DulcimerDriver {
   public static void main(String[] args) {
        String bassKeys = "a   s   d   f   g   h   j   k   l   ;   '";       
        String dashes = "--- --- --- --- --- --- --- --- --- --- --- ---";
        String dashess = "--- --- --- --- --- --- --- --- --- --- ---";
        String bassNotes = "G-  A   B   C   D   E   F   G   A+ A#+  C+";
        String treble1Keys="1   2   3   4   5   6   7   8   9   0   -   =   ";
        String treble1Notes="C#  D   E   F#  G  A+  B+  C+  D+  E+  F#+  G+";
        String treble2Keys="q   w   e   r   t   y   u   i   o   p   [   ]   ";
        String treble2Notes="G# A+  B+  C#+ D+  E+  F#+ G+  A++ B++ C++ D++";
        
        StdDraw.setFont(new Font("Monospaced", Font.PLAIN, 12));
        StdDraw.textLeft(0.00, 1.00, "DULCIMER KEY MAPPINGS");
        StdDraw.textLeft(0.00, 0.90, "    keysTreble1:  " + treble1Keys);
        StdDraw.textLeft(0.00, 0.87, "TREBLE1          " + dashes);
        StdDraw.textLeft(0.00, 0.84, "       notes:     " + treble1Notes);
        StdDraw.textLeft(0.00, 0.78, "    keysTreble2:  " + treble2Keys);
        StdDraw.textLeft(0.00, 0.75, "TREBLE2          " + dashes);
        StdDraw.textLeft(0.00, 0.72, "       notes:     " + treble2Notes);
        StdDraw.textLeft(0.00, 0.66, "    keysBass:     " + bassKeys);
        StdDraw.textLeft(0.00, 0.63, "BASS             " + dashess);
        StdDraw.textLeft(0.00, 0.60, "       notes:     " + bassNotes);

        
        String keysBass = bassKeys.replace(" ","");
        String keysTreb1= treble1Keys.replace(" ","");
        String keysTreb2= treble2Keys.replace(" ","");
        
        
        Dulcimer dulc = new Dulcimer(bassNotes, treble1Notes,treble2Notes); //creates dulcimer

        while (true) { 
            if (StdDraw.hasNextKeyTyped()) {
            	char test =StdDraw.nextKeyTyped();
                if(keysBass.indexOf(test)>=0) {
                	dulc.hammerBass(keysBass.indexOf(test));// hammers bass string
                }
                else if(keysTreb1.indexOf(test)>=0) {
                	dulc.hammerTreb1(keysTreb1.indexOf(test)); //hammers treble 1 string
                }
                else if(keysTreb2.indexOf(test)>=0) {
                	dulc.hammerTreb2(keysTreb2.indexOf(test)); //hammers treble 2 string
                }
            }
                
            dulc.play(); 
        }
    }    
}
