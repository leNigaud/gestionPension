package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.JTableHeader;
import javax.swing.border.Border;
import java.time.LocalDate;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class secWin_newPers extends JDialog {
    private static JTextField[] textFields = new JTextField[9];
    private static String[] values;
    private static JButton ajouterButton = new JButton("Ajouter");
    private static JSpinner DateSpinner_Pers = new JSpinner(new SpinnerDateModel());
    private static JSpinner.DateEditor DateEditor_Pers = new JSpinner.DateEditor(DateSpinner_Pers, "dd-MM-yyyy");
    public secWin_newPers(Frame parent) {
        super(parent, "Ajouter un nouvelle personne", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(500, 400);
        setLocationRelativeTo(parent);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        String[] nomchamp = new String[]{"IM","NOM","PRENOMS","Date de naissance","Diplôme","Contact","Statut","Situation","Nom de Conjoint(e)","Prenom de conjoint(e)"};
        int ok = 0;
        for (int i = 0; i < 9 ; i++) {
            int j = i;
            if(ok!=0)j++;
            JLabel label = new JLabel(nomchamp[j]); 
            label.setFont(new Font("Bookman Old Style", Font.PLAIN, 15));
            label.setForeground(new Color(0, 224, 230));
            constraints.gridx = 0;
            constraints.gridy = j;
            contentPanel.add(label, constraints);

            if(i==3 && ok==0){
                DateSpinner_Pers.setEditor(DateEditor_Pers);
                constraints.gridx = 1;
                contentPanel.add(DateSpinner_Pers, constraints);
                i--;ok++;
            }else{
                textFields[i] = new JTextField(20);
            constraints.gridx = 1;
            contentPanel.add(textFields[i], constraints);
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
                dispose();
            }
        });

        buttonPanel.add(ajouterButton);
        buttonPanel.add(annulerButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().setBackground(Color.WHITE);
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


}