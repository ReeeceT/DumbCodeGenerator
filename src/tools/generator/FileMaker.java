package src.tools.generator;

import src.tools.calculator.Calculator;
import src.tools.calculator.Calculator.Operations;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.io.IOException;

/*
    TODO:
    Builder design pattern is ideal because this class will generate multiple files with multiple different parameters and configurations.

        Generator.java
    Which file is being written to is controlled by this class, not the driver. This is because it can easily be automated.
    For example you can set the file youre editing with .onFile(), then finish and finally wirte to it with .generate()

    Make a single array which will contain everything

    Builder design pattern which will plug in certain things into specific areas, like the boilerplate
    Something will scan through the array to find when non boilerplate code can begin and where it will end


        Files.java
    Creation of files

*/

// Manage creation of files and putting text in there
public class FileMaker {
    private File f;
    private BufferedWriter writer;

    public FileMaker() {
        f = null;
        writer = null;
    }

    // Manage creating the arrays of text which the writer will write.
    public class TextBuilder {
        private ArrayList textBuffer; // Base, formatted boilerplate requires this many lines

        public TextBuilder() {
            textBuffer = new ArrayList<String>(11);
        }
    }   
}
