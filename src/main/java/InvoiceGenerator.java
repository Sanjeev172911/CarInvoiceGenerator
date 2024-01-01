import java.util.ArrayList;
import java.util.Arrays;
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

    public ArrayList<Double> generateInvoice(ArrayList<Ride>allRides){
        ArrayList<Double>list=new ArrayList<>();

        Double cntRides=(double)allRides.size();
        list.add(cntRides);
        Double totalFare=totalFareForMultipleRide(allRides);
        list.add(totalFare);
        Double averageFarePerRide=totalFare/cntRides;
        Double roundedValue = Math.round(averageFarePerRide * 100.0) / 100.0;
        list.add(roundedValue);
        return list;
    }

}
