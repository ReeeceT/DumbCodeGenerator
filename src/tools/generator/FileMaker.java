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

        FileMaker.java
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

    /**
     * 
     * Creates a file from a given path.
     * 
     * @param relPath - The path to the file which will be created. This is relative to this program's root directory
     * @param suffix - The type of file. (.txt, .java, .jav)
     * 
     * @return A reference to the created file
     */
    public File createFile(String relPath, String suffix) {
        f = new File(relPath + suffix);

        try {
            if (f.exists()) {
                System.out.println("File already exists. Overwriting instead.");
                f.delete();
            }
            f.createNewFile();
            return f;
        } catch (IOException e) { 
            System.out.println("vvv Could not create a file or overwrite an existing one vvv\n\n" + e.getMessage()); 
            return f;
        }
    }

    /**
     * 
     * Writes to a specified file
     * 
     * @param f - The file to be written to
     * @param text - The text to be written. Each index of the ArrayList writes to a new line in the file.
     */
    public void writeToFile(File f, ArrayList<String> text) {

    }

    /**
     * 
     * Writes to a specified file
     * 
     * @param f - The file to be written to
     * @param text - The text to be written
     */
    public void writeToFile(File f, String text) {

    }

    // Manage creating the arrays of text which the writer will write.
    public class TextBuilder {
        private ArrayList textBuffer; // Base, formatted boilerplate requires this many lines

        public TextBuilder() {
            textBuffer = new ArrayList<String>(11);
        }

        // Builder design pattern which constructs the text for a file.
    }   
}
