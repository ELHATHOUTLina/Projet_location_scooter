package Vue;

import javax.swing.*;
import java.awt.*;
import Controller.ParcController;
import Model.ParcModel;

public class ParcVue extends JFrame {
    private ParcModel parc;
    private JButton ajoutClientButton;
    private JButton ajoutScooterButton;
    private JButton afficherClientsButton;
    private JButton afficherScootersButton;

    private JTextField nomField;
    private JTextField prenomField;
    private JTextField dateField;
    private JTextField idClientField;
    private JTextField mailField;
    private JComboBox<String> permisComboBox;

    private JTextField modeleField;
    private JTextField idScooterField;
    private JTextField kilometrageField;
    private JTextField prixField;
    private JCheckBox dispoCheckBox;

    public ParcVue(ParcModel parc) {
        this.parc = parc;
        setTitle("Gestion du Parc");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panneau pour les clients
        JPanel clientPanel = new JPanel(new GridLayout(15, 1));
        clientPanel.setBorder(BorderFactory.createTitledBorder("Informations Client"));

        JLabel nomLabel = new JLabel("Nom");
        nomField = new JTextField();
        JLabel prenomLabel = new JLabel("Prénom");
        prenomField = new JTextField();
        JLabel dateLabel = new JLabel("Date de Naissance (dd/MM/yyyy)");
        dateField = new JTextField();
        JLabel idClientLabel = new JLabel("ID Client");
        idClientField = new JTextField();
        JLabel mailLabel = new JLabel("Adresse Mail");
        mailField = new JTextField();
        JLabel permisLabel = new JLabel("Type de Permis");
        String[] permisOptions = {"Sélectionner un type de permis", "AM", "A1", "A2", "A", "B", "Sans permis"};
        permisComboBox = new JComboBox<>(permisOptions);

        ajoutClientButton = new JButton("Ajouter Client");
        afficherClientsButton = new JButton("Afficher les Clients");

        clientPanel.add(nomLabel);
        clientPanel.add(nomField);
        clientPanel.add(prenomLabel);
        clientPanel.add(prenomField);
        clientPanel.add(dateLabel);
        clientPanel.add(dateField);
        clientPanel.add(idClientLabel);
        clientPanel.add(idClientField);
        clientPanel.add(mailLabel);
        clientPanel.add(mailField);
        clientPanel.add(permisLabel);
        clientPanel.add(permisComboBox);
        clientPanel.add(ajoutClientButton);
        clientPanel.add(afficherClientsButton);

        // Panneau pour les scooters
        JPanel scooterPanel = new JPanel(new GridLayout(15, 1));
        scooterPanel.setBorder(BorderFactory.createTitledBorder("Informations Scooter"));

        JLabel modeleLabel = new JLabel("Modèle");
        modeleField = new JTextField();
        JLabel idScooterLabel = new JLabel("ID Scooter");
        idScooterField = new JTextField();
        JLabel kilometrageLabel = new JLabel("Kilométrage");
        kilometrageField = new JTextField();
        JLabel prixLabel = new JLabel("Prix/Jour");
        prixField = new JTextField();
        JLabel dispoLabel = new JLabel("Disponibilité");
        dispoCheckBox = new JCheckBox("Disponible");

        ajoutScooterButton = new JButton("Ajouter Scooter");
        afficherScootersButton = new JButton("Afficher les Scooters");

        scooterPanel.add(modeleLabel);
        scooterPanel.add(modeleField);
        scooterPanel.add(idScooterLabel);
        scooterPanel.add(idScooterField);
        scooterPanel.add(kilometrageLabel);
        scooterPanel.add(kilometrageField);
        scooterPanel.add(prixLabel);
        scooterPanel.add(prixField);
        scooterPanel.add(dispoLabel);
        scooterPanel.add(dispoCheckBox);
        scooterPanel.add(ajoutScooterButton);
        scooterPanel.add(afficherScootersButton);

        // Ajouter les panneaux au panneau principal
        // mainPanel.add(clientPanel, BorderLayout.WEST);
        // mainPanel.add(scooterPanel, BorderLayout.EAST);

        // Ajouter les panneaux au panneau principal avec un JSplitPane 
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, clientPanel, scooterPanel);
        splitPane.setDividerLocation(400); // Position initiale du séparateur
        splitPane.setResizeWeight(0.5); 

        // Ajouter le JSplitPane au panneau principal
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Ajouter le panneau principal à la fenêtre
        getContentPane().add(mainPanel);

        // Créer le contrôleur et connecter les boutons
        ParcController controller = new ParcController(this,parc);
        ajoutClientButton.addActionListener(controller);
        ajoutScooterButton.addActionListener(controller);
        afficherClientsButton.addActionListener(controller);
        afficherScootersButton.addActionListener(controller);

        setVisible(true);
    }

    // Getters pour les champs et boutons
    public JTextField getNomField() {
        return nomField;
    }

    public JTextField getPrenomField() {
        return prenomField;
    }

    public JTextField getDateField() {
        return dateField;
    }

    public JTextField getIdClientField() {
        return idClientField;
    }

    public JTextField getMailField() {
        return mailField;
    }

    public JComboBox<String> getPermisComboBox() {
        return permisComboBox;
    }

    public JTextField getModeleField() {
        return modeleField;
    }

    public JTextField getIdScooterField() {
        return idScooterField;
    }

    public JTextField getKilometrageField() {
        return kilometrageField;
    }

    public JTextField getPrixField() {
        return prixField;
    }

    public JCheckBox getDispoCheckBox() {
        return dispoCheckBox;
    }

    public JButton getAjoutClientButton() {
        return ajoutClientButton;
    }

    public JButton getAjoutScooterButton() {
        return ajoutScooterButton;
    }

    public JButton getAfficherClientsButton() {
        return afficherClientsButton;
    }

    public JButton getAfficherScootersButton() {
        return afficherScootersButton;
    }
}




