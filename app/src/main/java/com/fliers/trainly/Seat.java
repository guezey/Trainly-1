/**
* A class for seats in a Wagon
*@author Erkin Aydın
*@version 23-04-2021__v/1.1
*/
public class Seat {

    boolean isEmpty;
    String seatNumber;
    Wagon linkedWagon;

    public Seat( String seatNumber, Wagon linkedWagon) {
        isEmpty = true;
        this.seatNumber = seatNumber;
        this.linkedWagon = linkedWagon;
    }

    public Wagon getLinkedWagon() {
        return linkedWagon;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty( boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}
