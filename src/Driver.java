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
        fM.createFile(pathToFolder, "fM.txt");
        fM.createFile(pathToFolder, "fM2.txt");

        fM.writeToFile(fM.getFile("fM.txt"), "WASSSUUUUUPPPPP");
        fM.writeToFile(fM.getFile("fM2.txt"), "BRAAHHHHH");

        fM.deleteFile("fM.txt");
    }
}
