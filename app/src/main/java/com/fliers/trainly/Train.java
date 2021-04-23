import java.sql.Date;
import java.util.ArrayList;

public class Train {

    double businessPrice;
    double economyPrice;
    int id;
    int posX;
    int posY;
    int businessWagonNum;
    int economyWagonNum;
    Line line;
    ArrayList<Schedule> schedules;
    Company linkedCompany;

    public Train( Company company, Line line, Place spawnPlace, int businessWagonNum,
                int economyWagonNum, double bPrice, double ePrice) {
        linkedCompany = company;
        this.line = line;
        posX = spawnPlace.getPosX();
        posY = spawnPlace.getPosY();
        this.businessWagonNum = businessWagonNum;
        this.economyWagonNum = economyWagonNum;
        businessPrice = bPrice;
        economyPrice = ePrice;
    }

    public int getId() {
        return id;
    }

    public void setBusinessWagonNum( int a) {
        businessWagonNum = a;
    }

    public void setEconomyWagonNum( int a) {
        economyWagonNum = a;
    }

    public void addSchedule( Schedule s) {
        schedules.add(s);
    }

    public Schedule getSchedule( Date d) {
        for( int i = 0; i < schedules.size(); i++) {

            if( d.after( schedules.get(i).getDepartureDate()) && d.before( schedules.get(i).getArrivalDate())
                    || d.compareTo( schedules.get(i).getDepartureDate()) == 0) {
                return schedules.get(i);
            }
        }
        return null;
    }

    public Place getDeparturePlace() {
        return line.getDeparturePlace();
    }

    public Place getArrivalPlace() {
        return line.getArrivalPlace();
    }

    public double getBusinessPrice() {
        return businessPrice;
    }

    public double getEconomyPrice() {
        return economyPrice;
    }

    public void setLine( Line a) {
        line = a;
    }

    public Company getLinkedCompany() {
        return linkedCompany;
    }
}
