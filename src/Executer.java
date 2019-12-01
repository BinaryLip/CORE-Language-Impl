import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Executer {
    private Tokenizer tokenizer;
    private ProgramNode prgm;

    public Executer(Tokenizer t) {
        this.tokenizer = t;
        this.prgm = new ProgramNode();
    }

    /**
     * Starts the parsing of the program.
     */
    public void Parse() {
        this.prgm.parseProgram(this.tokenizer);
    }

    /**
     * Pretty prints the program.
     */
    public void PrettyPrint() {
        this.prgm.printProgram();
    }

    /**
     * Starts the execution of the program.
     */
    public void Exec() {
        this.prgm.execProgram();
    }

    /**
     * The main method to parse and then run a CORE program.
     * @param args the name of CORE/txt file to parse
     */
    public static void main(String[] args) {
        //turn arg filename into a BufferedReader
        BufferedReader reader = null;
        try {
            File file = new File(args[0]);
            reader = new BufferedReader(new FileReader(file));

            // create Executer object
            Executer exe = new Executer(new Tokenizer(reader));
            // parse the file
            exe.Parse();
            // execute the file
            exe.Exec();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
