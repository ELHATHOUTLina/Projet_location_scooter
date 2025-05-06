package Controller;

import Vue.RetourVue;
import Model.ParcModel;
import Model.ClientModel;
import Model.LocationModel;
import Model.RetourModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RetourController implements ActionListener {
    private RetourVue view;
    private ParcModel parc;

    public RetourController(RetourVue view, ParcModel parc) {
        this.view = view;
        this.parc = parc;

        // Connecter les boutons aux actions
        view.getAddButton().addActionListener(this);
        view.getModifyButton().addActionListener(this);
        view.getDeleteButton().addActionListener(this);
        view.getSearchButton().addActionListener(this);
        view.getAllButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == view.getAddButton()) {
            ajouterRetour();
        } else if (source == view.getModifyButton()) {
            modifierRetour();
        } else if (source == view.getDeleteButton()) {
            supprimerRetour();
        } else if (source == view.getSearchButton()) {
            rechercherRetour();
        } else if (source == view.getAllButton()) {
            afficherTousLesRetours();
        }
    }

    private void ajouterRetour() {
        // Exemple de formulaire pour ajouter un retour
        JTextField idField = new JTextField();
        JTextField kmField = new JTextField();
        JTextField dateField = new JTextField("dd/MM/yyyy");
        JComboBox<LocationModel> locationComboBox = new JComboBox<>();

        // Remplir le comboBox avec les locations disponibles
        for (ClientModel client : parc.getClients()) {
            for (LocationModel location : client.getLocation()) {
                if (location.getRetour() == null) { // Location sans retour
                    locationComboBox.addItem(location);
                }
            }
        }

        Object[] message = {
            "ID Retour :", idField,
            "Kilométrage ajouté :", kmField,
            "Date de retour (dd/MM/yyyy) :", dateField,
            "Location :", locationComboBox
        };

        int option = JOptionPane.showConfirmDialog(view, message, "Ajouter un retour", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                int km = Integer.parseInt(kmField.getText());
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateField.getText());
                LocationModel location = (LocationModel) locationComboBox.getSelectedItem();

                if (location != null) {
                    RetourModel retour = new RetourModel(id, km, date, location);
                    location.setRetour(retour); // Associer le retour à la location

                    JOptionPane.showMessageDialog(view, "Retour ajouté avec succès !");
                    afficherTousLesRetours(); // Rafraîchir le tableau
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur lors de l'ajout du retour : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierRetour() {
        int selectedRow = view.getRetourTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un retour à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel tableModel = (DefaultTableModel) view.getRetourTable().getModel();
        int idRetour = (int) tableModel.getValueAt(selectedRow, 0);

        RetourModel retourAModifier = null;
        for (ClientModel client : parc.getClients()) {
            for (LocationModel location : client.getLocation()) {
                RetourModel retour = location.getRetour();
                if (retour != null && retour.getIdRetour() == idRetour) {
                    retourAModifier = retour;
                    break;
                }
            }
        }

        if (retourAModifier == null) {
            JOptionPane.showMessageDialog(view, "Retour introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField kmField = new JTextField(String.valueOf(retourAModifier.getKilometrage_ajouter()));
        JTextField dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(retourAModifier.getDateR()));

        Object[] message = {
            "Kilométrage ajouté :", kmField,
            "Date de retour (dd/MM/yyyy) :", dateField
        };

        int option = JOptionPane.showConfirmDialog(view, message, "Modifier un retour", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int km = Integer.parseInt(kmField.getText());
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateField.getText());

                retourAModifier.setKilometrage_ajouter(km);
                retourAModifier.setDateR(date);

                JOptionPane.showMessageDialog(view, "Retour modifié avec succès !");
                afficherTousLesRetours(); // Rafraîchir le tableau
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erreur lors de la modification du retour : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerRetour() {
        int selectedRow = view.getRetourTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un retour à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel tableModel = (DefaultTableModel) view.getRetourTable().getModel();
        int idRetour = (int) tableModel.getValueAt(selectedRow, 0);

        for (ClientModel client : parc.getClients()) {
            for (LocationModel location : client.getLocation()) {
                RetourModel retour = location.getRetour();
                if (retour != null && retour.getIdRetour() == idRetour) {
                    location.supprimerRetour(); // Supprimer le retour de la location
                    JOptionPane.showMessageDialog(view, "Retour supprimé avec succès !");
                    afficherTousLesRetours(); // Rafraîchir le tableau
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(view, "Retour introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void rechercherRetour() {
        String searchTerm = JOptionPane.showInputDialog(view, "Entrez l'ID du retour ou le nom du client :", "Rechercher un retour", JOptionPane.QUESTION_MESSAGE);

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Veuillez entrer un critère de recherche valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel tableModel = (DefaultTableModel) view.getRetourTable().getModel();
        tableModel.setRowCount(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (ClientModel client : parc.getClients()) {
            for (LocationModel location : client.getLocation()) {
                RetourModel retour = location.getRetour();
                if (retour != null && (String.valueOf(retour.getIdRetour()).equals(searchTerm) ||
                        (client.getNom() + " " + client.getPrenom()).toLowerCase().contains(searchTerm.toLowerCase()))) {
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
    }

    private void afficherTousLesRetours() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getRetourTable().getModel();
        tableModel.setRowCount(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (ClientModel client : parc.getClients()) {
            for (LocationModel location : client.getLocation()) {
                RetourModel retour = location.getRetour();
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
    }
}