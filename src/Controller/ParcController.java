package Controller;

import java.awt.event.*;
import javax.swing.JOptionPane;
import Vue.ParcVue;
import Vue.ScooterVue;
import Vue.ClientVue;
import Model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParcController implements ActionListener {

    private ParcVue view;
    private ParcModel parc;

    public ParcController(ParcVue view, ParcModel parc) {
        this.parc = parc;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == view.getAjoutClientButton()) {
            System.out.println("Ajouter Client cliqué");
            ajouterClient();
        } else if (source == view.getAjoutScooterButton()) {
            ajouterScooter();
        } else if (source == view.getAfficherClientsButton()) {
            System.out.println("Afficher Clients cliqué");
            afficherClients();
        } else if (source == view.getAfficherScootersButton()) {
            afficherScooters();
        }
    }

    private void ajouterClient() {
        // Récupérer les informations saisies dans les champs
        String nom = view.getNomField().getText();
        String prenom = view.getPrenomField().getText();
        String dateNaissance = view.getDateField().getText();
        String idClient = view.getIdClientField().getText();
        String mail = view.getMailField().getText();
        String permis = (String) view.getPermisComboBox().getSelectedItem();

        // Vérifier que les champs obligatoires ne sont pas vides
        if (nom.isEmpty() || prenom.isEmpty() || dateNaissance.isEmpty() || idClient.isEmpty() || mail.isEmpty() || permis.equals("Sélectionner un type de permis")) {
            JOptionPane.showMessageDialog(view, "Veuillez remplir tous les champs obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Créer un nouvel objet ClientModel
        try {
            // Convertir la date de naissance en objet Date si nécessaire
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateNaissance);

            ClientModel client = new ClientModel(nom, prenom, date, idClient, mail, permis, parc);

            // Ajouter le client au parc
            parc.ajouterClient(client);

            // Réinitialiser les champs après l'ajout
            view.getNomField().setText("");
            view.getPrenomField().setText("");
            view.getDateField().setText("");
            view.getIdClientField().setText("");
            view.getMailField().setText("");
            view.getPermisComboBox().setSelectedIndex(0);

            JOptionPane.showMessageDialog(view, "Client ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(view, "Veuillez entrer une date de naissance valide (format : dd/MM/yyyy).", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ajouterScooter() {
        
            // Récupérer les informations saisies dans les champs
            String modele = view.getModeleField().getText();
            String idScooter = view.getIdScooterField().getText();
            double kilometrage = Double.parseDouble(view.getKilometrageField().getText());
            double prix = Double.parseDouble(view.getPrixField().getText());
            boolean disponible = view.getDispoCheckBox().isSelected();
            // Vérifier que les champs obligatoires ne sont pas vides
            if (modele.isEmpty() || idScooter.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Veuillez remplir tous les champs obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Créer un nouvel objet ScooterModel
            ScooterModel scooter = new ScooterModel(modele, disponible, idScooter, kilometrage, parc, prix);

            parc.ajouterScooter(scooter);

            // Réinitialiser les champs après l'ajout
            view.getModeleField().setText("");
            view.getIdScooterField().setText("");
            view.getKilometrageField().setText("");
            view.getPrixField().setText("");
            view.getDispoCheckBox().setSelected(false);

            JOptionPane.showMessageDialog(view, "Scooter ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        
    }

    private void afficherClients() {
        // Afficher la vue des clients
        ClientVue clientVue = new ClientVue(parc);
        new ClientController(clientVue, parc);
        clientVue.setVisible(true);
    }

    private void afficherScooters() {
        // Afficher la vue des scooters
        ScooterVue scooterVue = new ScooterVue(parc);
        new ScooterController(scooterVue, parc);
        scooterVue.setVisible(true);
    }
}



















