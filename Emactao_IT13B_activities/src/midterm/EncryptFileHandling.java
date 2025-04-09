
package midterm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EncryptFileHandling {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        try (FileWriter myWriter = new FileWriter("C:\\Users\\Eunace Faith Emactao\\OneDrive\\Desktop\\EmactaoInput1.txt")) {
            myWriter.write("I love you!\nGwapa ko!\nBuotan si Ma'am.");
        }

        File myFile = new File("C:\\Users\\Eunace Faith Emactao\\OneDrive\\Desktop\\EmactaoInput1.txt");
        Scanner in = new Scanner(myFile);

        System.out.println("Encrypted Message:");
        while (in.hasNextLine()) {
            String data = in.nextLine();
            int key = 6;
            String message = data;
            String encryptedMessage = encryptMessage(message, key);
            System.out.println(encryptedMessage);

            try (FileWriter myWriter = new FileWriter("C:\\Users\\Eunace Faith Emactao\\OneDrive\\Desktop\\EmactaoEncrypted1.txt", true)) {
                myWriter.write(encryptedMessage + "\n");
        }
    }}

    public static String encryptMessage(String message, int key) {
        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] += key;
        }
        return new String(chars);

    }
        
    }





