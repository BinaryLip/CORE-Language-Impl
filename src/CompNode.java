
public class CompNode {
    FacNode facN1;
    CompOpNode compOpN;
    FacNode facN2;

    public CompNode() {
        this.facN1 = new FacNode();
        this.compOpN = new CompOpNode();
        this.facN2 = new FacNode();
    }

    /**
     * Parse a comparison statement.
     * @param t	A tokenizer whose position is at the start of a comparison statement.
     */
    public void parseComp(Tokenizer t) {
        if (t.currentTokenToInt() != 20) { // make sure "(" token
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); // pop "(" token
        this.facN1.parseFac(t);
        this.compOpN.parseCompOp(t);
        this.facN2.parseFac(t);
        if (t.currentTokenToInt() != 21) { // make sure ")" token
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); // pop ")" token
    }

    /**
     * Pretty print a comparison statement.
     */
    public void printComp() {
        System.out.print("( ");
        this.facN1.printFac();
        System.out.print(" ");
        this.compOpN.printCompOp();
        System.out.print(" ");
        this.facN2.printFac();
        System.out.print(" )");
    }

    /**
     * Evaluates a comparison statement.
     * @return the result of the comparison statement.
     */
    public boolean evalComp() {
        switch (this.compOpN.compType()) {
            case 1:
                return (this.facN1.evalFac() != this.facN2.evalFac());
            case 2:
                return (this.facN1.evalFac() == this.facN2.evalFac());
            case 3:
                return (this.facN1.evalFac() >= this.facN2.evalFac());
            case 4:
                return (this.facN1.evalFac() <= this.facN2.evalFac());
            case 5:
                return (this.facN1.evalFac() > this.facN2.evalFac());
            case 6:
                return (this.facN1.evalFac() < this.facN2.evalFac());
            default:
                System.err.println(
                        "Invalid comparison found during runtime. This shouldn't happen!");
                System.exit(-1);
                return false;
        }
    }
}
