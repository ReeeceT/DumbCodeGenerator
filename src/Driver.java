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

        execute();
    }

    public void execute() {
        gen.newBPGenerator()


        // // Generate a main file with imports
        // gen.createFileFromPath("generation/YesMainYesImports.txt", 20);
        // gen.setWritingToFile("generation/YesMainYesImports.txt");
        // gen.generateBoilerPlate("generation", 5, "YesMainYesImports", true);
        // gen.beginWriting();

        // System.out.println("\n\n\n Non BP Begin: " + gen.getNonBPBegin());
        // System.out.println("\n\n\n Non BP End: " + gen.getNonBPEnd());

        // // Generate a main file with NO imports
        // gen.createFileFromPath("generation/YesMainNoImports.txt", 20);
        // gen.setWritingToFile("generation/YesMainNoImports.txt");
        // gen.generateBoilerPlate("generation", "YesMainNoImports", true);
        // gen.beginWriting();

        // System.out.println("\n\n\n Non BP Begin: " + gen.getNonBPBegin());
        // System.out.println("\n\n\n Non BP End: " + gen.getNonBPEnd());

        // // Generate a NON-main file with imports
        // gen.createFileFromPath("generation/NoMainYesImports.txt", 20);
        // gen.setWritingToFile("generation/NoMainYesImports.txt");
        // gen.generateBoilerPlate("generation", 5, "NoMainYesImports", false);
        // gen.beginWriting();

        // System.out.println("\n\n\n Non BP Begin: " + gen.getNonBPBegin());
        // System.out.println("\n\n\n Non BP End: " + gen.getNonBPEnd());

        // // Generate a NON-main file with NO imports
        // gen.createFileFromPath("generation/NoMainNoImports.txt", 20);
        // gen.setWritingToFile("generation/NoMainNoImports.txt");
        // gen.generateBoilerPlate("generation", "NoMainNoImports", false);
        // gen.beginWriting();
    
        // System.out.println("\n\n\n Non BP Begin: " + gen.getNonBPBegin());
        // System.out.println("\n\n\n Non BP End: " + gen.getNonBPEnd());
    }
}
