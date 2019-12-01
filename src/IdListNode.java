
public class IdListNode {
    IdNode idN;
    IdListNode idListN;
    int altNo;

    public IdListNode() {
        this.idN = null;
        this.idListN = null;
        this.altNo = 0;
    }

    /**
	 * Parse an ID List.
	 * @param t			A tokenizer whose position is at the start of an ID List.
	 * @param declaring	True if the variable is being declared, otherwise false.
	 */
    public void parseIdList(Tokenizer t, boolean declaring) {
        // check for identifier token
        if (t.currentTokenToInt() == 32) {
            this.altNo = 1;
            this.idN = IdNode.parseIdNode(t);
            // if the variable is being declared
            if (declaring) {
                if (this.idN.isDeclared()) {
                    System.err.println("Error: Variable " + this.idN.getName()
                            + " has already been declared!");
                    System.exit(-1);
                } else {
                    this.idN.setDeclared();
                }
            }
            // check if more IDs (comma token)
            if (t.currentTokenToInt() == 15) {
                this.altNo = 2;
                this.idListN = new IdListNode();
                t.nextToken(); // skip comma token
                this.idListN.parseIdList(t, declaring);
            }

        } else {
            System.err.println("Error: Unexpected token " + t.currentToken()
                    + " on line " + t.lineNumber() + ".");
            System.exit(-1);
        }
    }

    /**
	 * Pretty print an ID List.
	 */
    public void printIdList() {
        if (this.altNo > 0) {
            this.idN.printIdNode();
            if (this.altNo == 2) {
                System.out.print(", ");
                this.idListN.printIdList();
            }
        }

    }

    /**
     * Writes out an ID List.
     */
    public void writeIdList() {
        if (this.altNo > 0) {
            this.idN.writeId();
            if (this.altNo == 2) {
                this.idListN.writeIdList();
            }
        }
    }

    /**
     * Reads in an ID List.
     */
    public void readIdList() {
        if (this.altNo > 0) {
            this.idN.readId();
            if (this.altNo == 2) {
                this.idListN.readIdList();
            }
        }
    }
}
