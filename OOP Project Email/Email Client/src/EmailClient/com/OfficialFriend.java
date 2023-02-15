package EmailClient.com;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//user can create official friend recipients
public class OfficialFriend extends OfficialRecipient implements BirthDayWishSendable{
    private LocalDate birthDate;


    public OfficialFriend(String name, String emailAddress, String designation, LocalDate birthDate) {
        super(name, emailAddress, designation);
        this.birthDate = birthDate;
    }

    // get the official friend recipient Birth of date
    public LocalDate getDate (){
        return birthDate;
    }

    @Override
    public String getBDayWish() {
        return "Wish you a Happy Birthday by Thanu ";
    }
}
