package src;

import java.io.File;
import java.nio.file.Path;
import java.text.DecimalFormat;

import src.tools.calculator.Calculator;
import src.tools.calculator.Calculator.Operations;
import src.tools.generator.Generator;
import src.tools.generator.FileMaker;
import src.tools.generator.FileMaker.TextBuilder;
import src.tools.generator.FileMaker.TextContent;


public class Driver {
    private FileMaker fM;
    private String[] args;

    public Driver() {
        fM = new FileMaker();
        args = null;

        execute();
    }

    public void execute() {
        fM.cd("generation");
        fM.createFile("fM.txt");
        fM.writeToFile("fM.txt", fM.new TextContent("wasssah\ndesdwdwdwd\n lolololol"));
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
