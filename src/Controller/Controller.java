package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.*;
import View.*;

public class Controller {
    private Model myModel;
    private View myView;

    public Controller(Model myModel,View myView) {
        this.myModel=myModel;
        this.myView=myView;
        associerGestionnaireEvenement();
    }

   private void associerGestionnaireEvenement() {
       JButton bouton = myView.getFilterButton_Pers();
       JTextField rechercher = myView.getSearchField_Pers();


       bouton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               // Code pour gérer l'événement du bouton
               // Appeler des méthodes spécifiques du contrôleur ou effectuer des actions nécessaires
                rechercher.setText("Ca marche");
           }
       });
   }

}
