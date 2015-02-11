

public class CSPTest {
  public static void main(String[] args) {
    test1();
    test2();
    test3();
  }

  public static void test1() {
    System.out.println("This should have an exact solution");
    Integer[] possibleValues = {1, 2, 3};
    Variable var0 = new Variable(1);
    Variable var1 = new Variable(possibleValues);
    Variable var2 = new Variable(possibleValues);
    Variable var3 = new Variable(3);
    Constraint con0 = new ConstraintNotEqual(var0,var1);
    Constraint con1 = new ConstraintNotEqual(var1,var2);
    Constraint con2 = new ConstraintNotEqual(var2,var3);
    Constraint con3 = new ConstraintNotEqual(var1,var3);
    CSP csp = new CSP();
    csp.addVariable(var0);
    csp.addVariable(var1);
    csp.addVariable(var2);
    csp.addVariable(var3);
    csp.addConstraint(con0);
    csp.addConstraint(con1);
    csp.addConstraint(con2);
    csp.addConstraint(con3);
    tryCSP(csp);
  }

  public static void test2() {
    System.out.println("\nThis should have an inexact solution");
    Integer[] possibleValues = {1, 2, 3, 4, 5};
    Variable var0 = new Variable(1);
    Variable var1 = new Variable(possibleValues);
    Variable var2 = new Variable(possibleValues);
    Variable var3 = new Variable(5);
    Constraint con0 = new ConstraintLessThan(var0,var1);
    Constraint con1 = new ConstraintLessThan(var1,var2);
    Constraint con2 = new ConstraintLessThan(var2,var3);
    CSP csp = new CSP();
    csp.addVariable(var0);
    csp.addVariable(var1);
    csp.addVariable(var2);
    csp.addVariable(var3);
    csp.addConstraint(con0);
    csp.addConstraint(con1);
    csp.addConstraint(con2);
    tryCSP(csp);
  }

  public static void test3() {
    System.out.println("\nThis should have no solution");
    Integer[] possibleValues = {1, 2, 3, 4, 5};
    Variable var0 = new Variable(1);
    Variable var1 = new Variable(possibleValues);
    Variable var2 = new Variable(possibleValues);
    Variable var3 = new Variable(5);
    Constraint con0 = new ConstraintLessThan(var0,var1);
    Constraint con1 = new ConstraintLessThan(var1,var2);
    Constraint con2 = new ConstraintLessThan(var2,var3);
    Constraint con3 = new ConstraintLessThan(var3,var0);
    CSP csp = new CSP();
    csp.addVariable(var0);
    csp.addVariable(var1);
    csp.addVariable(var2);
    csp.addVariable(var3);
    csp.addConstraint(con0);
    csp.addConstraint(con1);
    csp.addConstraint(con2);
    csp.addConstraint(con3);
    tryCSP(csp);
  }
    
  public static void tryCSP(CSP csp) {
    csp.consistency();
    System.out.println("csp.isPossible() = " + csp.isPossible());
    System.out.println("csp.foundSolution() = " + csp.foundSolution());
    if (csp.foundSolution()) {
      System.out.println("csp.getSolution() = " + csp.getSolution());
    }
    int index = 0;
    for (Object[] list : csp.getValues()) {
      System.out.print("Variable " + index + ":");
      for (Object value : list) {
	System.out.print(" " + value);
      }
      System.out.print("\n");
      index++;
    }
  }
}
	
