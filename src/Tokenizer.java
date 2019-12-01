
// Written by Phillip Loveland
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {
    private BufferedReader stream;
    private int currentLine;
    private String currentToken;
    private int currentTokenInt;

    // stored in arraylist since token value is easily calculated based on index
    private static final ArrayList<String> RESERVED_WORDS = new ArrayList<>();
    private static final ArrayList<String> SPECIAL_SYMBOLS = new ArrayList<>();

    // initialize the token arrays
    static {
        RESERVED_WORDS.addAll(
                Arrays.asList("program", "begin", "end", "int", "if", "then",
                        "else", "while", "loop", "read", "write", "and", "or"));
        SPECIAL_SYMBOLS.addAll(Arrays.asList(";", ",", "=", "!", "[", "]", "(",
                ")", "+", "-", "*", "!=", "==", ">=", "<=", ">", "<"));
    }

    /*
     * Instaniate the Tokenizer object and read in first token Input: a
     * BufferedReader of the core file
     */
    public Tokenizer(BufferedReader in) {
        this.stream = in;
        this.currentLine = 1;
        this.nextToken();
    }

    /*
     * Gets the next token and leaves the BufferedReader at the start of the next token
     *
     */
    public void nextToken() {
        StringBuilder builder = new StringBuilder();
        int c, cOld = 0;
        boolean specialSymbolFlag = false; // needed for determining if token is !=, ==, <=, or >=
        try {
            c = this.stream.read(); // read in a character

            while (c != -1) {
                // check if char is a single special symbol
                int symbol = SPECIAL_SYMBOLS
                        .indexOf(Character.toString((char) c));
                if (symbol > -1) {
                    specialSymbolFlag = true;
                }

                // if c is a whitespace, keep reading until no more whitespace
                if (Character.isWhitespace(c)) {
                    if (c == '\n') { // updates line number for lf and crlf
                        this.currentLine++;
                    }
                    cOld = c; // store current char
                    this.stream.mark(1); // save point for buffer if gone too far
                    c = this.stream.read();
                    while (Character.isWhitespace(c) && c != -1) {
                        if (c == '\n') { // update line number for lf and crlf
                            this.currentLine++;
                        }
                        this.stream.mark(1);
                        c = this.stream.read();
                    }
                    // read 1 char too far. roll back 1 character
                    this.stream.reset();
                    c = cOld;
                    break;
                } else if (symbol > -1 && builder.length() > 0) { // special symbol next to token catch
                    // if there's already been a char read in and then a special symbol
                    if (symbol != 2 || builder.length() > 1) {
                        // gone too far. read in an extra token
                        this.stream.reset();
                        c = cOld;
                        break;
                    } else if (SPECIAL_SYMBOLS
                            .indexOf(Character.toString((char) cOld)) != -1) {
                        // comparator token
                        builder.append((char) c);
                        break;
                    } else {
                        // gone too far. single char token
                        this.stream.reset();
                        c = cOld;
                        break;
                    }

                }
                //special char and then another char
                if (specialSymbolFlag && builder.length() == 1) {
                    this.stream.reset();
                    c = cOld;
                    break;
                }
                builder.append((char) c);
                cOld = c; // store previous char
                this.stream.mark(1); // save point
                c = this.stream.read();
            }

            if (builder.length() < 1 && c == -1) { // haven't read in a new token and end of file
                this.currentToken = "";
                this.currentTokenInt = 33;
            } else { // check valid token and update
                this.currentToken = builder.toString(); //update current token
                this.currentTokenInt = this.tokenToInt(); //validate and update current token int
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Converts this.currentToken into it's integer representation
     * @return the int representation of the current token
     */
    private int tokenToInt() {
        // check if accidental whitespace token
        if (this.currentToken.trim().length() == 0) {
            // get next token
            this.nextToken();
            return this.tokenToInt();
        } else {
            // check if reserved word
            int reserved = RESERVED_WORDS.indexOf(this.currentToken);
            if (reserved != -1) {
                return reserved + 1;
            } else {
                // check if special symbol
                int special = SPECIAL_SYMBOLS.indexOf(this.currentToken);
                if (special != -1) {
                    return special + 14;
                } else {
                    // check if valid integer
                    if (this.currentToken.matches("\\A\\d{1,8}\\z")) {
                        return 31;
                    } else {
                        // check if identifier & error
                        if (this.currentToken.length() > 9
                                && this.currentToken.matches("\\A[A-Z]+\\d*")) {
                            //invalid identifier token
                            System.err.println(
                                    "Invalid token (identifier longer than 8 characters) on line "
                                            + this.currentLine + ": "
                                            + this.currentToken);
                            System.exit(-1);
                            return -1;
                        } else if (this.currentToken.length() > 9
                                && this.currentToken.matches("\\A\\d*")) {
                            //invalid integer token
                            System.err.println(
                                    "Invalid token (integer longer than 8 decimal digits) on line "
                                            + this.currentLine + ": "
                                            + this.currentToken);
                            System.exit(-1);
                            return -1;
                        } else if (this.currentToken.length() < 9
                                && this.currentToken.matches("\\A[A-Z]+\\d*")) {
                            // valid identifier token
                            return 32;
                        } else {
                            //invalid token
                            System.err.println(
                                    "Invalid token on line " + this.currentLine
                                            + ": " + this.currentToken);
                            System.exit(-1);
                            return -1;
                        }
                    }
                }
            }
        }
    }

    /**
     * Getter method for currentToken
     * @return a string containing the current token
     */
    public String currentToken() {
        return this.currentToken;
    }

    /**
     * Getter method for currentTokenInt 
     * @return  the current token's int representation
     */
    public int currentTokenToInt() {
        return this.currentTokenInt;
    }

    /**
     * Getter method for current line number.
     * @return the current line number the tokenizer is on
     */
    public int lineNumber() {
        return this.currentLine-1;
    }

}
