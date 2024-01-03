import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PassengerBuilder {
    Map<Integer,Passenger>allPassenger;

    public PassengerBuilder() {
        this.allPassenger = new HashMap<>();
    }

    public void addPassenger(int id){
        Random random=new Random();

        Passenger newPassenger=new Passenger(id);
        allPassenger.put(id,newPassenger);

        System.out.println("New Customer added with "+id+" Passenger Id");
    }

    public boolean addRide(int Id,double distance,double expectedTime,boolean isPremium){
        if(!allPassenger.containsKey(Id)){
            System.out.println("Customer Not Register");
            return false;
        }

        Ride newRide=new Ride(distance,expectedTime,isPremium);

        allPassenger.get(Id).allRides.add(newRide);
        return true;
    }

    public ArrayList<Ride> getAllRides(int id) throws InvalidUserException{
        if(!allPassenger.containsKey(id)){
            System.out.println("Customer Not Register");
            throw new InvalidUserException("User not found");
        }
        return allPassenger.get(id).allRides;
    }


}
