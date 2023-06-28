package Interface;

import java.util.List;
import Data.Personne;

public interface PersonneInterface {

    //CRUD functions
    //Add/Create a Personne in the DB 
    void addPersonne(Personne person);

    //Display/Read all Persons 
    List<Personne> getAllPersons();

    //Modify/Update Personne 
    void updatePerson(Personne person);

    //Delete a Personne 
    void deletePerson(String IM);

    //Other functions
    //Get a Person from DB by his IM 
    Personne getPersonne(String IM);

    //Verify if a person is dead
    boolean isDead(Personne personne);

    //Adding nomConjoint in conjoint Table if Dead 
    void addConjoint(Personne personne);

    //Finding a person by his Matricule (IM) 
    List<Personne> getPersonneIM(String IM);

    //Finding a person by his name or firstname  
    List<Personne> getPersonneName(String name);

    //List of Personnes by statut, The total effectif is just getAllPersonneByStatut().size() 
    List<Personne> listAllPersonneByStatut();

    //get all the ages of personne alive
    int[] getAllAges();

    //Delete the corresponding Conjoint 
    void deleteConjoint(String nameConjoint, String firstNameConjoint);

}


