public class Passenger {
    static int cnt=0;
    int passengerId;
    boolean isPremium;
    InvoiceGenerator invoiceApp;

    Passenger(){
        passengerId=++cnt;
        isPremium=false;
    }
}
