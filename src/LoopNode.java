
public class LoopNode {
    CondNode condition;
    StmtSeqNode stmtSeqN;

    public LoopNode() {
        this.condition = new CondNode();
        this.stmtSeqN = new StmtSeqNode();
    }

    /**
	 * Parse a while loop statement.
	 * @param t	A tokenizer whose position is at the start of a while loop statement.
	 */
    public void parseLoop(Tokenizer t) {
        if (t.currentTokenToInt() != 8) { // check "while" token
            System.err.println("Error: Expected \"while\", got \""
                    + t.currentToken() + "\" on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); // pop "while" token
        this.condition.parseCond(t);
        if (t.currentTokenToInt() != 9) { // check "loop" token
            System.err.println("Error: Expected \"loop\", got \""
                    + t.currentToken() + "\" on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); // pop "loop" token
        this.stmtSeqN.parseStmtSeq(t);
        int endLine = t.lineNumber();
        if (t.currentTokenToInt() == 3) { // check for "end" token
            t.nextToken(); // pop "end" token
            if (t.currentTokenToInt() != 14) { // check for ";" token
                System.err.println("Error: Expected \"end;\", got \"end"
                        + t.currentToken() + "\" on line " + endLine + ".");
                System.exit(-1);
            }
        } else {
            System.err.println("Error: Expected \"end\", got \""
                    + t.currentToken() + "\" on line " + endLine + ".");
            System.exit(-1);
        }

        t.nextToken(); // pop ";" token
    }

    /**
	 * Pretty print a while loop statement.
	 * @param indent	The number of spaces to indent the statement.
	 */
    public void printLoop(int indent) {
        System.out.print("while ");
        this.condition.printCond();
        System.out.println(" loop");
        this.stmtSeqN.printStmtSeq(indent + 2);
        System.out.format("%1$" + indent + "s", "");
        System.out.println("end;");
    }

    /**
     * Executes the loop statement.
     */
    public void execLoop() {
        while (this.condition.evalCond()) {
            this.stmtSeqN.execStmtSeq();
        }
    }
}
