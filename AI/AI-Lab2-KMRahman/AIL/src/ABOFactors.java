public class ABOFactors {

    // Returns a factor for one allele where no parent is specified.
    // 0, 1 and 2 are indexes into ABO.ALLELLES.
    public static Factor alleleNoParent(Variable allele) {
        Factor factor = new Factor(new Variable[] { allele });
        factor.set(new int[] { 0 }, 0.6);
        factor.set(new int[] { 1 }, 0.3);
        factor.set(new int[] { 2 }, 0.1);
        return factor;
    }

    // Returns a factor for an allele from one parent.
    // For example, the mother has two alleles.
    // One of those alleles is copied to the child.
    // cAllele is the child's allele.
    // pAllele1 is an allele of the parent.
    // pAllele2 is the other allele of the same parent.
    // 0, 1 and 2 are indexes into ABO.ALLELLES.
    // Each int array has the parent's alleles first and child's allele last.
    public static Factor alleleFromParent(Variable cAllele, Variable pAllele1,
            Variable pAllele2) {

        Factor factor = new Factor(
                new Variable[] { pAllele1, pAllele2, cAllele });

        factor.set(new int[] { 0, 0, 0 }, 1.0);
        factor.set(new int[] { 0, 0, 1 }, 0.0);
        factor.set(new int[] { 0, 0, 2 }, 0.0);

        factor.set(new int[] { 0, 1, 0 }, 0.5);
        factor.set(new int[] { 0, 1, 1 }, 0.5);
        factor.set(new int[] { 0, 1, 2 }, 0.0);

        factor.set(new int[] { 0, 2, 0 }, 0.5);
        factor.set(new int[] { 0, 2, 1 }, 0.0);
        factor.set(new int[] { 0, 2, 2 }, 0.5);

        factor.set(new int[] { 1, 0, 0 }, 0.5);
        factor.set(new int[] { 1, 0, 1 }, 0.5);
        factor.set(new int[] { 1, 0, 2 }, 0.0);

        factor.set(new int[] { 1, 1, 0 }, 0.0);
        factor.set(new int[] { 1, 1, 1 }, 1.0);
        factor.set(new int[] { 1, 1, 2 }, 0.0);

        factor.set(new int[] { 1, 2, 0 }, 0.0);
        factor.set(new int[] { 1, 2, 1 }, 0.5);
        factor.set(new int[] { 1, 2, 2 }, 0.5);

        factor.set(new int[] { 2, 0, 0 }, 0.5);
        factor.set(new int[] { 2, 0, 1 }, 0.0);
        factor.set(new int[] { 2, 0, 2 }, 0.5);

        factor.set(new int[] { 2, 1, 0 }, 0.0);
        factor.set(new int[] { 2, 1, 1 }, 0.5);
        factor.set(new int[] { 2, 1, 2 }, 0.5);

        factor.set(new int[] { 2, 2, 0 }, 0.0);
        factor.set(new int[] { 2, 2, 1 }, 0.0);
        factor.set(new int[] { 2, 2, 2 }, 1.0);

        return factor;
    }

    // Returns a factor for the person's blood type from the
    // person's alleles.
    // bloodType is the person's blood type (ABO.BLOODTYPES).
    // allele1 is one allele of the person (ABO.ALLELLES).
    // allele2 is the other allele of the person (ABO.ALLELLES).
    // allele2 is the other allele of the person (ABO.ALLELLES).
    // Each int array has the alleles first and blood type last.
    // For the alleles, 0, 1 and 2 are indexes into ABO.ALLELLES.
    // For the blood type, 0, 1, 2 and 3 are indexes into ABO.BLOODTYPES.
    public static Factor bloodTypeFromAlleles(Variable bloodType,
            Variable allele1, Variable allele2) {

        Factor factor = new Factor(
                new Variable[] { allele1, allele2, bloodType });

        factor.set(new int[] { 0, 0, 0 }, 1.0);
        factor.set(new int[] { 0, 0, 1 }, 0.0);
        factor.set(new int[] { 0, 0, 2 }, 0.0);
        factor.set(new int[] { 0, 0, 3 }, 0.0);

        factor.set(new int[] { 0, 1, 0 }, 0.0);
        factor.set(new int[] { 0, 1, 1 }, 1.0);
        factor.set(new int[] { 0, 1, 2 }, 0.0);
        factor.set(new int[] { 0, 1, 3 }, 0.0);

        factor.set(new int[] { 0, 2, 0 }, 0.0);
        factor.set(new int[] { 0, 2, 1 }, 0.0);
        factor.set(new int[] { 0, 2, 2 }, 1.0);
        factor.set(new int[] { 0, 2, 3 }, 0.0);

        factor.set(new int[] { 1, 0, 0 }, 0.0);
        factor.set(new int[] { 1, 0, 1 }, 1.0);
        factor.set(new int[] { 1, 0, 2 }, 0.0);
        factor.set(new int[] { 1, 0, 3 }, 0.0);

        factor.set(new int[] { 1, 1, 0 }, 0.0);
        factor.set(new int[] { 1, 1, 1 }, 1.0);
        factor.set(new int[] { 1, 1, 2 }, 0.0);
        factor.set(new int[] { 1, 1, 3 }, 0.0);

        factor.set(new int[] { 1, 2, 0 }, 0.0);
        factor.set(new int[] { 1, 2, 1 }, 0.0);
        factor.set(new int[] { 1, 2, 2 }, 0.0);
        factor.set(new int[] { 1, 2, 3 }, 1.0);

        factor.set(new int[] { 2, 0, 0 }, 0.0);
        factor.set(new int[] { 2, 0, 1 }, 0.0);
        factor.set(new int[] { 2, 0, 2 }, 1.0);
        factor.set(new int[] { 2, 0, 3 }, 0.0);

        factor.set(new int[] { 2, 1, 0 }, 0.0);
        factor.set(new int[] { 2, 1, 1 }, 0.0);
        factor.set(new int[] { 2, 1, 2 }, 0.0);
        factor.set(new int[] { 2, 1, 3 }, 1.0);

        factor.set(new int[] { 2, 2, 0 }, 0.0);
        factor.set(new int[] { 2, 2, 1 }, 0.0);
        factor.set(new int[] { 2, 2, 2 }, 1.0);
        factor.set(new int[] { 2, 2, 3 }, 0.0);

        return factor;
    }
}
