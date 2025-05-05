
package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JOptionPane; 
import Model.ScooterModel;          
import Model.PenaliteModel;         
import Model.ParcModel;      

public class RetourController implements ActionListener {
    Model.ParcModel parcScooter;
    //page1
    JTextField text;
    JPanel page;
    JPanel page2;
    //page2
    JTextField Km;
    JTextField Penalite;
    JTextField Prix;

    ScooterModel scooterTrouve = null; 

    
    public RetourController(ParcModel pS, JTextField t, JPanel p1, JPanel p2,
                            JTextField km, JTextField p, JTextField px) {
        parcScooter = pS;
        text = t;
        page = p1;
        page2 = p2;
        Km = km;
        Penalite = p;
        Prix =px;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String idScooter = text.getText(); 

        if (((JButton) e.getSource()).getText().equals("recherchez")) {
//            // --- Ajouté : rechercher le scooter correspondant
//            for (Scooter s : parcScooter.roleScooter) {
//                if (s.numero_identification.equals(idScooter)) {
//                    scooterTrouve = s;
//                    break;
//                }
//            }
//
//            if (scooterTrouve != null) {
//                page.setVisible(false);
//                page2.setVisible(true);
//            } else {
//                JOptionPane.showMessageDialog(null, "Aucun scooter trouvé avec cet identifiant.");
//            }
        	page.setVisible(false);
            page2.setVisible(true);

        } else if (((JButton) e.getSource()).getText().equals("Valider le retour")) { 
            if (scooterTrouve == null) {
                JOptionPane.showMessageDialog(null, "Aucun scooter à retourner.");
                return;
            }

            // --- Ajouté : mise à jour du kilométrage
            String km = Km.getText().trim();
            if (!km.isEmpty()) {
                scooterTrouve.kilometrage = km;
            }

            // --- Ajouté : ajout de pénalité si précisée
            String type = Penalite.getText().trim();
            String prixStr = Prix.getText().trim();

            if (!type.isEmpty() && !prixStr.isEmpty()) {
                try {
                    float prix = Float.parseFloat(prixStr);
                    PenaliteModel p = new PenaliteModel(type, prix, parcScooter);
                    parcScooter.ajouterPenalite(p);
                    JOptionPane.showMessageDialog(null, "Retour enregistré avec pénalité.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Prix invalide !");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Retour enregistré sans pénalité.");
            }

            // --- Ajouté : retour à la première page
            page2.setVisible(false);
            page.setVisible(true);
        }
    }
}