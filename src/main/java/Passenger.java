import java.util.ArrayList;

public class Passenger {
    int passengerId;
    ArrayList<Ride>allRides;

    Passenger(int id){
        this.passengerId=id;
        allRides=new ArrayList<Ride>();
    }
}
