import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account created with balance: $" + initialBalance);
    }

    public String getUserId() {
        return userId;
    }

    public boolean verifyPin(String pin) {
        return this.userPin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            addTransaction("Withdrawn: $" + amount);
            System.out.println("Withdrawal successful. Available balance: $" + balance);  
        }
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction("Deposited: $" + amount);
        System.out.println("Deposit successful. Available balance: $" + balance); 
    }

    public boolean transfer(User recipient, double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance for transfer.");
            return false;
        }
        this.withdraw(amount);
        recipient.deposit(amount);

        addTransaction("Transferred: $" + amount + " to user " + recipient.getUserId());

        System.out.println("Transfer successful. Your remaining balance: $" + this.getBalance());

        return true;
    }
}

public class task3 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        User user1 = new User("Pandari", "1234", 5000.00);
        User user2 = new User("Vishnu", "5678", 3000.00);

        System.out.println("Welcome to the ATM System!");
        User currentUser = authenticateUser(user1, user2);
        if (currentUser != null) {
            performOperations(currentUser, user1, user2);
        } else {
            System.out.println("Authentication failed. Exiting.");
        }
    }

    private static User authenticateUser(User user1, User user2) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (user1.getUserId().equals(userId) && user1.verifyPin(pin)) {
            return user1;
        } else if (user2.getUserId().equals(userId) && user2.verifyPin(pin)) {
            return user2;
        }
        return null;
    }

    private static void performOperations(User currentUser, User user1, User user2) {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentUser.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    currentUser.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    currentUser.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient User ID: ");
                    scanner.nextLine();
                    String recipientId = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    User recipient = recipientId.equals(user1.getUserId()) ? user1 :
                                     recipientId.equals(user2.getUserId()) ? user2 : null;

                    if (recipient != null && !recipient.equals(currentUser)) {
                        currentUser.transfer(recipient, transferAmount);
                    } else {
                        System.out.println("Invalid recipient or self-transfer not allowed.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }   
}
