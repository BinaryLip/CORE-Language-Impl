
public class CompOpNode {
    int altNo;

    public CompOpNode() {
        this.altNo = 0;
    }

    /**
     * Parse a comparison operation.
     * @param t	A tokenizer whose position is at the start of a comparison operation.
     */
    public void parseCompOp(Tokenizer t) {
        int tknVal = t.currentTokenToInt();
        if (tknVal == 25) { // not equals
            this.altNo = 1;
        } else if (tknVal == 26) { // equals
            this.altNo = 2;
        } else if (tknVal == 27) { // greater than or equal to
            this.altNo = 3;
        } else if (tknVal == 28) { // less than or equal to
            this.altNo = 4;
        } else if (tknVal == 29) { // greater than
            this.altNo = 5;
        } else if (tknVal == 30) { // less than
            this.altNo = 6;
        } else {
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); // pop compOp token
    }

    /**
     * Pretty print a comparison operation.
     */
    public void printCompOp() {
        if (this.altNo == 1) {
            System.out.print("!=");
        } else if (this.altNo == 2) {
            System.out.print("==");
        } else if (this.altNo == 3) {
            System.out.print(">=");
        } else if (this.altNo == 4) {
            System.out.print("<=");
        } else if (this.altNo == 5) {
            System.out.print(">");
        } else if (this.altNo == 6) {
            System.out.print("<");
        }
    }

    /**
     * Calculates the comparison operator token.
     * @return the int representation of the comparison operator
     */
    public int compType() {
        return this.altNo;
    }

}
