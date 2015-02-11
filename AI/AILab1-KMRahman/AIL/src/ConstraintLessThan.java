

public class ConstraintLessThan implements Constraint {
  private Variable var1, var2;

  public ConstraintLessThan(Variable var1, Variable var2) {
    this.var1 = var1;
    this.var2 = var2;
  }

  // Remove inconsistent values.
  // Return true if any changes were made.
  public boolean consistency() {
    // Keep track of whether any changes are made.
    boolean changed = false;

    // Remove values from var1 that are ! < some value from var2.  
    // That is, remove any var1 value that is >= every var2 value.
    for (Object val1 : var1) {
      // Determine whether val1 is a possible value.
      boolean possible = false;
      if (val1 instanceof Integer) {
	int i1 = (Integer) val1;
	for (Object val2 : var2) {
	  if (val2 instanceof Integer) {
	    int i2 = (Integer) val2;
	    // Found a value from var2 that is > than val1
	    if (i1 < i2)
	      possible = true;
	  }
	}
      }
      if (! possible) {
	var1.remove(val1);
	changed = true;
      }
    }

    // Remove values from var2 that are ! > some value from var1.  
    // That is, remove any var2 value that is <= every var1 value.
    for (Object val2 : var2) {
      // Determine whether val2 is a possible value.
      boolean possible = false;
      if (val2 instanceof Integer) {
	int i2 = (Integer) val2;
	for (Object val1 : var1) {
	  if (val1 instanceof Integer) {
	    int i1 = (Integer) val1;
	    // Found a value in var1 that is < val1
	    if (i1 < i2)
	      possible = true;
	  }
	}
      }
      if (! possible) {
	var2.remove(val2);
	changed = true;
      }
    }

    return changed;
  }
}	
