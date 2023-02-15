package EmailClient.com;


import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class EmailSystem {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // we get date given format yyy/mm/dd

    // we maintain sending mail history and serialize object
    //this will use for serialization and deserialization
    private SendMailHistory SerializeOrDeserializeObject = new SendMailHistory();

    //we stop for repeated send birthday wish
    //this object use for check send birthday wish
    private CheckBirthDayWish checkSendBirthDayWish = new CheckBirthDayWish();


    //We store recipient objects
    private ArrayList<Recipient> recipients = new ArrayList<Recipient>();

    // We store BirthdayWishSendable objects
    private ArrayList<BirthDayWishSendable> birthDayWishSender = new ArrayList<BirthDayWishSendable>();

    //create a new file
    private File myFile = new File("src\\clientList.txt");


    //read the clientList file
    // create object => using call the method createRecipientObject
    // store in array list
    public void readFile(){
        System.out.println("read the file");
        //read file
        try {
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) { // we read multiple detail
                String data = myReader.nextLine();
                Recipient recipient = createRecipientObject(data);
                recipients.add(recipient);  // Add recipient in our array list
            }
            myReader.close();
        } catch ( FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }



    //add the recipient detail in my file
    public void addRecipientDetail(String inputDetail) {

        // write the recipient detail in the file
        try (FileWriter fileWriter = new FileWriter(myFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);) {
            printWriter.println(inputDetail);
        }
        catch (IOException i) {
            i.printStackTrace();
        }


        // create a recipient object
        Recipient recipient = createRecipientObject(inputDetail);
        recipients.add(recipient);  // Add recipient in our array list

    }

    //this method use for we create a recipient object
    //return recipient object
    public Recipient createRecipientObject (String inputDetail) {
        Recipient recipient=null;
        //find the recipient class
        String recipientType = (inputDetail.split(":"))[0].trim();
        String name = (inputDetail.split(":"))[1].split(",")[0].trim();  //Get the input from user and set name


        // Check the recipient type
        String emailAddress;
        String designation;
        LocalDate birthDate;
        switch (recipientType) {
            case "Official":
                emailAddress = (inputDetail.split(":"))[1].split(",")[1].trim(); // Get the input from user and set email address

                designation = (inputDetail.split(":"))[1].split(",")[2].trim();  // Get the input from user and set designation


                //create an object using officialRecipient constructor
                recipient = new OfficialRecipient(name, emailAddress, designation);
                break;
            case "Office_friend":
                emailAddress = (inputDetail.split(":"))[1].split(",")[1].trim(); //Get the input from user and  set email address

                designation = (inputDetail.split(":"))[1].split(",")[2].trim();  //Get the input from user and  set designation

                birthDate = LocalDate.parse(((inputDetail.split(":"))[1].split(",")[3]), dateFormat); //Get the input from user and  set birth of date in yyyy/mm/dd format


                //create an object using officialFriend constructor
                recipient = new OfficialFriend(name, emailAddress, designation, birthDate);
                break;
            case "Personal":
                // check the input has a nickname
                if ((inputDetail.split(":"))[1].split(",").length == 4) {
                    //input has a nike name
                    String nickName = (inputDetail.split(":"))[1].split(",")[1].trim(); //Get the input from user and  set nickname.
                    emailAddress = (inputDetail.split(":"))[1].split(",")[2].trim(); //Get the input from user and  set email address
                    birthDate = LocalDate.parse(((inputDetail.split(":"))[1].split(",")[3]), dateFormat); //Get the input from user and  set birth of date in yyyy/mm/dd format

                    //create an object using PersonalRecipient constructor
                    recipient = new PersonalRecipient(name, nickName, emailAddress, birthDate);
                } else {
                    emailAddress = (inputDetail.split(":"))[1].split(",")[1].trim(); //Get the input from user and  set email address
                    birthDate = LocalDate.parse((inputDetail.split(":"))[1].split(",")[2], dateFormat); //Get the input from user and  set birth of date in LocalDate type

                    //create an object using PersonalRecipient constructor
                    recipient = new PersonalRecipient(name, emailAddress, birthDate);
                }
                break;
        }
        return recipient;
    }


    //this is check the birthday on today
    //call sendTheMail method in email system class
    //we stop repeated wish
    public void sendTodayBirthDayWish() throws IOException, ClassNotFoundException {
        LocalDate currentDate =LocalDate.now();  //get the today date


        // traversal our recipients array list
        for (int i=0; i<recipients.size(); i++){
            Recipient currentRecipient = recipients.get(i);

            if (currentRecipient instanceof OfficialFriend){
                if (currentDate.getDayOfMonth() == ((OfficialFriend) currentRecipient).getDate().getDayOfMonth()
                        && currentDate.getMonth() == ((OfficialFriend) currentRecipient).getDate().getMonth()){
//                    System.out.println(((OfficialFriend) currentRecipient).getBDayWish());
                    //we stop for repeated send birthday wish
                    if (!checkSendBirthDayWish.checkForSendBirthdayWish(currentRecipient.getEmailAddress())) {

                        //Send the birthday wish
                        sendTheMail(currentRecipient.getEmailAddress(), "Happy BirthDay", ((OfficialFriend) currentRecipient).getBDayWish());
                        //write the emailID, whose birthday wish send
                        checkSendBirthDayWish.writeSendBirthdayWishEmailID(currentRecipient.getEmailAddress());
                    }
                }
            } else if (currentRecipient instanceof PersonalRecipient) {
                if (currentDate.getDayOfMonth() == ((PersonalRecipient) currentRecipient).getDate().getDayOfMonth()
                        && currentDate.getMonth() == ((PersonalRecipient) currentRecipient).getDate().getMonth()) {
                    //System.out.println(((PersonalRecipient) currentRecipient).getBDayWish());
                    //we stop for repeated send birthday wish
                    if (!checkSendBirthDayWish.checkForSendBirthdayWish(currentRecipient.getEmailAddress())) {
                        //send the birthday wish
                        sendTheMail(currentRecipient.getEmailAddress(), "Happy Birthday", ((PersonalRecipient) currentRecipient).getBDayWish());
                        //write the emailID, whose birthday wish send
                        checkSendBirthDayWish.writeSendBirthdayWishEmailID(currentRecipient.getEmailAddress());
                    }
                }
            }


            //we maintain the arraylist send birthday wish details
            if (currentRecipient instanceof BirthDayWishSendable){
                birthDayWishSender.add((BirthDayWishSendable)currentRecipient); // using down casting
            }

        }
    }


    //check recipients who have birthdays on the given date
    //print the name who have birthdays on the given date
    public void checkBirthDay(String inputDate) {

        //change the data type String to LocalDate
        LocalDate givenDate = LocalDate.parse(inputDate,dateFormat);
        Boolean isBirthday = false;

        //Traverse the BirthDayWishSendable objets
        for(BirthDayWishSendable currentRecipient : birthDayWishSender){
            //check the current recipient is instanceof OfficialFriend
            if (currentRecipient instanceof OfficialFriend) {
                //we will check date and month
                if (givenDate.getDayOfMonth() == ((OfficialFriend) currentRecipient).getDate().getDayOfMonth()
                        && givenDate.getMonth() == ((OfficialFriend) currentRecipient).getDate().getMonth()){
                       // && givenDate.getYear() == ((OfficialFriend) currentRecipient).getDate().getYear() ) {
                    //get the recipient name
                    //print the name
                    System.out.println(((OfficialFriend) currentRecipient).getName());  // down cast
                    isBirthday = true;
                }
            }
            //check the current recipient is instanceof PersonalRecipient
            else if (currentRecipient instanceof PersonalRecipient) {
                if (givenDate.getDayOfMonth() == ((PersonalRecipient) currentRecipient).getDate().getDayOfMonth()
                        && givenDate.getMonth() == ((PersonalRecipient) currentRecipient).getDate().getMonth()) {
                    //get the recipient name
                    //print the name
                    System.out.println(((PersonalRecipient) currentRecipient).getName());  // down cast
                    isBirthday = true;
                }
            }

        }

//        // traversal our recipients array list
//        for (int i=0; i<recipients.size(); i++) {
//            Recipient currentRecipient = recipients.get(i);
//            //only official friend and personal recipient objects have birthday date
//            //So we will check OfficialFriend or PersonalRecipient
//            if (currentRecipient instanceof OfficialFriend) {
//                //we will check date and month
//                if (givenDate.getDayOfMonth() == ((OfficialFriend) currentRecipient).getDate().getDayOfMonth()
//                        && givenDate.getMonth() == ((OfficialFriend) currentRecipient).getDate().getMonth()){
//                       // && givenDate.getYear() == ((OfficialFriend) currentRecipient).getDate().getYear() ) {
//                    //get the recipient name
//                    //print the name
//                    System.out.println(((OfficialFriend) currentRecipient).getName());  // down cast
//                    isBirthday = true;
//                }
//            }
//            else if (currentRecipient instanceof PersonalRecipient) {
//                if (givenDate.getDayOfMonth() == ((PersonalRecipient) currentRecipient).getDate().getDayOfMonth()
//                        && givenDate.getMonth() == ((PersonalRecipient) currentRecipient).getDate().getMonth()) {
//                    //get the recipient name
//                    //print the name
//                    System.out.println(((PersonalRecipient) currentRecipient).getName());  // down cast
//                    isBirthday = true;
//                }
//            }
//        }

        //check is the current date have no birthday
        if (!isBirthday){
            System.out.println("No recipient birthdays on your input date.");
        }
    }

    //create object in mail class
    //then we will call sendMail method in mail class
    //create SerilizableFile in src folder
    //mail object is Serialized in our SerilizableFile
    public void sendTheMail(String emailAddress, String subject, String body) throws IOException, ClassNotFoundException {
        Mail mail = new Mail(emailAddress,subject, body );
        mail.sendMail();

        //Store the send mail objets use for deserialization
        ArrayList<Mail> tempMailObjects = new ArrayList<Mail>();

        // Deserialization
        tempMailObjects = SerializeOrDeserializeObject.deserialization( tempMailObjects);

        // Serialization
        SerializeOrDeserializeObject.serialization(tempMailObjects,mail);
    }


    //deserialized our serializableFile
    public void checkSendMailGivenDate(String date) throws IOException {
        LocalDate givenDate = LocalDate.parse(date, dateFormat);
        ArrayList<Mail> tempMailObjects = new ArrayList<Mail>();
        Boolean isSendMail = false;

        ArrayList<Mail> deserializeMailObject;
        // Deserialization
        deserializeMailObject = SerializeOrDeserializeObject.deserialization(tempMailObjects);
        if (deserializeMailObject.size() != 0) {
            for (int i = 0; i < deserializeMailObject.size(); i++) {
                if (givenDate.getYear() == deserializeMailObject.get(i).getSendMailDate().getYear()
                        && givenDate.getMonth() == deserializeMailObject.get(i).getSendMailDate().getMonth()
                        && givenDate.getDayOfMonth() == deserializeMailObject.get(i).getSendMailDate().getDayOfMonth()) {
                    isSendMail = true;
                    System.out.println("send to email address is : " + deserializeMailObject.get(i).getToMailID());
                    System.out.println("send mail subject is :  " + deserializeMailObject.get(i).getSubject());
                    System.out.println("send mail body is : " + deserializeMailObject.get(i).getBody());
                }

            }
            //check is the current date have no sent mails
            if (!isSendMail) {
                System.out.println("No emails sent on the input date");
            }
        } else {
            System.out.println("No emails sent on the input date");
        }
    }

    //return the number of recipient objects in the application
    public int getRecipientObjectsInApplication(){
        return Recipient.getNoOfRecipient();
    }

}

