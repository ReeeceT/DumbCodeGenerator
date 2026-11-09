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

        gen.createFileFromPath("generation/Main.java");
        gen.writeToFile("generation/Main.java");
        gen.generateMain();
        gen.closeWriter();

        gen.createFileFromPath("generation/HardcodedCalculator.java");
        gen.writeToFile("generation/HardcodedCalculator.java");
        gen.closeWriter();
        
    }
}
