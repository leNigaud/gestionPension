package Model;

import java.util.List;
import Data.Personne;
import Interface.PersonneInterface;

public class PersonneDAO implements PersonneInterface {
    private Model model;

    public PersonneDAO(Model model) {
        this.model = model;
    }

    @Override
    public void addPersonne(Personne person) {
        model.addPersonne(person);
    }

    @Override
    public List<Personne> getAllPersons() {
        return model.getAllPersons();
    }

    @Override
    public void updatePerson(Personne person) {
        model.updatePerson(person);
    }

    @Override
    public void deletePerson(String IM) {
        model.deletePerson(IM);
    }

    @Override
    public Personne getPersonne(String IM) {
        return model.getPersonne(IM);
    }

    @Override
    public boolean isDead(Personne personne) {
        return model.isDead(personne);
    }

    @Override
    public void addConjoint(Personne personne) {
        model.addConjoint(personne);
    }

    @Override
    public List<Personne> getPersonneIM(String IM) {
        return model.getPersonneIM(IM);
    }

    @Override
    public List<Personne> getPersonneName(String name) {
        return model.getPersonneName(name);
    }

    @Override
    public List<Personne> listAllPersonneByStatut() {
        return model.listAllPersonneByStatut();
    }

    @Override
    public int[] getAllAges() {
        return model.getAllAges();
    }

    @Override
    public void deleteConjoint(String nameConjoint, String firstNameConjoint) {
        model.deleteConjoint(nameConjoint, firstNameConjoint);
    }
}

