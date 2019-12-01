
public class TermNode {
    FacNode facN;
    TermNode termN;
    int altNo;

    public TermNode() {
        this.facN = new FacNode();
        this.termN = null;
        this.altNo = 1;
    }

    /**
	 * Parse a term statement.
	 * @param t	A tokenizer whose position is at the start of a term statement.
	 */
    public void parseTerm(Tokenizer t) {
        this.facN.parseFac(t);
        this.altNo = 1;
        if (t.currentTokenToInt() == 24) { // check for "*" token
            t.nextToken(); // pop "*" token
            this.termN = new TermNode();
            this.termN.parseTerm(t);
            this.altNo = 2;
        }
    }

    /**
	 * Pretty print a term statement.
	 */
    public void printTerm() {
        this.facN.printFac();
        if (this.altNo == 2) {
            System.out.print(" * ");
            this.termN.printTerm();
        }
    }

    /**
     * Evalutes a term statement.
     * @return The result of the term statement.
     */
    public int evalTerm() {
        if (this.altNo == 1) {
            return this.facN.evalFac();
        } else if (this.altNo == 2) {
            int fac = this.facN.evalFac();
            int term = this.termN.evalTerm();
            if (!this.checkMultiply(fac, term)) {
                return fac * term;
            } else {
                System.err.println("Integer Overflow when trying to multiply "
                        + fac + " * " + term);
                System.exit(-1);
                return 0;
            }
        } else {
            System.err.println(
                    "Invalid expression found during runtime. This shouldn't happen!");
            System.exit(-1);
            return 0;
        }
    }

    /**
     * Return true if multiplication causes overflow
     */
    private boolean checkMultiply(int fac, int term) {
        long temp = fac * term;
        return (temp > Integer.MAX_VALUE);
    }
}
