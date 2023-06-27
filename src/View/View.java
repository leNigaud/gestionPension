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


public class View {

    //declaration anle frame
    public static JFrame frame;
    public static String[] rowNow;

    //variable miasa ao @methode mampiditra contenu an'ny Nouveau
    private static JPanel contentPanel_New;
    private static JPanel contentPanel1_New;
    private static JPanel contentPanel2_New;
    private static JPanel contentPanel3_New;
    private static JPanel contentPanel4_New;
    private static JLabel label_New;
    private static JButton button1_New;
    private static JButton button2_New;
    private static JButton button3_New;
    private static Font labelFont_New;
    private static Color labelColor_New;
    private static Font buttonFont_New;
    private static Color textColor_New;
    private static ImageIcon icon1_New;
    private static ImageIcon icon2_New;
    private static ImageIcon icon3_New;


    //variable miasa ao @methode mampiditra contenu an'ny paiement
    private static JPanel contentPanel_Pay;
    private static JPanel topPanel_Pay;
    private static JSpinner startDateSpinner_Pay;
    private static JSpinner endDateSpinner_Pay;
    private static JSpinner.DateEditor startDateEditor_Pay;
    private static JSpinner.DateEditor endDateEditor_Pay;
    private static Font dateFont_Pay;
    private static Color dateForeground_Pay;
    private static Color dateBackground_Pay;
    private static Border dateBorder_Pay;
    private static JButton filterPay;
    private static JButton modifBut_Pay;
    private static JButton delBut_Pay;
    private static JButton recu_Pay;
    private static JLabel label_Pay;
    private static JLabel label1_Pay;
    private static JLabel label2_Pay;
    private static Font labelFont_Pay;
    private static Color labelColor_Pay;
    private static JPanel labelPanel_Pay;
    private static String[] columnNames_Pay;
    private static Object[][] data_Pay;
    private static JTable table_Pay;
    private static JTableHeader header_Pay;
    private static JPanel tablePanel_Pay;

    //variable maiasa ao @methode mampiditra contenu an'ny personne
    private static JPanel contentPanel_Pers;
    private static GridBagConstraints gbc_Pers;
    private static JLabel label_Pers;
    private static JPanel searchPanel_Pers;
    private static JTextField searchField_Pers;
    private static JButton searchButton_Pers;
    private static Font buttonFont_Pay_Pers;
    private static Color buttonForeground_Pay_Pers;
    private static Color buttonBackground_Pay_Pers;
    private static JPanel checkboxFilterPanel_Pers;
    private static JCheckBox checkboxMort_Pers;
    private static JCheckBox checkboxVivant_Pers;
    private static JButton filterButton_Pers;
    private static JPanel buttonPanel_Pers;
    private static JButton modifyButton_Pers;
    private static JButton deleteButton_Pers;
    private static JPanel tablePanel_Pers;
    private static String[] columnNames_Pers;
    private static Object[][] data_Pers;
    private static JTable table_Pers;
    private static JTableHeader header_Pers;

    //variable miasa amle contenu an'ny bouton tarif
    private static JPanel panel_Tarif;
    private static JLabel label_Tarif;
    private static JButton modifierButton_Tarif;
    private static JButton supprimerButton_Tarif;
    private static String[] columnNames_Tarif;
    private static Object[][] data_Tarif;
    private static JTable table_Tarif;
    private static JTableHeader tableHeader_Tarif;
    private static JPanel topPanel_Tarif;
    private static JPanel buttonPanel_Tarif;
    private static secWin_newPers fenetreModale1;
    private static secWin_newPay fenetreModale2;
    private static secWin_newTar fenetreModale3; 


    private static JButton[] bouttons; // Déclaration du tableau bouttons en tant que variable de classe
    private static JPanel[] childPanels; // Déclaration du tableau childPanels en tant que variable de classe
    private static JPanel rightPanel; // Déclaration du JPanel rightPanel en tant que variable de classe
    private static JPanel leftPanel;
    private static JButton histogrammeButton ;

    public static String[] getSelectedRowData(JTable table) {
    int selectedRow = table.getSelectedRow();
    
    if (selectedRow != -1) {
        TableModel model = table.getModel();
        int columnCount = model.getColumnCount();
        String[] rowData = new String[columnCount];
        
        for (int column = 0; column < columnCount; column++) {
            Object value = model.getValueAt(selectedRow, column);
            rowData[column] = value != null ? value.toString() : "";
        } 
        
        return rowData;
    }
    
    return null; // Aucune ligne sélectionnée
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Création de la fenêtre
            frame = new JFrame("Pension Manager");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Définition de la taille de la fenêtre
            frame.setSize(1366, 768);

            // Empêcher la redimension de la fenêtre
            frame.setResizable(false);

            // Centre la fenêtre sur l'écran
            frame.setLocationRelativeTo(null);

            // Création des deux JPanel
            leftPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }
            };
            rightPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
            rightPanel.setLayout(new BorderLayout());

            // Calcul de la taille des panneaux
            int panelWidth = frame.getWidth() / 5;
            int panelHeight = frame.getHeight();

            // Définition des tailles des panneaux
            leftPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
            rightPanel.setPreferredSize(new Dimension(frame.getWidth() - panelWidth, panelHeight));

            //mampiditra ny contenu an'ny rightpanel vo manomboka
            rightPanel.add(createContentPanel4(), BorderLayout.CENTER);
            rightPanel.removeAll();
            rightPanel.add(createContentPanel3(), BorderLayout.CENTER);
            rightPanel.removeAll();
            rightPanel.add(createContentPanel2(), BorderLayout.CENTER);
            rightPanel.removeAll();
            rightPanel.add(createContentPanel1(), BorderLayout.CENTER);

            // Layout de la fenêtre
            frame.setLayout(new BorderLayout());

            // Ajout des panneaux à la fenêtre
            frame.add(leftPanel, BorderLayout.WEST);
            frame.add(rightPanel, BorderLayout.CENTER);

            //tableau des boutons
            bouttons = new JButton[5];

            // Création du bouton "Nouveau"
            JButton nouveauButton = createStyledButton("Nouveau", panelWidth, 57);
            bouttons[0] = nouveauButton;

            // Ajout du bouton "Nouveau" au panel de gauche
            leftPanel.add(nouveauButton);

            // Création du label "Liste"
            JLabel listeLabel = createStyledLabel("Liste", panelWidth, 57);

            // Ajout du label "Liste" au panel de gauche
            leftPanel.add(listeLabel);

            // Création des boutons supplémentaires
            for (int i = 0; i < 3; i++) {
                String buttonText = "";
                if (i == 0) {
                    buttonText = "Paiement";
                } else if (i == 1) {
                    buttonText = "Personne";
                } else if (i == 2) {
                    buttonText = "Tarif";
                }
                JButton button = createStyledButton(buttonText, panelWidth * 3 / 4, 57);
                bouttons[i + 1] = button;
                leftPanel.add(button);
            }

            // Création du bouton "Histogramme"
            histogrammeButton = createStyledButton("Histogramme", panelWidth, 57);
            bouttons[4] = histogrammeButton;

            // Ajout du bouton "Histogramme" au panel de gauche
            leftPanel.add(histogrammeButton);

            // Affichage de la fenêtre
            frame.setVisible(true);
        });
    }

    // Crée un bouton stylisé avec les caractéristiques spécifiées
    private static JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(new Color(176, 224, 230));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(24f));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer l'index du bouton
                int index = -1;
                for (int i = 0; i < View.bouttons.length; i++) {
                    if (View.bouttons[i] == button) {
                        index = i;
                        break;
                    }
                }

                // Afficher le JPanel correspondant
                if (index == 0) {
                    for (int i = 0; i < View.bouttons.length; i++) {
                        bouttons[i].setBackground(new Color(176, 224, 230));
                }
                    bouttons[index].setBackground(new Color(176, 214, 200));
                    leftPanel.revalidate();
                    leftPanel.repaint();
                    JPanel contentPanel = createContentPanel1();
                    rightPanel.removeAll();
                    rightPanel.add(contentPanel, BorderLayout.CENTER);
                    rightPanel.revalidate();
                    rightPanel.repaint();
                }else if(index == 1){
                    for (int i = 0; i < View.bouttons.length; i++) {
                        bouttons[i].setBackground(new Color(176, 224, 230));
                }
                    bouttons[index].setBackground(new Color(176, 214, 200));
                    JPanel contentPanel = createContentPanel2();
                    rightPanel.removeAll();
                    rightPanel.add(contentPanel, BorderLayout.CENTER);
                    rightPanel.revalidate();
                    rightPanel.repaint();
                }else if(index == 2){
                    for (int i = 0; i < View.bouttons.length; i++) {
                        bouttons[i].setBackground(new Color(176, 224, 230));
                }
                    bouttons[index].setBackground(new Color(176, 214, 200));
                    JPanel contentPanel = createContentPanel3();
                    rightPanel.removeAll();
                    rightPanel.add(contentPanel, BorderLayout.CENTER);
                    rightPanel.revalidate();
                    rightPanel.repaint();
                }else if(index == 3){
                    for (int i = 0; i < View.bouttons.length; i++) {
                        bouttons[i].setBackground(new Color(176, 224, 230));
                }
                    bouttons[index].setBackground(new Color(176, 214, 200));
                    JPanel contentPanel = createContentPanel4();
                    rightPanel.removeAll();
                    rightPanel.add(contentPanel, BorderLayout.CENTER);
                    rightPanel.revalidate();
                    rightPanel.repaint();
                }
            }
        });
        return button;
    }

    // Crée un label stylisé avec les caractéristiques spécifiées
    private static JLabel createStyledLabel(String text, int width, int height) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(width, height));
        label.setForeground(new Color(176, 224, 230));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(24f));
        return label;
    }

    // Crée le contenu du rightPanel
    private static JPanel createContentPanel1() {
    contentPanel_New = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    contentPanel_New.setLayout(new BoxLayout(contentPanel_New, BoxLayout.X_AXIS));
    contentPanel1_New =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    contentPanel1_New.setLayout(new BoxLayout(contentPanel1_New, BoxLayout.Y_AXIS));
    contentPanel2_New =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    contentPanel2_New.setLayout(new FlowLayout(FlowLayout.LEFT));
    contentPanel2_New.setPreferredSize(new Dimension(contentPanel2_New.getWidth(), 70));
    contentPanel3_New =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    contentPanel4_New =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};

    label_New = new JLabel("Nouveau");
    label_New.setPreferredSize(new Dimension(200, 60));
    labelFont_New = new Font("Bookman Old Style", Font.PLAIN, 30);
    labelColor_New = new Color(176, 224, 230);
    label_New.setFont(labelFont_New);
    label_New.setForeground(labelColor_New);

    contentPanel2_New.add(label_New);
    contentPanel1_New.add(contentPanel2_New);

    button1_New = new JButton("Nouvelle Personne");
    button1_New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetreModale1 = new secWin_newPers(frame);
                fenetreModale1.setVisible(true);
            }
        });
    button2_New = new JButton("Nouveau Paiement");
    button2_New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetreModale2 = new secWin_newPay(frame);
                fenetreModale2.setVisible(true);
            }
        });
    button3_New = new JButton("Nouveau Tarif");
    //secWin_newTar
    button3_New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetreModale3 = new secWin_newTar(frame);
                fenetreModale3.setVisible(true);
            }
        });
    button1_New.setPreferredSize(new Dimension(180, 157));
    button2_New.setPreferredSize(new Dimension(180, 157));
    button3_New.setPreferredSize(new Dimension(180, 157));
    button1_New.setBackground(new Color(176, 224, 230));
    button2_New.setBackground(new Color(176, 224, 230));
    button3_New.setBackground(new Color(176, 224, 230));
    buttonFont_New = new Font("Bookman Old Style", Font.BOLD, 15);
    textColor_New = Color.WHITE;
    button1_New.setFont(buttonFont_New);
    button1_New.setForeground(textColor_New);
    button2_New.setFont(buttonFont_New);
    button2_New.setForeground(textColor_New);
    button3_New.setFont(buttonFont_New);
    button3_New.setForeground(textColor_New);

    icon1_New = new ImageIcon("sary1.png");
    icon2_New = new ImageIcon("sary2.png");
    icon3_New = new ImageIcon("sary3.png");
    button1_New.setIcon(icon1_New);
    button1_New.setVerticalTextPosition(SwingConstants.BOTTOM);
    button1_New.setHorizontalTextPosition(SwingConstants.CENTER);
    button2_New.setIcon(icon2_New);
    button2_New.setVerticalTextPosition(SwingConstants.BOTTOM);
    button2_New.setHorizontalTextPosition(SwingConstants.CENTER);
    button3_New.setIcon(icon3_New);
    button3_New.setVerticalTextPosition(SwingConstants.BOTTOM);
    button3_New.setHorizontalTextPosition(SwingConstants.CENTER);

    contentPanel_New.add(Box.createHorizontalGlue());
    contentPanel_New.add(button1_New);
    contentPanel_New.add(Box.createHorizontalGlue());
    contentPanel_New.add(button2_New);
    contentPanel_New.add(Box.createHorizontalGlue());
    contentPanel_New.add(button3_New);
    contentPanel_New.add(Box.createHorizontalGlue());

    contentPanel_New.add(Box.createVerticalGlue());
    contentPanel1_New.add(contentPanel_New);
    contentPanel1_New.add(contentPanel3_New);
    contentPanel1_New.add(contentPanel4_New);

    return contentPanel1_New;
}


    
    private static JPanel createContentPanel2() {

    contentPanel_Pay =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    contentPanel_Pay.setLayout(new BoxLayout(contentPanel_Pay, BoxLayout.PAGE_AXIS));

    topPanel_Pay =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    topPanel_Pay.setLayout(new FlowLayout(FlowLayout.LEFT));

    startDateSpinner_Pay = new JSpinner(new SpinnerDateModel());
    endDateSpinner_Pay = new JSpinner(new SpinnerDateModel());
    startDateEditor_Pay = new JSpinner.DateEditor(startDateSpinner_Pay, "dd/MM/yyyy");
    endDateEditor_Pay = new JSpinner.DateEditor(endDateSpinner_Pay, "dd/MM/yyyy");
    startDateSpinner_Pay.setEditor(startDateEditor_Pay);
    endDateSpinner_Pay.setEditor(endDateEditor_Pay);

    dateFont_Pay = new Font("Bookman Old Style", Font.PLAIN, 20);
    dateForeground_Pay = Color.WHITE;
    dateBackground_Pay = new Color(176, 224, 230);
    dateBorder_Pay = BorderFactory.createLineBorder(Color.WHITE);
    startDateSpinner_Pay.setFont(dateFont_Pay);
    startDateSpinner_Pay.setForeground(dateForeground_Pay);
    startDateSpinner_Pay.setBackground(dateBackground_Pay);
    startDateSpinner_Pay.setBorder(dateBorder_Pay);
    endDateSpinner_Pay.setFont(dateFont_Pay);
    endDateSpinner_Pay.setForeground(dateForeground_Pay);
    endDateSpinner_Pay.setBackground(dateBackground_Pay);
    endDateSpinner_Pay.setBorder(dateBorder_Pay);

    filterPay = new JButton("Filtrer");
    modifBut_Pay = new JButton("Modifier");
    modifBut_Pay.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        // Vérifier si une ligne est sélectionnée
        int selectedRow = table_Pay.getSelectedRow();
        if (selectedRow != -1) {
            // Récupérer les données de la ligne sélectionnée
            TableModel model = table_Pay.getModel();
            int columnCount = model.getColumnCount();
            String[] rowData = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                rowData[i] = model.getValueAt(selectedRow, i).toString();
            }

            // Utiliser le tableau de données (rowData) comme vous le souhaitez
            // ...
            rowNow = rowData;
            fenetreModale2 = new secWin_newPay(frame);
            fenetreModale2.setVisible(true);
        }
            }
        });
    delBut_Pay = new JButton("Supprimer");
    
    recu_Pay = new JButton("Générer un reçu");

    Font buttonFont_Pay = new Font("Bookman Old Style", Font.PLAIN, 20);
    Color buttonForeground_Pay = Color.WHITE;
    Color buttonBackground_Pay = new Color(176, 224, 230);
    filterPay.setFont(buttonFont_Pay);
    filterPay.setForeground(buttonForeground_Pay);
    filterPay.setBackground(buttonBackground_Pay);
    modifBut_Pay.setFont(buttonFont_Pay);
    modifBut_Pay.setForeground(buttonForeground_Pay);
    modifBut_Pay.setBackground(buttonBackground_Pay);
    delBut_Pay.setFont(buttonFont_Pay);
    delBut_Pay.setForeground(buttonForeground_Pay);
    delBut_Pay.setBackground(buttonBackground_Pay);
    recu_Pay.setFont(buttonFont_Pay);
    recu_Pay.setForeground(buttonForeground_Pay);
    recu_Pay.setBackground(buttonBackground_Pay);

    label_Pay = new JLabel("Payement");
    label1_Pay = new JLabel("         ");
    label2_Pay = new JLabel("         ");
    labelFont_Pay = new Font("Bookman Old Style", Font.PLAIN, 30);
    labelColor_Pay = new Color(176, 224, 230);
    label_Pay.setFont(labelFont_Pay);
    label_Pay.setForeground(labelColor_Pay);
    label1_Pay.setFont(labelFont_Pay);
    label1_Pay.setForeground(labelColor_Pay);
    label2_Pay.setFont(labelFont_Pay);
    label2_Pay.setForeground(labelColor_Pay);

    labelPanel_Pay =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    labelPanel_Pay.setLayout(new FlowLayout(FlowLayout.LEFT));
    labelPanel_Pay.add(label_Pay);

    topPanel_Pay.add(startDateSpinner_Pay);
    topPanel_Pay.add(endDateSpinner_Pay);
    topPanel_Pay.add(filterPay);
    topPanel_Pay.add(modifBut_Pay);
    topPanel_Pay.add(delBut_Pay);
    topPanel_Pay.add(recu_Pay);
    topPanel_Pay.add(label1_Pay);
    topPanel_Pay.add(label2_Pay);

    contentPanel_Pay.add(labelPanel_Pay);
    contentPanel_Pay.add(topPanel_Pay);

    columnNames_Pay = new String[]{"IM", "Nom", "Numero de Tarif", "Montant", "Date de Paiement"};
    data_Pay = new Object[][]{
        //{"1", "1", "1", "1","1"},
        //{"54", "2", "28", "265","266"}
    };
    table_Pay = new JTable(data_Pay, columnNames_Pay);

    header_Pay = table_Pay.getTableHeader();
    header_Pay.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
    header_Pay.setBackground(new Color(176, 224, 230));
    header_Pay.setForeground(Color.WHITE);

    table_Pay.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
    table_Pay.setRowHeight(25);
    table_Pay.setGridColor(new Color(176, 224, 230));
    table_Pay.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String[] rowData = getSelectedRowData(table_Pay);
            
            if (rowData != null) {
                rowNow = rowData;
            }
        }
    }
});


    tablePanel_Pay = new JPanel(new BorderLayout());
    tablePanel_Pay.add(new JScrollPane(table_Pay), BorderLayout.CENTER);

    contentPanel_Pay.add(tablePanel_Pay);

    return contentPanel_Pay;
}


private static JPanel createContentPanel3() {

    // Initialisation du contentPanel
    contentPanel_Pers =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    contentPanel_Pers.setLayout(new GridBagLayout());
    gbc_Pers = new GridBagConstraints();

    // Ajout du label "Personne"
    label_Pers = new JLabel("Personne");
    label_Pers.setFont(new Font("Bookman Old Style", Font.PLAIN, 30));
    label_Pers.setForeground(new Color(176, 224, 230));
    gbc_Pers.gridx = 0;
    gbc_Pers.gridy = 0;
    gbc_Pers.anchor = GridBagConstraints.NORTHWEST;
    gbc_Pers.insets = new Insets(10, 10, 10, 10);
    contentPanel_Pers.add(label_Pers, gbc_Pers);

    // Ajout du panel de recherche
    searchPanel_Pers =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    searchField_Pers = new JTextField(20);
    searchField_Pers.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
    searchField_Pers.setForeground(Color.BLACK);
    searchField_Pers.setBackground(Color.WHITE);
    searchField_Pers.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    searchField_Pers.setPreferredSize(new Dimension(200, 30));
    searchButton_Pers = new JButton("Rechercher");
    buttonFont_Pay_Pers = new Font("Bookman Old Style", Font.PLAIN, 20);
    buttonForeground_Pay_Pers = Color.WHITE;
    buttonBackground_Pay_Pers = new Color(176, 224, 230);
    searchButton_Pers.setFont(buttonFont_Pay_Pers);
    searchButton_Pers.setForeground(buttonForeground_Pay_Pers);
    searchButton_Pers.setBackground(buttonBackground_Pay_Pers);

    searchPanel_Pers.add(searchField_Pers);
    searchPanel_Pers.add(searchButton_Pers);
    gbc_Pers.gridy = 1;
    gbc_Pers.anchor = GridBagConstraints.WEST;
    contentPanel_Pers.add(searchPanel_Pers, gbc_Pers);

    // Ajout du panel de checkboxes et du bouton "Filtrer"
    checkboxFilterPanel_Pers =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    checkboxMort_Pers = new JCheckBox("Mort");
    checkboxVivant_Pers = new JCheckBox("Vivant");
    checkboxMort_Pers.setForeground(buttonBackground_Pay_Pers);
    checkboxVivant_Pers.setForeground(buttonBackground_Pay_Pers);
    checkboxMort_Pers.setFont(buttonFont_Pay_Pers);
    checkboxVivant_Pers.setFont(buttonFont_Pay_Pers);
    checkboxMort_Pers.setSelected(true);
    checkboxVivant_Pers.setSelected(true);
    checkboxMort_Pers.setOpaque(true);
    checkboxVivant_Pers.setOpaque(true);
    checkboxMort_Pers.setBackground(Color.WHITE);
    checkboxVivant_Pers.setBackground(Color.WHITE);
    checkboxFilterPanel_Pers.add(checkboxMort_Pers);
    checkboxFilterPanel_Pers.add(checkboxVivant_Pers);
    checkboxFilterPanel_Pers.add(Box.createHorizontalStrut(10)); // Espacement entre les checkboxes et le bouton "Filtrer"
    filterButton_Pers = new JButton("Filtrer");
    filterButton_Pers.setFont(buttonFont_Pay_Pers);
    filterButton_Pers.setForeground(buttonForeground_Pay_Pers);
    filterButton_Pers.setBackground(buttonBackground_Pay_Pers);
    checkboxFilterPanel_Pers.add(filterButton_Pers);
    gbc_Pers.gridy = 2;
    gbc_Pers.anchor = GridBagConstraints.WEST;
    contentPanel_Pers.add(checkboxFilterPanel_Pers, gbc_Pers);

    // Ajout du panel de boutons "Modifier" et "Supprimer"
    buttonPanel_Pers =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    modifyButton_Pers = new JButton("Modifier");
    modifyButton_Pers.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        // Vérifier si une ligne est sélectionnée
        int selectedRow = table_Pers.getSelectedRow();
        if (selectedRow != -1) {
            // Récupérer les données de la ligne sélectionnée
            TableModel model = table_Pers.getModel();
            int columnCount = model.getColumnCount();
            String[] rowData = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                rowData[i] = model.getValueAt(selectedRow, i).toString();
            }

            // Utiliser le tableau de données (rowData) comme vous le souhaitez
            // ...
            rowNow = rowData;
            fenetreModale1 = new secWin_newPers(frame);
            fenetreModale1.setVisible(true);
        }
                
            }
        });
    deleteButton_Pers = new JButton("Supprimer");
    
    modifyButton_Pers.setFont(buttonFont_Pay_Pers);
    deleteButton_Pers.setFont(buttonFont_Pay_Pers);
    modifyButton_Pers.setForeground(buttonForeground_Pay_Pers);
    deleteButton_Pers.setForeground(buttonForeground_Pay_Pers);
    modifyButton_Pers.setBackground(buttonBackground_Pay_Pers);
    deleteButton_Pers.setBackground(buttonBackground_Pay_Pers);
    buttonPanel_Pers.add(modifyButton_Pers);
    buttonPanel_Pers.add(deleteButton_Pers);
    gbc_Pers.gridy = 2;
    gbc_Pers.anchor = GridBagConstraints.EAST;
    contentPanel_Pers.add(buttonPanel_Pers, gbc_Pers);

    // Ajout du panel de la table
    tablePanel_Pers = new JPanel(new BorderLayout());
    columnNames_Pers = new String[]{"IM","NOM","PRENOMS","Date de naissance","Diplôme","Contact","Statut","Situation","Nom de Conjoint(e)","Prenom de conjoint(e)"};
    data_Pers = new Object[][]{
        //{"im","nom","pnom","22/12/1995","dipom","contact","stats","situ","conj","Pconj"},
        //{"","","","","","","","","",""},
        //{"","","","","","","","","",""},
        //{"","","","","","","","","",""} 
    };
    table_Pers = new JTable(data_Pers, columnNames_Pers);

    header_Pers = table_Pers.getTableHeader();
    header_Pers.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
    header_Pers.setBackground(new Color(176, 224, 230));
    header_Pers.setForeground(Color.WHITE);

    table_Pers.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
    table_Pers.setRowHeight(25);
    table_Pers.setGridColor(new Color(176, 224, 230));
    table_Pers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String[] rowData = getSelectedRowData(table_Pers);
            
            if (rowData != null) {
                rowNow = rowData;
            }
        }
    }
});

    tablePanel_Pers.add(new JScrollPane(table_Pers), BorderLayout.CENTER);
    gbc_Pers.gridy = 4;
    gbc_Pers.weightx = 1.0;
    gbc_Pers.weighty = 1.0;
    gbc_Pers.fill = GridBagConstraints.BOTH;
    contentPanel_Pers.add(tablePanel_Pers, gbc_Pers);

    return contentPanel_Pers;
}

private static JPanel createContentPanel4() {

    panel_Tarif =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    label_Tarif = new JLabel("TARIF");
    label_Tarif.setFont(new Font("Bookman Old Style", Font.PLAIN, 30));
    label_Tarif.setForeground(new Color(176, 224, 230));

    modifierButton_Tarif = new JButton("Modifier");
    modifierButton_Tarif.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        // Vérifier si une ligne est sélectionnée
        int selectedRow = table_Tarif.getSelectedRow();
        if (selectedRow != -1) {
            // Récupérer les données de la ligne sélectionnée
            TableModel model = table_Tarif.getModel();
            int columnCount = model.getColumnCount();
            String[] rowData = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                rowData[i] = model.getValueAt(selectedRow, i).toString();
            }

            // Utiliser le tableau de données (rowData) comme vous le souhaitez
            // ...
            rowNow = rowData;
            fenetreModale3 = new secWin_newTar(frame);
            fenetreModale3.setVisible(true);
        }
                
            }
        });
    modifierButton_Tarif.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
    modifierButton_Tarif.setForeground(Color.WHITE);
    modifierButton_Tarif.setBackground(new Color(176, 224, 230));

    supprimerButton_Tarif = new JButton("Supprimer");
    
    supprimerButton_Tarif.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
    supprimerButton_Tarif.setForeground(Color.WHITE);
    supprimerButton_Tarif.setBackground(new Color(176, 224, 230));

    columnNames_Tarif = new String[]{"Numuro de Tarif", "Diplôme", "Catégorie", "Montant"};
    data_Tarif = new Object[][]{
         //{"Donnée 1-1", "Donnée 1-2", "Donnée 1-3", "Donnée 1-4"},
          //{"Donnée 2-1", "Donnée 2-2", "Donnée 2-3", "Donnée 2-4"},
          //{"Donnée 3-1", "Donnée 3-2", "Donnée 3-3", "Donnée 3-4"} 
    };
    table_Tarif = new JTable(data_Tarif, columnNames_Tarif);
    table_Tarif.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String[] rowData = getSelectedRowData(table_Tarif);
            
            if (rowData != null) {
                rowNow = rowData;
            }
        }
    }
});

    tableHeader_Tarif = table_Tarif.getTableHeader();
    tableHeader_Tarif.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
    tableHeader_Tarif.setBackground(new Color(176, 224, 230));
    tableHeader_Tarif.setForeground(Color.WHITE);
    

    panel_Tarif.setLayout(new BorderLayout());

    topPanel_Tarif = new JPanel(new BorderLayout()){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    topPanel_Tarif.add(label_Tarif, BorderLayout.WEST);

    buttonPanel_Tarif =  new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setBackground(Color.WHITE);
                }};
    buttonPanel_Tarif.setLayout(new FlowLayout(FlowLayout.LEFT));
    buttonPanel_Tarif.add(modifierButton_Tarif);
    buttonPanel_Tarif.add(supprimerButton_Tarif);
    topPanel_Tarif.add(buttonPanel_Tarif, BorderLayout.SOUTH);

    panel_Tarif.add(topPanel_Tarif, BorderLayout.NORTH);
    panel_Tarif.add(new JScrollPane(table_Tarif), BorderLayout.CENTER);

    return panel_Tarif;
}


//getters
public static JPanel getContentPanel_New() {
    return contentPanel_New;
}

public static JPanel getContentPanel1_New() {
    return contentPanel1_New;
}

public static JPanel getContentPanel2_New() {
    return contentPanel2_New;
}

public static JPanel getContentPanel3_New() {
    return contentPanel3_New;
}

public static JPanel getContentPanel4_New() {
    return contentPanel4_New;
}

public static JLabel getLabel_New() {
    return label_New;
}

public static JButton getButton1_New() {
    return button1_New;
}

public static JButton getButton2_New() {
    return button2_New;
}

public static JButton getButton3_New() {
    return button3_New;
}

public static Font getLabelFont_New() {
    return labelFont_New;
}

public static Color getLabelColor_New() {
    return labelColor_New;
}

public static Font getButtonFont_New() {
    return buttonFont_New;
}

public static Color getTextColor_New() {
    return textColor_New;
}

public static ImageIcon getIcon1_New() {
    return icon1_New;
}

public static ImageIcon getIcon2_New() {
    return icon2_New;
}

public static ImageIcon getIcon3_New() {
    return icon3_New;
}
public static JPanel getContentPanel_Pay() {
    return contentPanel_Pay;
}

public static JPanel getTopPanel_Pay() {
    return topPanel_Pay;
}

public static JSpinner.DateEditor getStartDateEditor_Pay() {
    return startDateEditor_Pay;
}

public static JSpinner.DateEditor getEndDateEditor_Pay() {
    return endDateEditor_Pay;
}

public static Font getDateFont_Pay() {
    return dateFont_Pay;
}

public static Color getDateForeground_Pay() {
    return dateForeground_Pay;
}

public static Color getDateBackground_Pay() {
    return dateBackground_Pay;
}

public static Border getDateBorder_Pay() {
    return dateBorder_Pay;
}

public static JButton getModifBut_Pay() {
    return modifBut_Pay;
}

public static JLabel getLabel_Pay() {
    return label_Pay;
}

public static JLabel getLabel1_Pay() {
    return label1_Pay;
}

public static JLabel getLabel2_Pay() {
    return label2_Pay;
}

public static Font getLabelFont_Pay() {
    return labelFont_Pay;
}

public static Color getLabelColor_Pay() {
    return labelColor_Pay;
}

public static JPanel getLabelPanel_Pay() {
    return labelPanel_Pay;
}

public static String[] getColumnNames_Pay() {
    return columnNames_Pay;
}

public static Object[][] getData_Pay() {
    return data_Pay;
}

public static JTableHeader getHeader_Pay() {
    return header_Pay;
}

public static JPanel getTablePanel_Pay() {
    return tablePanel_Pay;
}
public static JPanel getContentPanel_Pers() {
    return contentPanel_Pers;
}

public static GridBagConstraints getGbc_Pers() {
    return gbc_Pers;
}

public static JLabel getLabel_Pers() {
    return label_Pers;
}

public static JPanel getSearchPanel_Pers() {
    return searchPanel_Pers;
}

public static Font getButtonFont_Pay_Pers() {
    return buttonFont_Pay_Pers;
}

public static Color getButtonForeground_Pay_Pers() {
    return buttonForeground_Pay_Pers;
}

public static Color getButtonBackground_Pay_Pers() {
    return buttonBackground_Pay_Pers;
}

public static JPanel getCheckboxFilterPanel_Pers() {
    return checkboxFilterPanel_Pers;
}

public static JPanel getButtonPanel_Pers() {
    return buttonPanel_Pers;
}

public static JButton getModifyButton_Pers() {
    return modifyButton_Pers;
}

public static JPanel getTablePanel_Pers() {
    return tablePanel_Pers;
}

public static String[] getColumnNames_Pers() {
    return columnNames_Pers;
}

public static Object[][] getData_Pers() {
    return data_Pers;
}

public static JTableHeader getHeader_Pers() {
    return header_Pers;
}

public static JPanel getPanel_Tarif() {
    return panel_Tarif;
}

public static JLabel getLabel_Tarif() {
    return label_Tarif;
}

public static JButton getModifierButton_Tarif() {
    return modifierButton_Tarif;
}

public static String[] getColumnNames_Tarif() {
    return columnNames_Tarif;
}

public static Object[][] getData_Tarif() {
    return data_Tarif;
}

public static JTableHeader getTableHeader_Tarif() {
    return tableHeader_Tarif;
}

public static JPanel getTopPanel_Tarif() {
    return topPanel_Tarif;
}

public static JPanel getButtonPanel_Tarif() {
    return buttonPanel_Tarif;
}
//#menu paiement

//date de debut (le saisi de date eo @gauche)
public static JSpinner getStartDateSpinner_Pay() {
    return startDateSpinner_Pay;
}

//date de fin (le saisi de date eo @droite)
public static JSpinner getEndDateSpinner_Pay() {
    return endDateSpinner_Pay;
}

//bouton filtrer
public static JButton getfilterPay() {
    return filterPay;
}

//bouton supprimer
public static JButton getDelBut_Pay() {
    return delBut_Pay;
}
//messagebox manontany confirmation de la suppression , ampiasaina @le suppression
public static boolean afficherQuestionOuiNon() {
    int choix = JOptionPane.showOptionDialog(null, "Vouleé-vous vraiment supprimer cette ligne ?", "Suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    return choix == JOptionPane.YES_OPTION;
}

//bouton generer un recu 
public static JButton getRecu_Pay() {
    return recu_Pay;
}

//tableau
public static JTable getTable_Pay() {
    return table_Pay;
}

//#menu personne

//zone de texte fanaovana recherche
public static JTextField getSearchField_Pers() {
    return searchField_Pers;
}

//bouton rechercher
public static JButton getSearchButton_Pers() {
    return searchButton_Pers;
}

//checkbox : mort
public static JCheckBox getCheckboxMort_Pers() {
    return checkboxMort_Pers;
}

//checkbox : vivant
public static JCheckBox getCheckboxVivant_Pers() {
    return checkboxVivant_Pers;
}

//bouton filtrer selon statut ao @menu personne
public static JButton getFilterButton_Pers() {
    return filterButton_Pers;
}

//bouton supprimer une personne 
public static JButton getDeleteButton_Pers() {
    return deleteButton_Pers;
}

//tableau ao @personne : 
public static JTable getTable_Pers() {
    return table_Pers;
}

//# menu tarif

//bouton supprimer 
public static JButton getSupprimerButton_Tarif() {
    return supprimerButton_Tarif;
}

//tableau tarif
public static JTable getTable_Tarif() {
    return table_Tarif;
}


//#bouton histogramme
public static JButton getHistoG(){
    return histogrammeButton ;
}

//getters anle instance anle fenetre modals
public static secWin_newPers getWinPers(){
    return fenetreModale1;
}
public static secWin_newPay getWinPay(){
    return fenetreModale2;
}
public static secWin_newTar getWinTarif(){
    return fenetreModale3;
}

//ireo ambony ireo lay getters anle fenetre modale fa adinoko teo ; any anatin'ny classe fenetre secondaire 1/1 no misy ireo methode getter propre ho an'ny fenetre secondaire tsirairay
}
