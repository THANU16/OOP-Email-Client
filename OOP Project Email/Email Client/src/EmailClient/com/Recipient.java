package EmailClient.com;

// user can't create the object in Recipients so, we make abstarct class
public abstract class Recipient {
    private String name;
    private String emailAddress;
    private static int noOfRecipient; // We have to count number of recipients

    // create constructor
    public Recipient(String name, String emailAddress){
        this.name = name;
        this.emailAddress = emailAddress;
        noOfRecipient = getNoOfRecipient() + 1;
    }


    // get the recipient name
    public String getName (){
        return name;
    }

    //get the recipient email address
    public String getEmailAddress (){
        return emailAddress;
    }

    //get the number of recipient
    public static int getNoOfRecipient(){
        return noOfRecipient;
    }
}
