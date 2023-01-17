/**
 * Represents a Bus for the purpose of geometric calculations.
 * 
 * @author chris he 
 * @version 1
 */
public class Bus
{
    private int numSeats;

    // Dimensions of the passenger compartment (In feet)
    private int length;
    public static final int HEIGHT = 6;
    public static final int WIDTH = 8;
    
    public static final int SEATS_PER_ROW = 4;
    public static final int FEET_PER_ROW = 4;

    public Bus(int numSeats)
    {
        int length;
        this.numSeats = numSeats;
        int numRows = numSeats / 4;
        length = numRows * FEET_PER_ROW;
    }

    public int volumePassengerCompartment() {
        return WIDTH * HEIGHT * length;
    }

    public int getNumSeats()
    {
        return numSeats;
    }
    
    public int getLength()
    {
        return length;
    }
    
    
}