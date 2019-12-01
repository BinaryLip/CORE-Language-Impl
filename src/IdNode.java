import java.util.HashMap;
import java.util.Scanner;

public class IdNode {
    String name;
    int value;
    boolean initialized;
    boolean declared;
    static HashMap<String, IdNode> symTable = new HashMap<String, IdNode>();

    public IdNode(String n) {
        this.name = n;
        this.initialized = false;
        this.declared = false;
    }

    /**
	 * Parse an ID statement.
	 * @param t	A tokenizer whose position is at the start of an ID statement.
	 * @return The IdNode created or found based on the ID name.
	 */
    public static IdNode parseIdNode(Tokenizer t) {
        String tok = t.currentToken();
        t.nextToken();
        if (!symTable.containsKey(tok)) {
            IdNode node = new IdNode(tok);
            symTable.put(tok, node);
        }
        return symTable.get(tok);
    }

    /**
     * Sets the value.
     * @param v The value to set.
     */
    void setValue(int v) {
        this.value = v;
        this.initialized = true;
    }

    /**
	 * Get the ID's value.
	 * @return	The value.
	 */
    int getValue() {
        return this.value;
    }

    /**
	 * Get the ID's name.
	 * @return	The ID.
	 */
    String getName() {
        return this.name;
    }

    /**
	 * Check whether the ID has been declared or not.
	 * @return	True if declared, false otherwise.
	 */
    boolean isDeclared() {
        return this.declared;
    }

    /**
	 * Sets the ID as declared.
	 */
    void setDeclared() {
        this.declared = true;
    }

    /**
	 * Check whether the ID has been assigned or not.
	 * @return	True if assigned, false otherwise.
	 */
    boolean isAssigned() {
        return this.initialized;
    }

    /**
	 * Pretty print ID statement.
	 */
    public void printIdNode() {
        System.out.print(this.name);
    }

    /**
     * Writes the name and value of an ID Node.
     */
    public void writeId() {
        if (this.initialized) {
            System.out.println(this.name + " = " + this.value);
        } else {
            System.err.println("Can not write variable " + this.name
                    + ". It has not been initialized.");
            System.exit(-1);
        }
    }

    /**
     * Reads in the value of an ID from the user.
     */
    public void readId() {
        if (this.declared) {
            @SuppressWarnings("resource") //closing caused problems
            Scanner scanner = new Scanner(System.in);
            System.out.print(this.name + " =? ");
            String input = scanner.next();
            // make sure all digits
            if (input.matches("-?\\d+")) {
                try {
                    this.setValue(Integer.parseInt(input));
                } catch (NumberFormatException e) {
                    System.err.println("Could not read in value for "
                            + this.name + ". Integer too large!");
                    System.exit(-1);
                }

            } else {
                System.err.println("Could not read in value for " + this.name
                        + ". Non integer number entered.");
                System.exit(-1);
            }
        } else {
            // shouldn't happen?
            System.err.println(
                    "Can not write variable because it has not been declared.");
            System.exit(-1);
        }
    }

}
