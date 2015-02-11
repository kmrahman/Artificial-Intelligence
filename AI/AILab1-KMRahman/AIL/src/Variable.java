
import java.util.*;

public class Variable implements Iterable {
  // The initial list of all possible values for this variable.
  private ArrayList values;

  // Indicate for each value, whether it is currently possible or not.
  private boolean[] possible;

  // The number of possible values currently.
  // This should be the number of trues in the possible array.
  private int size;

  // possibleValues includes all the values that the
  // variable might have.  No other values can ever
  // be added to this variable.
  public Variable(Object[] possibleValues) {
    size = possibleValues.length;
    possible = new boolean[size];
    Arrays.fill(possible, true);
    values = new ArrayList();
    for (Object val : possibleValues) {
      values.add(val);
    }
  }

  // value is the only value that the variable might have.
  // This is equivalent to permanently setting the 
  // variable to this value.
  public Variable(Object value) {
    size = 1;
    possible = new boolean[size];
    Arrays.fill(possible, true);
    values = new ArrayList();
    values.add(value);
  }

  // These will be all be included as possible values
  // as long as each value is in the ArrayList values.
  public void setValues(Object[] possibleValues) {
    Arrays.fill(possible,false);
    size = 0;
    for (Object val : possibleValues) {
      this.add(val);
    }
  }

  // Return possible values as an array.
  public Object[] getValues() {
    Object[] possibleValues = new Object[size];
    int index = 0;
    for (Object value : this) {
      possibleValues[index] = value;
      index++;
    }
    return possibleValues;
  }

  // remove val as a possible value
  public void remove(Object val) {
    int index = values.indexOf(val);
    if (index != -1 && possible[index]) {
      possible[index] = false;
      size--;
    }
  }

  // add val as a possible value
  public void add(Object val) {
    int index = values.indexOf(val);
    if (index != -1 && ! possible[index]) {
      possible[index] = true;
      size++;
    }
  }

  // return the number of possible values
  public int getSize() { return size; }

  // if the variable has one possible value, return that value.
  // Otherwise, an exception is thrown.
  public Object getValue() { 
    if (size != 1)
      throw new RuntimeException("Variable has " + size + " possible values");
    int index = 0;
    while (index < possible.length && ! possible[index]) {
      index++;
    }
    if (index == possible.length)
      throw new RuntimeException("This shouldn't happen!!");
    return values.get(index);
  }

  // Return an Iterator to loop thru all current possible values.
  public Iterator iterator() {
    return new VariableIterator();
  }

  // An Iterator for looping thru all the values that
  // have a corresponding true in the possible array.
  class VariableIterator implements Iterator {
    private int next, previous;
    
    public VariableIterator() {
      previous = -1;
      next = 0;
      while (next < possible.length && ! possible[next]) next++;
    }

    public boolean hasNext() {
      return next < possible.length;
    }
    
    public Object next() {
      Object result = null;
      if (next < possible.length) {
	result = values.get(next);
	previous = next;
	do {
	  next++;
	} while (next < possible.length && ! possible[next]);
      } else {
	throw new NoSuchElementException();
      }
      return result;
    }
    
    public void remove() {
      if (previous >= 0 && previous < possible.length) {
	possible[previous] = false;
      } else {
	throw new IllegalStateException();
      }
    }
  }
}
