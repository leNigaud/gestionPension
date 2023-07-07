package Model;
import Data.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Random;

//for the pdf
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

//for the histogramm
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.Range;

import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;

import java.awt.Color;

//All implementations are here, to perform actions use the *DAO class
public class Model {
    private final String DB_URL = "jdbc:postgresql://localhost:5432/gestion_pension";
    private final String USER = "postgres";
    private final String PASSWORD = "njaranji";
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

    public Object[][] getAllPersByStatut(String state) {
            Object[][] rowDataArray = null;
    
        try {
            String query = "SELECT * FROM personne WHERE statut = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, state);
            
            ResultSet rs = ps.executeQuery();

            List<Object[]> rowDataList = new ArrayList<>();

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

                Object[] rowData = {
                        IM,
                        nom,
                        prénoms,
                        datenais, 
                        diplome,
                        contact,
                        statut,
                        situation,
                        nomConjoint,
                        prenomConjoint
                };

                rowDataList.add(rowData);
            }

            rowDataArray = new Object[rowDataList.size()][];
            rowDataList.toArray(rowDataArray);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rowDataArray;
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

    public List<String> getDiplome() {
        List<String> allTarifs = new ArrayList<>();
        try {
            String query = "SELECT diplome FROM tarif";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String diplome = rs.getString("diplome");
                allTarifs.add(diplome);
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
    

    public Object[][] getObjectPay() {
 
        Object[][] rowDataArray = null;
    
        try {
            String query = "SELECT personne.\"IM\", personne.nom, payer.num_tarif, tarif.montant, payer.date FROM personne, payer, tarif WHERE personne.\"IM\" = payer.\"IM\" AND tarif.num_tarif = payer.num_tarif";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            List<Object[]> rowDataList = new ArrayList<>();

            while (rs.next()) {
                String IM = rs.getString("IM");
                String nom = rs.getString("nom");
                String numTarif = rs.getString("num_tarif");
                int montant = rs.getInt("montant");
                LocalDate date = rs.getDate("date").toLocalDate();

                Object[] rowData = {
                        IM,
                        nom,
                        numTarif,
                        montant,
                        date
                };

                rowDataList.add(rowData);
            }

            rowDataArray = new Object[rowDataList.size()][];
            rowDataList.toArray(rowDataArray);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rowDataArray;
    }

    public boolean Payexiste(String im,String numTar) {
 
        try {
            String query = "SELECT COUNT(*) FROM personne, payer, tarif WHERE personne.\"IM\" = payer.\"IM\" AND tarif.num_tarif = payer.num_tarif AND personne.\\\"IM\\\" = ? AND tarif.num_tarif = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, im);
            ps.setString(2, numTar);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count>0;

        

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }


    //Filtrer par date 
    public Object[][] FiltrerDatePay(LocalDate date1,LocalDate date2) {
 
        Object[][] rowDataArray = null;
    
        try {
            String query = "SELECT personne.\"IM\", personne.nom, payer.num_tarif, tarif.montant, payer.date FROM personne, payer, tarif WHERE personne.\"IM\" = payer.\"IM\" AND tarif.num_tarif = payer.num_tarif AND payer.date BETWEEN ? AND ?";
            PreparedStatement ps = connection.prepareStatement(query);
            java.sql.Date date11 = java.sql.Date.valueOf(date1); 
            java.sql.Date date22 = java.sql.Date.valueOf(date2); 
            
            ps.setDate(1, date11);
            ps.setDate(2, date22);
            
            List<Object[]> rowDataList = new ArrayList<>();
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String IM = rs.getString("IM");
                String nom = rs.getString("nom");
                String numTarif = rs.getString("num_tarif");
                int montant = rs.getInt("montant");
                LocalDate date = rs.getDate("date").toLocalDate();

                Object[] rowData = {
                        IM,
                        nom,
                        numTarif,
                        montant,
                        date
                };

                rowDataList.add(rowData);
            }

            rowDataArray = new Object[rowDataList.size()][];
            rowDataList.toArray(rowDataArray);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rowDataArray;
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

    public boolean numTarifexiste(String numTarif) {
        Tarif tarif = null;
        try {
            String query = "SELECT COUNT(*) FROM tarif WHERE \"num_tarif\" = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, numTarif);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0 ;

           

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    //Get a Payment in the DB based on IM, numtarif, date
    public Payer getPayer(String IM, String num_tarif, LocalDate date) {
        Payer payment = null;
        try {
            String query = "SELECT * FROM payer WHERE \"IM\" = ? AND num_tarif = ? AND date = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, IM);
            ps.setString(2, num_tarif);
            
            java.sql.Date dateP = java.sql.Date.valueOf(date); 
            ps.setDate(3, dateP);

            ResultSet rs = ps.executeQuery();
            rs.next();

            String im = rs.getString("IM");
            String numTarif = rs.getString("num_tarif");
            LocalDate datee = rs.getDate("date").toLocalDate();

            payment = new Payer(im, numTarif, datee);

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return payment;
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

    public boolean IMexiste(String IM) {
      
        try {
            String query = "SELECT COUNT(*) FROM personne WHERE \"IM\" = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, IM);
            
            ResultSet rs = statement.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return false;
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
    //personne par tel
    public List<Personne> getPersonneTel(String num) {
        List<Personne> allPersons = new ArrayList<>();
        try {
            String query = "SELECT * FROM personne WHERE contact ILIKE '%" + num + "%'";

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

    //Create the pdf reçu, specify the path where the pdf will appear like(.../MyFolder/)
    public void createPDF(Payer payment, String path) {
        Personne person = getPersonne(payment.getIM());
        Tarif tarif = getTarif(payment.getNum_tarif());
        
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000; // Generates a number between 1000 and 9999
        path += "recu" + String.valueOf(randomNumber)+ ".pdf"; //Append the random number to the name of the pdf

        String IM = person.getIM();
        String Nom = person.getNom();
        String Prenoms = person.getPrénoms();
        String Mois = payment.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()); ;
        String Annee = String.valueOf(payment.getDate().getYear());
        String Montant = String.valueOf(tarif.getMontant());
        Montant += " Ar";
        createPDF(path, IM, Nom, Prenoms, Mois, Annee, Montant);
    }

    //get all the ages of personne alive
    public int[] getAllAges() {
        List<Integer> ages = new ArrayList<>();

        String query = "SELECT EXTRACT(YEAR FROM current_date) - EXTRACT(YEAR FROM datenais) AS age FROM personne WHERE statut != 'decede'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int age = resultSet.getInt("age");
                ages.add(age);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }

        int[] agesArray = new int[ages.size()];
        for (int i = 0; i < ages.size(); i++) {
            agesArray[i] = ages.get(i);
        }

        return agesArray;
    }

    //show the histogram of all ages of person
    public void showHistogram() {
    
    }

    //helper for showHistogram()
    public void createHistogram(int[] data) {
        SimpleHistogramDataset dataset = new SimpleHistogramDataset("Key");
        // add bars
        for(int i=50; i<100; i+=2){
            SimpleHistogramBin bin = new SimpleHistogramBin(i, i+2, true, false);
            int count = 0;
            for(int d: data){
                if(bin.accepts(d)){
                    bin.setItemCount(bin.getItemCount()+1);
                    count++;
                }
            }
            bin.setItemCount(count*2);
            dataset.addBin(bin);
        }


        JFreeChart histogram = ChartFactory.createHistogram(
                "Histogramme représentant le nombre de pensionnaires selon leur âge",
                "Âge",
                "Nombre de personnes",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        XYPlot plot = histogram.getXYPlot();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setTickUnit(new NumberTickUnit(1)); // Set the tick unit for the y-axis

        ValueAxis xAxis = plot.getDomainAxis();
        xAxis.setRange(new Range(50, 100)); // Set the range from 50 to 100

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 102, 204)); // Set the color of the bars using RGB values
        renderer.setDrawBarOutline(true); // Enable bar outline
        renderer.setSeriesOutlinePaint(0, Color.BLACK); // Set the color of the bar outline

        renderer.setMargin(0.0);
        renderer.setShadowVisible(false); // Disable shadow
        renderer.setBarPainter(new StandardXYBarPainter());

        // Customize plot colors
        plot.setBackgroundPaint(new Color(230, 230, 230)); // Set the background color of the plot
        plot.setRangeGridlinePaint(Color.GRAY); 
        plot.setDomainGridlinePaint(Color.GRAY);

        ChartFrame chartFrame = new ChartFrame("Histogram", histogram);
        chartFrame.pack();
        chartFrame.setVisible(true);
    }
    
    //helper function for the createPDF(Payer, path)
    public static void createPDF(String path, String IM, String Nom, String Prenoms, String Mois, String Annee, String Montant) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));

            document.open();

            // title
            Paragraph title = new Paragraph("Reçu", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Chunk("\n")); // a little space

            // table
            PdfPTable table = new PdfPTable(2); // 2 columns

            // add cells
            addTableCell(table, "IM", IM);
            addTableCell(table, "Nom", Nom);
            addTableCell(table, "Prénoms", Prenoms);
            addTableCell(table, "Mois", Mois);
            addTableCell(table, "Année", Annee);
            addTableCell(table, "Montant", Montant);

            document.add(table);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //helper function to add cells to the table
    private static void addTableCell(PdfPTable table, String header, String value) {
        PdfPCell headerCell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        headerCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(headerCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value));
        valueCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(valueCell);
    }

    //Display/Read all Conjoints
    public List<Conjoint> getAllConjoints() {
        List<Conjoint> allConjoints = new ArrayList<>();
        try {
            String query = "SELECT * FROM conjoint";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String numPension = rs.getString("numPension"); 
                String nomConjoint = rs.getString("nomConjoint");
                String prenomConjoint = rs.getString("prenomConjoint");
                int montant = rs.getInt("montant");
            
                Conjoint conjoint = new Conjoint(numPension, nomConjoint, prenomConjoint, montant); 
                allConjoints.add(conjoint);                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allConjoints;
    }

}