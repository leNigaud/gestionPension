package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

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

   private void ajouterPersonne() {
       JButton boutonAjouter = myView.getWinPers().getAjouterButton();

       boutonAjouter.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                JTextField[] infoPers = new JTextField[9];
                infoPers = myView.getWinPers().getTextFields();

            //recuperation de la date
            JSpinner date = myView.getWinPers().getBirthDate(); // Votre instance de JSpinner
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
            System.out.println("ajout de personne reussi");
           }
       });
   }

}
