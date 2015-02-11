import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Tester implements Runnable {
    Scanner in; // for reading from the agent
    PrintStream out; // for printing info to the agent

    public Tester(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        Scanner data = null;
        try {
            data = new Scanner(new File("data.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("unable to open data.txt");
        }

        // for storing inferences and comment lines with solution
        double infer[] = new double[4];
        double solution[] = new double[4];
        while (data.hasNextLine()) {
            String line = data.nextLine();
            if (line.startsWith("#")) {
                Pattern pattern = Pattern
                        .compile("should be O: (\\d\\.\\d+), A: (\\d\\.\\d+), B: (\\d\\.\\d+), AB: (\\d.\\d+)");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    for (int i = 0; i < 4; i++)
                        solution[i] = Double.valueOf(matcher.group(i + 1));
                    boolean success = true;
                    for (int i = 0; i < 4; i++)
                        if (infer[i] < solution[i] - 0.0005
                                || infer[i] > solution[i] + 0.0005)
                            success = false;
                    System.out.println(success ? "CORRECT" : "INCORRECT");
                }
            } else {
                out.println(line);

                if (line.startsWith("infer")) {
                    // skip lines until the Variable line appears
                    while (!in.nextLine().startsWith("Variable"));
                    for (int i = 0; i < 4; i++) {
                        // each line contains an int and a double
                        in.nextInt(); // skip integer
                        infer[i] = in.nextDouble();
                    }
                    in.nextLine(); // to skip newline character
                } else if (line.startsWith("quit"))
                    break;
            }

        }
        in.close();
        out.close();
        data.close();
    }
}
