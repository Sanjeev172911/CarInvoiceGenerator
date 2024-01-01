import java.util.ArrayList;

public class Passenger {
    int passengerId;
    boolean isPremium;
    ArrayList<Ride>allRides;

    Passenger(int id){
        this.passengerId=id;
        isPremium=false;
        allRides=new ArrayList<Ride>();
    }
}
