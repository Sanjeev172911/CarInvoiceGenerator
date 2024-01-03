import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class InvoiceGenerator {
    double costPerKm;
    double costPerMin;
    double minFare;
    double costPerKmPremium;
    double costPerMinPremium;
    double minFarePremium;

    public InvoiceGenerator(double costPerKm, double costPerMin, double minFare, double costPerKmPremium, double costPerMinPremium, double minFarePremium) {
        this.costPerKm = costPerKm;
        this.costPerMin = costPerMin;
        this.minFare = minFare;
        this.costPerKmPremium = costPerKmPremium;
        this.costPerMinPremium = costPerMinPremium;
        this.minFarePremium = minFarePremium;
    }

    public double totalFare(double distance,double time,boolean isPremium){
        if(distance<=0 || time<=0) return 0.0;
        if(isPremium){
            double totalFare=distance*costPerKmPremium+time*costPerMinPremium;
            return Math.max(totalFare,minFarePremium);
        }

        double totalFare=distance*costPerKm+time*costPerMin;
        return Math.max(totalFare,minFare);
    }

    public double totalFareForMultipleRide(ArrayList<Ride>rides){
        double totalCost=0;
        for(Ride ride:rides){
            totalCost+=totalFare(ride.distance,ride.time,ride.isPremium);
        }
        return totalCost;
    }

    public Invoice generateInvoice(ArrayList<Ride>allRides){
        int cntRides=allRides.size();
        Double totalFare=totalFareForMultipleRide(allRides);
        Double averageFarePerRide=totalFare/cntRides;
        Double roundedValue = Math.round(averageFarePerRide * 100.0) / 100.0;

        return new Invoice(cntRides,totalFare,averageFarePerRide);
    }

    public Invoice generateInvoiceViaId(int Id) throws InvalidUserException {
        ArrayList<Ride>allRides;
        PassengerBuilder passengerBuilder=new PassengerBuilder();
        try {
            allRides=passengerBuilder.getAllRides(Id);
        } catch (InvalidUserException e) {
            throw new InvalidUserException(e.getMessage());
        }

        return generateInvoice(allRides);
    }


}