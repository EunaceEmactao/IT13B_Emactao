package midterm;

import java.util.Scanner;

public class assignment_Palindrome {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        
        String name;
        System.out.println("Welcome to the play of is it a Palindrome or not!");
        System.out.println("Give me a word: ");
       
        name = in.nextLine();
        name = name.toLowerCase();

        int length = name.length();

        for (int a = 0, j = length - 1; a < j; a++, j--) {
            if (name.charAt(a) == name.charAt(j)) {
                System.out.println("That word is a Palindrome!");
                return;
            } } System.out.println("That word is not a Palindrome.");
    }}
