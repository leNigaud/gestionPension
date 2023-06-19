import java.time.LocalDate;
import Model.*;
import Data.*;

public class Main {

public static void main(String[] args) {

    Model model = new Model();
    // Initialize DAO implementation
    PersonneDAO personneDAO = new PersonneDAO(model);

    // Create a new Personne object
    Personne newPersonne = new Personne(
        "12345", // IM
        "Smith", // Nom
        "John", // Prénoms
        LocalDate.of(1990, 1, 1), // Date de naissance
        "Diploma XYZ", // Diplome
        "123-456-7890", // Contact
        "Status A", // Statut
        "Situation A", // Situation
        "Doe", // Nom du conjoint
        "Jane" // Prénom du conjoint
    );

    // Add this Personne to the database
    personneDAO.addPersonne(newPersonne);
}

}
