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
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
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
        
        ajouterPersonne();
        ajouterPaiement();
        ajouterTarif();

        showTablePay();
        showTablePers();
        showTableTar();

        filtrerTablePay();
        filtrerTablePers();

        rechercherPers();

        modifierPers();
        modifierPay();
        modifierTarif();
        
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
    public Object[][] convertListPerstoObj(List<Personne> personnes) {
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

    //feedback
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Enregistrement réussie", JOptionPane.INFORMATION_MESSAGE);
    }    
    
    public boolean contientChiffre(String str) {
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


    //ajouter une personne dans la base de donnees
    
    private void ajouterPersonne() {
       JButton boutonAjouter = myView.getWinPers().getAjouterButton();
       
       
        // final JTextField[] infoPers = info_Pers.clone(); // Créer une copie finale de infoPers

       boutonAjouter.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                JTextField[] infoPers = new JTextField[9];
                    infoPers = myView.getWinPers().getTextFields();

                //recuperation de la date
                JSpinner date = myView.getWinPers().getBirthDate(); 

                if (areFirstSevenFieldsNotEmpty(infoPers) && isSpinnerValueValid(date)) {
                    if (containsOnlyNumbers(infoPers[0])) {    
                        LocalDate dateNais = convertirEnLocalDateJava(date);
                        Personne personneAjouter = new Personne(
                            infoPers[0].getText(),
                            infoPers[1].getText(),
                            infoPers[2].getText(),
                            dateNais,
                            infoPers[3].getText(),
                            infoPers[4].getText(),
                            infoPers[5].getText(),
                            infoPers[6].getText(),
                            infoPers[7].getText(),
                            infoPers[8].getText()
                        );
                
                        myModel.addPersonne(personneAjouter);
                        showSuccessMessage("Les informations de la personne ont été bien enregistrées");
                        
                        //reinitialiser les champs
                        resetTextFields(infoPers);
                        setCurrentDate(date);

                        //fermer la fenetre
                        myView.getWinPers().dispose();
                        showTablePers();
                        System.out.println("ajout de personne reussi");
                    } else
                        showErrorMessage("L'IM doit contenir un nombre");
                } else 
                    showErrorMessage("Vous avez mal rempli au moins un des champs");
            
           }
       });
    }

    //ajouter un paiement
    private void ajouterPaiement() {
        JButton boutonAjouter = myView.getWinPay().getAjouterButton();

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
                        myView.setTableDataPers(myModel.getAllPersByStatut("décédé"));
                    else if (myView.getCheckboxVivant_Pers().isSelected())
                        myView.setTableDataPers(myModel.getAllPersByStatut("vivant"));
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
        JButton filtrer = myView.getfilterPay();
            filtrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
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
        JButton filtrer = myView.getfilterPay();
            filtrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
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
        JButton filtrer = myView.getfilterPay();
            filtrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
    }


}
