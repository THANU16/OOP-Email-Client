package EmailClient.com;

import java.io.*;
import java.util.ArrayList;

public class SendMailHistory {


    //create Serialize file or open
    File serializeFile = new File("SerializableFile.ser");


    //Serialization
    public void serialization( ArrayList<Mail> tempMailObjects,Mail mail) {
        // Serialization
        try {
            //Saving of object in a SerializableFile
            FileOutputStream fileOut = new FileOutputStream(serializeFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            // Method for serialization of object
            for (int i = 0; i < tempMailObjects.size(); i++) {
                out.writeObject(tempMailObjects.get(i));
                out.flush();
            }
            out.writeObject(mail);
            out.flush();

            out.close();
            fileOut.close();

            System.out.println("Object has been serialized");
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }


    // Deserialization
    public ArrayList<Mail> deserialization(ArrayList<Mail> tempMailObjects) throws IOException {
        // Deserialization
        if(serializeFile.length() != 0) {

            // Deserialize of multiple object
            // Reading the Multiple object from a file
            // store the mail object in ArrayList
            FileInputStream file = new FileInputStream(serializeFile);
            ObjectInputStream in = new ObjectInputStream(file);
            while (true) {
                // Method for deserialization of object
                try {
                    Mail mailDeserialization = (Mail) in.readObject();
                    tempMailObjects.add( mailDeserialization); // add the mail object in ArrayList
                } catch (EOFException | ClassNotFoundException e) {
                    break;
                }
            }
            in.close();
            file.close();
        }
        return tempMailObjects;
    }

}

