package Controller;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import Vue.ScooterVue;
import Model.ScooterModel;
import Model.ParcModel;
public class ScooterController implements ActionListener {

    private ScooterVue view;
    private ParcModel parc;

    public ScooterController(ScooterVue view, ParcModel parc) {
        this.parc = parc;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == view.getModifyButton()) {
            modifierScooter();
        } else if (source == view.getDeleteButton()) {
            System.out.println("Bouton 'Supprimer' cliqué");
            supprimerScooter();
        } else if (source == view.getSearchButton()) {
            System.out.println("Bouton 'Rechercher' cliqué");
            rechercherScooter();
        } else if (source == view.getAllButton()) {
            afficherTousLesScooters();
        }
        
    }


    private void modifierScooter() {
        // Vérifier si une ligne est sélectionnée
        int selectedRow = view.getScooterTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un scooter à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer les données actuelles du scooter sélectionné
        JTable table = view.getScooterTable();
        String id = table.getValueAt(selectedRow, 0).toString(); // ID (non modifiable)
        String modele = table.getValueAt(selectedRow, 1).toString(); // Modèle (non modifiable)
        String kilometrage = table.getValueAt(selectedRow, 2).toString();
        String prix = table.getValueAt(selectedRow, 3).toString();
        String disponible = table.getValueAt(selectedRow, 4).toString();

        // Créer un formulaire de modification
        JTextField kilometrageField = new JTextField(kilometrage);
        JTextField prixField = new JTextField(prix);
        JCheckBox dispoCheckBox = new JCheckBox("Disponible", disponible.equals("Oui"));

        Object[] message = {
            "Matricule  : " + id,
            "Modèle  : " + modele,
            "Kilométrage :", kilometrageField,
            "Prix/Jour :", prixField,
            "Disponible :", dispoCheckBox
        };

        int option = JOptionPane.showConfirmDialog(view, message, "Modifier Scooter", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            ScooterModel scooterToModify = parc.rechercherScooterId(id);
            if (scooterToModify == null) {
                JOptionPane.showMessageDialog(view, "Le scooter sélectionné n'existe pas dans le parc.");
                return;
            }
            // Mettre à jour les données dans le tableau
            table.setValueAt(kilometrageField.getText(), selectedRow, 2);
            scooterToModify.setKilometrage(Double.parseDouble(kilometrageField.getText()));
            table.setValueAt(prixField.getText(), selectedRow, 3);
            scooterToModify.setPrixJ(Double.parseDouble(prixField.getText()));
            table.setValueAt(dispoCheckBox.isSelected() ? "Oui" : "Non", selectedRow, 4);
            scooterToModify.setDisponible(dispoCheckBox.isSelected());

            JOptionPane.showMessageDialog(view, "Le scooter a été modifié avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
        System.out.println(parc.getScooters().toString());
    }

    private void supprimerScooter() {
        // Vérifier si une ligne est sélectionnée
        int selectedRow = view.getScooterTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un scooter à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmer la suppression
        int confirmation = JOptionPane.showConfirmDialog(view, "Êtes-vous sûr de vouloir supprimer ce scooter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            // Récupérer l'ID du scooter sélectionné
            JTable table = view.getScooterTable();
            String scooterId = table.getValueAt(selectedRow, 0).toString();

            // Trouver l'objet ScooterModel correspondant dans le parc
            ScooterModel scooterToRemove = parc.rechercherScooterId(scooterId);
            

            // Supprimer le scooter du parc
            if (scooterToRemove != null) {
                parc.supprimerScooter(scooterToRemove);

                // Supprimer la ligne du tableau
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                tableModel.removeRow(selectedRow);

                JOptionPane.showMessageDialog(view, "Le scooter a été supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Le scooter sélectionné n'existe pas dans le parc.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
        System.out.println(parc.getScooters().toString());
    }

    private void rechercherScooter() {
        // Demander à l'utilisateur de saisir un critère de recherche
        String searchTerm = JOptionPane.showInputDialog(view, "Entrez l'ID ou le modèle du scooter à rechercher :", "Rechercher Scooter", JOptionPane.QUESTION_MESSAGE);

        // Vérifier si l'utilisateur a annulé ou laissé le champ vide
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Veuillez entrer un critère de recherche valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir le critère en minuscules pour une recherche insensible à la casse
        searchTerm = searchTerm.toLowerCase();

        // Filtrer les scooters correspondants
        Vector<ScooterModel> filteredScooters = new Vector<>();
        for (ScooterModel scooter : parc.getScooters()) {
            if (scooter.getNumero_identification().toLowerCase().equals(searchTerm)) {
                filteredScooters.add(scooter);
            }
        }

        // Vérifier si des résultats ont été trouvés
        if (filteredScooters.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucun scooter trouvé pour le critère : " + searchTerm, "Résultat de la recherche", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mettre à jour le tableau avec les résultats de la recherche
        DefaultTableModel tableModel = (DefaultTableModel) view.getScooterTable().getModel();
        tableModel.setRowCount(0); // Effacer les lignes existantes

        for (ScooterModel scooter : filteredScooters) {
            Object[] rowData = {
                scooter.getNumero_identification(),
                scooter.getModele(),
                scooter.getKilometrage(),
                scooter.getPrixJ(),
                scooter.isDisponible() ? "Oui" : "Non"
            };
            tableModel.addRow(rowData);
        }

        JOptionPane.showMessageDialog(view, "Recherche terminée. " + filteredScooters.size() + " résultat(s) trouvé(s).", "Résultat de la recherche", JOptionPane.INFORMATION_MESSAGE);
    }
    private void afficherTousLesScooters() {
        // Récupérer tous les scooters du parc
        Vector<ScooterModel> allScooters = parc.getScooters();
    
        // Mettre à jour le tableau avec tous les scooters
        DefaultTableModel tableModel = (DefaultTableModel) view.getScooterTable().getModel();
        tableModel.setRowCount(0); // Effacer les lignes existantes
    
        for (ScooterModel scooter : allScooters) {
            Object[] rowData = {
                scooter.getNumero_identification(),
                scooter.getModele(),
                scooter.getKilometrage(),
                scooter.getPrixJ(),
                scooter.isDisponible() ? "Oui" : "Non"
            };
            tableModel.addRow(rowData);
        }
    
        JOptionPane.showMessageDialog(view, "Tous les scooters ont été affichés.", "Affichage complet", JOptionPane.INFORMATION_MESSAGE);
    }
}