I created this project as part of Hyperskill's Java Backend Developer course. For the project, I was provided with a general description of what the program needed to accomplish and expected output of the program. It was my job to write the code to produce the expected output after multiple test cases are run.

This was a fun project and is what I would consider my first "major" Java project, judging by the amount of time it took me to complete and the complexity of the program. The goal of the program is to simulate software that a theatre might use, with features for customers and theatre managers. The program begins with the user inputting the number of rows, and the amount of seats in each row. Based on the provided inputs, a seat layout is created and can be shown to the user upon request. The user is able to purchase tickets, with the ticket price varying based on the number of seats in the theatre. There is also a "Statistics" option in the program, where the user can see different data points, such as the number of tickets sold, the percentage of total tickets sold, and the current income from ticket sales.

Here is a general description of the methods in this program:<br>
**main:** The main area where user input is managed and methods are called depending on user input.<br>
**initializeLayout:** Initializes the layout of the seats.<br>
**showTheSeats:** Prints the current seat layout.<br>
 **buyATicket:** After ensuring the user enters a valid input, the method goes through the process of buying a ticket.<br>
 **seatPrices:** Creates a 2D array that holds the prices of the seats. If the total number of seats is greater than 60, then the seat prices are $10 for the front half and $8 for the back half. If the number of rows is odd, 9 for example, then the first 4 rows would be $10 and the last 5 rows would be $8.
