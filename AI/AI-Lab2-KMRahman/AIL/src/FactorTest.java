
public class FactorTest {

    public static void main(String[] args) {
        figure6point5();
        figure6point6();
        figure6point7();
    }
    
    public static void figure6point5() {
        System.out.println("Example from Figure 6.5\n");
        
        String[] values = {"false", "true"};  // so false is 0 and true is 1
        Variable x = new Variable("X", values);
        Variable y = new Variable("Y", values);
        Variable z = new Variable("Z", values);
        Variable[] vars = new Variable[3];
        vars[0] = x;
        vars[1] = y;
        vars[2] = z;
        
        Factor factor0 = new Factor(vars);
        factor0.set(new int[]{1, 1, 1}, 0.1);
        factor0.set(new int[]{1, 1, 0}, 0.9);
        factor0.set(new int[]{1, 0, 1}, 0.2);
        factor0.set(new int[]{1, 0, 0}, 0.8);
        factor0.set(new int[]{0, 1, 1}, 0.4);
        factor0.set(new int[]{0, 1, 0}, 0.6);
        factor0.set(new int[]{0, 0, 1}, 0.3);
        factor0.set(new int[]{0, 0, 0}, 0.7);
        System.out.println("factor = \n" + factor0);
        
        Factor factor1 = factor0.observe(x, "true");
        // or Factor factor1 = factor0.observe(x, 1);
        System.out.println("observe(X = true) = \n" + factor1);
        
        Factor factor2 = factor0.observe(x, 1).observe(z, "false");
        // or Factor factor2 = factor0.observe(x, 1).observe(z, 0);
        // or Factor factor2 = factor0.observe(x, "true").observe(z, 0);
        // or Factor factor2 = factor0.observe(x, "true").observe(z, "false");
        // or Factor factor2 = factor1.observe(z, 0);
        // or Factor factor2 = factor1.observe(z, "false");
        System.out.println("observe(X = true, Z = false) = \n" + factor2);
    }
    
    public static void figure6point6() {
        System.out.println("Example from Figure 6.6\n");
        
        String[] values = {"false", "true"};  // so false is 0 and true is 1
        Variable a = new Variable("A", values);
        Variable b = new Variable("B", values);
        Variable c = new Variable("C", values);
        
        Variable[] vars = new Variable[2];
        vars[0] = a;
        vars[1] = b;
        
        Factor factor1 = new Factor(vars);
        factor1.set(new int[]{1, 1}, 0.1);
        factor1.set(new int[]{1, 0}, 0.9);
        factor1.set(new int[]{0, 1}, 0.2);
        factor1.set(new int[]{0, 0}, 0.8);
        
        System.out.println("factor1 = \n" + factor1);
        
        vars = new Variable[2];
        vars[0] = b;
        vars[1] = c;
        
        Factor factor2 = new Factor(vars);
        factor2.set(new int[]{1, 1}, 0.3);
        factor2.set(new int[]{1, 0}, 0.7);
        factor2.set(new int[]{0, 1}, 0.6);
        factor2.set(new int[]{0, 0}, 0.4);
        
        System.out.println("factor2 = \n" + factor2);
        
        Factor factor3 = factor1.multiply(factor2);
        // or Factor factor3 = factor2.multiply(factor1); with variables ordered differently
        
        System.out.println("factor1 * factor2 = \n" + factor3);
    }
    
    public static void figure6point7() {
        System.out.println("Example from Figure 6.7\n");
        
        String[] values = {"false", "true"};  // so false is 0 and true is 1
        Variable a = new Variable("A", values);
        Variable b = new Variable("B", values);
        Variable c = new Variable("C", values);
        Variable[] vars = new Variable[3];
        vars[0] = a;
        vars[1] = b;
        vars[2] = c;
        
        Factor factor3 = new Factor(vars);
        factor3.set(new int[]{1, 1, 1}, 0.03);
        factor3.set(new int[]{1, 1, 0}, 0.07);
        factor3.set(new int[]{1, 0, 1}, 0.54);
        factor3.set(new int[]{1, 0, 0}, 0.36);
        factor3.set(new int[]{0, 1, 1}, 0.06);
        factor3.set(new int[]{0, 1, 0}, 0.14);
        factor3.set(new int[]{0, 0, 1}, 0.48);
        factor3.set(new int[]{0, 0, 0}, 0.32);
        System.out.println("factor3 = \n" + factor3);
        
        Factor factor4 = factor3.sumout(b);
        System.out.println("sumout(B) = \n" + factor4);
    }
}
