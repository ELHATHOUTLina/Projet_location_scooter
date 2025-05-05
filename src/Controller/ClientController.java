package Controller;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import Vue.ClientVue;
import Model.ClientModel;
import Model.ParcModel;

public class ClientController implements ActionListener {

    private ClientVue view;
    private ParcModel parc;

    public ClientController(ClientVue view, ParcModel parc) {
        this.parc = parc;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == view.getModifyButton()) {
            modifierClient();
        } else if (source == view.getDeleteButton()) {
            supprimerClient();
        } else if (source == view.getSearchButton()) {
            rechercherClient();
        } else if (source == view.getAllButton()) {
            afficherTousLesClients();
        }
    }

    private void modifierClient() {
        // Vérifier si une ligne est sélectionnée
        int selectedRow = view.getClientTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un client à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer les données actuelles du client sélectionné
        JTable table = view.getClientTable();
        String id = table.getValueAt(selectedRow, 0).toString(); 
        String nom = table.getValueAt(selectedRow, 1).toString();
        String prenom = table.getValueAt(selectedRow, 2).toString();
        String dateNaissance = table.getValueAt(selectedRow, 3).toString();
        String email = table.getValueAt(selectedRow, 4).toString();
        String permis = table.getValueAt(selectedRow, 5).toString();

        // Créer un formulaire de modification
        JTextField emailField = new JTextField(email);
        JTextField permisField = new JTextField(permis);

        Object[] message = {
            "ID Client : " + id,
            "Nom :"+ nom,
            "Prénom :" + prenom,
            "Date de Naissance :"+ dateNaissance,
            "Email :", emailField,
            "Permis :", permisField
        };

        int option = JOptionPane.showConfirmDialog(view, message, "Modifier Client", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            ClientModel clientToModify = parc.rechercherClientId(id);
            if (clientToModify == null) {
                JOptionPane.showMessageDialog(view, "Le client sélectionné n'existe pas dans le parc.");
                return;
            }

            // Mettre à jour les données dans le tableau
            table.setValueAt(emailField.getText(), selectedRow, 4);
            clientToModify.setMail(emailField.getText());
            table.setValueAt(permisField.getText(), selectedRow, 5);
            clientToModify.setPermis(permisField.getText());

            JOptionPane.showMessageDialog(view, "Le client a été modifié avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void supprimerClient() {
        // Vérifier si une ligne est sélectionnée
        int selectedRow = view.getClientTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un client à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmer la suppression
        int confirmation = JOptionPane.showConfirmDialog(view, "Êtes-vous sûr de vouloir supprimer ce client ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            // Récupérer l'ID du client sélectionné
            JTable table = view.getClientTable();
            String clientId = table.getValueAt(selectedRow, 0).toString();

            // Trouver l'objet ClientModel correspondant dans le parc
            ClientModel clientToRemove = parc.rechercherClientId(clientId);

            // Supprimer le client du parc
            if (clientToRemove != null) {
                parc.supprimerClient(clientToRemove);

                // Supprimer la ligne du tableau
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                tableModel.removeRow(selectedRow);

                JOptionPane.showMessageDialog(view, "Le client a été supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Le client sélectionné n'existe pas dans le parc.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void rechercherClient() {
        // Demander à l'utilisateur de saisir un critère de recherche
        String searchTerm = JOptionPane.showInputDialog(view, "Entrez l'ID ou le nom du client à rechercher :", "Rechercher Client", JOptionPane.QUESTION_MESSAGE);

        // Vérifier si l'utilisateur a annulé ou laissé le champ vide
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Veuillez entrer un critère de recherche valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir le critère en minuscules pour une recherche insensible à la casse
        searchTerm = searchTerm.toLowerCase();

        // Filtrer les clients correspondants
        Vector<ClientModel> filteredClients = new Vector<>();
        for (ClientModel client : parc.getClients()) {
            if (client.getId_client().toLowerCase().equals(searchTerm) ||
                client.getNom().toLowerCase().equals(searchTerm)) {
                filteredClients.add(client);
            }
        }

        // Vérifier si des résultats ont été trouvés
        if (filteredClients.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucun client trouvé pour le critère : " + searchTerm, "Résultat de la recherche", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mettre à jour le tableau avec les résultats de la recherche
        DefaultTableModel tableModel = (DefaultTableModel) view.getClientTable().getModel();
        tableModel.setRowCount(0); // Effacer les lignes existantes

        for (ClientModel client : filteredClients) {
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

        JOptionPane.showMessageDialog(view, "Recherche terminée. " + filteredClients.size() + " résultat(s) trouvé(s).", "Résultat de la recherche", JOptionPane.INFORMATION_MESSAGE);
    }

    private void afficherTousLesClients() {
        // Récupérer tous les clients du parc
        Vector<ClientModel> allClients = parc.getClients();

        // Mettre à jour le tableau avec tous les clients
        DefaultTableModel tableModel = (DefaultTableModel) view.getClientTable().getModel();
        tableModel.setRowCount(0); // Effacer les lignes existantes

        for (ClientModel client : allClients) {
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

        JOptionPane.showMessageDialog(view, "Tous les clients ont été affichés.", "Affichage complet", JOptionPane.INFORMATION_MESSAGE);
    }
}