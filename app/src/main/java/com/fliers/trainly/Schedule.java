import java.util.Date;
import java.util.ArrayList;

/**
 * Schedule Object 
 * @author Cengizhan Terzioğlu
 * @version 22.04.2021
*/
public class Schedule {
    
    // Properties
    private Date departureDate;
    private Date arrivalDate;
    private ArrayList<Wagon> wagons;
    private Train linkedTrain;

    // Constructors
    public Schedule( Date departureDate, Date arrivalDate, int business, int economy ) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        createWagons( business, economy );
    }

    // Methods
    public Date getDepartureDate() {
        return this.departureDate;
    }

    public Date getArrivalDate() {
        return this.arrivalDate;
    }

    public ArrayList<Wagon> getWagons() {
        return this.wagons;
    }

    public Wagon getWagon( int wagonIndex ) {
        return getWagons().get( wagonIndex );
    }

    public Train getLinkedTrain() {
        return this.linkedTrain;
    }

    private void createWagons( int business, int economy ) {
        Wagon currentWagon;

        for ( int i = 0; i < business; i++ ) {
            currentWagon = new Wagon( true );
            this.getWagons().add( currentWagon );
        }

        for ( int j = 0; j < economy; j++ ) {
            currentWagon = new Wagon( false );
            this.getWagons().add( currentWagon );
        }
    }
}