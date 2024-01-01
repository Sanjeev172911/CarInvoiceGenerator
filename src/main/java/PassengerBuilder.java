import java.util.Map;
import java.util.Random;

public class PassengerBuilder {
    Map<Integer,Passenger>allPassenger;

    public void addPassenger(){
        Random random=new Random();
        int id=Math.abs(random.nextInt());

        Passenger newPassenger=new Passenger(id);
        allPassenger.put(id,newPassenger);

        System.out.println("New Customer added with "+id+" Passenger Id");
    }

    public boolean addRide(int Id,double distance,double expectedTime){
        if(!allPassenger.containsKey(Id)){
            System.out.println("Customer Not Register");
            return false;
        }

        Ride newRide=new Ride(distance,expectedTime);

        allPassenger.get(Id).allRides.add(newRide);
        return true;
    }


}
