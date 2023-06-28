package Controller;

import Model.*;
import View.*;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;


import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;


import javax.swing.JTextField;

import Data.*;

public class Controller {
    private Model myModel;
    private View myView;

    public Controller(View view,Model model) {

        myModel = model;
        myView = view;

        // Ajout des écouteurs d'événements
        myView.addReadListener(new ReadListener());

    // }

    // class ReadListener implements ActionListener {
    //     public void actionPerformed(ActionEvent e) {
    //         // Appeler la méthode appropriée du modèle pour récupérer toutes les payements
    //         List<Payer> paiements = myModel.getAllPayers();
            
    //         // Afficher les personnes dans la vue ou effectuer toute autre opération
    //             //  view.displayPeople(people);
    //             DefaultTableModel model = new DefaultTableModel(); 
    //                 JTable mytablePay = View.getTable_Pay();
    //                 mytablePay.setModel(model);

    //                 for (Payer paiement : paiements) {

    //                     model.addRow(new Object []{paiement.getIM(),paiement.getNum_tarif(),paiement.getDate()});
    //                 }

            
    //     }
    // }
    // Implémentation des écouteurs d'événements
    


        // Ajout des écouteurs d'événements

        
        //menu nouveau 
                // recuperer les valeurs saisies par l'utilisateur dans la fenetre personne et les stocker dans la base de donnees
                    //manao action rehefa miclique bouton ajouter 
                 
                            myView.getFenetrePersonne().getAjouterButton().addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {        
                                    JTextField[] infoPers = new JTextField[9];

                                    infoPers = myView.getFenetrePersonne().getTextFields();

                                    JSpinner datenaisPers = new JSpinner();
                                    datenaisPers = myView.getBirthDate();
                                                                             
                                    Personne personneAjouter = new Personne(
                                        infoPers[0].getText(),
                                        infoPers[1].getText(),
                                        datenaisPers.getValue(),
                                        infoPers[2].getText(),
                                        infoPers[3].getText(),
                                        infoPers[4].getText(),
                                        infoPers[5].getText(),
                                        infoPers[6].getText(),
                                        infoPers[7].getText(),
                                        infoPers[8].getText()
                                    );
                                    
                                    myModel.addPersonne(personneAjouter);
                                    
                                });
                                
                    }

                
                //recuperer les valeurs saisies par l'utilisateur dans la fenetre paiement et les stocker dans la base de donnees
                    //manao action rehefa miclique bouton ajouter

                    myView.getFenetrePaiement().getAjouterButton().addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {        
                                JTextField[] infoPay = new JTextField[2];
                                
                                infoPay = myView.getFenetrePersonne().getTextFields();

                                JSpinner datePay = new JSpinner();
                                     datePay = myView.getDateSpinner_Pay();
                            
                                Personne personneAjouter = new Payer(
                                    infoPay[0].getText(),
                                    infoPay[1].getText(),
                                    datePay.getValue()
                                );

                                myModel.addPayer(personneAjouter);
                            
		                });

                //recuperer les valeurs saisies par l'utilisateur dans la fenetre tarif et les stocker dans la base de donnees
                    //manao action rehefa miclique bouton ajouter

                    myView.getFenetreTarif().getAjouterButton().addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {        
                                JTextField[] infoTar = new JTextField[4];
                                
                               infoTar = myView.getFenetrePersonne().getTextFields();

                                JSpinner datenaisPers = new JSpinner();
                                     datenaisPers = myView.getBirthDate();

                                Personne personneAjouter = new Tarif(
                                    infoTar[0].getText(),
                                    infoTar[1].getText(),
                                    infoTar[2].getText(),
                                    infoTar[3].getText(),
                                );

                                myModel.addPayer(personneAjouter);
                            
		                });
        
    
}





    

    


