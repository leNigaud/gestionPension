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


public class modiftar extends JDialog {
    private static JTextField[] textFields = new JTextField[4];
    // Récupérer les valeurs saisies dans les champs de texte
    private static String[] values;
    private static JButton ajouterButton = new JButton("Ajouter");
    public modiftar(Frame parent,String Buttname,String title) {
        super(parent, title, true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(400, 200);
        setLocationRelativeTo(parent);

        JPanel contentPanel = new JPanel();
        ajouterButton.setText(Buttname);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        String[] nomchamp = {"numero de tarif",  "diplome", "catégorie", "montant"};
        for (int i = 0; i < 4; i++) {
            JLabel label = new JLabel(nomchamp[i]); 
            label.setFont(new Font("Bookman Old Style", Font.PLAIN, 15));
            label.setForeground(new Color(0, 224, 230));
            constraints.gridx = 0;
            constraints.gridy = i;
            contentPanel.add(label, constraints);
            
            textFields[i] = new JTextField(20);
            if(View.rowNow != null) textFields[i].setText(View.rowNow[i]);
            constraints.gridx = 1;
            contentPanel.add(textFields[i], constraints);
        }

        JPanel buttonPanel = new JPanel();
        ajouterButton.setBackground(new Color(176, 224, 230));
        ajouterButton.setForeground(Color.WHITE);
        ajouterButton.setFont(ajouterButton.getFont().deriveFont(15f));

        JButton annulerButton = new JButton("Annuler");
        annulerButton.setBackground(new Color(176, 224, 230));
        annulerButton.setForeground(Color.WHITE);
        annulerButton.setFont(annulerButton.getFont().deriveFont(15f));
        annulerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (JTextField zonet : textFields) {
                zonet.setText("");
                }
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
    

//zone de texte efatra no retourner-n'ty getter ray ty : num tarif , diplome , catégorie , montant
    public static JTextField[] getTextFields() {
    return textFields;
}
//retourne le bouton ajouter
    public static JButton getAjouterButton() {
    return ajouterButton;
}

//mampiditra ny valeur anle zone de texte 1/1
public void setElementValues(String[] newValues) {
    if (newValues.length != 4) {
        throw new IllegalArgumentException("Le tableau de valeurs doit avoir une longueur de 4.");
    }
    
    for (int i = 0; i < 4; i++) {
        textFields[i].setText(newValues[i]);
    }
}

//retourner tout les texte saisis au clavier pour tout les zones de texte
public static String[] getElementValues() {
    String[] values = new String[textFields.length];
    for (int i = 0; i < textFields.length; i++) {
        values[i] = textFields[i].getText();
    }
    return values;
}


}