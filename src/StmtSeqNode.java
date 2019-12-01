
public class StmtSeqNode {
    StmtNode stmtN;
    StmtSeqNode stmtSeqN;
    int altNo;

    public StmtSeqNode() {
        this.stmtN = new StmtNode();
        this.stmtSeqN = null;
        this.altNo = 0;
    }

    /**
	 * Parse a statement sequence.
	 * @param t	A tokenizer whose position is at the start of a statement sequence.
	 */
    public void parseStmtSeq(Tokenizer t) {
        // check to make sure there is a proper token then parse
        int tknVal = t.currentTokenToInt();
        if (tknVal == 5 || tknVal == 8 || tknVal == 10 || tknVal == 11
                || tknVal == 32) {
            this.altNo = 1;
            this.stmtN.parseStmt(t);
        } else {
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        // if another stmt create new stmt seq object
        tknVal = t.currentTokenToInt();
        if (tknVal == 5 || tknVal == 8 || tknVal == 10 || tknVal == 11
                || tknVal == 32) {
            this.altNo = 2;
            this.stmtSeqN = new StmtSeqNode();
            this.stmtSeqN.parseStmtSeq(t);
        }
    }

    /**
	 * Pretty print a statement sequence.
	 * @param indent	The number of spaces to indent the statement.
	 */
    public void printStmtSeq(int indent) {
        if (this.altNo > 0) {
            this.stmtN.printStmt(indent);
            if (this.altNo == 2) {
                this.stmtSeqN.printStmtSeq(indent);
            }
        }
    }

    /**
     * Executes a statement sequence.
     */
    public void execStmtSeq() {
        if (this.altNo == 1) {
            this.stmtN.execStmt();
        } else if (this.altNo == 2) {
            this.stmtN.execStmt();
            this.stmtSeqN.execStmtSeq();
        }
    }
}
