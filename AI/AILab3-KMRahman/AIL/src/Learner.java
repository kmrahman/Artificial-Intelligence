
import java.io.*;
import java.util.*;

public class Learner implements Runnable {

    Scanner in; // for reading networks
    PrintStream out; // for printing out solutions
    String line;
    int[] input;
    double n;
    double[] nd;
    double[][] ndi;

    public Learner(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
        nd = new double[10];
        ndi = new double[10][64];
    }

    public void run() {
        input = new int[65];
        line = in.nextLine();
        double probab_y;
        double log_value;
        double prob_x_1;
        double prob_x_0;
        
        while (!line.equals("quit")) {
            input[0] = 1; // for bias weight
            for (int i = 0; i < 64; i++) {
                char c = line.charAt(i);
                input[i + 1] = (c == '1') ? 1 : 0;
            }

            // replace this with a naive Bayes prediction
            int prediction = 7;
            double probability = -111111;

            for (int i = 0; i < 10; i++) {
                probab_y = (nd[i] + 1) / (n + 10);
                log_value = Math.log(probab_y);
                for (int j = 0; j < 64; j++) {
                    if (input[j + 1] == 0) {
                        prob_x_0 = (nd[i] - ndi[i][j] + 1) / (nd[i] + 2);
                        log_value= log_value + Math.log(prob_x_0);

                    } else {
                        prob_x_1 = (ndi[i][j] + 1) / (nd[i] + 2);
                        log_value = log_value + Math.log(prob_x_1);
                    }
                }

                if (probability < log_value) {
                    probability = log_value;
                    prediction = i;
                }
            }

            out.println(prediction);

            line = in.nextLine();
            while (line == null || "".equals(line))
                line = in.nextLine();

            int label = 0;
            if (line.startsWith("incorrect")) {
                label = line.charAt(20) - 48;
            } else {
                label = line.charAt(18) - 48;
            }

            // use label to update naive Bayes counts
            n++;
            nd[label]++;
            for (int i = 0; i < 64; i++) {
                ndi[label][i] += input[i + 1];
            }

            line = in.nextLine();
        }

        in.close();
        out.close();
    }
    public static final String[] cleanImages = {
        "0000000000111100010000100100001001000010010000100100001000111100",
        "0000000000001000000010000000100000001000000010000000100000001000",
        "0000000000111100010000100000010000001000000100000010000001111110",
        "0000000000111100010000100000001000111100000000100100001000111100",
        "0000000001000100010001000100010001111110000001000000010000000100",
        "0000000001111110010000000100000001111100000000100000001001111100",
        "0000000000000000000000000000000000000000000000000000000000000000",
        "0000000000111100010000100100000001111100010000100100001000111100",
        "0000000001111110000000100000010000001000000100000010000001000000",
        "0000000000111100010000100100001000111100010000100100001000111100",
        "0000000000111100010000100100001000111110000000100100001000111100",
        "0011110001000010010000100100001001000010010000100011110000000000",
        "0001000000010000000100000001000000010000000100000001000000000000",
        "0011110001000010000001000000100000010000001000000111111000000000",
        "0011110001000010000000100011110000000010010000100011110000000000",
        "0100010001000100010001000111111000000100000001000000010000000000",
        "0111111001000000010000000111110000000010000000100111110000000000",
        "0011110001000010010000000111110001000010010000100011110000000000",
        "0111111000000010000001000000100000010000001000000100000000000000",
        "0011110001000010010000100011110001000010010000100011110000000000",
        "0011110001000010010000100011111000000010010000100011110000000000"};

    public static void main(String[] args) throws Exception {
        PipedOutputStream pipeout = new PipedOutputStream();
        PipedInputStream pipein;
        try {
            pipein = new PipedInputStream(pipeout);
        } catch (Exception e) {
            throw new RuntimeException("pipe failed " + e);
        }
        Scanner agentIn = new Scanner(pipein);
        PrintStream printToAgent = new PrintStream(pipeout, true);
        pipeout = new PipedOutputStream();
        try {
            pipein = new PipedInputStream(pipeout);
        } catch (Exception e) {
            throw new RuntimeException("pipe failed " + e);
        }
        Scanner readFromAgent = new Scanner(new InputStreamReader(pipein));
        PrintStream agentOut = new PrintStream(pipeout, true);

        Runnable agent = new Learner(agentIn, agentOut);
        Thread athread = new Thread(agent);
        athread.start();

        int correct = 0, incorrect = 0;
        for (int i = 0; i < 1000; i++) {
            String image = cleanImages[i % 20];
            int label = i % 10;
            printToAgent.println(image);
            // System.out.println(image);
            while (!readFromAgent.hasNext()) {
                Thread.sleep(0);
            }
            int prediction = readFromAgent.nextInt();
            String line = "";
            if (prediction == label) {
                line += "correct ";
                correct++;
            } else {
                line += "incorrect ";
                incorrect++;
            }
            line += String.format("(label is %d, error rate = %d/%d = %.2f)",
                    label, incorrect, correct + incorrect, 100
                    * (0.0 + incorrect) / (0.0 + correct + incorrect));
            printToAgent.println(line);
            int linetest = 10;
            while (linetest < i + 1) {
                linetest *= 10;
            }
            linetest /= 10;
            if ((i + 1) % linetest == 0) {
                System.out.println(image);
                System.out.println(prediction);
                System.out.println(line);
            }
        }
        printToAgent.println("quit");
        athread.join();
        agentIn.close();
        agentOut.close();
        readFromAgent.close();
        printToAgent.close();

    }
}
