/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prelim;

import java.util.Scanner;

public class Lab_act2_FindTheGreatestNumber {
      public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int x, y, z;
        System.out.println("Input the 1st number");
        x = in.nextInt();
        System.out.println("Input the 2nd number");
        y = in.nextInt();
        System.out.println("Input the 3rd number");
        z = in.nextInt();
        int greatest = x;

        if (y > greatest) {
            greatest = z;
        }
        if (z > greatest) {

        }
        System.out.println("The greatest number is" + " " + greatest);
        in.close();


      
    }
}
