package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controller.ClientController;

import java.awt.*;

import Model.ParcModel;
import Model.ClientModel;

public class ClientVue extends JFrame {

    private JTable clientTable;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton allButton;

    public ClientVue(ParcModel parc) {
        setTitle("Gestion des Clients");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer un panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tableau pour afficher les clients
        String[] columnNames = {"ID Client", "Nom", "Prénom", "Date de Naissance", "Email", "Permis"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Remplir le tableau avec les données des clients
        for (ClientModel client : parc.getClients()) {
            Object[] rowData = {
                client.getId_client(),
                client.getNom(),
                client.getPrenom(),
                client.getDateDeNaissance(),
                client.getMail(),
                client.getPermis()
            };
            tableModel.addRow(rowData);
        }

        clientTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(clientTable);
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

        ClientController controller = new ClientController(this, parc);
        modifyButton.addActionListener(controller);
        deleteButton.addActionListener(controller);
        searchButton.addActionListener(controller);
        allButton.addActionListener(controller);

        // Ajouter le panneau principal à la fenêtre
        getContentPane().add(mainPanel);

        setVisible(true);
    }

    public JTable getClientTable() {
        return clientTable;
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
