package Controller;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.util.Date;
import Vue.LocationVue;
import Model.LocationModel;
import Model.ParcModel;
import Model.ScooterModel;
import Model.ClientModel;

public class LocationController implements ActionListener {

    private LocationVue view;
    private ParcModel parc;

    public LocationController(LocationVue view, ParcModel parc) {
        this.parc = parc;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == view.getAddButton()) {
            ajouterLocation();
        } else if (source == view.getModifyButton()) {
            modifierLocation();
        } else if (source == view.getDeleteButton()) {
            supprimerLocation();
        } else if (source == view.getSearchButton()) {
            rechercherLocation();
        } else if (source == view.getAllButton()) {
            afficherToutesLesLocations();
        }
    }

    private void ajouterLocation() {
        // Créer un formulaire pour ajouter une location

        // Récupérer les clients
        Vector<ClientModel> clients = parc.getClients();
        if (clients.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucun client disponible.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer les scooters disponibles
        Vector<ScooterModel> scootersDisponibles = parc.getScootersDispo();

        if (scootersDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucun scooter disponible.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Créer les JComboBox pour les clients et les scooters
        JComboBox<ClientModel> clientComboBox = new JComboBox<>(clients);
        JComboBox<ScooterModel> scooterComboBox = new JComboBox<>(scootersDisponibles);

        // Créer les champs pour les dates
        JTextField dateDebutField = new JTextField();
        JTextField dateFinField = new JTextField();

        // Créer le formulaire
        Object[] message = {
            "Sélectionnez un client :", clientComboBox,
            "Sélectionnez un scooter :", scooterComboBox,
            "Date de début (dd/MM/yyyy) :", dateDebutField,
            "Date de fin (dd/MM/yyyy) :", dateFinField
        };

        // Afficher le formulaire
        int option = JOptionPane.showConfirmDialog(view, message, "Ajouter une location", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) {
            return; 
        }

        // Récupérer les valeurs sélectionnées et saisies
        ClientModel client = (ClientModel) clientComboBox.getSelectedItem();
        ScooterModel scooter = (ScooterModel) scooterComboBox.getSelectedItem();
        String dateDebutStr = dateDebutField.getText();
        String dateFinStr = dateFinField.getText();

        // Valider les dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        Date dateDebut, dateFin;
        try {
            dateDebut = dateFormat.parse(dateDebutStr);
            dateFin = dateFormat.parse(dateFinStr);

            if (dateDebut.after(dateFin)) {
                JOptionPane.showMessageDialog(view, "La date de début doit être avant la date de fin.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Format de date invalide. Utilisez le format dd/MM/yyyy.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Créer la location
        LocationModel nouvelleLocation = new LocationModel(dateDebut,dateFin,client,scooter);

        // Mettre à jour le tableau
        DefaultTableModel tableModel = (DefaultTableModel) view.getLocationTable().getModel();
        tableModel.addRow(new Object[]{
            client.getNom() + " " + client.getPrenom(),
            client.getId_client(),
            scooter.getNumero_identification(),
            dateDebutStr,
            dateFinStr,
            nouvelleLocation.calculerMontant(),
            nouvelleLocation.getRetour()==null ? "En cours" : "Retourné"
        });

        JOptionPane.showMessageDialog(view, "La location a été ajoutée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void modifierLocation() {
        int selectedRow = view.getLocationTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner une location à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(view, "Fonctionnalité Modifier Location à implémenter.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void supprimerLocation() {
        int selectedRow = view.getLocationTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner une location à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTable table = view.getLocationTable();
        String clientId = table.getValueAt(selectedRow, 1).toString(); // ID du client
        String scooterId = table.getValueAt(selectedRow, 2).toString(); // Modèle du scooter

        // Trouver le scooter correspondant
        ScooterModel scooterCorrespondant = parc.rechercherScooterId(scooterId); 
        if (scooterCorrespondant == null) {
            JOptionPane.showMessageDialog(view, "Le scooter sélectionné n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Trouver la location correspondante dans le client
        LocationModel locationToRemove = null;
        for (LocationModel location : scooterCorrespondant.getLocation()) {
            if (location.getClient().getId_client().equals(clientId)) {
                locationToRemove = location;
                break;
            }
        }

        if (locationToRemove == null) {
            JOptionPane.showMessageDialog(view, "La location sélectionnée n'existe pas pour ce client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Supprimer la location
        scooterCorrespondant.supprimerLocation(locationToRemove);

        // Supprimer la ligne du tableau
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.removeRow(selectedRow);

        JOptionPane.showMessageDialog(view, "La location a été supprimée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void rechercherLocation() {
        String searchTerm = JOptionPane.showInputDialog(view, "Entrez le nom du client ou l'Id du scooter :", "Rechercher Location", JOptionPane.QUESTION_MESSAGE);

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Veuillez entrer un critère de recherche valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        searchTerm = searchTerm.toLowerCase();
        Vector<Object[]> filteredLocations = new Vector<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (ClientModel client : parc.getClients()) {
            for (LocationModel location : client.getLocation()) {
                if ((client.getNom() + " " + client.getPrenom()).toLowerCase().contains(searchTerm) ||
                    location.getScooter().getNumero_identification().toLowerCase().equals(searchTerm)) {
                    filteredLocations.add(new Object[]{
                        client.getNom() + " " + client.getPrenom(),
                        client.getId_client(),
                        location.getScooter().getNumero_identification(),
                        dateFormat.format(location.getDateDebut()), 
                        dateFormat.format(location.getDateFin()),   
                        location.calculerMontant(),
                        location.getRetour()==null ? "En cours" : "Retourné"
                    });
                }
            }
        }

        if (filteredLocations.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucune location trouvée pour le critère : " + searchTerm, "Résultat de la recherche", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DefaultTableModel tableModel = (DefaultTableModel) view.getLocationTable().getModel();
        tableModel.setRowCount(0);

        for (Object[] rowData : filteredLocations) {
            tableModel.addRow(rowData);
        }

        JOptionPane.showMessageDialog(view, "Recherche terminée. " + filteredLocations.size() + " résultat(s) trouvé(s).", "Résultat de la recherche", JOptionPane.INFORMATION_MESSAGE);
    }

    private void afficherToutesLesLocations() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getLocationTable().getModel();
        tableModel.setRowCount(0);

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

        JOptionPane.showMessageDialog(view, "Toutes les locations ont été affichées.", "Affichage complet", JOptionPane.INFORMATION_MESSAGE);
    }
}
