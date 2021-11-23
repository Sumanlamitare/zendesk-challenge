# ZenDesk Coding Challenge
This is a Command Line Interface (CLI) project which connects to the ZenDesk ticketing API, fetches a batch of tickets, and displays in the standard output.
## Tools Used
- Language: JAVA
- Unit Test: JUnit 4
- External tool: [JSONsimple.jar](https://code.google.com/archive/p/json-simple/downloads)
## Pre-requisite
Need to have [JDK installed](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html) in the system.
## How to Run the CLI App
1. Download the `ZendeskCodingChallenge.jar` located inside `./dist` directory
2. Set the following three environment variables - [How?](https://www.serverlab.ca/tutorials/linux/administration-linux/how-to-set-environment-variables-in-linux/)
    - `ZENDESK_URI` (Your URI to connect to the ZenDesk API - e.g: `https://<your-subdomain>.zendesk.com`)
    - `ZENDESK_USER_NAME` (Your ZenDesk Username - which is usually an email as, `test@gmail.com`)
    - `ZENDESK_TOKEN` (Your ZenDesk API token). Refer to [this documents](https://developer.zendesk.com/api-reference/ticketing/tickets/ticket-requests/) on how to get one
3. Run the following command on your terminal of choice
``` java -jar dist/ZendeskCodingChallenge.jar```
## How to Run the Unit Test
The app is developed using [NetBeans](https://netbeans.apache.org/download/index.html). 
- Download the Project in a local directory
- Open the project in NetBeans Editor
- Navigate to the `Test Packages`
- Right-click the `ZendeskTicketingSystemTest.java` file and click `Test File`

## How does the App Work
1. When the application runs, you will be presented with a menu with three options.
    - 1: Get all tickets. (Fetches 25 tickets from the ZenDesk API and presents them in a list)
    - 2: Get one ticket.  (Asks for the ticket ID number and fetches that ticket from the system, if that ticket does not exist it will return an error message.)
    - 3: Exit.             (Exits out of the application)
2. If there are more than 25 tickets in the system, the menu option 1 will change to "5) Get next tickets".
3. If 5 is entered, the menu will once again change to "4) Get previous tickets 5) Get next tickets"
  
## Screenshot of the application
1. When option 1 is selected

![1](https://user-images.githubusercontent.com/79996375/143091404-bc5d5234-f1da-4476-9192-89a9abe13a5f.png)
**The menu changes


2. When option 5 is selected
![2](https://user-images.githubusercontent.com/79996375/143091507-6712f5f7-f21b-49fd-b762-66b338a7e8e7.png)
**The meny changes one more time


3. When option 2 is included and the ticket exist
![3](https://user-images.githubusercontent.com/79996375/143091664-9af0253d-87c5-47ea-bd29-271addb8ffe3.png)

4. When option 2 is selected and the ticket doesn't exist
![4](https://user-images.githubusercontent.com/79996375/143091778-161d6e7c-255f-4b9e-aa45-9800013f4c9a.png)

