
public class ExprNode {
    TermNode termN;
    ExprNode exprN;
    int altNo;

    public ExprNode() {
        this.termN = new TermNode();
        this.exprN = null;
        this.altNo = 1;
    }

    /**
	 * Parse an expression statement.
	 * @param t	A tokenizer whose position is at the start of an expression statement.
	 */
    public void parseExpr(Tokenizer t) {
        this.termN.parseTerm(t);
        if (t.currentTokenToInt() == 22) { // check for "+" token
            t.nextToken(); // pop "+" token
            this.altNo = 2;
            this.exprN = new ExprNode();
            this.exprN.parseExpr(t);
        } else if (t.currentTokenToInt() == 23) { // check for "-" token
            t.nextToken(); // pop "-" token
            this.altNo = 3;
            this.exprN = new ExprNode();
            this.exprN.parseExpr(t);
        }
    }

    /**
	 * Pretty print an expression statement.
	 */
    public void printExpr() {
        this.termN.printTerm();
        if (this.altNo == 2) {
            System.out.print(" + ");
            this.exprN.printExpr();
        } else if (this.altNo == 3) {
            System.out.print(" - ");
            this.exprN.printExpr();
        }
    }

    /**
     * Evaluates the expression statement.
     * @return the result of the expression statement.
     */
    public int evalExpr() {
        switch (this.altNo) {
            case 1:
                return this.termN.evalTerm();
            case 2:
                int term = this.termN.evalTerm();
                int expr = this.exprN.evalExpr();
                // check for overflow
                if (this.checkAdd(term, expr)) {
                    System.err.println("Integer Overflow when trying to add "
                            + term + " + " + expr);
                    System.exit(-1);
                    return 0;
                } else {
                    return term + expr;
                }
            case 3:
                int term1 = this.termN.evalTerm();
                int expr1 = this.exprN.evalExpr();
                // check for overflow
                if (this.checkSub(term1, expr1)) {
                    System.err.println("Integer Overflow when trying to add "
                            + term1 + " - " + expr1);
                    System.exit(-1);
                    return 0;
                } else {
                    return term1 - expr1;
                }
            default:
                System.err.println(
                        "Invalid expression found during runtime. This shouldn't happen!");
                System.exit(-1);
                return 0;
        }
    }

    /**
     * Returns true if overflow is caused
     */
    private boolean checkAdd(int term, int expr) {
        long temp = term + expr;
        if (temp > Integer.MAX_VALUE) {
            return true;
        } else if (temp < Integer.MIN_VALUE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if overflow is caused
     */
    private boolean checkSub(int term, int expr) {
        long temp = term - expr;
        if (temp > Integer.MAX_VALUE) {
            return true;
        } else if (temp < Integer.MIN_VALUE) {
            return true;
        } else {
            return false;
        }
    }
}
