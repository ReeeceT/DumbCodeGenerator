package src;

import java.text.DecimalFormat;

import src.tools.calculator.Calculator;
import src.tools.calculator.Calculator.Operations;
import src.tools.generator.Generator;
import java.text.DecimalFormat;

public class Driver {
    private Calculator calc;
    private Generator gen;
    private final DecimalFormat df;

    public Driver(int min, int max) {
        calc = new Calculator(min, max);
        gen = new Generator(calc);
        df = new DecimalFormat("#0.######");

        // Generate a main file with imports
        gen.createFileFromPath("generation/main.j", 20);
        gen.setWritingToFile("generation/main.j");
        gen.generateBoilerPlate("generation", 5, "main", true);

        gen.beginWriting();
        System.out.println("\n\n\n Non BP Begin: " + gen.getNonBPBegin());
        System.out.println("\n\n\n Non BP End: " + gen.getNonBPEnd());
    }
}
