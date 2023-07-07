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
import Data.*;
import Model.*;

import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.Date;


public class secWin_newPay extends JDialog {
    private static JSpinner DateSpinner_Pay = new JSpinner(new SpinnerDateModel());
    private static JSpinner.DateEditor DateEditor_Pay = new JSpinner.DateEditor(DateSpinner_Pay, "dd/MM/yyyy");
    private static JTextField[] textFields = new JTextField[2];
    private static JButton ajouterButton = new JButton("Ajouter");
    // Récupérer les valeurs saisies dans les champs de texte
    private static String[] values;
    public secWin_newPay(Frame parent,String Buttname,String title) {
        super(parent, title , true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(400, 200);
        setLocationRelativeTo(parent);
        ajouterButton.setText(Buttname);
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
                // for (JTextField zonet : textFields) {
                // zonet.setText("");
                // }
                // Calendar calendar = Calendar.getInstance();
                // calendar.setTime(new java.util.Date());
                // Date currentDate = calendar.getTime();
                // DateSpinner_Pay.setValue(currentDate);
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
                DateSpinner_Pay.setValue(currentDate);
                dispose();
}


//tableau de type zone de texte de taille = 2 // lay zone de texte roa
    public static JTextField[] getTextFields() {
    return textFields;
}

//maka anlay composant fanaovana saisis de date
    public static JSpinner getDateSpinner_Pay() {
    return DateSpinner_Pay;
}

//bouton ajouter
    public static JButton getAjouterButton() {
    return ajouterButton;
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

//set content
    public static void setcontent(Object[] entre){
        String one = (String) entre[0];
        String two = (String) entre[2];
        Date now = convertLocalDateToDate(entre[4]);
        textFields[0].setText(one);
        textFields[1].setText(two);
        DateSpinner_Pay.setValue(now);
    }

//get anle elemnt saisis rehetra
public Object[] getElementValues() {
    Object[] values = new Object[textFields.length];
    for (int i = 0; i < textFields.length; i++) {
        if (i == 2) { // Si c'est le JSpinner pour la date
            values[i] = DateSpinner_Pay.getValue();
        } else {
            values[i] = textFields[i].getText();
        }
    }
    return values;
}


}
