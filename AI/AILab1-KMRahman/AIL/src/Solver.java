
import java.util.*;
import java.io.*;

// This Sudoku solver doesn't solve anything, but it does
// implement the input-output.
public class Solver implements Runnable {
  Scanner in;      // for reading puzzles
  PrintStream out; // for printing out solutions
  String puzzle;   // the current puzzle
  int[][] board;   // the Sudoku board
  
  public Solver(Scanner in, PrintStream out) {
    this.in = in;
    this.out = out;
  }
  
  public void run() {
    puzzle = in.next();
    // quit indicates no more puzzles
    while (! puzzle.equals("quit")) {
      // Convert puzzle string into a 2D array
    	
    	int x=0;
    	this.board = new int[9][9];
    	for(int j=0; j<9; j++)
    	{
    		for(int k=0; k<9; k++)
    		{ // Take the string in the board.
    			this.board[j][k]= Character.digit(puzzle.charAt(x), 10);
    			//System.out.printf(board[j][k] + " ");
    			x++;
    		}
    		//System.out.println("\n");
    	}

      // Create a CSP.
      // Add a variable for each Sudoku square.
      // Add constraints for any pair of squares in the
      // same row, column or block.

        Integer[] possibleValues = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        Variable [][] var= new Variable[9][9];
        //create and initialize 81 variables
        for(int j=0; j<9; j++){
        	for(int k=0; k<9; k++){
                if(this.board[j][k] == -1)
                { var[j][k] = new Variable(possibleValues);}
                else { var[j][k] = new Variable(this.board[j][k]);}
        	}
        }
        	

        //Constraints in same rows.
        Constraint [][][]cons_row = new Constraint[9][8][9];
        

        for(int i=0; i<9; i++){
	        for(int j=0; j<8; j++){
	        	for(int k=j+1; k<9; k++) {
	        		cons_row[i][j][k] = new ConstraintNotEqual(var[i][j],var[i][k]);
	        		   
	        	}
	        }
        }
        	
        
        //Constraints in same column.
        Constraint [][][]cons_column = new Constraint[9][8][9];
       
        for(int i=0; i<9; i++){
	        for(int j=0; j<8; j++){
	        	for(int k=j+1; k<9; k++) {
	        		cons_column[i][j][k] = new ConstraintNotEqual(var[j][i],var[k][i]);
	        		   
	        	}
	        }
        }
        	
        
        //Constraints in same box.
       Constraint [][]cons_box = new Constraint[9][18];
        
         
      	        
       
           int r=0,c=0;
        	for(int box=0; box<9; box++){

        		 if(box== 0) {r=0; c= 0; }
        		 else if(box== 1) {r=0; c= 3; }
        		 else if(box== 2) {r=0; c= 6; }
        		 else if(box== 3) {r=3; c= 0; }
        		 else if(box== 4) {r=3; c= 3; }
        		 else if(box== 5) {r=3; c= 6; }
        		 else if(box== 6) {r=6; c= 0; }
        		 else if(box== 7) {r=6; c= 3; }
        		 else if(box== 8) {r=6; c= 6; }
        		 
			     cons_box[box][0] = new ConstraintNotEqual(var[r][r],var[r+1][c+1]);	    
			     cons_box[box][1] = new ConstraintNotEqual(var[r][c],var[r+1][c+2]);
			     cons_box[box][2] = new ConstraintNotEqual(var[r][c],var[r+2][c+1]);	    
			     cons_box[box][3] = new ConstraintNotEqual(var[r][c],var[r+2][c+2]);    
			    
			     cons_box[box][4] = new ConstraintNotEqual(var[r][c+1],var[r+1][c]);	    
			     cons_box[box][5] = new ConstraintNotEqual(var[r][c+1],var[r+1][r+2]);
			     cons_box[box][6] = new ConstraintNotEqual(var[r][c+1],var[r+2][c]);	    
			     cons_box[box][7] = new ConstraintNotEqual(var[r][c+1],var[r+2][c+2]);   
		
			     cons_box[box][8] = new ConstraintNotEqual(var[r][c+2],var[r+1][c]);	    
			     cons_box[box][9] = new ConstraintNotEqual(var[r][c+2],var[r+1][c+1]);
			     cons_box[box][10] = new ConstraintNotEqual(var[r][c+2],var[r+2][c]);	    
			     cons_box[box][11] = new ConstraintNotEqual(var[r][c+2],var[r+2][c+1]);   
		
			     cons_box[box][12] = new ConstraintNotEqual(var[r+1][c],var[r+2][c+1]);	    
			     cons_box[box][13] = new ConstraintNotEqual(var[r+1][c],var[r+2][c+2]);   
		
			     cons_box[box][14] = new ConstraintNotEqual(var[r+1][r+1],var[r+2][c]);	    
			     cons_box[box][15] = new ConstraintNotEqual(var[r+1][r+1],var[r+2][c+2]);   
			    
			     cons_box[box][16] = new ConstraintNotEqual(var[r+1][c+2],var[r+2][c]);	    
			     cons_box[box][17] = new ConstraintNotEqual(var[r+1][c+2],var[r+2][c+1]);   
		  
        	}
        

        CSP csp = new CSP();
        //Add variables to CSP
        for(int j=0; j<9; j++){
        	for(int k=0; k<9; k++){
        		csp.addVariable(var[j][k]);
        	}
        }


        
        //Add row & column constraints
        for(int i=0; i<9; i++){
	        for(int j=0; j<8; j++){
	        	for(int k=j+1; k<9; k++){
	        		csp.addConstraint(cons_row[i][j][k]);
	        		csp.addConstraint(cons_column[i][j][k]); 
	        		
	        	}
	        }
        }
 // Add constraints in the box..
        for(int j=0; j<9; j++){
        	for(int k=0; k<18; k++){
        		csp.addConstraint(cons_box[j][k]);
        	}
        }    

        tryCSP(csp);
    	
      // Calling the CSP consistency method should solve the
      // the first 10 puzzles.
      // Once you can do that, then you need to code a 
      // domain splitting per the lab's instructions.

      // Instead of printing puzzle, which will fail, 
      // you should print the solution.
      out.println(puzzle);
      //System.out.println("Solution = " + csp.getSolution());
      puzzle = in.next();
    }
    out.close();
    in.close();
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
