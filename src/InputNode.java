
public class InputNode {
    IdListNode idList;

    public InputNode() {
        this.idList = new IdListNode();
    }

    /**
	 * Parse a read statement.
	 * @param t	A tokenizer whose position is at the start of a read statement.
	 */
    public void parseInput(Tokenizer t) {
        if (t.currentTokenToInt() != 10) { // make sure "read" token
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); // pop "read" token
        if (t.currentTokenToInt() != 32) { // make sure id token
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        this.idList.parseIdList(t, false);
        if (t.currentTokenToInt() != 14) { // make sure ";" token
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); // pop ";" token
    }

    /**
	 * Pretty print a read statement.
	 */
    public void printInput() {
        System.out.print("read ");
        this.idList.printIdList();
        System.out.println(";");
    }

    /**
     * Reads in the ID List.
     */
    public void execInput() {
        this.idList.readIdList();
    }
}
