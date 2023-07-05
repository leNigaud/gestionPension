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
        
         
        ajouterPaiement();
        ajouterPersonne();
        ajouterTarif();

        modifierPay();
        modifierPers();
        modifierTarif();

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
        
    }
    
    //conversion
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

    private boolean isSpinnerValueValid(JSpinner spinner) {
            Object value = spinner.getValue();

            // Vérification si le JSpinner est vide
            if (value == null)
                return false;

            // Vérification du format "JJ-MM-AAAA"
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String spinnerValue = dateFormat.format(((SpinnerDateModel) spinner.getModel()).getDate());

            // Vérification si la valeur correspond à une date valide
            try {
                Date parsedDate = dateFormat.parse(spinnerValue);
                if (!spinnerValue.equals(dateFormat.format(parsedDate)))
                    return false;
            } catch (ParseException e) {
                return false;
            }

            // Vérification supplémentaire pour s'assurer que la valeur représente une date valide
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(spinnerValue);
            } catch (ParseException e) {
                return false;
            }

            return true;
    }

    private boolean containsOnlyNumbers(JTextField field) {
        String text = field.getText();
        return text.matches("\\d+");
    }


    public static boolean areObjetsNotEmpty(Object[] array) {
       for (Object element : array) {
           if (element == null || (element instanceof String && ((String) element).isEmpty())) {
               return false;
           }
       }
       return true;
    }   
    //feedback
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Enregistrement réussie", JOptionPane.INFORMATION_MESSAGE);
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

    private void setDiplome() {
        
      String[] diplomes = new String[myModel.getDiplome().size()];    
                        int i =0;    
                        for (String diplome : myModel.getDiplome()) {
                                   diplomes[i] = diplome;
                                   
                                    i++;
                        }        
                        myView.getWinPers().setListeDeroulante3Content(diplomes);

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
                        setDiplome();
                        if (donnee!=null) {
                             myView.getWinPersModif().setcontent(donnee);
                            myView.getWinPersModif().setVisible(true);
                        }
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


    public static LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
                System.out.println("Ajouter personne");
                
                Object[] infoPers = myView.getWinPers().getValeursSaisies();
                String conjoint = "";
                String conjoint2 = "";
                String contact = myView.getWinPers().getTextFields(3).getText();
               
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
                      
                
                    myModel.addPersonne(personneAjouter);
                    System.out.println(infoPers[4].toString());
                    System.out.println(infoPers[5].toString());
                    showSuccessMessage("Les informations de la personne ont été bien enregistrées");
                        
                        //reinitialiser les champs
                        // resetTextFields(infoPers);
                        // setCurrentDate(date);

                        //fermer la fenetre
                        myView.getWinPers().dispose();
                        showTablePers();
                        System.out.println("ajout de personne reussi");
                    
                    //     showErrorMessage("L'IM doit contenir un nombre");
                
                    // showErrorMessage("Vous avez mal rempli au moins un des champs");
            
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
                JTextField[] infoPay = new JTextField[2];
                     System.out.println("");
                    infoPay = myView.getWinPay().getTextFields();
                     System.out.println(areFieldsNotEmpty(infoPay));
                    // System.out.println(infoPay[1].getText());

                //recuperation de la date
                JSpinner date = myView.getWinPay().getDateSpinner_Pay(); 

                if(areFieldsNotEmpty(infoPay) && isSpinnerValueValid(date)) {
                    if (containsOnlyNumbers(infoPay[0]) && containsOnlyNumbers(infoPay[1])) {
                        LocalDate datePay = convertirEnLocalDateJava(date);
                
                            Payer payAjouter = new Payer(
                                infoPay[0].getText(),
                                infoPay[1].getText(),
                                datePay
                            );
                            myModel.addPayer(payAjouter);
                            showSuccessMessage("Les informations du paiement ont \u00E9t\u00E9 bien enregistr\u00E9es");
                            
                            //reinitialiser les champs
                            resetTextFields(infoPay);
                            setCurrentDate(date);

                            //fermer la fenetre
                            myView.getWinPers().dispose();
                            showTablePay();
                            System.out.println("ajout de paiement reussi");
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
                        System.out.println("ajout de tarif reussi");
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
        JButton modifier = myView.getModifyButton_Pers();
            modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
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
        JButton modifier = myView.getModifBut_Pay();
            modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
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
                     System.out.println("Creation du recu");
                }
                
            }
        });
    }

    public LocalDate convertirStringToLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        return localDate;
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

    private void modifierTarif() {
        JButton modifier = myView.getModifierButton_Tarif();
            modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
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
}
