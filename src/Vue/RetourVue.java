package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import Model.ParcModel;
import Model.ClientModel;
import Model.LocationModel;
import Model.RetourModel;

public class RetourVue extends JFrame {
    private JTable retourTable;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton allButton;

    public RetourVue(ParcModel parc) {
        setTitle("Gestion des Retours");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer un panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tableau pour afficher les retours
        String[] columnNames = { "ID Retour", "Date Retour", "Kilométrage effectué", "Client", "Immatricule Scooter", "Date Début", "Date Fin", "Montant Total" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Remplir le tableau avec les données des retours
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (ClientModel client : parc.getClients()) { // Parcourir les clients
            for (LocationModel location : client.getLocation()) { // Parcourir les locations du client
                RetourModel retour = location.getRetour(); // Récupérer le retour associé à la location
                if (retour != null) { 
                    Object[] rowData = {
                        retour.getIdRetour(),
                        dateFormat.format(retour.getDateR()),
                        retour.getKilometrage_ajouter(),
                        client.getNom() + " " + client.getPrenom(),
                        location.getScooter().getNumero_identification(),
                        dateFormat.format(location.getDateDebut()),
                        dateFormat.format(location.getDateFin()),
                        location.calculerMontant()
                    };
                    tableModel.addRow(rowData);
                }
            }
        }

        retourTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(retourTable);
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

        // Ajouter le panneau principal à la fenêtre
        getContentPane().add(mainPanel);

        setVisible(true);
    }

    public JTable getRetourTable() {
        return retourTable;
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