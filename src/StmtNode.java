
public class StmtNode {
    AssignNode assignN;
    IfNode ifN;
    LoopNode loopN;
    InputNode inputN;
    OutputNode outputN;
    int altNo;

    public StmtNode() {
        this.assignN = null;
        this.ifN = null;
        this.loopN = null;
        this.inputN = null;
        this.outputN = null;
        this.altNo = 0;
    }

    /**
	 * Parse a statement.
	 * @param t	A tokenizer whose position is at the start of a statement.
	 */
    public void parseStmt(Tokenizer t) {
        int tknVal = t.currentTokenToInt();
        if (tknVal == 32) { //assign
            this.assignN = new AssignNode();
            this.altNo = 1;
            this.assignN.parseAssign(t);
        } else if (tknVal == 5) { //if
            this.ifN = new IfNode();
            this.altNo = 2;
            this.ifN.parseIf(t);
        } else if (tknVal == 8) { //loop
            this.loopN = new LoopNode();
            this.altNo = 3;
            this.loopN.parseLoop(t);
        } else if (tknVal == 10) { //in
            this.inputN = new InputNode();
            this.altNo = 4;
            this.inputN.parseInput(t);
        } else if (tknVal == 11) { //out
            this.outputN = new OutputNode();
            this.altNo = 5;
            this.outputN.parseOutput(t);
        }
    }

    /**
	 * Pretty print a statement.
	 * @param indent	The number of spaces to indent the statement.
	 */
    public void printStmt(int indent) {
        System.out.format("%1$" + indent + "s", "");
        if (this.altNo == 1) {
            this.assignN.printAssign();
        } else if (this.altNo == 2) {
            this.ifN.printIf(indent);
        } else if (this.altNo == 3) {
            this.loopN.printLoop(indent);
        } else if (this.altNo == 4) {
            this.inputN.printInput();
        } else if (this.altNo == 5) {
            this.outputN.printOutput();
        }
    }

    /**
     * Executes a statement.
     */
    public void execStmt() {
        switch (this.altNo) {
            case 1:
                this.assignN.execAssign();
                break;
            case 2:
                this.ifN.execIf();
                break;
            case 3:
                this.loopN.execLoop();
                break;
            case 4:
                this.inputN.execInput();
                break;
            case 5:
                this.outputN.execOutput();
                break;
            default:
                break;
        }

    }

}
