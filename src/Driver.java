package src;

import java.io.File;
import java.nio.file.Path;
import java.text.DecimalFormat;

import src.tools.calculator.Calculator;
import src.tools.calculator.Calculator.Operations;
import src.tools.generator.Generator;
import src.tools.generator.FileMaker;


public class Driver {
    private Calculator calc;
    private FileMaker fM;
    
    private Path pathToFolder;

    public Driver(int min, int max) {
        calc = new Calculator(min, max);
        fM = new FileMaker();

        pathToFolder = Path.of("generation");

        execute();
    }

    public void execute() {
        fM.createFile("generation", "fM.txt");

        fM.writeToFile(fM.getFile("fM.txt"), "Wassup my brother!!!! oooohhhh yeah \nHellll yeahh");

        // fM.deleteFile("fM.txt");
    }
}
