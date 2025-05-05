package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controller.ScooterController;

import java.awt.*;

import Model.ParcModel;
import Model.ScooterModel;

public class ScooterVue extends JFrame {

    private JTable scooterTable;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton allButton;

    public ScooterVue(ParcModel parc) {
        setTitle("Gestion des Scooters");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer un panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tableau pour afficher les scooters
        String[] columnNames = {"Matricule", "Modèle", "Kilométrage", "Prix/Jour", "Disponible"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Remplir le tableau avec les données des scooters
        for (ScooterModel scooter : parc.getScooters()) {
            Object[] rowData = {
                scooter.getNumero_identification(),
                scooter.getModele(),
                scooter.getKilometrage(),
                scooter.getPrixJ(),
                scooter.isDisponible() ? "Oui" : "Non"
            };
            tableModel.addRow(rowData);
        }

        scooterTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(scooterTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        modifyButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");
        searchButton = new JButton("Rechercher");
        allButton = new JButton("Afficher tous");

        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(allButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        ScooterController controller = new ScooterController(this, parc);
        modifyButton.addActionListener(controller);
        deleteButton.addActionListener(controller);
        searchButton.addActionListener(controller);
        allButton.addActionListener(controller);
        // Ajouter le panneau principal à la fenêtre
        getContentPane().add(mainPanel);

        setVisible(true);
    }

    public JTable getScooterTable() {
        return scooterTable;
    }

    public JButton getModifyButton() {
        return modifyButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
    public JButton getAllButton() {
        return allButton;
    }
}