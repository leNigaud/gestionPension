package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.table.JTableHeader;
import javax.swing.border.Border;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Calendar;
import java.util.Date;


public class secWin_newPers extends JDialog {
    private static JTextField[] textFields = new JTextField[6];
    private static String[] values;
    private static JButton ajouterButton = new JButton("Ajouter");
    private static JSpinner DateSpinner_Pers = new JSpinner(new SpinnerDateModel());
    private static JSpinner.DateEditor DateEditor_Pers = new JSpinner.DateEditor(DateSpinner_Pers, "dd/MM/yyyy");
    private static String[] options1 = {"vivant(e)","mort(e)"};
    private static String[] options11 = {"mort(e)","vivant(e)"};
    private static String[] options2 = {"marié(e)","veuf(ve)", "divorcé(e)"};
    private static String[] options21 = {"veuf(ve)","marié(e)", "divorcé(e)"};
    private static String[] options22 = { "divorcé(e)","marié(e)","veuf(ve)"};
    private static String[] options3 = new String[20];
    private static JComboBox<String> listeDeroulante1 = new JComboBox<>(options1);
    private static JComboBox<String> listeDeroulante2 = new JComboBox<>(options2);
    private static JComboBox<String> listeDeroulante3 = new JComboBox<>(options3);
    
    public secWin_newPers(Frame parent,String Buttname,String title) {
        
        super(parent, title, true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        ajouterButton.setText(Buttname);
        listeDeroulante2.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Condition basée sur la sélection de la combobox 1 (listeDeroulante1)
        if(listeDeroulante2.getSelectedItem().equals("marié(e)")){
            textFields[4].setEnabled(true); 
            textFields[5].setEnabled(true);
        }
        else{
            textFields[4].setEnabled(false); 
            textFields[5].setEnabled(false);
        }
         
    }
});
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        String[] nomchamp = new String[]{"IM","NOM","PRENOMS","Date de naissance","Diplôme","Contact","Statut","Situation","Nom de Conjoint(e)","Prenom de conjoint(e)"};
        int nb = 0;
        for (int i = 0; i < 10 ; i++) {
            JLabel label = new JLabel(nomchamp[i]); 
            label.setFont(new Font("Bookman Old Style", Font.PLAIN, 15));
            label.setForeground(new Color(0, 224, 230));
            constraints.gridx = 0;
            constraints.gridy = i;
            contentPanel.add(label, constraints);

            if(i==3){
                DateSpinner_Pers.setEditor(DateEditor_Pers);
                constraints.gridx = 1;
                contentPanel.add(DateSpinner_Pers, constraints);
            }else if(i==4){
                constraints.gridx = 1;
                contentPanel.add(listeDeroulante3, constraints);
            }
            else if(i==6){
                constraints.gridx = 1;
                contentPanel.add(listeDeroulante1, constraints);
            }else if(i==7){
                constraints.gridx = 1;
                contentPanel.add(listeDeroulante2, constraints);
            }
            else{
                textFields[nb] = new JTextField(20);
            constraints.gridx = 1;
            contentPanel.add(textFields[nb], constraints);nb++;
            }
        }
        JPanel buttonPanel = new JPanel();
        //if(View.rowNow != null) textFields[i].setText(View.rowNow[i]);
        String aj = "";
        if(View.rowNow != null){aj = "Modifier"; View.rowNow = null;}
        ajouterButton.setBackground(new Color(176, 224, 230));
        ajouterButton.setForeground(Color.WHITE);
        ajouterButton.setFont(ajouterButton.getFont().deriveFont(15f));

        JButton annulerButton = new JButton("Annuler");
        annulerButton.setBackground(new Color(176, 224, 230));
        annulerButton.setForeground(Color.WHITE);
        annulerButton.setFont(annulerButton.getFont().deriveFont(15f));
        annulerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // for (JTextField zonet : textFields) {
                // zonet.setText("");
                // }
                // Calendar calendar = Calendar.getInstance();
                // calendar.setTime(new java.util.Date());
                // Date currentDate = calendar.getTime();
                // DateSpinner_Pers.setValue(currentDate);
                // dispose();
                resetValues();
            }
        });

        buttonPanel.add(ajouterButton);
        buttonPanel.add(annulerButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().setBackground(Color.WHITE);
    }
    

    public void resetValues() {
        for (JTextField zonet : textFields) {
                zonet.setText("");
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new java.util.Date());
                Date currentDate = calendar.getTime();
                DateSpinner_Pers.setValue(currentDate);
                dispose();
    }

    private static String[] getValues() {
    return values;
}

//maka ny tableau de type zone de texte  (ilaina @fangalana ny entrée an'ny nouvel utilisateur) : im , nom , prenom , diplome , contact , status , situation , Nom conjoint , prenom conjoint //tsy ao lay date de naissance
   public static JTextField[] getTextFields() {
    return textFields;
}
//maka anle objet fanaovana saisis de date an'ny fanaovana saisis de date de naissance
    public static JSpinner getBirthDate(){
        return DateSpinner_Pers;
    }

//bouton ajouter
    public static JButton getAjouterButton() {
    return ajouterButton;
}

public static void setListeDeroulante1DefaultSelection(String value) {
    listeDeroulante1.setSelectedItem(value);
}

public static void setListeDeroulante2DefaultSelection(String value) {
    listeDeroulante2.setSelectedItem(value);
}

public static void setListeDeroulante3DefaultSelection(String value) {
    listeDeroulante3.setSelectedItem(value);
}

public static void setListeDeroulante3Content(String[] content) {
    listeDeroulante3.removeAllItems();
    for (String item : content) {
        listeDeroulante3.addItem(item);
    }
}

public static void setAjouterButtonText(String text) {
    ajouterButton.setText(text);
}

public static Date convertLocalDateToDate(Object loc) {
    LocalDate localDate = (LocalDate) loc;
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = localDate.format(formatter);
        return new SimpleDateFormat("dd/MM/yyyy").parse(formattedDate);
    } catch (DateTimeParseException | ParseException e) {
        e.printStackTrace();
        return null;
    }
}

//set content : mampiditra ny contenu an'ny champ 1/1
    public static void setcontent(Object[] entre){
        //"IM","NOM","PRENOMS","Date de naissance","Diplôme","Contact","Statut","Situation","Nom de Conjoint(e)","Prenom de conjoint(e)"
        String im = (String) entre[0];
        String nom = (String) entre[1];
        String prenom = (String) entre[2];
        Date nais = convertLocalDateToDate(entre[3]);
        String Diploma = (String) entre[4];
        String contact = (String) entre[5];
        String stat = (String) entre[6];
        String situ = (String) entre[7];
        String nomC = (String) entre[8];
        String pnomC = (String) entre[9];

        textFields[0].setText(im);
        textFields[1].setText(nom);
        textFields[2].setText(prenom);
        textFields[3].setText(contact);
        textFields[4].setText(nomC);
        textFields[5].setText(pnomC);
        setListeDeroulante1DefaultSelection(stat);
        setListeDeroulante2DefaultSelection(situ);
        setListeDeroulante3DefaultSelection(Diploma);
        DateSpinner_Pers.setValue(nais);

        
    }

    public static Object[] getValeursSaisies() {
    Object[] valeurs = new Object[10];

    // Récupérer les valeurs des JTextField
    int i = 0;
    for (; i < 3; i++) {
        valeurs[i] = textFields[i].getText();
    }

    // Récupérer la valeur sélectionnée dans le JSpinner (Date de naissance)
    valeurs[3] = DateSpinner_Pers.getValue();
    
    valeurs[4] = listeDeroulante3.getSelectedItem();
    valeurs[5] =  textFields[i++].getText();
    // Récupérer la valeur sélectionnée dans la liste déroulante 1 (Statut)
    valeurs[6] = listeDeroulante1.getSelectedItem();

    // Récupérer la valeur sélectionnée dans la liste déroulante 2 (Situation)
    valeurs[7] = listeDeroulante2.getSelectedItem();
    valeurs[8] =  textFields[i++].getText();
    valeurs[9] =  textFields[i].getText();

    // Récupérer la valeur sélectionnée dans la liste déroulante 3 (Diplôme)

    return valeurs;
}



}