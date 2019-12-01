
public class DeclSeqNode {
    DeclNode declN;
    DeclSeqNode declSeqN;
    int altNo;

    public DeclSeqNode() {
        this.declN = new DeclNode();
        this.declSeqN = null;
        this.altNo = 0;
    }

    /**
	 * Parse a declaration sequence statement.
	 * @param t	A tokenizer whose position is at the start of an declaration sequence statement.
	 */
    public void parseDeclSeq(Tokenizer t) {
        // check to make sure there is a int token then parse
        if (t.currentTokenToInt() == 4) {
            this.altNo = 1;
            this.declN.parseDecl(t);
        }
        // if another declaration create new decl seq object
        if (t.currentTokenToInt() == 4) {
            this.altNo = 2;
            this.declSeqN = new DeclSeqNode();
            this.declSeqN.parseDeclSeq(t);
        }
    }

    /**
	 * Pretty print a declaration sequence statement.
	 * @param indent	The number of spaces to indent the statement.
	 */
    public void printDeclSeq(int indent) {
        if (this.altNo > 0) {
            this.declN.printDecl(indent);
            if (this.altNo == 2) {
                this.declSeqN.printDeclSeq(indent);
            }
        }
    }
}
