package src.tools.generator;

import java.util.ArrayList;

// Manage creating the arrays of text which the writer will write.
// Builder design pattern which constructs the text for a file.
public class TextBuilder {
    private final int BOILER_PLATE_LINES;
    private TextContent textBuffer;

    private ArrayList<String> textList;
    private String text;

    public TextBuilder() {
        textBuffer = new TextContent();
        BOILER_PLATE_LINES = 11;
    }

    public TextBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public TextBuilder asStringList() {
        this.textList = toStringList(text);
    }

    public TextContent build() {
        return textBuffer;
    }

    private TextBuilder toStringList(String text) {
            final char[] chars = text.toCharArray();
            final int maxCapacity = 50;
            int wordLength = 0;

            StringBuilder word = new StringBuilder(maxCapacity);

            for (int i = 0; i < chars.length; i++) {
                switch (chars[i]) {
                    case ' ':
                        textList.add(word.toString() + " ");
                        word.delete(0, 50);
                        wordLength = 0;
                        break;
                    case '\n':
                        textList.add(word.toString());
                        word.delete(0, 50);
                        wordLength = 0;

                        textList.add("\n");
                        word.delete(0, 50);
                        break;
                    default:
                        wordLength++;
                        word.append(chars[i]);
                        break;
                }

                if (wordLength >= maxCapacity) {
                    System.out.println("ERR: Word length greater than " + maxCapacity + " not allowed");
                    break;
                }
            }

            textList.add(word.toString());
            word.delete(0, 50);

            return textList;
        }
    
    private class TextContent {        
        public TextContent() {
            textList = new ArrayList<String>(BOILER_PLATE_LINES);
            text = "";
        }

        // TODO: Maybe change these out. Methods that take in a TextContent type will grab the data they need for the user depending on what TextContent contains.
        ArrayList<String> getTextList() {
            return null;
        }

        String getText() {
            return null;
        }
    }

}   
