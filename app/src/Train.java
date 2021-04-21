import java.sql.Date;

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

    public Train() {

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
        for( int i = 0; i < schedules.length; i++) {

            if( d.after( schedules[i].getDepartureDate()) && d.before( schedules[i].getArrivalDate())
            || d.compareTo( schedules[i].getDepartureDate()) == 0) {
                return schedules[i];
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
