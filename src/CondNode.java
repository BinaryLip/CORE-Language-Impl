
public class CondNode {
    CompNode compN;
    CondNode condN1;
    CondNode condN2;
    int altNo;

    public CondNode() {
        this.compN = null;
        this.condN1 = null;
        this.condN2 = null;
        this.altNo = 1;
    }

    /**
     * Parse a condition statement.
     * @param t	A tokenizer whose position is at the start of an condition statement.
     */
    public void parseCond(Tokenizer t) {
        int tknVal = t.currentTokenToInt();
        if (tknVal == 20) { // "(" token, normal comp
            this.compN = new CompNode();
            this.compN.parseComp(t);
            this.altNo = 1;
        } else if (tknVal == 17) { // "!" token, not'd cond
            t.nextToken(); // pop "!" token
            this.condN1 = new CondNode();
            this.condN1.parseCond(t);
            this.altNo = 2;
        } else if (tknVal == 18) { // "[" token, && or || cond
            t.nextToken(); // pop "[" token
            this.condN1 = new CondNode();
            this.condN1.parseCond(t);
            tknVal = t.currentTokenToInt(); // get and || or token
            if (tknVal == 12) { // "and" token
                t.nextToken(); // pop "and" token
                this.condN2 = new CondNode();
                this.condN2.parseCond(t);
                t.nextToken(); // pop "]" token
                this.altNo = 3;
            } else if (tknVal == 13) { // "or" token
                t.nextToken(); // pop "or" token
                this.condN2 = new CondNode();
                this.condN2.parseCond(t);
                t.nextToken(); // pop "]" token
                this.altNo = 4;
            } else {
                System.err.println("Error: Unexpected token " + t.currentToken()
                        + " on line " + t.lineNumber() + ".");
                System.exit(-1);
            }
        } else {
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
    }

    /**
     * Pretty print a condition statement.
     */
    public void printCond() {
        if (this.altNo == 1) { //comp
            this.compN.printComp();
        } else if (this.altNo == 2) { // !cond
            System.out.print("!");
            this.condN1.printCond();
        } else if (this.altNo == 3) { // and
            System.out.print("[ ");
            this.condN1.printCond();
            System.out.print(" and ");
            this.condN2.printCond();
            System.out.print(" ]");
        } else if (this.altNo == 4) { // or
            System.out.print("[ ");
            this.condN1.printCond();
            System.out.print(" or ");
            this.condN2.printCond();
            System.out.print(" ]");
        }
    }

    /**
     * Evaluates a condition statement.
     * @return the result of the condition statement
     */
    public boolean evalCond() {
        switch (this.altNo) {
            case 1:
                return this.compN.evalComp();
            case 2:
                return !this.condN1.evalCond();
            case 3:
                return (this.condN1.evalCond() && this.condN2.evalCond());
            case 4:
                return (this.condN1.evalCond() || this.condN2.evalCond());
            default:
                System.err.println(
                        "Invalid condition found during runtime. This shouldn't happen!");
                System.exit(-1);
                return false;
        }
    }
}
