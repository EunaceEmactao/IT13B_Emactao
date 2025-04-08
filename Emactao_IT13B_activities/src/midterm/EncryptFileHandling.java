/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package midterm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EncryptFileHandling {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File myFile = new File("C:\\Users\\Eunace Faith Emactao\\OneDrive\\Desktop\\EmactaoFile.txt");
        Scanner in = new Scanner(myFile);
        System.out.println("Encrypted Message:");
        while (in.hasNextLine()) {
            String data = in.nextLine();

            int key = 6;
            String message = data;

            String encryptedMessage = encryptMessage(message, key);

            System.out.println(encryptedMessage);

            FileWriter myWriter = new FileWriter("C:\\Users\\Eunace Faith Emactao\\OneDrive\\Desktop\\Encrypted.txt");
            myWriter.write(encryptMessage(message, key));
            myWriter.close();
        }
    }

    public static String encryptMessage(String message, int key) {
        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] += key;
        }
        return new String(chars);

    }
        
    }





