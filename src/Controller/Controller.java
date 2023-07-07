package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

import Data.Conjoint;
import Data.Payer;
import Data.Personne;
import Data.Tarif;
import Model.*;
import View.*;

public class Controller {
    private Model myModel;
    private View myView;

    public Controller(Model myModel,View myView) {
        this.myModel=myModel;
        this.myView=myView;
        
        rafraichir();
         
        ajouterPaiement();
        ajouterPersonne();
        ajouterTarif();

        afficherPers();
        afficherPay();
        afficherTar();
        

        showTablePay();
        showTablePers();
        showTableTar();

        filtrerTablePay();
        filtrerTablePers();

        rechercherPers();

        modifierPers();
        modifierPay();
        modifierTarif();

        genererRecu();
        creerHisto();

        supprimerPay();
        supprimerPers();
        supprimerTarif();

        gestionConjoint();
        
    }
    
    //conversion

    private LocalDate convertirStringToLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        return localDate;
    }

    private  LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    private LocalDate convertirEnLocalDateJava(JSpinner jSpinner) {
        // Obtenir la valeur sélectionnée dans le JSpinner
        Object valeurSelectionnee = jSpinner.getValue();

        // Vérifier si la valeur est de type java.util.Date
        if (valeurSelectionnee instanceof java.util.Date) {
            // Convertir la valeur en LocalDate
            java.util.Date date = (java.util.Date) valeurSelectionnee;
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            return localDate;
        } else {
            throw new IllegalArgumentException("Le JSpinner ne contient pas une valeur de type Date.");
        }
    }
    private Object[][] convertListPerstoObj(List<Personne> personnes) {
        Object[][] result = new Object[personnes.size()][];
        
        for (int i = 0; i < personnes.size(); i++) {
            Personne personne = personnes.get(i);
            
            Object[] rowData = {
                personne.getIM(),
                personne.getNom(),
                personne.getPrénoms(),
                personne.getDatenais(),
                personne.getDiplome(),
                personne.getContact(),
                personne.getStatut(),
                personne.getSituation(),
                personne.getNomConjoint(),
                personne.getPrenomConjoint()
            };
            
            result[i] = rowData;
        }
    
        return result;
    }

    private List<Personne> convertObjToListPers(Object[][] array) {
        List<Personne> personnes = new ArrayList<>();

        for (Object[] rowData : array) {
            String IM = (String) rowData[0];
            String nom = (String) rowData[1];
            String prenoms = (String) rowData[2];
            LocalDate datenais = (LocalDate) rowData[3];
            String diplome = (String) rowData[4];
            String contact = (String) rowData[5];
            String statut = (String) rowData[6];
            String situation = (String) rowData[7];
            String nomConjoint = (String) rowData[8];
            String prenomConjoint = (String) rowData[9];

            Personne personne = new Personne(IM, nom, prenoms, datenais, diplome, contact, statut, situation, nomConjoint, prenomConjoint);
            personnes.add(personne);
        }

            return personnes;
    }

    private Object[][] convertObjToListTarif(List<Tarif> tarifs) {
        Object[][] result = new Object[tarifs.size()][];
        
        for (int i = 0; i < tarifs.size(); i++) {
            Tarif tarif = tarifs.get(i);

            Object[] rowData = {
                tarif.getNum_tarif(),
                tarif.getDiplome(),
                tarif.getCatégorie(),
                tarif.getMontant()
            };

            result[i] = rowData;
        }

        return result;
    }

    public static Object[] convertirStringEnObject(String[] strings) {
         if (strings == null) {
        return null; // Retourne null si le tableau strings est null
        }
        
        
        Object[] objects = new Object[strings.length];
        
        for (int i = 0; i < strings.length; i++) {
            objects[i] = strings[i];
        }

        return objects;
    }

    private  Object[] convertConjointToArray(List<Conjoint> listeConjoints) {
        Object[] array = new Object[listeConjoints.size()];
        for (int i = 0; i < listeConjoints.size(); i++) {
            Conjoint conjoint = listeConjoints.get(i);
            Object[] obj = new Object[4];
            obj[0] = conjoint.getNumPension();
            obj[1] = conjoint.getNomConjoint();
            obj[2] = conjoint.getPrenomConjoint();
            obj[3] = conjoint.getMontant();
            array[i] = obj;
        }
        return array;
    }

        private  Object[][] convertConjointToArray2(List<Conjoint> listeConjoints) {
        Object[][] array = new Object[listeConjoints.size()][];
        for (int i = 0; i < listeConjoints.size(); i++) {
            Conjoint conjoint = listeConjoints.get(i);
            Object[] obj = {
                conjoint.getNumPension(),
                conjoint.getNomConjoint(),
                conjoint.getPrenomConjoint(),
                conjoint.getMontant()
            };
            array[i] = obj;
        }
        return array;
    }
    
    
    //authentifications
    private boolean areFieldsNotEmpty(JTextField[] fields) {
        for (JTextField field : fields) {
            // if (field ==null) return false;
            String text = field.getText();
            if (text.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean areFirstSevenFieldsNotEmpty(JTextField[] fields) {
        for (int i = 0; i < 7; i++) {
            if (fields[i].getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isDateValid(JSpinner spinner) {
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date selectedDate = (Date) spinner.getValue();
        String formattedDate = sdf.format(selectedDate);

        try {
            Date parsedDate = sdf.parse(formattedDate);
            return formattedDate.equals(sdf.format(parsedDate));
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean containsOnlyNumbers(JTextField field) {
        String text = field.getText();
        return text.matches("\\d+");
    }

    private boolean containsOnlyNumbers(String text) {
        return text.matches("\\d+");
    }

    private boolean containsContact(String text) {
        return text.matches("^03[2-48] [0-9]{2} [0-9]{3} [0-9]{2}$");
    }


    private boolean areObjetsNotEmpty(Object[] array) {
       for (Object element : array) {
           if (element == null || (element instanceof String && ((String) element).isEmpty())) {
               return false;
           }
       }
       return true;
    }   
    
    private boolean contientChiffre(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }

        return false;
    }
    
    //feedback
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Enregistrement réussie", JOptionPane.INFORMATION_MESSAGE);
    }    
    


    //reinitialisation des valeurs
    private void resetTextFields(JTextField[] fields) {
        for (JTextField field : fields) {
            field.setText("");
         }
    }

    private void resetDate(JSpinner spinner) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        spinner.setValue(currentDate);

    }

    private void setCurrentDate(JSpinner spinner) {
        Date currentDate = new Date();
        spinner.setModel(new SpinnerDateModel(currentDate, null, null, java.util.Calendar.DAY_OF_MONTH));
    }

    //getter

    public Object[] getSelectedRowData(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int columnCount = table.getColumnCount();
            Object[] rowData = new Object[columnCount];

            for (int i = 0; i < columnCount; i++) {
                // if (i==3) 
                //     rowData[i] = (Date) table.getValueAt(selectedRow, i);
                // else
                     rowData[i] = table.getValueAt(selectedRow, i);
            }

            return rowData;
        }

        return null;
    }

    //diplome

    private void setDiplome() {
        
      String[] diplomes = new String[myModel.getDiplome().size()];    
                        int i =0;    
                        for (String diplome : myModel.getDiplome()) {
                                   diplomes[i] = diplome;
                                   
                                    i++;
                        }        
                        myView.getWinPers().setListeDeroulante3Content(diplomes);

    }

    private void setDiplomeModif() {
        
      String[] diplomes = new String[myModel.getDiplome().size()];    
                        int i =0;    
                        for (String diplome : myModel.getDiplome()) {
                                   diplomes[i] = diplome;
                                   
                                    i++;
                        }        
                        myView.getWinPersModif().setListeDeroulante3Content(diplomes);

    }
    
    //afficher les sous fenetres 

    private void afficherPers() {

        JButton nouveau = myView.getButton1_New(),
                modifier = myView.getModifyButton_Pers();
            
                nouveau.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setDiplome();
                        myView.getWinPers().setVisible(true);
                    }
                });
                
                modifier.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object[] donnee = getSelectedRowData(myView.getTable_Pers());
                        setDiplomeModif();
                        if (donnee!=null) {
                             myView.getWinPersModif().setcontent(donnee);
                            myView.getWinPersModif().setVisible(true);
                        }
                }
                
         });
    }

    private void rafraichir() {
        JButton rafraichirPay=myView.getrefreshBut_Pay(),
                rafraichirPers=myView.getRefreshButton_Pers(),
                rafraichirTar=myView.getRefreshButton_Tarif();

                rafraichirPay.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       showTablePay();

                       
                    }
                });
                rafraichirPers.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      showTablePers();
                       
                    }
                });
                rafraichirTar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showTableTar();
                       
                    }
                });


    }

    private void afficherPay() {
        JButton nouveau = myView.getButton2_New(),
                modifier = myView.getModifBut_Pay();
            
                nouveau.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        myView.getWinPay().setVisible(true);
                       
                    }
                });

                modifier.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object[] donnee = getSelectedRowData(myView.getTable_Pay());
                        if (donnee!=null) {
                             myView.getWinPayModif().setcontent(donnee);
                            myView.getWinPayModif().setVisible(true);
                        }
                    }

                });
        
    }
    private void afficherTar() {
        JButton nouveau = myView.getButton3_New(),
                modifier = myView.getModifierButton_Tarif();
            
                nouveau.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        myView.getWinTarif().setVisible(true);
                    }
                });

                modifier.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String[] donnee = myView.getSelectedRowData(myView.getTable_Tarif());
                        if (donnee!=null) {
                            myView.getWinTarifModif().setElementValues(donnee);
                            myView.getWinTarifModif().setVisible(true);
                        }   

                    }
                
                });
    }



    //ajouter une personne dans la base de donnees
    
    private void ajouterPersonne() {
       JButton boutonAjouter = myView.getWinPers().getAjouterButton();
       
       // Supprimer tous les écouteurs d'événements existants 
        // ActionListener[] listeners = boutonAjouter.getActionListeners();
        // for (ActionListener listener : listeners) {
        //         boutonAjouter.removeActionListener(listener);
        // }
    

       boutonAjouter.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                
                Boolean entree=true;
                Object[] infoPers = myView.getWinPers().getValeursSaisies();
                JTextField[] champ = myView.getWinPers().getTextFields();
                        
               
                String conjoint = "";
                String conjoint2 = "";
                // String contact = myView.getWinPers().getTextFields(3).getText();
               
                if (infoPers[8] != null && infoPers[9] != null) {
                    conjoint = infoPers[8].toString();
                    conjoint2 = infoPers[9].toString();
                }
                Personne personneAjouter = new Personne(
                    infoPers[0].toString(),
                    infoPers[1].toString(),
                    infoPers[2].toString(),
                    convertDateToLocalDate((Date) infoPers[3]),
                    infoPers[4].toString(),
                    infoPers[5].toString(),
                    infoPers[6].toString(),
                    infoPers[7].toString(),
                    conjoint,
                    conjoint2
                );
                System.out.println(myModel.IMexiste("IM"+infoPers[0].toString()));

                   
                  
                    

                   
                    for(int i=0;i<8;i++) {
                        if (infoPers[i].toString().isEmpty()) {
                            
                            showErrorMessage("Vous avez mal rempli au moins un des champs");
                            entree=false;
                            break;
                        } else {
                            if (i==0) {
                                if (!containsOnlyNumbers(infoPers[i].toString())) {
                                    showErrorMessage("L'IM doit être un nombre");
                                    entree=false;
                                    break;
                                } else if (myModel.IMexiste(infoPers[i].toString())) {
                                    showErrorMessage("L'IM existe déjà");
                                    entree=false;
                                    break;
                                }
                            }
                            if (i==1) {
                                if (contientChiffre(infoPers[i].toString())) {
                                    showErrorMessage("Le nom ne doit pas contenir de chiffre");
                                    entree=false;
                                    break;
                                }
                            }
                            if (i==2) {
                                if (contientChiffre(infoPers[i].toString())) {
                                    showErrorMessage("Le prénom ne doit pas contenir de chiffre");
                                    entree=false;
                                    break;
                                }
                            }
                            if (i==5) {
                                if (!containsContact(infoPers[i].toString())) {
                                    showErrorMessage("Le contact n'est pas valide");
                                    entree=false;
                                    break;
                                }
                            }

                            if (i==7) {
                                if (infoPers[i].toString() == "marié(e)") {
                                    if (conjoint.isEmpty()) {
                                        showErrorMessage("Le nom du conjoint ne doit pas être vide");
                                        entree=false;
                                        break;
                                    } else if (contientChiffre(conjoint)){
                                         showErrorMessage("Le nom du conjoint ne doit pas contenir de chiffre");
                                         entree=false;
                                         break;
                                    }
                                    if (conjoint2.isEmpty()) {
                                        showErrorMessage("Le prénom du conjoint ne doit pas être vide");
                                        entree=false;
                                        break;
                                    } else if (contientChiffre(conjoint2)) {
                                        showErrorMessage("Le prénom du conjoint ne doit pas contenir de chiffre");
                                        entree=false;
                                        break;
                                    }

                                 }

                            }
                        }   

                    }
                    
                    if (entree) {
                      
                        myModel.addPersonne(personneAjouter);
                        System.out.println(infoPers[4].toString());
                        System.out.println(infoPers[5].toString());
                        showSuccessMessage("Les informations de la personne ont été bien enregistrées");

                        //reinitialiser les champs et fermer les fenetres
                        myView.getWinPers().resetValues();
                        showTablePers();
                        System.out.println("Ajout de personne réussi");
                    
                    
                    }
            
            }
    });
     
    }

    //ajouter un paiement
    private void ajouterPaiement() {
        JButton boutonAjouter = myView.getWinPay().getAjouterButton();

        // Supprimer tous les écouteurs d'événements existants 
        ActionListener[] listeners = boutonAjouter.getActionListeners();
        for (ActionListener listener : listeners) {
                boutonAjouter.removeActionListener(listener);
        }

        boutonAjouter.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                JTextField[] infoPay = myView.getWinPay().getTextFields();
            
                    // System.out.println(infoPay[1].getText());

                //recuperation de la date
                JSpinner date = myView.getWinPay().getDateSpinner_Pay(); 

                if(areFieldsNotEmpty(infoPay)) {
                    if (containsOnlyNumbers(infoPay[0]) && containsOnlyNumbers(infoPay[1])) {
                        if (!myModel.IMexiste(infoPay[0].getText()) )
                            showErrorMessage("L'IM n'existe pas");
                        if (!myModel.numTarifexiste(infoPay[1].getText()) )
                            showErrorMessage("Le numéro de tarif n'existe pas");
                        else {
                            LocalDate datePay = convertirEnLocalDateJava(date);
                        
                                Payer payAjouter = new Payer(
                                    infoPay[0].getText(),
                                    infoPay[1].getText(),
                                    datePay
                                );
                                myModel.addPayer(payAjouter);
                                showSuccessMessage("Les informations du paiement ont \u00E9t\u00E9 bien enregistr\u00E9es");

                                //reinitialiser les champs
                                myView.getWinPers().resetValues();

                                //fermer la fenetre

                                showTablePay();
                                System.out.println("Ajout de paiement réussi");
                        }
                    } else 
                        showErrorMessage("Vos devez saisir un nombre sur les champs \"IM\" et \"numero de tarif\"");
                
                }   else
                         showErrorMessage("Vous avez mal rempli au moins un des champs");

               
            }
        });
    }

    //ajouter tarif

    private void ajouterTarif() {
        JButton boutonAjouter = myView.getWinTarif().getAjouterButton();

        boutonAjouter.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                JTextField[] infoTar = new JTextField[4];
                infoTar = myView.getWinTarif().getTextFields();
                
                
                
                if(areFieldsNotEmpty(infoTar)) {
                    if (containsOnlyNumbers(infoTar[3])) {
                        if (myModel.numTarifexiste(infoTar[0].getText()))    
                            showErrorMessage("Le numéro de tarif existe déjà");
                        else {
                            //conversion du montant en entier
                            int montant = Integer.parseInt(infoTar[3].getText());
                            Tarif tarifAjouter = new Tarif(
                                 infoTar[0].getText(),
                                 infoTar[1].getText(),
                                 infoTar[2].getText(),
                                 montant
                            );
                            myModel.addTarif(tarifAjouter);
                            showSuccessMessage("Les informations du tarif ont \u00E9t\u00E9 bien enregistr\u00E9es");

                             //reinitialiser les champs
                            resetTextFields(infoTar);

                            //fermer la fenetre
                            myView.getWinTarif().dispose();
                            showTableTar();
                            System.out.println("Ajout de tarif réussi");
                        }
                    } else
                        showErrorMessage("Vos devez saisir un nombre sur le champ \"montant\"");
                
                }   else
                         showErrorMessage("Vous avez mal rempli au moins un des champs");

               
            }
        });
    }

    //menu personne
    private void showTablePers() {
        List<Personne> personnes = myModel.getAllPersons();
        Object[][] matrix = convertListPerstoObj(personnes);
        myView.setTableDataPers(matrix);
    }

    private void filtrerTablePers() {
        JButton filtrer = myView.getFilterButton_Pers();
            filtrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myView.getCheckboxMort_Pers().isSelected() && myView.getCheckboxVivant_Pers().isSelected())
                    showTablePers();
                else {     
                    if (myView.getCheckboxMort_Pers().isSelected())
                        myView.setTableDataPers(myModel.getAllPersByStatut("mort(e)"));
                    else if (myView.getCheckboxVivant_Pers().isSelected())
                        myView.setTableDataPers(myModel.getAllPersByStatut("vivant(e)"));
                    else
                        myView.setTableDataPers(null);
               }
            }
        });
    }

    private void rechercherPers() {
        JButton rechercher = myView.getSearchButton_Pers();
            rechercher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String motRechercher= myView.getSearchField_Pers().getText();
                if (myView.getSearchField_Pers().getText().isEmpty())
                    showTablePers();
                else {
                    if (contientChiffre(motRechercher)) {
                        Object[][] donnee=convertListPerstoObj(myModel.getPersonneTel(motRechercher));
                        myView.setTableDataPers(donnee);
                    }
                    else {
                        Object[][] donnee=convertListPerstoObj(myModel.getPersonneName(motRechercher));
                        myView.setTableDataPers(donnee);

                    }
                        
                }
                //  System.out.println(motRechercher);

            }
        });
    }

     private void modifierPers() {
        JButton modifier = myView.getWinPersModif().getAjouterButton();
            modifier.addActionListener(new ActionListener() {
            @Override
                    public void actionPerformed(ActionEvent e) {
                            Boolean entree=true;
                            Object[] infoPers = myView.getWinPersModif().getValeursSaisies();
                            JTextField[] champ = myView.getWinPersModif().getTextFields();
                                    
                            boolean conjointvide=true;
                            String conjoint = "";
                            String conjoint2 = "";
                            // String contact = myView.getWinPers().getTextFields(3).getText();
                        
                            if (infoPers[8] != null && infoPers[9] != null) {
                                conjoint = infoPers[8].toString();
                                conjoint2 = infoPers[9].toString();
                                
                            }
                            Personne personneAjouter = new Personne(
                                infoPers[0].toString(),
                                infoPers[1].toString(),
                                infoPers[2].toString(),
                                convertDateToLocalDate((Date) infoPers[3]),
                                infoPers[4].toString(),
                                infoPers[5].toString(),
                                infoPers[6].toString(),
                                infoPers[7].toString(),
                                conjoint,
                                conjoint2
                            );
                        
                            // System.out.println(infoPers[7].toString()=="marié(e)");
                            // System.out.println(conjoint.isEmpty());
                    
                            for(int i=0;i<8;i++) {
                                if (infoPers[i].toString().isEmpty()) {
                                    
                                    showErrorMessage("Vous avez mal rempli au moins un des champs");
                                    entree=false;
                                    break;
                                } else {
                                    if (i==0) {
                                        if (!containsOnlyNumbers(infoPers[i].toString())) {
                                            showErrorMessage("L'IM doit être un nombre");
                                            entree=false;
                                            break;
                                        } else if (myModel.IMexiste(infoPers[i].toString())) {
                                            if (!myView.afficherQuestionOuiNon("L'IM existe déjà,vous êtes sûre de vouloir écraser les informations? ")) {
                                                    entree=false;
                                                    break;
                                            }
                                        }
                                    }
                                    if (i==1) {
                                        if (contientChiffre(infoPers[i].toString())) {
                                            showErrorMessage("Le nom ne doit pas contenir de chiffre");
                                            entree=false;
                                            break;
                                        }
                                    }
                                    if (i==2) {
                                        if (contientChiffre(infoPers[i].toString())) {
                                            showErrorMessage("Le prénom ne doit pas contenir de chiffre");
                                            entree=false;
                                            break;
                                        }
                                    }
                                    if (i==5) {
                                        if (!containsContact(infoPers[i].toString())) {
                                            showErrorMessage("Le contact n'est pas valide");
                                            entree=false;
                                            break;
                                        }
                                    }
                                    if (i==7) {
                                        if (infoPers[i].toString() == "marié(e)") {
                                            if (conjoint.isEmpty()) {
                                                showErrorMessage("Le nom du conjoint ne doit pas être vide");
                                                entree=false;
                                                break;
                                            } else if (contientChiffre(conjoint)){
                                                 showErrorMessage("Le nom du conjoint ne doit pas contenir de chiffre");
                                                 entree=false;
                                                 break;
                                            }
                                            if (conjoint2.isEmpty()) {
                                                showErrorMessage("Le prénom du conjoint ne doit pas être vide");
                                                entree=false;
                                                break;
                                            } else if (contientChiffre(conjoint2)) {
                                                showErrorMessage("Le prénom du conjoint ne doit pas contenir de chiffre");
                                                entree=false;
                                                break;
                                            }

                                        }

                                    }

                                }
                            
                            }
                            
                            
                            if (entree) {
                            
                                myModel.updatePerson(personneAjouter);
                                showSuccessMessage("Les informations de la personne ont bien été mises à jour");

                                //reinitialiser les champs et fermer les fenetres
                                myView.getWinPersModif().resetValues();
                                showTablePers();
                                System.out.println("Mise à jour de la personne réussie");
                            
                            
                            }
            
                    }
                    
            });
              
    }

    private void supprimerPers() {
        JButton supprimer = myView.getDeleteButton_Pers();
            supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] donnee = myView.getSelectedRowData(myView.getTable_Pers());
                if (donnee!=null) {
                    if (myView.afficherQuestionOuiNon("Vous êtes sûre de vouloir supprimer cette personne de la base de données?")) { 
                        myModel.deletePerson(donnee[0]);
                        showTablePers();
                    }
                }

            }
        });
    }


    //menu paiement
    private void showTablePay() {
     
        myView.setTableDataPay(myModel.getObjectPay());

    }

    private void filtrerTablePay() {
        JButton filtrer = myView.getfilterPay();
            filtrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate datedeb = convertirEnLocalDateJava(myView.getStartDateSpinner_Pay());
                LocalDate datefin = convertirEnLocalDateJava(myView.getEndDateSpinner_Pay());
                myView.setTableDataPay(myModel.FiltrerDatePay(datedeb,datefin));
            }
        });
    }

     private void modifierPay() {
        JButton modifier = myView.getWinPayModif().getAjouterButton();
            modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField[] infoPay = myView.getWinPayModif().getTextFields();
            
                    // System.out.println(infoPay[1].getText());

                //recuperation de la date
                JSpinner date = myView.getWinPayModif().getDateSpinner_Pay(); 

                if(areFieldsNotEmpty(infoPay)) {
                    if (containsOnlyNumbers(infoPay[0]) && containsOnlyNumbers(infoPay[1])) {
                        LocalDate datePay = convertirEnLocalDateJava(date);
                
                            Payer payAjouter = new Payer(
                                infoPay[0].getText(),
                                infoPay[1].getText(),
                                datePay
                            );
                            myModel.updatePayer(payAjouter);
                            showSuccessMessage("Les informations du paiement ont bien été mises à jour");
                            
                            //reinitialiser les champs
                            myView.getWinPayModif().resetValues();

                            //fermer la fenetre
                            
                            showTablePay();
                            System.out.println("Mise à jour du paiement réussie");
                    } else 
                        showErrorMessage("Vos devez saisir un nombre sur les champs \"IM\" et \"numero de tarif\"");
                
                }   else
                         showErrorMessage("Vous avez mal rempli au moins un des champs");
            }
        });
    }


    private void genererRecu() {
        JButton generer = myView.getRecu_Pay();
            generer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] donnee = myView.getSelectedRowData(myView.getTable_Pay());
                if (donnee!=null) {
                    
                    LocalDate date = LocalDate.parse(donnee[4]);
                    Payer paie = new Payer(donnee[0], donnee[2], date);
                    myModel.createPDF(paie, "D:\\");
                    showSuccessMessage("Vous pouvez réupérer votre reçu à 'D:\\' ");
                     System.out.println("Création du reçu");
                }
                
            }
        });
    }


    private void supprimerPay() {
        JButton supprimer = myView.getDelBut_Pay();
            supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] donnee = myView.getSelectedRowData(myView.getTable_Pay());
                if (donnee!=null) {
                    if (myView.afficherQuestionOuiNon("Vous êtes sûre de vouloir supprimer ce paiement de la base de données?"))    
                        {
                            myModel.deletePayer(donnee[0],donnee[2],convertirStringToLocalDate(donnee[4]));
                            showTablePay();
                        }
                }
            }
        });
    }



    //menu tarif
    private void showTableTar() {
        List<Tarif> tarifs = myModel.getAllTarifs();
        Object[][] matrix = new Object[tarifs.size()][];
        
        for (int i = 0; i < tarifs.size(); i++) {
            Tarif tarif = tarifs.get(i);
            Object[] rowData = {
                tarif.getNum_tarif(),
                tarif.getDiplome(),
                tarif.getCatégorie(),
                tarif.getMontant()
            };
            matrix[i] = rowData;
        }
        myView.setTableDataTarif(matrix);

    }

    private void  updatetar (JTextField[] infoTar) {
              int montant = Integer.parseInt(infoTar[3].getText());
                                    Tarif tarifAjouter = new Tarif(
                                         infoTar[0].getText(),
                                         infoTar[1].getText(),
                                         infoTar[2].getText(),
                                         montant
                                    );
                                    myModel.updateTarif(tarifAjouter);
                                    showSuccessMessage("Les informations du tarif ont bien été mises à jour");

                                     //reinitialiser les champs
                                    resetTextFields(infoTar);

                                    //fermer la fenetre
                                    myView.getWinTarifModif().dispose();
                                    showTableTar();
                                    System.out.println("Mise à jour du tarif réussi");
    }

    private void modifierTarif() {
        JButton modifier = myView.getWinTarifModif().getAjouterButton();
            modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 JTextField[] infoTar = myView.getWinTarifModif().getTextFields();
          
                
                if(areFieldsNotEmpty(infoTar)) {
                    if (containsOnlyNumbers(infoTar[3]) && containsOnlyNumbers(infoTar[0])) { 
                        if (myModel.numTarifexiste(infoTar[0].getText())) {
                            if ( myView.afficherQuestionOuiNon("Le numéro de tarif existe déjà,vous êtes sûre de vouloir écraser les informations?") ) {
                                updatetar(infoTar); System.out.println(" myView.afficherQuestionOuiNon");
                            }
                              
                                  
                             
                            
                        }
                        
                        else {
                            updatetar(infoTar);  System.out.println(" else");
                             
                        }
                                
                        
                        
                    } else
                        showErrorMessage("Vos devez saisir un nombre sur les champs  \"numéro de tarif\" et \"montant\"");
                }  else
                         showErrorMessage("Vous avez mal rempli au moins un des champs");
            }
        });
    }

    private void supprimerTarif() {
        JButton supprimer = myView.getSupprimerButton_Tarif();
            supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] donnee = myView.getSelectedRowData(myView.getTable_Tarif());
                if (donnee!=null) {
                    if (myView.afficherQuestionOuiNon("Vous êtes sûre de vouloir supprimer ce tarif de la base de données?"))  {
                        myModel.deleteTarif(donnee[0]);
                        showTableTar();
                    }
                }
            }
        });
    }


    //histogramme
    private void creerHisto() {    
        JButton histo = myView.gethistogrammeButton();
            histo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myModel.createHistogram(myModel.getAllAges());
            }
        });
    }


    //conjoint
    private void gestionConjoint() {
        JButton boutonconjoint = myView.getconjointButton_Pers();
            boutonconjoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                List<Conjoint> conjoints = myModel.getAllConjoints();
                Object[][] donnee = convertConjointToArray2(conjoints);
                for (int i=0;i < donnee.length;i++) {
                      for (int j=0;j < 4;j++)
                      System.out.println("test"+donnee[i][j]);
                }

                for (Conjoint conjoint : conjoints) {
                    System.out.println(conjoint.getNomConjoint());
                }
               ;
                myView.getWinConjoint().addtab(convertConjointToArray2(conjoints));
                myView.getWinConjoint().setVisible(true);

            }
        });

    }
    
}
