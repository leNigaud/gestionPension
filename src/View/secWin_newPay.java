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


class secWin_newPay extends JDialog {
    private static JSpinner DateSpinner_Pay = new JSpinner(new SpinnerDateModel());
    private static JSpinner.DateEditor DateEditor_Pay = new JSpinner.DateEditor(DateSpinner_Pay, "dd/MM/yyyy");
    private static JTextField[] textFields = new JTextField[10];
    private static JButton ajouterButton = new JButton("Ajouter");
    // Récupérer les valeurs saisies dans les champs de texte
    private static String[] values;
    public secWin_newPay(Frame parent) {
        super(parent, "Ajouter un nouveau paiement" , true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(400, 200);
        setLocationRelativeTo(parent);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        String[] nomchamp = {"IM", "numero de tarif","date"};
        for (int i = 0; i < 3; i++) {
            JLabel label = new JLabel(nomchamp[i]); 
            label.setFont(new Font("Bookman Old Style", Font.PLAIN, 15));
            label.setForeground(new Color(0, 224, 230));
            constraints.gridx = 0;
            constraints.gridy = i;
            contentPanel.add(label, constraints);

            if(i==2){
                DateSpinner_Pay.setEditor(DateEditor_Pay);
                constraints.gridx = 1;
                contentPanel.add(DateSpinner_Pay, constraints);
            }
            else{
                textFields[i] = new JTextField(20);
                constraints.gridx = 1;
                contentPanel.add(textFields[i], constraints);
            }
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

//tableau de type zone de texte de taille = 2 // lay zone de texte roa
    public static JTextField getTextFields(int i) {
    return textFields[i];
}

//maka anlay composant fanaovana saisis de date
    public static JSpinner getDateSpinner_Pay() {
    return DateSpinner_Pay;
}

//bouton ajouter
    public static JButton getAjouterButton() {
    return ajouterButton;
}

}
