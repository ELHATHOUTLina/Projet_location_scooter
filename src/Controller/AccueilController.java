package Controller;

import java.awt.event.*;

import javax.swing.JOptionPane;

import Vue.AccueilVue;
import Vue.LocationVue;
import Vue.ParcVue;
import Vue.RetourVue;
import Model.ClientModel;
import Model.LocationModel;
import Model.ParcModel;


public class AccueilController implements ActionListener {
    private AccueilVue vue;
    private ParcModel parc;

    public AccueilController(AccueilVue vue, ParcModel parc) {
        this.parc = parc;
        this.vue = vue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String buttonText = "";

        if (source instanceof javax.swing.JButton) {
            buttonText = ((javax.swing.JButton) source).getText();
        }

        if (buttonText.equals("Locations")) {
            LocationVue locationVue = new LocationVue(parc);
            new LocationController(locationVue, parc);
            locationVue.setVisible(true);
        } else if (buttonText.equals("Retours")) {
            RetourVue retourVue = new RetourVue(parc);
            new RetourController(retourVue, parc);
            retourVue.setVisible(true);
    
        } else if (buttonText.equals("Statistiques")) {
            afficherStatistiques();
            
        } else if (buttonText.equals("Saisie de Parc")) {
            ParcVue parcVue = new ParcVue(parc);
            new ParcController(parcVue, parc);
            parcVue.setVisible(true);
        } else if (buttonText.equals("Quitter")) {
            System.exit(0);
        }else {
            System.out.println("Action non reconnue");
        }
    }
    private void afficherStatistiques() {
    // Initialiser les compteurs
    int totalClients = parc.getClients().size();
    int totalScooters = parc.getScooters().size();
    int locationsEnCours = 0;
    int retoursEffectues = 0;

    // Parcourir les clients pour compter les locations en cours et les retours effectués
    for (ClientModel client : parc.getClients()) {
        for (LocationModel location : client.getLocation()) {
            if (location.getRetour() == null) {
                locationsEnCours++; // Location sans retour
            } else {
                retoursEffectues++; // Location avec retour
            }
        }
    }

    // Construire le message des statistiques
    String message = String.format(
        "Statistiques :\n" +
        "- Nombre total de clients : %d\n" +
        "- Nombre total de scooters : %d\n" +
        "- Locations en cours : %d\n" +
        "- Retours effectués : %d",
        totalClients, totalScooters, locationsEnCours, retoursEffectues
    );

    // Afficher les statistiques dans une boîte de dialogue
    JOptionPane.showMessageDialog(vue, message, "Statistiques", JOptionPane.INFORMATION_MESSAGE);
}
}