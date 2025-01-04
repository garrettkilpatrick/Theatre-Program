import java.util.Scanner;

public class Theatre {
    private static char[][] seatLayout;
    private static int[][] seatPrices;
    private static int numSold = 0; //counts number of tickets sold
    private static int currentIncome = 0; //keeps track of income made from ticket sales
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        //Initializing and populating the seatLayout and seatPrices arrays
        initializeLayout(rows, seats);
        seatPrices(rows, seats);

        String[] options = new String[4]; //creates an array of options that the user can select from
        options[0] = "1. Show the seats";
        options[1] = "2. Buy a ticket";
        options[2] = "3. Statistics";
        options[3] = "0. Exit";
        int choice = Integer.MAX_VALUE; //MAX_VALUE has no significance, just needed to initialize the choice variable.
        int totalIncome = 0; //this variable represents the income earned if all possible tickets are sold.
        //calculating totalIncome:
        for (int[] row : seatPrices) {
            for (int price : row) {
                totalIncome += price;
            }
        }

        while (choice != 0) { //the user entering 0 is the exit condition for this program
            for (String option : options) { //prints the options
                System.out.println(option);
            }
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showTheSeats();
                    System.out.println(); //empty println to add spacing
                    break;
                case 2:
                    while (true) { //keeps looping until a ticket is successfully bought
                        System.out.println("Enter a row number:");
                        int seatRow = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        int seatColumn = scanner.nextInt();
                        boolean ticketPurchased = buyATicket(seatRow, seatColumn); //returns false if ticket purchased
                        if (!ticketPurchased) {
                            break;
                        }
                    }
                    System.out.println(); //empty println to add spacing
                    break;
                case 3:
                    System.out.println("Number of purchased tickets: " + numSold);
                    double percentSold = (numSold / (double) (seats * rows)) * 100;
                    System.out.printf("Percentage: %.2f%%%n", percentSold); //prints percent sold and newline
                    System.out.println("Current income: $" + currentIncome);
                    System.out.println("Total income: $" + totalIncome);
                    System.out.println();
            }
        }
    }

    private static void initializeLayout(int rows, int seats) {
        //I created some "counters" to help create the labels for the seat rows and columns.
        char counterI = '1';
        char counterJ = '1';
        //initializing the seatLayout array. I made it a static class attribute in order to not have to create a Cinema
        //object every time I need to access it (used in multiple methods). I added 1 to the size of the rows and seats
        //passed in to create room for the labels for the seat rows and columns.
        seatLayout = new char[rows + 1][seats + 1];

        //populating the array
        for (int i = 0; i < seatLayout.length; i++) {
            for (int j = 0; j < seatLayout[i].length; j++) {
                if (i == 0 && j == 0) {
                    seatLayout[i][j] = ' '; //Top-left corner needs to be a space for formatting
                } else if (i == 0) {
                    seatLayout[i][j] = counterJ++; //creates the seat column number labels
                } else if (j == 0) {
                    seatLayout[i][j] = counterI++; //creates the seat row number labels
                } else {
                    seatLayout[i][j] = 'S'; //Fills in the seats (S = seat)
                }
            }
        }
    }

    private static void showTheSeats() {
        //I created an array that will convert each row of seats (an array of chars) in to a string.
        String[] rowStrings = new String[seatLayout.length];
        for (int i = 0; i < seatLayout.length; i++) {
            rowStrings[i] = new String(seatLayout[i]);
        }
        //printing the seats to the console
        System.out.println("Cinema:");
        for (int i = 0; i < rowStrings.length; i++) {
            if (i == 0) {
                System.out.print("  "); //This creates a space to allow for proper formatting of column label numbers.
            }
            //This println statement iterates through the rowStrings array and adds spaces between the letters.
            System.out.println(rowStrings[i].replace("", " ").trim());
        }
    }

    private static void seatPrices(int rows, int seats) {
        //I created this array to hold the seat prices. Declared as a static class attribute for similar reasons as the
        //seatLayout array.
        seatPrices = new int[rows][seats];
        int totalSeats = rows * seats;
        for (int i = 0; i < seatPrices.length; i++) {
            for (int j = 0; j < seatPrices[i].length; j++) {
                //The price of each seat in a theatre with less than 60 total seats is $10.
                //Theatres with more than 60 seats are priced differently. The front half of the theatre is $10
                //per seat, and the back half is $8 per seat. If the amount of rows is odd (9, for example) then the
                //first 4 rows would be $10 and the last 5 rows would be $8.
                if (totalSeats <= 60) {
                    seatPrices[i][j] = 10;
                } else {
                    if (i < (rows / 2)) { //using integer division in case of an odd number of rows
                        seatPrices[i][j] = 10;
                    } else {
                        seatPrices[i][j] = 8;
                    }
                }
            }
        }
    }
    //this method returns a boolean value in order to stop the while loop in the main method if a ticket was purchased.
    private static boolean buyATicket(int row, int seat) {
        //checks input to avoid out of bounds exception
        if ((row < 1) || (row >= seatLayout.length) || (seat < 1) || (seat >= seatLayout[row].length)) {
            System.out.println("Wrong input!");
            return true; //returns true to repeat while loop
        } else {
            //checks to see if seat was already purchased
            if (seatLayout[row][seat] == 'B') {
                System.out.println("That ticket has already been purchased!");
                return true;
            } else {
                seatLayout[row][seat] = 'B'; //changes 'S' to 'B' in seatLayout to indicate that the seat was bought
                //The row values in the println statement below are reduced by 1 in order to act as an index
                //value for seatPrices. This was not necessary when using these variables as an index for seatLayout
                //since seatLayout has an extra row and column for number labels, while seatPrices does not.
                System.out.println("Ticket price: $" + seatPrices[row - 1][seat - 1]);
                numSold++;
                currentIncome += seatPrices[row - 1][seat - 1]; //variables - 1 for same reason as before
                return false; //breaks the while loop since a ticket has been sold
            }
        }
    }
}