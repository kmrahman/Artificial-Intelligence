
import java.util.*;
import java.io.*;

// This reads puzzles from puzzles.txt and prints them out.
// It expects to read solutions from its Scanner input.
public class Sudoku implements Runnable {
  Scanner in;
  PrintStream out;
  ArrayList<String> puzzles;
  
  // The constructor reads the puzzles from puzzles.txt into puzzles
  public Sudoku(Scanner in, PrintStream out) {
    this.in = in;
    this.out = out;
    puzzles = new ArrayList<String>();
    try {
      Scanner scan = new Scanner( new File(System.getProperty("user.dir") + "/puzzles.txt"));
      puzzles.add(scan.next());
      while (scan.hasNext()) {
	puzzles.add(scan.next());
      }
      scan.close();
    } catch (Exception e) {
      throw new RuntimeException("failed to open puzzles.txt");
    }
  }
  
  // This prints each puzzle out, and it reads and tests proposed
  // solutions.  After it finishes this loop, it prints a line
  // to stop the solver and quits.
  public void run() {
    for (String puzzle : puzzles) {
      int[][] initial = new int[9][9];
      for (int i = 0; i < 9; i++) {
	for (int j = 0; j < 9; j++) {
	  char c = puzzle.charAt(i * 9 + j);
	  if (c >= '1' && c <= '9') {
	    initial[i][j] = c - '0';
	  }
	}
      }
      out.println(puzzle);
      puzzle = in.next();
      int[][] solution = new int[9][9];
      boolean success = true;
      for (int i = 0; i < 9; i++) {
	for (int j = 0; j < 9; j++) {
	  char c = puzzle.charAt(i * 9 + j);
	  if (c >= '1' && c <= '9') {
	    solution[i][j] = c - '0';
	    if (initial[i][j] != 0 && initial[i][j] != solution[i][j]) {
	      success = false;
	    }
	  } else {
	    success = false;
	  }
	}
      }
    
      // check all pairs of squares
      for (int square1 = 0; square1 < 81; square1++) {
	int row1 = square1 / 9;
	int column1 = square1 % 9;
	int block1 = row1 / 3 + 3 * (column1 / 3);
	for (int square2 = square1 + 1; square2 < 81; square2++) {
	  int row2 = square2 / 9;
	  int column2 = square2 % 9;
	  int block2 = row2 / 3 + 3 * (column2 / 3);
	  // same row, column or block should be not equal
	  if (row1 == row2 || column1 == column2 || block1 == block2) {
	    if (solution[row1][column1] == solution[row2][column2]) {
	      success = false;
	    }
	  }
	}
      }
      
      if (success) {
	System.out.println("success");
      } else {
	System.out.println("failure");
      }
    }
    out.println("quit");
    out.close();
    in.close();
  }
  
  public static void main(String[] args) {
    Sudoku sudoku = new Sudoku(new Scanner(System.in), System.out);
    sudoku.run();
  }
}
