import java.util.ArrayList;

public class Wagon {
    
    final int BUSINESS_SEAT_NUM = 15;
    final int ECONOMY_SEAT_NUM = 30;
    ArrayList<Seat> seats;
    boolean business;
    Schedule linkedSchedule;

    public Wagon( Schedule schedule, boolean b) {

        linkedSchedule = schedule;
        business = b;
        createSeates();
    }

    private void createSeates() {

        if( isBusiness()) {
            seats = new ArrayList<Seat>( BUSINESS_SEAT_NUM);
        }
        else {
            seats = new ArrayList<Seat>( ECONOMY_SEAT_NUM);
        }
    }

    public boolean isBusiness() {
        return business;
    }

    public Seat getSeat( int i) {
        return seats[i];
    }

    public int getTotalSeatNo() {
        if( isBusiness()) {
            return BUSINESS_SEAT_NUM;
        }
        else {
            return ECONOMY_SEAT_NUM;
        }
    }

    public Schedule getLinkedSchedule() {
        return linkedSchedule;
    }
}