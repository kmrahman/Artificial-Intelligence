import java.io.*;
import java.util.*;

public class ABO implements Runnable {
    Scanner in; // for reading networks
    PrintStream out; // for printing out solutions
    BayesianNetwork bn; // the Bayesian Network
    boolean debug = false;
    public static final String[] ALLELLES = { "O", "A", "B" };
    public static final String[] BLOODTYPES = { "O", "A", "B", "AB" };

    public ABO(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (debug)
                System.out.println(line);
            Scanner scanline = new Scanner(line);
            if (!scanline.hasNext()) {
                // skip empty lines
                continue;
            }
            String keyword = scanline.next();
            if (keyword.startsWith("#")) {
                // skip comments
            } else if (keyword.equals("start")) {
                bn = new BayesianNetwork();
            } else if (keyword.equals("end")) {
                // nothing to do here
            } else if (keyword.equals("quit")) {
                break;
            } else if (keyword.equals("person")) {
                String name = nextOrBust(scanline, line);
                String mother = null, father = null;

                if (scanline.hasNext()) {
                    keyword = scanline.next();
                    if (keyword.equals("parents")) {
                        mother = nextOrBust(scanline, line);
                        father = nextOrBust(scanline, line);
                       // ABOMethods.createFactors(bn, name, mother, father);
                    } else {

                        throwInTheTowel(line);
                    }
                }

                ABOMethods.createFactors(bn, name, mother, father);

            } else if (keyword.equals("observe")) {
                String name = nextOrBust(scanline, line);
                keyword = nextOrBust(scanline, line);
                if (!keyword.equals("bloodtype")) {
                    throwInTheTowel(line);
                }
                String bloodtype = nextOrBust(scanline, line);
                
                ABOMethods.observeEvidence(bn, name, bloodtype);
                
            } else if (keyword.equals("infer")) {
                String name = nextOrBust(scanline, line);
                keyword = nextOrBust(scanline, line);
                if (!keyword.equals("bloodtype")) {
                    throwInTheTowel(line);
                }
                
                if (debug)
                    System.out.println();
                
                ABOMethods.runNetwork(bn, name, out);
                
                if (debug)
                    System.out.println();
                
            } else {
                throwInTheTowel(line);
            }
            scanline.close();
        }
        in.close();
        out.close();
    }

    // return the next token or
    // throw an exception complaining about the line being parsed
    public static String nextOrBust(Scanner in, String line) {
        if (in.hasNext()) {
            return in.next();
        }
        throwInTheTowel(line);
        return null; // to make the compiler happy
    }

    // throw an exception complaining about the line being parsed
    public static void throwInTheTowel(String line) {
        throw new RuntimeException("unable to parse: " + line);
    }

    public static void main(String[] args) throws Exception {
        Scanner in = null;
        try {
            in = new Scanner(new File("data.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("unable to open data.txt");
        }
        ABO abo = new ABO(in, System.out);
        abo.debug = true;
        abo.run();
    }
}
