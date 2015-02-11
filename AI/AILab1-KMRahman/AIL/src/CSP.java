
import java.util.*;

public class CSP {
  private ArrayList<Variable> vars;
  private ArrayList<Constraint> cons;
  
  public CSP() {
    vars = new ArrayList<Variable>();
    cons = new ArrayList<Constraint>();
  }

  public void addVariable(Variable var) {
    vars.add(var);
  }

  public void addConstraint(Constraint con) {
    cons.add(con);
  }

  // Return true if there might be a solution to this CSP.
  // It tests whether each variable has at least one possible value.
  public boolean isPossible() {
    for (Variable var : vars) 
      if (var.getSize() == 0) 
	return false;
    return true;
  }

  // A solution has been found if each variable
  // has one possible value.
  public boolean foundSolution() {
    for (Variable var : vars) 
      if (var.getSize() != 1) 
	return false;
    return true;
  }

  // Return a solution, a list of values, one for each variable.
  public ArrayList getSolution() {
    ArrayList solution = new ArrayList();
    for (Variable var : vars) 
      solution.add(var.getValue());
    return solution;
  }

  // Return possible values, a array of values for each variable.
  public ArrayList<Object[]> getValues() {
    ArrayList<Object[]> values = new ArrayList<>();
    for (Variable var : vars) 
      values.add(var.getValues());
    return values;
  }

  // Apply the consistency algorithm.
  // Keep calling the constraints' consistency methods
  // until no more changes are made.
  public void consistency() {
    boolean changed = false;
    do {
      changed = false;
      for (Constraint con : cons) {
	if (con.consistency())
	  changed = true;
      }
    } while (changed);
  }
}
    
