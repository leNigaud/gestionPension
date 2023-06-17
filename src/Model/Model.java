package Model;
import java.sql.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import Data.*;

public class Model {
    private final String DB_URL = "jdbc:postgresql://localhost:5432/test";
    private final String USER = "postgres";
    private final String PASSWORD = "root";
    private Connection connection;

    //Constructor for initializing the connection to the DB
    public Model() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Add/Create a Personne in the DB
    public void addPersonne(Personne person) {
        try {
            String query = "INSERT INTO personne (IM, nom, prénoms, datenais, diplome, contact, statut, situation, nomConjoint, prenomConjoint) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, person.getIM());
            ps.setString(2, person.getNom());
            ps.setString(3, person.getPrénoms());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = person.getDatenais().format(formatter);
            ps.setString(4, dateString);

            ps.setString(5, person.getDiplome());
            ps.setString(6, person.getContact());
            ps.setString(7, person.getStatut());
            ps.setString(8, person.getSituation());
            ps.setString(9, person.getNomConjoint());
            ps.setString(10, person.getPrenomConjoint());
            
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Add/Create a Tarif in DB
    public void addTarif(Tarif tarif) {
        try {
            String query = "INSERT INTO tarif (num_tarif, diplome, catégorie, montant) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, tarif.getNum_tarif());
            ps.setString(2, tarif.getDiplome());
            ps.setString(3, tarif.getCatégorie());
            ps.setString(4, String.valueOf(tarif.getMontant()));  
            
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Add/Create a Payment in DB
    public void addPayer(Payer payment) {
        try {
            String query = "INSERT INTO tarif (IM, num_tarif, date) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, payment.getIM());
            ps.setString(2, payment.getNum_tarif());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = payment.getDate().format(formatter);
            ps.setString(3, dateString);
            
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Display/Read all Persons
    public List<Personne> getAllPersons() {
        List<Personne> allPersons = new ArrayList<>();
        try {
            String query = "SELECT * FROM personne";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String IM = rs.getString("IM"); //take all the columns for a row in the rs
                String nom = rs.getString("nom");
                String prénoms = rs.getString("prénoms");
                LocalDate datenais = rs.getDate("datenais").toLocalDate(); //need a LocalDate type
                String diplome = rs.getString("diplome");
                String contact = rs.getString("contact");
                String statut = rs.getString("statut");
                String situation = rs.getString("situation");
                String nomConjoint = rs.getString("nomConjoint");
                String prenomConjoint = rs.getString("prenomConjoint");
            
                Personne personne = new Personne(IM, nom, prénoms, datenais, diplome, contact, statut, situation, nomConjoint, prenomConjoint); // create a Personne 
                allPersons.add(personne);                // add the Personne in the list
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPersons;
    }

    //Display/Read all Tarifs
    public List<Tarif> getAllTarifs() {
        List<Tarif> allTarifs = new ArrayList<>();
        try {
            String query = "SELECT * FROM tarif";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String numTarif = rs.getString("num_tarif");
                String diplome = rs.getString("diplome");
                String categorie = rs.getString("catégorie");
                int montant = rs.getInt("montant");

                Tarif tarif = new Tarif(numTarif, diplome, categorie, montant);
                allTarifs.add(tarif);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allTarifs;
    }

    //Display/Read all Payer
    public List<Payer> getAllPayers() {
        List<Payer> allPayers = new ArrayList<>();
        try {
            String query = "SELECT * FROM payer";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String IM = rs.getString("IM");
                String numTarif = rs.getString("num_tarif");
                LocalDate date = rs.getDate("date").toLocalDate();
    
                Payer payer = new Payer(IM, numTarif, date);
                allPayers.add(payer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPayers;
    }

    //Modify/Update Personne
    public void updatePerson(Personne person) {
        try {
            String editQuery = "UPDATE personne SET nom = ?, prénoms = ?, datenais = ?, diplome = ?, contact = ?, statut = ?, situation = ?, nomConjoint = ?, prenomConjoint = ? WHERE IM = ?";
            PreparedStatement editStatement = connection.prepareStatement(editQuery);
            editStatement.setString(1, person.getNom());
            editStatement.setString(2, person.getPrénoms());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = person.getDatenais().format(formatter);
            editStatement.setString(3, dateString);
            
            editStatement.setString(4, person.getDiplome());
            editStatement.setString(5, person.getContact());
            editStatement.setString(6, person.getStatut());
            editStatement.setString(7, person.getSituation());
            editStatement.setString(8, person.getNomConjoint());
            editStatement.setString(9, person.getPrenomConjoint());
            editStatement.setString(10, person.getIM());
            editStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Modify/Update Tarif
    public void updateTarif(Tarif tarif) {
        try {
            String editQuery = "UPDATE tarif SET diplome = ?, categorie = ?, montant = ? WHERE num_tarif = ?";
            PreparedStatement editStatement = connection.prepareStatement(editQuery);
            editStatement.setString(1, tarif.getDiplome());
            editStatement.setString(2, tarif.getCatégorie());
            editStatement.setInt(3, tarif.getMontant());
            editStatement.setString(4, tarif.getNum_tarif());
            editStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Modify/Update Payer
    public void updatePayer(Payer payer) {
        try {
            String editQuery = "UPDATE payer SET num_tarif = ?, date = ? WHERE IM = ?";
            PreparedStatement editStatement = connection.prepareStatement(editQuery);
            editStatement.setString(1, payer.getNum_tarif());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = payer.getDate().format(formatter);
            editStatement.setString(2, dateString);
            
            editStatement.setString(3, payer.getIM());
            editStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Delete a Personne
    public void deletePerson(String IM) {
        try {
            String deleteQuery = "DELETE FROM personne WHERE IM = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, IM);
            
            this.deletePayer(IM); // if personne deleted, all personnes in payer deleted
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Delete a Tarif
    public void deleteTarif(String numTarif) {
        try {
            String deleteQuery = "DELETE FROM tarif WHERE num_tarif = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, numTarif);

            deletePayert(numTarif); // if tarif deleted, all tarifs in payer deleted
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Delete a Payment
    public void deletePayer(String IM) {
        try {
            String deleteQuery = "DELETE FROM payer WHERE IM = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, IM);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Overloaded deletePayer for all
    public void deletePayer(String IM, String num_tarif, LocalDate date) {
        try {
            String deleteQuery = "DELETE FROM payer WHERE IM = ? AND num_tarif = ? AND date = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, IM);
            deleteStatement.setString(2, num_tarif);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = date.format(formatter);
            deleteStatement.setString(3, dateString);

            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //another deletePayer for num_tarif
    public void deletePayert(String num_tarif) {
        try {
            String deleteQuery = "DELETE FROM payer WHERE num_tarif = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, num_tarif);
            
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Other functions
    //Adding nomConjoint in conjoint Table if Dead 
    public void addConjoint() {
        
    }
    
}
