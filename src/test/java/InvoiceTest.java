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
        invoiceGenerator=new InvoiceGenerator(10,1,5);
    }

    @Test
    public void totalFareTest(){
        assertEquals(60,invoiceGenerator.totalFare(5,10));
    }

    @Test
    public void totalFareMinimumCostTest(){
        assertEquals(5,invoiceGenerator.totalFare(.2,2));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "-2.2,2",
                    "2.4,-.3",
                    "-4.0,-6"
            }
    )
    public void totalFareInvalidInputTest(String values){
        String[] valueArray = values.split(",");
        double distance = Double.parseDouble(valueArray[0]);
        double time = Double.parseDouble(valueArray[1]);
        assertEquals(0,invoiceGenerator.totalFare(distance,time));
    }

    static Stream<Arguments> InvalidTotalFareTestCase() {
        return Stream.of(
                Arguments.of(-1, -2),    // Test case 1
                Arguments.of(0, 0),    // Test case 2
                Arguments.of(-1, 1),   // Test case 3
                Arguments.of(10, -5)   // Test case 4
        );
    }

    @ParameterizedTest
    @MethodSource("InvalidTotalFareTestCase")
    public void totalFareInvalidInputTest(double distance,double time){
        assertEquals(0,invoiceGenerator.totalFare(distance,time));
    }

    static Stream<Arguments>multipleRides(){
        return Stream.of(
            Arguments.of(
            new ArrayList<Ride>(Arrays.asList(
                        new Ride(2,10),
                        new Ride(5,25))
              ),105),
            Arguments.of(
            new ArrayList<Ride>(Arrays.asList(
                        new Ride(12,40),
                        new Ride(5,15),
                        new Ride(.1,3))
            ),230)
        );
    }

    @ParameterizedTest
    @MethodSource("multipleRides")
    public void multipleRidesTotalFare(ArrayList<Ride> rides,double totalCost){
        assertEquals(totalCost,invoiceGenerator.totalFareForMultipleRide(rides));
    }

}
