import java.util.Scanner;
public class atm { 
    private double balance=10000;
    private String transactionHistory = "Transaction History\n";
    private final String pin = "1234";
    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nATM Menu");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt(); 
            switch (choice) {
                case 1:
                    System.out.println(transactionHistory);
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    sc.close(); 
                    return; 
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public void withdraw() {
        Scanner sc = new Scanner(System.in);
        if (!authenticateUser()) {
            System.out.println("Invalid PIN. Transaction cancelled.");
            return;
        }

        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }

        System.out.println("Choose currency denomination for withdrawal:");
        System.out.println("1. 100");
        System.out.println("2. 500");
        System.out.println("3. 2000");
        System.out.print("Enter your choice (1, 2, or 3): ");
        int denominationChoice = sc.nextInt();

        int denomination = 0;
        switch (denominationChoice) {
            case 1: denomination = 100; break;
            case 2: denomination = 500; break;
            case 3: denomination = 2000; break;
            default:
                System.out.println("Invalid denomination choice. Transaction cancelled.");
                return;
        }

        int notes = (int) (amount / denomination);
        if (amount % denomination != 0) {
            System.out.println("Amount cannot be dispensed in the selected denomination.");
            return;
        }

        balance -= amount; 
        transactionHistory += "Withdrawn: $" + amount + " (Denomination: " + denomination + ")\n";
        System.out.println("Successfully withdrawn: $" + amount);
        System.out.println("Dispensed in " + notes + " notes of $" + denomination);
        showBalance();
    }

    public void deposit() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        balance += amount; 
        transactionHistory += "Deposited: $" + amount + "\n";
        System.out.println("Successfully deposited: $" + amount);
        showBalance();
    }

    public void transfer() {
        Scanner sc = new Scanner(System.in);
        if (!authenticateUser()) {
            System.out.println("Invalid PIN. Transaction cancelled.");
            return;
        }

        System.out.print("Enter recipient account number: ");
        String accountNumber = sc.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = sc.nextDouble();

        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }

        balance -= amount; 
        transactionHistory += "Transferred: $" + amount + " to account " + accountNumber + "\n";
        System.out.println("Successfully transferred: $" + amount + " to account " + accountNumber);
        showBalance();
    }

    public void showBalance() {
        System.out.println("Current balance: $" + balance);
    }

    private boolean authenticateUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter PIN: ");
        String enteredPin = sc.nextLine();
        return enteredPin.equals(pin);
    }
    public static void main(String[] args) {
        atm atm = new atm();
        atm.displayMenu();
    }
}