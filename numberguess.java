import java.util.*;
public class numberguess {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        int maxRounds = 3;
        int totalScore = 0;
        System.out.println("Welcome to the number guessing game");
         for (int round = 1; round <= maxRounds; round++) {
            System.out.println("\nRound " + round + " starts");
            int randomnumber = r.nextInt(100) + 1;
            int attempts = 0;
            int maxAttempts = 7;
            boolean guessing = true;
            
            while (guessing && attempts < maxAttempts) {
                System.out.println("Attempt " + (attempts + 1) + ": Guess the number between 1 and 100");
                int guess = sc.nextInt();
                attempts++; 
                if (guess == randomnumber) {
                    System.out.println("Congratulations! You've guessed the number correctly.");
                    totalScore += (maxAttempts - attempts + 1) * 10;
                    guessing = false;
                } else if (guess < randomnumber) {
                    System.out.println("The number is higher! Try again.");
                } else {
                    System.out.println("The number is lower! Try again.");
                }
            }

            if (guessing) {
                System.out.println("Sorry! You've used all attempts. The correct number was " + randomnumber);
            }
        }
        
        System.out.println("\nGame over! Your total score is: " + totalScore);
        sc.close();
    }
}
