
public class FacNode {
    int value; // <int>
    IdNode idN; // <id>
    ExprNode exprN; // ( <exp> )
    int altNo;

    public FacNode() {
        this.idN = null;
        this.exprN = null;
        this.altNo = 1;
    }

    /**
	 * Parse a factor statement.
	 * @param t	A tokenizer whose position is at the start of an factor statement.
	 */
    public void parseFac(Tokenizer t) {
        int tknVal = t.currentTokenToInt();
        if (tknVal == 31) { // integer token
            this.value = Integer.parseInt(t.currentToken());
            this.altNo = 1;
            t.nextToken(); // move past the first token of the next sequence
        } else if (tknVal == 32) { // id token
            this.idN = IdNode.parseIdNode(t);
            if (!this.idN.isDeclared()) { //make sure variable is declared
                System.err.println("Error: " + this.idN.getName()
                        + " has not been declared!");
                System.exit(-1);
            }
            this.altNo = 2;
        } else if (tknVal == 20) { // "(" token
            t.nextToken(); // pop "(" token
            this.exprN = new ExprNode();
            this.exprN.parseExpr(t);
            if (t.currentTokenToInt() != 21) { // check for ")" token
                System.err.println("Error: Unexpected token " + t.currentToken()
                        + " on line " + t.lineNumber() + ".");
                System.exit(-1);
            }
            t.nextToken(); // pop ")" token
            this.altNo = 3;
        } else {
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
    }

    /**
	 * Pretty print a factor statement.
	 */
    public void printFac() {
        if (this.altNo == 1) { // int
            System.out.print(this.value);
        } else if (this.altNo == 2) { // id
            this.idN.printIdNode();
        } else if (this.altNo == 3) { // expr
            System.out.print("( ");
            this.exprN.printExpr();
            System.out.print(" )");

        }
    }

    /**
     * Evaluates the factor statement.
     * @return the result of the factor statement
     */
    public int evalFac() {
        switch (this.altNo) {
            case 1:
                return this.value;
            case 2:
                if (this.idN.isAssigned()) {
                    return this.idN.getValue();
                } else {
                    System.err.println("Trying to use variable "
                            + this.idN.getName()
                            + ", but it has not been assigned a value.");
                    System.exit(-1);
                    return 0;
                }
            case 3:
                return this.exprN.evalExpr();
            default:
                System.err.println(
                        "Invalid expression found during runtime. This shouldn't happen!");
                System.exit(-1);
                return 0;
        }
    }
}
