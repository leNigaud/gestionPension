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

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;


import Data.Personne;
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
        
    }

    public static LocalDate convertirEnLocalDateJava(JSpinner jSpinner) {
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

    
    //authentifications
    public boolean areFieldsNotEmpty(JTextField[] fields) {
        for (JTextField field : fields) {
            String text = field.getText();
            if (text.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean areFirstSevenFieldsNotEmpty(JTextField[] fields) {
        for (int i = 0; i < 7; i++) {
            if (fields[i].getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isSpinnerValueValid(JSpinner spinner) {
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

    //feedback
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Enregistrement réussie", JOptionPane.INFORMATION_MESSAGE);
    }    
    

    //reinitialisation des valeurs
    public void resetTextFields(JTextField[] fields) {
        for (JTextField field : fields) {
            field.setText("");
         }
    }

    public void resetDate(JSpinner spinner) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        spinner.setValue(currentDate);

    }

    public void setCurrentDate(JSpinner spinner) {
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
                    System.out.println("ajout de personne reussi");
                
                } else 
                    showErrorMessage("Vous avez mal rempli au moins un des champs");
            
           }
       });
    }

    //ajouter un paiement
    private void ajouterPaiement() {
        JButton boutonAjouter = myView.getWinPay().getAjouterButton();
    }
}
