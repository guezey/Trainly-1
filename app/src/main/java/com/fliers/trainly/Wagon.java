import java.util.ArrayList;

/*
* A class for wagons. It will contain properties indicating prices for economy class and business class,
* an ArrayList for seats, a boolean property indicating wagons business condition and a Schedule
* @author Erkin Aydın
* @version 23-04-2021__v/1.1
*/

public class Wagon {
    final int BUSINESS_SEAT_NUM = 15;
    final int ECONOMY_SEAT_NUM = 30;
    ArrayList<Seat> seats;
    boolean business;
    Schedule linkedSchedule;

    public Wagon( Schedule schedule, boolean b) {

        linkedSchedule = schedule;
        business = b;
        createSeats();
    }

    private void createSeats() {

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
        return seats.get(i);
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
