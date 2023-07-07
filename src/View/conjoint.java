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
import Data.*;
import Model.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class conjoint extends JDialog {
    private static DefaultTableModel modelConj;
    private static JTable tableconjoint;
    private static JScrollPane scrollPane;

    public conjoint(Frame parent) {
        super(parent, "Liste des conjoints(es) veuf(ve) des pensionnaires", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);

        modelConj = new DefaultTableModel();
        modelConj.addColumn("Numéro du/de la conjoint(e)");
        modelConj.addColumn("Nom du/de la conjoint(e)");
        modelConj.addColumn("Prénom du/de la conjoint(e)");
        modelConj.addColumn("Montant");

        tableconjoint = new JTable(modelConj);
        scrollPane = new JScrollPane(tableconjoint);

        JTableHeader header_Pers = tableconjoint.getTableHeader();
        header_Pers.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
        header_Pers.setBackground(new Color(176, 224, 230));
        header_Pers.setForeground(Color.WHITE);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(parent);
    }

    //methode mampiditra ao anatiny ligne par ligne
    public static void addConjointData(Object[] rowData) {
        modelConj.addRow(rowData);
    }

    public static void showDialog(Frame parent) {
        conjoint dialog = new conjoint(parent);
        dialog.setVisible(true);
    }
}

