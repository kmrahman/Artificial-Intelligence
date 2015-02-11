

// The only requirement for a constraint is to implement
// a method named consistency.
public interface Constraint {
  // This should return true if any changes were made.
  // This should return false if no changes were made.
  public boolean consistency();
}
