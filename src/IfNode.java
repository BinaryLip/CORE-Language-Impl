
public class IfNode {
    CondNode condition;
    StmtSeqNode thenSeq;
    StmtSeqNode elseSeq;
    int altNo;

    public IfNode() {
        this.condition = new CondNode();
        this.thenSeq = new StmtSeqNode();
        this.elseSeq = null;
        this.altNo = 1;
    }

    /**
	 * Parse an if statement.
	 * @param t	A tokenizer whose position is at the start of an if statement.
	 */
    public void parseIf(Tokenizer t) {
        t.nextToken(); // pop "if" token
        this.condition.parseCond(t);
        if (t.currentTokenToInt() != 6) { //verify "then" token
            System.err.println("Error: Expecting \"then\", got \""
                    + t.currentToken() + "\"!");
            System.exit(-1);
        }
        t.nextToken(); // pop "then" token
        this.thenSeq.parseStmtSeq(t);
        if (t.currentTokenToInt() == 7) { // else clause
            t.nextToken(); // pop "else" token
            this.altNo = 2;
            this.elseSeq = new StmtSeqNode();
            this.elseSeq.parseStmtSeq(t);
        }
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
	 * Pretty print an if statement.
	 * @param indent	The number of spaces to indent the statement.
	 */
    public void printIf(int indent) {
        System.out.print("if ");
        this.condition.printCond();
        System.out.println(" then");
        this.thenSeq.printStmtSeq(indent + 2);
        if (this.altNo == 2) {
            System.out.format("%1$" + indent + "s", "");
            System.out.println("else");
            this.elseSeq.printStmtSeq(indent + 2);
        }
        System.out.format("%1$" + indent + "s", "");
        System.out.println("end;");
    }

    /**
     * Evaluates an if statement.
     */
    public void execIf() {
        if (this.condition.evalCond()) {
            this.thenSeq.execStmtSeq();
        } else if (this.altNo == 2) {
            this.elseSeq.execStmtSeq();
        }
    }
}
