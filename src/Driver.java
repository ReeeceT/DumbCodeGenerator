package src;

import java.text.DecimalFormat;

import src.tools.calculator.Calculator;
import src.tools.calculator.Calculator.Operations;
import src.tools.generator.CodeGenerator;
import java.text.DecimalFormat;

public class Driver {
    private Calculator c;
    private CodeGenerator cG;
    private final DecimalFormat df;

    public Driver(int min, int max) {
        c = new Calculator(min, max);
        cG = new CodeGenerator(c);
        df = new DecimalFormat("#0.######");

        c.calculateOperation(Operations.ADDITION);
    }
}
