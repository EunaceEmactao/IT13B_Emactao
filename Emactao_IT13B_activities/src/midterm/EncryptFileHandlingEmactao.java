/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package midterm;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Eunace Faith Emactao
 */
public class EncryptFileHandlingEmactao {
    public static void main(String[] args) {
        try {
            FileWriter myText = new FileWriter("C:\\Users\\Eunace Faith Emactao\\OneDrive\\Desktop\\EmactaoInput.txt");
            myText.write("I love you!\nGwapa ko!\nBuotan si Ma'am\n");
            myText.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }public static char encryptChar(char ch) {
        return (char)(ch + 1);
    }

    public static void encrypt (String[] args) {
        String inputFile = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Desktop\\EmactaoInput.txt";
        String outputFile = "C:\\Users\\Eunace Faith Emactao\\OneDrive\\Desktop\\EmactaoEncrypted.txt";

        try (
            FileReader fr = new FileReader(inputFile);
            FileWriter fw = new FileWriter(outputFile)
        ) {
            int data;
            while ((data = fr.read()) != -1) {
                char ch = (char) data;
                fw.write(encryptChar(ch)); 
            }

            System.out.println("Encryption complete. Output written to: " + outputFile);

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
