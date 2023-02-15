package EmailClient.com;
import java.time.LocalDate;


//user can create personal recipients
public class PersonalRecipient extends Recipient implements BirthDayWishSendable{
    private String nickName;
    private LocalDate birthDate;


    public PersonalRecipient(String name, String nickName, String emailAddress, LocalDate birthDate) {
        super(name, emailAddress);
        this.nickName = nickName;
        this.birthDate = (birthDate);
    }

    public PersonalRecipient(String name, String emailAddress, LocalDate birthDate) {
        super(name, emailAddress);
        this.nickName = null;
        this.birthDate = birthDate;
    }

    // get the personal recipient nickName
    public String getNickName (){
        return nickName;
    }

    // get the personal recipients birth of date
    public LocalDate getDate (){
        return birthDate;
    }

    @Override
    public String getBDayWish() {
        return "Hugs and love on your birthday by Thanu";
    }
}
