
public class BayesianNetworkTest {
    public static final String[] TRUTH_VALUES = {"false", "true"}; 
    
    public static void main(String[] args) {
        BayesianNetwork bn = exampleSixTen();
        exampleSixThirteen(bn);
    }

    public static BayesianNetwork exampleSixTen() {
        BayesianNetwork bn = new BayesianNetwork();
        Variable tampering = new Variable("Tampering", TRUTH_VALUES);
        Variable fire = new Variable("Fire", TRUTH_VALUES);
        Variable alarm = new Variable("Alarm", TRUTH_VALUES);
        Variable smoke = new Variable("Smoke", TRUTH_VALUES);
        Variable leaving = new Variable("Leaving", TRUTH_VALUES);
        Variable report = new Variable("Report", TRUTH_VALUES);
        
        bn.addVariable(tampering);
        bn.addVariable(fire);
        bn.addVariable(alarm);
        bn.addVariable(smoke);
        bn.addVariable(leaving);
        bn.addVariable(report);
     
        // P(tampering) = 0.02
        Factor f1 = new Factor(new Variable[] {tampering});
        f1.set(new int[] { 0 }, 0.98);
        f1.set(new int[] { 1 }, 0.02);
        
        // P(fire) = 0.01
        Factor f2 = new Factor(new Variable[] {fire});
        f2.set(new int[] { 0 }, 0.99);
        f2.set(new int[] { 1 }, 0.01);
        
        // P(alarm | fire & tampering) = 0.5
        // P(alarm | fire & !tampering) = 0.99
        // P(alarm | !fire & tampering) = 0.85
        // P(alarm | !fire & !tampering) = 0.0001
        Factor f3 = new Factor(new Variable[] {tampering, fire, alarm});
        f3.set(new int[] { 0, 0, 0 }, 0.9999);
        f3.set(new int[] { 0, 0, 1 }, 0.0001);
        f3.set(new int[] { 0, 1, 0 }, 0.01);
        f3.set(new int[] { 0, 1, 1 }, 0.99);
        f3.set(new int[] { 1, 0, 0 }, 0.15);
        f3.set(new int[] { 1, 0, 1 }, 0.85);
        f3.set(new int[] { 1, 1, 0 }, 0.5);
        f3.set(new int[] { 1, 1, 1 }, 0.5);
        
        // P(smoke | fire ) = 0.9
        // P(smoke | ~fire ) = 0.01
        Factor f4 = new Factor(new Variable[] {fire, smoke});
        f4.set(new int[] { 0, 0 }, 0.99);
        f4.set(new int[] { 0, 1 }, 0.01);
        f4.set(new int[] { 1, 0 }, 0.1);
        f4.set(new int[] { 1, 1 }, 0.9);
        
        // P(leaving | alarm) = 0.88
        // P(leaving | ~alarm ) = 0.001
        Factor f5 = new Factor(new Variable[] {alarm, leaving});
        f5.set(new int[] { 0, 0 }, 0.999);
        f5.set(new int[] { 0, 1 }, 0.001);
        f5.set(new int[] { 1, 0 }, 0.12);
        f5.set(new int[] { 1, 1 }, 0.88);
        
        // P(report | leaving ) = 0.75
        // P(report | ~leaving ) = 0.01 
        Factor f6 = new Factor(new Variable[] {leaving, report});
        f6.set(new int[] { 0, 0 }, 0.99);
        f6.set(new int[] { 0, 1 }, 0.01);
        f6.set(new int[] { 1, 0 }, 0.25);
        f6.set(new int[] { 1, 1 }, 0.75);
       
        bn.addFactor(f1);
        bn.addFactor(f2);
        bn.addFactor(f3);
        bn.addFactor(f4);
        bn.addFactor(f5);
        bn.addFactor(f6);  
        
        return bn;
    }
    
    public static void exampleSixThirteen(BayesianNetwork bn) {
        System.out.println("Prior probabilities\n");
        // P(tampering ) = 0.02
        // P(fire) = 0.01
        // P(report ) = 0.028
        // P(smoke) = 0.0189 
        Factor result = bn.eliminateVariables(bn.findVariable("Tampering"));
        System.out.println(result);
        result = bn.eliminateVariables(bn.findVariable("Fire"));
        System.out.println(result);
        result = bn.eliminateVariables(bn.findVariable("Report"));
        System.out.println(result);
        result = bn.eliminateVariables(bn.findVariable("Smoke"));
        System.out.println(result);
        
        System.out.println("Probabilities given Report=true\n");
        // P(tampering | report) = 0.399
        // P(fire | report)= 0.2305
        // P(smoke | report) = 0.215 
        // need to make copy because observe replaces a factor
        BayesianNetwork copybn = bn.copy();
        copybn.observe(bn.findVariable("Report"), "true");
        result = copybn.eliminateVariables(bn.findVariable("Tampering"));
        System.out.println(result);
        result = copybn.eliminateVariables(bn.findVariable("Fire"));
        System.out.println(result);
        result = copybn.eliminateVariables(bn.findVariable("Smoke"));
        System.out.println(result);
        
        System.out.println("Probabilities given Smoke=true\n");
        // P(tampering | smoke) = 0.02
        // P(fire | smoke) = 0.476
        // P(report | smoke) = 0.320 
        copybn = bn.copy();
        copybn.observe(bn.findVariable("Smoke"), "true");
        result = copybn.eliminateVariables(bn.findVariable("Tampering"));
        System.out.println(result);
        result = copybn.eliminateVariables(bn.findVariable("Fire"));
        System.out.println(result);
        result = copybn.eliminateVariables(bn.findVariable("Report"));
        System.out.println(result);
        
        System.out.println("Probabilities given Report=true and Smoke=true\n");
        // P(tampering | report & smoke) = 0.0284
        // P(fire | report & smoke) = 0.964 
        copybn = bn.copy();
        copybn.observe(bn.findVariable("Report"), "true");
        copybn.observe(bn.findVariable("Smoke"), "true");
        result = copybn.eliminateVariables(bn.findVariable("Tampering"));
        System.out.println(result);
        result = copybn.eliminateVariables(bn.findVariable("Fire"));
        System.out.println(result);
        
        System.out.println("Probabilities given Report=true and Smoke=false\n");
        // P(tampering | report & !smoke) = 0.501
        // P(fire | report & !smoke) = 0.0294
        copybn = bn.copy();
        copybn.observe(bn.findVariable("Report"), "true");
        copybn.observe(bn.findVariable("Smoke"), "false");
        result = copybn.eliminateVariables(bn.findVariable("Tampering"));
        System.out.println(result);
        result = copybn.eliminateVariables(bn.findVariable("Fire"));
        System.out.println(result);   
    }
}
