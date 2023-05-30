package ir.ac.kntu;

import java.util.Scanner;

public class Get {
    public static String getString() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        return input;
    }

    public static double getDouble() {
        Scanner sc = new Scanner(System.in);
        double input = sc.nextDouble();
        return input;
    }

    public static int getInt() {
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        return input;
    }
}
