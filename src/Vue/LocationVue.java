package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controller.LocationController;

import java.awt.*;
import java.text.SimpleDateFormat;

import Model.ParcModel;
import Model.ScooterModel;
import Model.ClientModel;
import Model.LocationModel;

public class LocationVue extends JFrame {

    private JTable locationTable;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton allButton;

    public LocationVue(ParcModel parc) {
        setTitle("Gestion des Locations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer un panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tableau pour afficher les locations
        String[] columnNames = { "Client","ID Client", "Immatriculation Scooter", "Date Début", "Date Fin", "Prix Total", "Statut" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Remplir le tableau avec les données des locations
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (ClientModel client : parc.getClients()) {
            for (LocationModel location : client.getLocation()) {
                Object[] rowData = {
                    client.getNom() + " " + client.getPrenom(),
                    client.getId_client(),
                    location.getScooter().getNumero_identification(),
                    dateFormat.format(location.getDateDebut()), 
                    dateFormat.format(location.getDateFin()),  
                    location.calculerMontant(),
                    location.getRetour()==null ? "En cours" : "Retourné"
                };
                tableModel.addRow(rowData);
            }
        }

        locationTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(locationTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Ajouter");
        modifyButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");
        searchButton = new JButton("Rechercher");
        allButton = new JButton("Afficher tous");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(allButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        LocationController controller = new LocationController(this, parc);
        addButton.addActionListener(controller);
        modifyButton.addActionListener(controller);
        deleteButton.addActionListener(controller);
        searchButton.addActionListener(controller);
        allButton.addActionListener(controller);

        // Ajouter le panneau principal à la fenêtre
        getContentPane().add(mainPanel);

        setVisible(true);
    }

    public JTable getLocationTable() {
        return locationTable;
    }

    public JButton getAddButton() {
        return addButton;
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