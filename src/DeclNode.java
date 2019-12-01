
public class DeclNode {
    IdListNode idList;

    public DeclNode() {
        this.idList = new IdListNode();
    }

    /**
	 * Parse an declaration statement.
	 * @param t	A tokenizer whose position is at the start of an declaration statement.
	 */
    public void parseDecl(Tokenizer t) {
        // make sure int token
        if (t.currentTokenToInt() == 4) {
            t.nextToken(); // pop int token
            this.idList.parseIdList(t, true);
            if (t.currentTokenToInt() != 14) { // check for ";" token
                System.err.println("Error: Unexpected token " + t.currentToken()
                        + " on line " + t.lineNumber() + ".");
                System.exit(-1);
            }
            t.nextToken(); // pop ";" token
        }
    }

    /**
	 * Pretty print a declaration statement.
	 * @param indent	The number of spaces to indent the statement.
	 */
    public void printDecl(int indent) {
        System.out.format("%1$" + indent + "s", "");
        System.out.print("int ");
        this.idList.printIdList();
        System.out.println(";");
    }
}
