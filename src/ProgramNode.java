
public class ProgramNode {
    DeclSeqNode declSeqN;
    StmtSeqNode stmtSeqN;

    public ProgramNode() {
        this.declSeqN = new DeclSeqNode();
        this.stmtSeqN = new StmtSeqNode();
    }

    /**
	 * Parse the program.
	 * @param t	A tokenizer whose position is at the start of a program.
	 */
    public void parseProgram(Tokenizer t) {
        if (t.currentTokenToInt() != 1) { // make sure "program" token
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); //skip "program"
        this.declSeqN.parseDeclSeq(t);
        if (t.currentTokenToInt() != 2) { // make sure "begin" token
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); //skip "begin"
        this.stmtSeqN.parseStmtSeq(t);
        if (t.currentTokenToInt() != 3) { // make sure "end" token
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); //skip "end"
        if (t.currentTokenToInt() != 33) { // make sure "eof" token
            System.err.println(
                    "Error: Expected end of file, but got \"" + t.currentToken()
                            + "\" on line " + t.lineNumber() + " instead.");
            System.exit(-1);
        }
    }

    /**
	 * Pretty print a program.
	 */
    public void printProgram() {
        System.out.println("program");
        this.declSeqN.printDeclSeq(2);
        System.out.println("  begin");
        this.stmtSeqN.printStmtSeq(4);
        System.out.println("  end");
    }

    /**
     * Executes a program.
     */
    public void execProgram() {
        // decSeq doesn't execute anything. All logic happens during parsing.
        this.stmtSeqN.execStmtSeq();
    }
}
