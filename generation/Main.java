package results;

import java.util.Scanner;

public class Main {

private static Scanner s = new Scanner(System.in);

private static int a;private static int b;private static String op;

    public static void main(String[] args) {
        System.out.print("Input operand #1: ");
        a = Integer.getInteger(s.nextLine());
        System.out.print("Input operand #2: ");
        b = Integer.getInteger(s.nextLine());
        System.out.print("Choose operator (add, sub, mul, div): ");
        op = s.nextLine();
        System.out.println("Calculating...");

        new HardcodedCalculator(int a, int b, String op);
    }
}