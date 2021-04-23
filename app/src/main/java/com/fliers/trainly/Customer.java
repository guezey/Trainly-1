/**
 * Customer Object 
 * @author Cengizhan Terzioğlu
 * @version 22.04.2021
*/
public class Customer implements User {
    
    // Properties
    private double discountPoints;

    // Constructors
    public Customer( int id, String name, String email, String password ) {
        super( id, name, email, password );
        setDiscountPoints( 0 );
    }

    // Methods
    public double getDiscountPoints() {
        return this.discountPoints;
    }

    public void setDiscountPoints( double discountPoints ) {
        this.discountPoints = discountPoints;
    }

    public void buyTicket( Ticket ticket ) {
        
    }
}