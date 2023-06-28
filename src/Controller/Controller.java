package Controller;

import Model.*;
import View.*;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;


import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

        // Ajout des écouteurs d'événements aux boutons ajouter de chaque sous fenetre de Boutton
        myView.addReadListener_BouttonAjouterPay(new ReadListener_BouttonAjouterPay());
        myView.addReadListener_BouttonAjouterPers(new ReadListener_BouttonAjouterPers());
        myView.addReadListener_BouttonAjouterTar(new ReadListener_BouttonAjouterTar());
        
        // Ajout des écouteurs d'événements aux boutons menus
        myView.addReadListener_BouttonPay(new ReadListener_BouttonPay());
        myView.addReadListener_BouttonPers(new ReadListener_BouttonPers());
        myView.addReadListener_BouttonTar(new ReadListener_BouttonTar());

    }
    // Implémentation des écouteurs d'événements des bouttons
    class ReadListener_BouttonAjouterPay implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JTextField[] infoPay = new JTextField[2];
            
            infoPay = View.getWinPay().getTextFields();
                
            //recuperation de la date
            JSpinner datePay = View.getWinPay().getDateSpinner_Pay();
            String date = datePay.toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate datepay = LocalDate.parse(date, formatter);
            
            Payer payAjouter = new Payer(
                infoPay[0].getText(),
                infoPay[1].getText(),
                datepay
            );
             myModel.addPayer(payAjouter);
        }
               
        }
        
    
    class ReadListener_BouttonAjouterPers implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JTextField[] infoPers = new JTextField[9];
                infoPers = secWin_newPers.getTextFields();

            //recuperation de la date
            JSpinner datenaisPers = View.getWinPers().getBirthDate(); // Votre instance de JSpinner
            String date = datenaisPers.getValue().toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate datenais = LocalDate.parse(date, formatter);

                                                    
            Personne personneAjouter = new Personne(
                infoPers[0].getText(),
                infoPers[1].getText(),
                infoPers[2].getText(),
                datenais,
                infoPers[3].getText(),
                infoPers[4].getText(),
                infoPers[5].getText(),
                infoPers[6].getText(),
                infoPers[7].getText(),
                infoPers[8].getText()
            );
            
            myModel.addPersonne(personneAjouter);
            }

    }

    class ReadListener_BouttonAjouterTar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            
            JTextField[] infoTar = new JTextField[4];
                infoTar = myView.getWinTarif().getTextFields();
                int montant = Integer.parseInt(infoTar[3].getText().toString());
           Tarif tarifAjouter = new Tarif(
               infoTar[0].getText(),
               infoTar[1].getText(),
               infoTar[2].getText(),
               montant
           );
           myModel.addTarif(tarifAjouter);
        }

    }



    // Implémentation des écouteurs d'événements des bouttons menus
    class ReadListener_BouttonPay implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Appeler la méthode appropriée du modèle pour récupérer toutes les payements
            List<Payer> paiements = myModel.getAllPayers();
            
            // Afficher les personnes dans la vue ou effectuer toute autre opération
                //  view.displayPay(paiements);
                DefaultTableModel model = new DefaultTableModel(); 
                    JTable mytablePay = View.getTable_Pay();
                    mytablePay.setModel(model);

                    for (Payer paiement : paiements) {

                        model.addRow(new Object []{paiement.getIM(),paiement.getNum_tarif(),paiement.getDate()});
                    }

            
        }
    }

    class ReadListener_BouttonPers implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Appeler la méthode appropriée du modèle pour récupérer toutes les personnes
            List<Personne> personnes = myModel.getAllPersons();
            
            // Afficher les personnes dans la vue ou effectuer toute autre opération
                
                //  view.displayPers(paiements);
                

            
        }
    }

    class ReadListener_BouttonTar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Appeler la méthode appropriée du modèle pour récupérer toutes les payements
            List<Tarif> tarifs = myModel.getAllTarifs();
            
            // Afficher les personnes dans la vue ou effectuer toute autre opération
                
            
                //  view.displayTar(tarifs);
                

            
        }
    }
    
    

    
    
    
}



//teste





        // Ajout des écouteurs d'événements

        
        //menu nouveau 
                // recuperer les valeurs saisies par l'utilisateur dans la fenetre personne et les stocker dans la base de donnees
                    //manao action rehefa miclique bouton ajouter 



// myView.getFenetrePersonne().getAjouterButton().addActionListener(new ActionListener() {
//     public void actionPerformed(ActionEvent e) {        
//         JTextField[] infoPers = new JTextField[9];

//         infoPers = myView.getFenetrePersonne().getTextFields();

//         JSpinner datenaisPers = new JSpinner();
//         datenaisPers = myView.getBirthDate();
                                                 
//         Personne personneAjouter = new Personne(
//             infoPers[0].getText(),
//             infoPers[1].getText(),
//             datenaisPers.getValue(),
//             infoPers[2].getText(),
//             infoPers[3].getText(),
//             infoPers[4].getText(),
//             infoPers[5].getText(),
//             infoPers[6].getText(),
//             infoPers[7].getText(),
//             infoPers[8].getText()
//         );
        
//         myModel.addPersonne(personneAjouter);
        
//     });
    
// }


// //recuperer les valeurs saisies par l'utilisateur dans la fenetre paiement et les stocker dans la base de donnees
// //manao action rehefa miclique bouton ajouter

// myView.getFenetrePaiement().getAjouterButton().addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e) {        
//     JTextField[] infoPay = new JTextField[2];
    
//     infoPay = myView.getFenetrePersonne().getTextFields();

//     JSpinner datePay = new JSpinner();
//          datePay = myView.getDateSpinner_Pay();

//     Personne personneAjouter = new Payer(
//         infoPay[0].getText(),
//         infoPay[1].getText(),
//         datePay.getValue()
//     );

//     myModel.addPayer(personneAjouter);

// });

// //recuperer les valeurs saisies par l'utilisateur dans la fenetre tarif et les stocker dans la base de donnees
// //manao action rehefa miclique bouton ajouter

// myView.getFenetreTarif().getAjouterButton().addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e) {        
//     JTextField[] infoTar = new JTextField[4];
    
//    infoTar = myView.getFenetrePersonne().getTextFields();

//     JSpinner datenaisPers = new JSpinner();
//          datenaisPers = myView.getBirthDate();

//     Personne personneAjouter = new Tarif(
//         infoTar[0].getText(),
//         infoTar[1].getText(),
//         infoTar[2].getText(),
//         infoTar[3].getText(),
//     );

//     myModel.addPayer(personneAjouter);

// });