package EmailClient.com;

//user can create official recipients
public class OfficialRecipient extends Recipient {
    private String designation;

    public OfficialRecipient(String name, String emailAddress, String designation) {
        super(name, emailAddress);
        this.designation = designation;
    }


    // get the official recipient designation
    public String getDesignation (){
        return designation;
    }
}


