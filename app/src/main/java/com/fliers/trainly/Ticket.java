/**
 * Ticket Object 
 * @author Cengizhan Terzioğlu
 * @version 22.04.2021
*/
public class Ticket {
    
    // Properties
    private int id;
    private Seat seat;
    private Customer customer;
    private String comment;
    private int starRating;

    // Constructors
    public Ticket( int id, Seat seat, Customer customer ) {
        this.id = id;
        this.seat = seat;
        this.customer = customer;
        setComment( "" );
        setStarRating(0);
    }

    // Methods
    public int getId() {
        return this.id;
    }

    public Seat getSeat() {
        return this.seat;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment( String comment ) {
        this.comment = comment;
    }

    public int getStarRating() {
        return this.starRating;
    }

    public void setStarRating( int starRating ) {
        this.starRating = starRating;
    }
}