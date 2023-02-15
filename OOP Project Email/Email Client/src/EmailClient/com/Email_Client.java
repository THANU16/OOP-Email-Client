package EmailClient.com;
// your index number 200636H
// I am Thanushanth.
// My index no is 200636H.


import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Email_Client {
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // we get date given format yyy/mm/dd

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        EmailSystem emailSystem = new EmailSystem();



        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter option type: \n"
                + "1 - Adding a new recipient\n"
                + "2 - Sending an email\n"
                + "3 - Printing out all the recipients who have birthdays\n"
                + "4 - Printing out details of all the emails sent\n"
                + "5 - Printing out the number of recipient objects in the application");


        //When email client is start,We read the file
        emailSystem.readFile();
        //When email client is start ,we send the mails to people who have birthday on today
        emailSystem.sendTodayBirthDayWish();
        System.out.println("Enter your option");
        while (scanner.hasNextInt()) {
            int option = scanner.nextInt();


            switch (option) {
                case 1:
                    // input format - Official: nimal,nimal@gmail.com,ceo
                    // Use a single input to get all the details of a recipient
                    // code to add a new recipient
                    // store details in clientList.txt file
                    // Hint: use methods for reading and writing files

                    Scanner detail = new Scanner(System.in);
                    System.out.println("You ar select adding a new recipient \n Input format - Official: nimal,nimal@gmail.com,ceo \nEnter recipient detail.....");
                    String recipientDetail = detail.nextLine();
                    emailSystem.addRecipientDetail(recipientDetail);
                    break;

                case 2:
                    // input format - email, subject, content
                    // code to send an email
                    System.out.println("You are select Sending an email");
                    System.out.println("Enter the to MailID...");
                    Scanner data = new Scanner(System.in);
                    String mailID = data.nextLine();  // get the mailID from user
                    System.out.println("Enter the sending mail subject...");
                    String subject = data.nextLine();  // get the sending mail subject from user
                    System.out.println("Enter the sending mail body");
                    String body = data.nextLine();  // get the sending mail body from user

                    emailSystem.sendTheMail(mailID.trim(), subject, body);
                    break;

                case 3:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    // code to print recipients who have birthdays on the given date
                    System.out.println("You are select printing out all the recipients who have birthdays\n Input format - yyyy/MM/dd (ex: 2018/09/17) \nEnter the searching birthday date.....");
                    Scanner date = new Scanner(System.in);
                    String inputDate = date.nextLine();  // get the date from user
                    emailSystem.checkBirthDay(inputDate);   // print recipient name  who have birthdays on the given date
                    break;

                case 4:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    // code to print the details of all the emails sent on the input date
                    System.out.println("You are select Printing out details of all the emails sent\n Input format - yyyy/MM/dd (ex: 2018/09/17) \nEnter the searching mail date.....");
                    Scanner searchMailDate = new Scanner(System.in);
                    String inputMailDate = searchMailDate.nextLine();  // get the date from user
                    emailSystem.checkSendMailGivenDate(inputMailDate);   // print recipient name  who have birthdays on the given date
                    break;

                case 5:
                    // code to print the number of recipient objects in the application
                    System.out.println("You are select printing out the number of recipient objects in the application");
                    System.out.println("the number of recipient objects in the application => "+emailSystem.getRecipientObjectsInApplication());
                    break;

            }

            // start email client
            // code to create objects for each recipient in clientList.txt
            // use necessary variables, methods and classes
            System.out.println("Enter your option");
        }
    }
}

// create more classes needed for the implementation (remove the  public access modifier from classes when you submit your code)