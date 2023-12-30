import java.util.ArrayList;
import java.util.Map;

public class InvoiceGenerator {
    double costPerKm;
    double costPerMin;
    double minFare;

    InvoiceGenerator(double costPerKm,double costPerMin,double minFare){
        this.costPerKm=costPerKm;
        this.costPerMin=costPerMin;
        this.minFare=minFare;
    }

    public double totalFare(double distance,double time){
        if(distance<=0 || time<=0) return 0.0;
        double totalFare=distance*costPerKm+time*costPerMin;
        return Math.max(totalFare,5.0);
    }

    public double totalFareForMultipleRide(ArrayList<Ride>rides){
        double totalCost=0;

        for(Ride ride:rides){
            totalCost+=totalFare(ride.distance,ride.time);
        }

        return totalCost;
    }
}
