package Vue;

import javax.swing.*;
import java.awt.*;

public class RetourVue extends JFrame {
    public RetourVue() {
        setTitle("LOCATION SCOOTER");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new FlowLayout());

        JButton Accueil = new JButton("Accueil");
        JButton Location = new JButton("Location");
        JButton Retour = new JButton("Retour");
        JButton Parc = new JButton("Saisie du parc");
        JButton Quitter = new JButton("Quitter");

        Retour.setBackground(Color.decode("#4CAF50")); // Changez la couleur de fond du bouton "Retour"

        panelPrincipal.add(Accueil);
        panelPrincipal.add(Location);
        panelPrincipal.add(Retour);
        panelPrincipal.add(Parc);
        panelPrincipal.add(Quitter);


//----------------------------------------------------------------


        Quitter.addActionListener(e -> System.exit(0));
// -----------------------------------------------------------------------------

        // Première page affichée
        JPanel pageRetour = new JPanel();

        JPanel pageRetourUn = new JPanel(new GridLayout(3, 1));
        JLabel id = new JLabel("Entrez l'id du scooter");
        JTextField idT = new JTextField("");
        JButton rechercher = new JButton("recherchez");

        pageRetourUn.add(id);
        pageRetourUn.add(idT);
        pageRetourUn.add(rechercher);

        // Deuxième page affichée (quand on clique sur rechercher)
        JPanel pageRetour2 = new JPanel(new GridLayout(7,1));

        JLabel Km = new JLabel("Entrez le kilométrage final du scooter :");
        JTextField KmT = new JTextField("");
        JLabel Penalite = new JLabel("Entrez le type de pénalité :");
        JTextField PenaliteT = new JTextField("");
        JLabel Prix = new JLabel("Prix de la pénalité :");
        JTextField PrixT = new JTextField("");
        JButton boutonValiderRetour = new JButton("Valider le retour");

        pageRetour2.add(Km);
        pageRetour2.add(KmT);
        pageRetour2.add(Penalite);
        pageRetour2.add(PenaliteT);
        pageRetour2.add(Prix);
        pageRetour2.add(PrixT);
        pageRetour2.add(boutonValiderRetour);

        pageRetour2.setVisible(false); 
        
        //les 2 pages 
        pageRetour.add(pageRetourUn);
        pageRetour.add(pageRetour2); 
        setContentPane(pageRetour); 

        //-------------------------FAUT REMPLIR RETOUR-CONTROLLER-----------------------------------------------------------------------------------------
        // Relier retour au controller
        // RetourController controller = new RetourController(Parc, idT, pageRetourUn, pageRetour2, KmT, PenaliteT, PrixT); // Modifié : ajout des nouveaux champs
        // rechercher.addActionListener((ActionListener) controller);
        // boutonValiderRetour.addActionListener((ActionListener) controller);
        // -----------------------------------------------------------------------------------------------------------------------------------------------
        add(panelPrincipal, BorderLayout.NORTH);
        setVisible(true);
    }
}