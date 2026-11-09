package results;

import java.util.Scanner;

public class Main {

private static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Input operand #1: ");
        s.nextLine();
        System.out.print("Input operand #2: ");
        s.nextLine();
        System.out.print("Choose operator (add, sub, mul, div): ");
        s.nextLine();
        System.out.println("Calculating...");

        new HardcodedCalculator();
    }
}