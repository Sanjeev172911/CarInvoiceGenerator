import java.time.LocalDate;

public class Ride {
    double distance;
    double time;
    boolean isPremium;

    Ride(double distance,double time,boolean premium){
        this.distance=distance;
        this.time=time;
        this.isPremium=premium;
    }
}
