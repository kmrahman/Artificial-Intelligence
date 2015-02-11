 import java.io.*;

// These are the methods you need to implement for Lab 2
public class ABOMethods {
    public static final String[] ALLELLES = { "O", "A", "B" };
    public static final String[] BLOODTYPES = { "O", "A", "B", "AB" };
   
    public static void createFactors(BayesianNetwork bn, String name){
    	//No parents
        String allel_str_mother= name + "'s Allele from mother ";
        String allel_str_father= name + "'s Allele from father ";   
        String bloodtype_str = name + "'s blood  type";
        
        Variable allel_mother = new Variable( allel_str_mother, ALLELLES);
        Variable allel_father = new Variable( allel_str_father, ALLELLES);
        Variable bloodtype = new Variable(bloodtype_str, BLOODTYPES);
        
        bn.addVariable(allel_mother);
        bn.addVariable(allel_father);
        bn.addVariable(bloodtype); 
        Factor f1_noparent = ABOFactors.alleleNoParent(allel_mother);
        Factor f2_noparent = ABOFactors.alleleNoParent(allel_father);
        Factor f3_noparent = ABOFactors.bloodTypeFromAlleles(bloodtype, allel_mother, allel_father);
        
        bn.addFactor(f1_noparent);
        bn.addFactor(f2_noparent);
        bn.addFactor(f3_noparent);

    }
    public static void createFactors(BayesianNetwork bn, String name,
            String mother, String father) {
        // Need to create three variables for name:
        // two alleles with values ABO.ALLELES, and
        // one blood type with values ABO.BLODDTYPES.
        // Add all three variables to bn.
    	
    	if(father== null){
    		ABOMethods.createFactors(bn, name);
    		return;
    	}
        String allel_str_mother= name + "'s Allele from " + mother;
        String allel_str_father= name + "'s Allele from " + father;   
        String bloodtype_str = name + "'s blood  type";
        
        Variable allel_mother = new Variable( allel_str_mother, ALLELLES);
        Variable allel_father = new Variable( allel_str_father, ALLELLES);
        Variable bloodtype = new Variable(bloodtype_str, BLOODTYPES);
        
     bn.addVariable(allel_mother);
     bn.addVariable(allel_father);     
     bn.addVariable(bloodtype); 
        
        
      // If mother and father are not null,
        // find the allele variables for the mother and the father, and
        // add these three factors to bn (use bn.addFactor):
        // a factor from ABOFactors.alleleFromParent for one allele and the
        // alleles from the mother,
        // a factor from ABOFactors.alleleFromParent for the other allele and
        // the alleles from the father, and
        // a factor from ABOFactors.bloodTypeFromAlleles for the blood type.
       // else{
       String mother_s_mother_str = mother + "'s Allele from mother ";
       String mother_s_father_str = mother + "'s Allele from father ";    
       String father_s_mother_str = father + "'s Allele from mother ";
       String father_s_father_str = father + "'s Allele from father ";     
 
       Variable mother_s_mom_var = bn.findVariable(mother_s_mother_str);
       Variable mother_s_dad_var = bn.findVariable(mother_s_father_str);
       Variable father_s_mom_var = bn.findVariable(father_s_mother_str);
       Variable father_s_dad_var = bn.findVariable(father_s_father_str);
       
       Factor f1_parent = ABOFactors.alleleFromParent(allel_mother, mother_s_mom_var , mother_s_dad_var);
       Factor f2_parent = ABOFactors.alleleFromParent(allel_father, father_s_mom_var, father_s_dad_var);
       Factor f3_parent = ABOFactors.bloodTypeFromAlleles(bloodtype, allel_mother, allel_father);
        
       bn.addFactor(f1_parent);
       bn.addFactor(f2_parent);
       bn.addFactor(f3_parent);

        
    }

    // Tell the Bayesian network that name has a specific blood type.
    public static void observeEvidence(BayesianNetwork bn, String name,
            String bloodtype) {
    	String bloodtype_str = name + "'s blood  type";
    	Variable v =  bn.findVariable(bloodtype_str);
        // find the appropriate variable for name's blood type
        // and store it in v

        if (v != null) {
        	bn.observe(v, bloodtype);
        }
    }

    // Run the eliminate variable algorithm for name's bloodtypes.
    public static void runNetwork(BayesianNetwork bn, String name,
            PrintStream out) {
    	String bloodtype_str = name + "'s blood  type";
    	Variable v =  bn.findVariable(bloodtype_str);
        // find the appropriate variable for name's blood type
        // and store it in v

        if (v != null) {
            Factor f = bn.eliminateVariables(v);
            out.print(f);
        } else {
            // print stuff out for Tester to reject
            // This should be CORRECT for the first network
            out.println("Variable " + name + " blood type: [O, A, B, AB]");
            out.println("0 0.36");
            out.println("1 0.45");
            out.println("2 0.13");
            out.println("3 0.06");
        }
    }
}
