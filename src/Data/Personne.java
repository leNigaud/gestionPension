package Data;
import java.time.LocalDate;

/**
 * Class of the table personne
 */
public class Personne {
    private String IM;
    private String nom;
    private String prénoms;
    private LocalDate datenais;
    private String diplome;
    private String contact;
    private String statut;
    private String situation;
    private String nomConjoint;
    private String prenomConjoint;

    //constructor of a Personne
    public Personne(String iM, String nom, String prénoms, LocalDate datenais, String diplome, String contact,
            String statut, String situation, String nomConjoint, String prenomConjoint) {
        this.IM = iM;
        this.nom = nom;
        this.prénoms = prénoms;
        this.datenais = datenais;
        this.diplome = diplome;
        this.contact = contact;
        this.statut = statut;
        this.situation = situation;
        this.nomConjoint = nomConjoint;
        this.prenomConjoint = prenomConjoint;
    }

    //Getters
    public String getIM() {
        return IM;
    }
    public String getNom() {
        return nom;
    }
    public String getPrénoms() {
        return prénoms;
    }
    public LocalDate getDatenais() {
        return datenais;
    }
    public String getDiplome() {
        return diplome;
    }
    public String getContact() {
        return contact;
    }
    public String getStatut() {
        return statut;
    }
    public String getSituation() {
        return situation;
    }
    public String getNomConjoint() {
        return nomConjoint;
    }
    public String getPrenomConjoint() {
        return prenomConjoint;
    }
    
}