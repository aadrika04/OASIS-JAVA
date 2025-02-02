import java.io.*;
import java.util.*;

class Railway {
    private static int pnrcounter = 1000;
    private int pnr;
    private int age;
    private int seatNumber;
    private int fare;
    private String name;
    private String source; 
    private String destination;
    private String date;
    private String time;
    private String trainName, classType;
    private boolean foodOrder;
    private static final Map<String, List<String>> availableTrains = Map.of(
        "Delhi-Mumbai", List.of("Rajdhani Express", "Duronto Express"),
        "Mumbai-Delhi", List.of("Mumbai Rajdhani", "Mumbai Duronto"),
        "Delhi-Kolkata", List.of("Sealdah Express", "Howrah Duronto"),
        "Kolkata-Delhi", List.of("Howrah Rajdhani", "Kolkata Shatabdi")
    );
    private static final Map<String, String> trainTimes = Map.of(
        "Rajdhani Express", "06:00 AM", 
        "Duronto Express", "08:00 AM",
        "Mumbai Rajdhani", "07:00 AM",
        "Mumbai Duronto", "09:00 AM",
        "Sealdah Express", "10:00 AM",
        "Howrah Duronto", "12:00 PM",
        "Howrah Rajdhani", "04:00 PM",
        "Kolkata Shatabdi", "05:00 PM"
    );

    private static final Map<String, Integer> coachFares = Map.of(
        "AC 1-Tier", 3000, "AC 2-Tier", 2000, "Sleeper", 800
    );

    public Railway(String name, int age, String source, String destination, String date, 
                   String trainName, String classType, boolean foodOrder) {
        this.pnr = pnrcounter++;
        this.name = name;
        this.age = age;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.trainName = trainName;
        this.classType = classType;
        this.foodOrder = foodOrder;
        this.fare = (age < 5) ? 0 : coachFares.getOrDefault(classType, 1000);
        this.seatNumber = new Random().nextInt(100) + 1;
        this.time = trainTimes.get(trainName); // Get the departure time from the map
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("railway_bookings.txt", true)) {
            writer.write(String.format("PNR: %d, Name: %s, Age: %d, Seat: %d, Train: %s, Class: %s, Fare: %d, Time: %s, Food: %s\n", 
                         pnr, name, age, seatNumber, trainName, classType, fare, time, foodOrder ? "Yes" : "No"));
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void displayDetails() {
        System.out.printf("\nPNR: %d | Name: %s | Age: %d | Seat: %d | Train: %s | Class: %s | Fare: %d | Time: %s | Food: %s\n",
                pnr, name, age, seatNumber, trainName, classType, fare, time, foodOrder ? "Yes" : "No");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Source: "); 
        String source = sc.nextLine();
        System.out.print("Enter Destination: "); 
        String destination = sc.nextLine();

        String route = source + "-" + destination;
        if (!availableTrains.containsKey(route)) {
            System.out.println("No trains available for this route."); 
            return;
        }

        List<String> trains = availableTrains.get(route);
        for (int i = 0; i < trains.size(); i++)
            System.out.println((i + 1) + ". " + trains.get(i));
        System.out.print("Select Train: "); 
        String trainName = trains.get(sc.nextInt() - 1);

        List<String> classes = new ArrayList<>(coachFares.keySet());
        for (int i = 0; i < classes.size(); i++)
            System.out.println((i + 1) + ". " + classes.get(i));
        System.out.print("Select Class: "); 
        String classType = classes.get(sc.nextInt() - 1);

        System.out.print("Enter Date (YYYY-MM-DD): "); 
        sc.nextLine(); 
        String date = sc.nextLine();

        System.out.print("Enter Number of Passengers: "); 
        int numPassengers = sc.nextInt();
        sc.nextLine();

        List<Railway> bookings = new ArrayList<>();
        for (int i = 0; i < numPassengers; i++) {
            System.out.print("Enter Passenger " + (i + 1) + " Name: "); 
            String name = sc.nextLine();
            System.out.print("Enter Age: "); 
            int age = sc.nextInt();
            sc.nextLine();
            System.out.print("Food Order (yes/no): "); 
            boolean foodOrder = sc.nextLine().equalsIgnoreCase("yes");

            Railway passenger = new Railway(name, age, source, destination, date, trainName, classType, foodOrder);
            bookings.add(passenger);
            passenger.saveToFile();
        }

        bookings.forEach(Railway::displayDetails);

        System.out.println("\nTicket booked successfully! Have a happy journey!\n");

        sc.close();
    }
}
