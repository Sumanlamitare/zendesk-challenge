# ZenDesk Coding Challenge
This is a Command Line Interface (CLI) project which connects to the ZenDesk ticketing API, fetches a batch of tickets, and displays in the standard output.
## Language Used
`JAVA`
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


