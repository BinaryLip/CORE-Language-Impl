
public class OutputNode {
    IdListNode idList;

    public OutputNode() {
        this.idList = new IdListNode();
    }

    /**
	 * Parse a write statement.
	 * @param t	A tokenizer whose position is at the start of a write statement.
	 */
    public void parseOutput(Tokenizer t) {
        if (t.currentTokenToInt() != 11) { // make sure "write" token
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
        t.nextToken(); // pop "write" token
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
	 * Pretty print a write statement.
	 */
    public void printOutput() {
        System.out.print("write ");
        this.idList.printIdList();
        System.out.println(";");
    }

    /**
     * Writes out the ID List.
     */
    public void execOutput() {
        this.idList.writeIdList();
    }
}
