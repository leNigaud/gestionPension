package Model;
import Data.*;
import java.sql.*;
import java.util.*;
import java.time.*;

public class Model {
    private final String DB_URL = "jdbc:postgresql://localhost:5432/gestion_pension";
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
    //CRUD functions
    //Add/Create a Personne in the DB 
    public void addPersonne(Personne person) {
        try {
            String query = "INSERT INTO personne (\"IM\", nom, prénoms, datenais, diplome, contact, statut, situation, \"nomConjoint\", \"prenomConjoint\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, person.getIM());
            ps.setString(2, person.getNom());
            ps.setString(3, person.getPrénoms());

            java.sql.Date datenais = java.sql.Date.valueOf(person.getDatenais()); //casting the LocalDate into a sql.Date type to be entered in the DB
            ps.setDate(4, datenais);

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
            ps.setInt(4, (tarif.getMontant()));  
            
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Add/Create a Payment in DB 
    public void addPayer(Payer payment) {
        try {
            String query = "INSERT INTO payer (\"IM\", num_tarif, date) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, payment.getIM());
            ps.setString(2, payment.getNum_tarif());

            java.sql.Date date = java.sql.Date.valueOf(payment.getDate());
            ps.setDate(3, date);

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
            String editQuery = "UPDATE personne SET nom = ?, prénoms = ?, datenais = ?, diplome = ?, contact = ?, statut = ?, situation = ?, \"nomConjoint\" = ?, \"prenomConjoint\" = ? WHERE \"IM\" = ?";
            PreparedStatement editStatement = connection.prepareStatement(editQuery);
            editStatement.setString(1, person.getNom());
            editStatement.setString(2, person.getPrénoms());

            java.sql.Date datenais = java.sql.Date.valueOf(person.getDatenais()); 
            editStatement.setDate(3, datenais);
            
            editStatement.setString(4, person.getDiplome());
            editStatement.setString(5, person.getContact());
            editStatement.setString(6, person.getStatut());
            editStatement.setString(7, person.getSituation());
            editStatement.setString(8, person.getNomConjoint());
            editStatement.setString(9, person.getPrenomConjoint());
            editStatement.setString(10, person.getIM());

            if(isDead(person)) { //if a person is dead, add his conjoint to the conjoint table
                addConjoint(person);
            }
            
            editStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Modify/Update Tarif 
    public void updateTarif(Tarif tarif) {
        try {
            String editQuery = "UPDATE tarif SET diplome = ?, catégorie = ?, montant = ? WHERE num_tarif = ?";
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
            String editQuery = "UPDATE payer SET num_tarif = ?, date = ? WHERE \"IM\" = ?";
            PreparedStatement editStatement = connection.prepareStatement(editQuery);
            editStatement.setString(1, payer.getNum_tarif());

            java.sql.Date date = java.sql.Date.valueOf(payer.getDate()); 
            editStatement.setDate(2, date);
            
            editStatement.setString(3, payer.getIM());
            editStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Delete a Personne 
    public void deletePerson(String IM) {
        try {
            Personne person = getPersonne(IM);
            deleteConjoint(person.getNomConjoint(), person.getPrenomConjoint()); // if personne deleted, respective conjoint deleted

            String deleteQuery = "DELETE FROM personne WHERE \"IM\" = ?";
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

            deletePayerTarif(numTarif); // if tarif deleted, all tarifs in payer deleted
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Delete a Payment 
    public void deletePayer(String IM) {
        try {
            String deleteQuery = "DELETE FROM payer WHERE \"IM\" = ?";
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
            String deleteQuery = "DELETE FROM payer WHERE \"IM\" = ? AND num_tarif = ? AND date = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, IM);
            deleteStatement.setString(2, num_tarif);
            
            java.sql.Date dateP = java.sql.Date.valueOf(date); 
            deleteStatement.setDate(3, dateP);

            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //another deletePayer for num_tarif 
    public void deletePayerTarif(String num_tarif) {
        try {
            String deleteQuery = "DELETE FROM payer WHERE num_tarif = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, num_tarif);
            
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Delete the corresponding Conjoint 
    public void deleteConjoint(String nameConjoint, String firstNameConjoint) {
        try {
            String deleteQuery = "DELETE FROM conjoint WHERE \"nomConjoint\" = ? AND \"prenomConjoint\" = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, nameConjoint);
            deleteStatement.setString(2, firstNameConjoint);
                
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
    }
        
    }

    //Other functions
    //Get a Person from DB by his IM 
    public Personne getPersonne(String IM) {
        Personne personne = null;
        try {
            String query = "SELECT * FROM personne WHERE \"IM\" = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, IM);
            ResultSet rs = ps.executeQuery();
            rs.next();

            String im = rs.getString("IM"); 
            String nom = rs.getString("nom");
            String prénoms = rs.getString("prénoms");
            LocalDate datenais = rs.getDate("datenais").toLocalDate();
            String diplome = rs.getString("diplome");
            String contact = rs.getString("contact");
            String statut = rs.getString("statut");
            String situation = rs.getString("situation");
            String nomConjoint = rs.getString("nomConjoint");
            String prenomConjoint = rs.getString("prenomConjoint");
            
            personne = new Personne(im, nom, prénoms, datenais, diplome, contact, statut, situation, nomConjoint, prenomConjoint);
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return personne;
    }

    //Get a Tarif from DB by its num_tarif 
    public Tarif getTarif(String numTarif) {
        Tarif tarif = null;
        try {
            String query = "SELECT * FROM tarif WHERE \"num_tarif\" = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, numTarif);
            ResultSet rs = ps.executeQuery();
            rs.next();

            String numtarif = rs.getString("num_tarif");
            String diplome = rs.getString("diplome");
            String catégorie = rs.getString("catégorie");
            int montant = rs.getInt("montant");

            tarif = new Tarif(numtarif, diplome, catégorie, montant);

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return tarif;
    }

    //Get a Tarif from DB by its diplome 
    public Tarif getTarifDiplome(String diplome) {
        Tarif tarif = null;
        try {
            String query = "SELECT * FROM tarif WHERE \"diplome\" = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, diplome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { //verify if the conjoint already exists
                String numtarif = rs.getString("num_tarif");
                String diplom = rs.getString("diplome");
                String catégorie = rs.getString("catégorie");
                int montant = rs.getInt("montant");
                tarif = new Tarif(numtarif, diplom, catégorie, montant);
            }   
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return tarif;
    }

    //Verify if a person is dead
    public boolean isDead(Personne personne) {
        if(personne.getStatut() == "decede") return true;
        else return false;
    }

    //Adding nomConjoint in conjoint Table if Dead 
    public void addConjoint(Personne personne) {
        try {
            String queryVerification = "SELECT * FROM conjoint WHERE \"numPension\" = ?";
            PreparedStatement psv = connection.prepareStatement(queryVerification);
            String numPension = personne.getIM() + "2";
            psv.setString(1, numPension);
            ResultSet rs = psv.executeQuery();
            
            if (!rs.next()) { //verify if the conjoint already exists
                String query = "INSERT INTO conjoint (\"numPension\", \"nomConjoint\", \"prenomConjoint\", montant) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(query);
                String nomConjoint = personne.getNomConjoint();
                String prenomConjoint = personne.getPrenomConjoint();
                String dip = personne.getDiplome();
    
                Tarif tarif = this.getTarifDiplome(dip);
                double m = 0.4 * (double) tarif.getMontant();
                int montant = (int) Math.round(m);


                ps.setString(1, numPension);
                ps.setString(2, nomConjoint);
                ps.setString(3, prenomConjoint);
                ps.setInt(4, montant);  

                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Finding a person by his Matricule (IM) 
    public List<Personne> getPersonneIM(String IM) {
        List<Personne> allPersons = new ArrayList<>();
        try {
            String query = "SELECT * FROM personne WHERE \"IM\" LIKE '%" + IM + "%'";
            Statement statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String im = rs.getString("IM"); 
                String nom = rs.getString("nom");
                String prénoms = rs.getString("prénoms");
                LocalDate datenais = rs.getDate("datenais").toLocalDate(); 
                String diplome = rs.getString("diplome");
                String contact = rs.getString("contact");
                String statut = rs.getString("statut");
                String situation = rs.getString("situation");
                String nomConjoint = rs.getString("nomConjoint");
                String prenomConjoint = rs.getString("prenomConjoint");
            
                Personne personne = new Personne(im, nom, prénoms, datenais, diplome, contact, statut, situation, nomConjoint, prenomConjoint); 
                allPersons.add(personne);                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPersons;
    }

    //Finding a person by his name or firstname  
    public List<Personne> getPersonneName(String name) {
        List<Personne> allPersons = new ArrayList<>();
        try {
            String query = "SELECT * FROM personne WHERE nom ILIKE '%" + name + "%' OR prénoms ILIKE '%" + name + "%'";

            Statement statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String im = rs.getString("IM"); 
                String nom = rs.getString("nom");
                String prénoms = rs.getString("prénoms");
                LocalDate datenais = rs.getDate("datenais").toLocalDate(); 
                String diplome = rs.getString("diplome");
                String contact = rs.getString("contact");
                String statut = rs.getString("statut");
                String situation = rs.getString("situation");
                String nomConjoint = rs.getString("nomConjoint");
                String prenomConjoint = rs.getString("prenomConjoint");
            
                Personne personne = new Personne(im, nom, prénoms, datenais, diplome, contact, statut, situation, nomConjoint, prenomConjoint); 
                allPersons.add(personne);                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPersons;
    }

    //List of Personnes by statut, The total effectif is just getAllPersonneByStatut().size() 
    public List<Personne> listAllPersonneByStatut() {
        List<Personne> allPersons = new ArrayList<>();
        try {
            String query = "SELECT * FROM personne ORDER BY statut DESC";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String IM = rs.getString("IM"); 
                String nom = rs.getString("nom");
                String prénoms = rs.getString("prénoms");
                LocalDate datenais = rs.getDate("datenais").toLocalDate(); 
                String diplome = rs.getString("diplome");
                String contact = rs.getString("contact");
                String statut = rs.getString("statut");
                String situation = rs.getString("situation");
                String nomConjoint = rs.getString("nomConjoint");
                String prenomConjoint = rs.getString("prenomConjoint");
            
                Personne personne = new Personne(IM, nom, prénoms, datenais, diplome, contact, statut, situation, nomConjoint, prenomConjoint); 
                allPersons.add(personne);                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPersons;
    }

    //List of paid pensions between two dates 
    public List<Payer> listAllPayersBetweenDates(LocalDate date1, LocalDate date2) {
        List<Payer> allPayers = new ArrayList<>();
        try {
            String query = "SELECT * FROM payer WHERE date BETWEEN ? AND ?";
            PreparedStatement ps = connection.prepareStatement(query);

            java.sql.Date date11 = java.sql.Date.valueOf(date1); 
            java.sql.Date date22 = java.sql.Date.valueOf(date2); 

            ps.setDate(1, date11);
            ps.setDate(2, date22);
            
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

    




}
