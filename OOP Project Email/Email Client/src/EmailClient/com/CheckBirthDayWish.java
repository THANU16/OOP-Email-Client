package EmailClient.com;

import java.io.*;
import java.util.Scanner;

public class CheckBirthDayWish  {
    private File myFile = new File("src\\storeSendBDayWish.txt");


    //check for is the birthday wish send
    public boolean checkForSendBirthdayWish (String emailAddress){
        boolean isBirthdaySend = false;
        //read file
        try {
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) { // we read multiple detail
                String data = myReader.nextLine();
                if(data.equals(emailAddress)){
                    isBirthdaySend = true;
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return isBirthdaySend;

    }


    //Write emailID in our file, who's birthday wish send the email
    public void writeSendBirthdayWishEmailID(String emailAddress){

        // send birthday wish emailID wrote in the file
        try (FileWriter myFileWriter = new FileWriter(myFile, true);
            BufferedWriter b = new BufferedWriter(myFileWriter);
            PrintWriter p = new PrintWriter(b)) {
            p.println(emailAddress);
        }
        catch (IOException i) {
            i.printStackTrace();
        }

    }


}
