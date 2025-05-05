package Controller;

import java.awt.event.*;
import Vue.AccueilVue;
import Vue.LocationVue;
import Vue.ParcVue;
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
        } else if (buttonText.equals("Retour")) {
            System.out.println("Bouton 'Retour' cliqué");
            
        } else if (buttonText.equals("Réservations")) {
            System.out.println("Bouton 'Réservations' cliqué");
            
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
}