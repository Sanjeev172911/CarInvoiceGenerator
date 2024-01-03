import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {
    static InvoiceGenerator invoiceGenerator;

    @BeforeAll
    public static void initializeInvoiceGenerator(){
        invoiceGenerator=new InvoiceGenerator(10,1,5,
                15,2,20);
    }

    @Test
    public void totalFareTest(){
        assertEquals(60,invoiceGenerator.totalFare(5,10,false));
    }

    @Test
    public void totalFareTestForPremium(){
        assertEquals(95,invoiceGenerator.totalFare(5,10,true));
    }


    @Test
    public void totalFareMinimumCostTest(){
        assertEquals(5,invoiceGenerator.totalFare(.1,2,false));
    }

    @Test
    public void totalFareMinimumCostTestForPremium(){
        assertEquals(20,invoiceGenerator.totalFare(1,2,true));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "-2.2,2,false",
                    "2.4,-.3,true",
                    "-4.0,-6,true"
            }
    )
    public void totalFareInvalidInputTest(String values){
        String[] valueArray = values.split(",");
        double distance = Double.parseDouble(valueArray[0]);
        double time = Double.parseDouble(valueArray[1]);
        boolean isPremium=Boolean.parseBoolean(valueArray[2]);
        assertEquals(0,invoiceGenerator.totalFare(distance,time,isPremium));
    }

    static Stream<Arguments> InvalidTotalFareTestCase() {
        return Stream.of(
                Arguments.of(-1, -2,false),    // Test case 1
                Arguments.of(0, 0,true),    // Test case 2
                Arguments.of(-1, 1,false),   // Test case 3
                Arguments.of(10, -5,false)   // Test case 4
        );
    }

    @ParameterizedTest
    @MethodSource("InvalidTotalFareTestCase")
    public void totalFareInvalidInputTest(double distance,double time,boolean isPremium){
        assertEquals(0,invoiceGenerator.totalFare(distance,time,isPremium));
    }

    static Stream<Arguments>multipleRides(){
        return Stream.of(
            Arguments.of(
            new ArrayList<Ride>(Arrays.asList(
                        new Ride(2,10,false),
                        new Ride(5,25,true))
              ),155),
            Arguments.of(
            new ArrayList<Ride>(Arrays.asList(
                        new Ride(12,40,false),
                        new Ride(5,15,true),
                        new Ride(.1,3,true))
            ),285),
            Arguments.of(
                    new ArrayList<Ride>(),0)
        );
    }

    @ParameterizedTest
    @MethodSource("multipleRides")
    public void multipleRidesTotalFare(ArrayList<Ride> rides,double totalCost){
        assertEquals(totalCost,invoiceGenerator.totalFareForMultipleRide(rides));
   }

    static Stream<Arguments>InvoiceData(){
        return Stream.of(
                Arguments.of(
                        new ArrayList<Ride>(Arrays.asList(
                                new Ride(2,10,false),
                                new Ride(5,25,true))
                        ),new Invoice(2,155,77.50),
                Arguments.of(
                        new ArrayList<Ride>(Arrays.asList(
                                new Ride(12,40,false),
                                new Ride(5,15,true),
                                new Ride(.1,3,true))
                        ),new Invoice(3,285,95.0)),
                Arguments.of(
                        new ArrayList<Ride>(),
                        new Invoice(0,0,0)
                        ))
        );
    }

    @ParameterizedTest
    @MethodSource("InvoiceData")
    public void InvoiceTestForMultipleRides(ArrayList<Ride> rides,Invoice invoice){
        Invoice generatedInvoice=invoiceGenerator.generateInvoice(rides);
        assertEquals(invoice.ridesCnt,generatedInvoice.ridesCnt);
        assertEquals(invoice.totalFare,generatedInvoice.totalFare);
        assertEquals(invoice.averageFarePerRide,generatedInvoice.averageFarePerRide);
    }

    private Invoice generateInvoiceViaIdTest(int Id){
        int id=11;
        Passenger newPassenger=new Passenger(id);
        PassengerBuilder passengerBuilder=new PassengerBuilder();

        passengerBuilder.addPassenger(id);

        passengerBuilder.addRide(id,12.0,40,false);
        passengerBuilder.addRide(id,5.0,15,true);
        passengerBuilder.addRide(id,.1,3,true);

        ArrayList<Ride>allRides= null;
        try {
            allRides = passengerBuilder.getAllRides(Id);
        } catch (InvalidUserException e) {
            throw new RuntimeException(e);
        }
        return invoiceGenerator.generateInvoice(allRides);
    }


    @Test
    public void InvoiceTestForMultipleRidesUsingPassengerId(){
        Invoice invoice=new Invoice(3,285,95);
        Invoice generatedInvoice=generateInvoiceViaIdTest(11);
        assertEquals(invoice.ridesCnt,generatedInvoice.ridesCnt);
        assertEquals(invoice.totalFare,generatedInvoice.totalFare);
        assertEquals(invoice.averageFarePerRide,generatedInvoice.averageFarePerRide);
    }

    @Test
    public void InvoiceTestWhereUserIsInvalid(){
        try{
            invoiceGenerator.generateInvoiceViaId(12);
        }catch(Exception e){
            assertEquals("User not found",e.getMessage());
        }
    }

}
