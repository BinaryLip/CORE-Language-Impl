
public class AssignNode {
    IdNode idN;
    ExprNode exprN;

    public AssignNode() {
        this.idN = null;
        this.exprN = new ExprNode();
    }

    /**
     * Parse an assignment statement.
     * @param t	A tokenizer whose position is at the start of an assignment statement.
     */
    public void parseAssign(Tokenizer t) {
        this.idN = IdNode.parseIdNode(t);
        if (!this.idN.isDeclared()) {
            System.err.println(
                    "Error: " + this.idN.getName() + " has not been declared!");
            System.exit(-1);
        }
        if (t.currentTokenToInt() != 16) { //make sure = sign
            System.err.println("Error: Expected \"=\", got \""
                    + t.currentToken() + "\" on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); // skip = sign
        this.exprN.parseExpr(t);
        if (t.currentTokenToInt() != 14) { // check for ";" token
            System.err.println("Error: Expected \";\", got \""
                    + t.currentToken() + "\" on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); // pop ";" token
    }

    /**
     * Pretty print an assignment statement.
     */
    public void printAssign() {
        this.idN.printIdNode();
        System.out.print(" = ");
        this.exprN.printExpr();
        System.out.println(";");
    }

    /**
     * Execute an assignment statement.
     */
    public void execAssign() {
        int value = this.exprN.evalExpr();
        this.idN.setValue(value);
    }
}
