package Prelim;

import java.util.Scanner;

public class Lab_act1_Arithmetics {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Input the first number = ");
        int myFirst;
        myFirst = in.nextInt();

        System.out.println("Input the second number = ");
        int mySecond;
        mySecond = in.nextInt();

        System.out.println("Input the third number = ");
        int myThird;
        myThird = in.nextInt();

        int myFourth = 3;

        System.out.println(myFirst + " * " + mySecond + " " + "+" + " " + myThird + " = " + "" + ((myFirst * mySecond) + myThird));
        System.out.println("(" + myFirst + " - " + mySecond + ")" + " % " + "" + myThird + " = " + "" + ((myFirst - mySecond) % myThird));
        System.out.println("(" + myFirst + " " + "+" + " "+ mySecond + " " + "+" + " " + myThird + ")" + "/" + myFourth + " " + "=" + " " + ((myFirst + mySecond + myThird) / myFourth));
        System.out.println(myFirst + " " + "*" + " " + myThird + " " + "-" + " " + "(" + mySecond + " " + "*" + " "+ mySecond + ")" +  " " + "=" + " " + (myFirst * myThird - mySecond * mySecond));
    }
}
