package Data;

/**
 * Class of the table Conjoint
 */
public class Conjoint {
    private String numPension;
    private String nomConjoint;
    private String prenomConjoint;
    private int montant;

    //Constructor
    public Conjoint(String numPension, String nomConjoint, String prenomConjoint, int montant) {
        this.numPension = numPension;
        this.nomConjoint = nomConjoint;
        this.prenomConjoint = prenomConjoint;
        this.montant = montant;
    }

    //Getters
    public String getNumPension() {
        return numPension;
    }
    public String getNomConjoint() {
        return nomConjoint;
    }
    public String getPrenomConjoint() {
        return prenomConjoint;
    }
    public int getMontant() {
        return montant;
    }

    //Setters
    public void setNumPension(String numPension) {
        this.numPension = numPension;
    }

    public void setNomConjoint(String nomConjoint) {
        this.nomConjoint = nomConjoint;
    }

    public void setPrenomConjoint(String prenomConjoint) {
        this.prenomConjoint = prenomConjoint;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }
    
    
 
}