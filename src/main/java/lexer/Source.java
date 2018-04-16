package lexer;

import java.io.*;

class Source {

    private InputStream source;

    private int charCounter = 1, lineCounter = 1;

    private char currentChar;

    private void getNextChar() {
        try {
            char nextChar = (char) source.read();

            if (nextChar == '\n') {
                ++lineCounter;

                charCounter = 1;

                getNextChar();
            } else {
                ++charCounter;

                currentChar = nextChar;
            }

        } catch (IOException e) {
            System.out.println("Could not read from source");
            throw new RuntimeException(e);
        }
    }

    char peek() {
        return currentChar;
    }

    char nextChar() {
        char tempChar = currentChar;

        getNextChar();

        return tempChar;
    }

    Source(InputStream source) {
        this.source = source;

        nextChar();
    }

    Source(File fileSource) {
        try {
            source = new FileInputStream(fileSource);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file for scanner source");
            throw new RuntimeException(e);
        }

        nextChar();
    }

    boolean isEoF() {
        return (int) currentChar == -1;
    }

    public int getCharCounter() {
        return charCounter;
    }

    public int getLineCounter() {
        return lineCounter;
    }
}
