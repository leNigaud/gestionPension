package Data;

/**
 * Class of the table tarif
 */
public class Tarif {

    private String num_tarif;
    private String diplome;
    private String catégorie;
    private int montant;

    //Constructor 
    public Tarif(String num_tarif, String diplome, String catégorie, int montant) {
        this.num_tarif = num_tarif;
        this.diplome = diplome;
        this.catégorie = catégorie;
        this.montant = montant;
    }

    //Getters
    public String getNum_tarif() {
        return num_tarif;
    }
    public String getDiplome() {
        return diplome;
    }
    public String getCatégorie() {
        return catégorie;
    }
    public int getMontant() {
        return montant;
    }

}