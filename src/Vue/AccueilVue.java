package Vue;

import javax.swing.*;
import java.awt.*;
import Controller.AccueilController;
import Model.ParcModel;

public class AccueilVue extends JFrame {
    private ParcModel parc;
    public AccueilVue(ParcModel parc) {
        this.parc = parc;
        setTitle("LOCATION SCOOTER");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer un JPanel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel titleLabel = new JLabel("Bienvenue dans le système de gestion de location de scooters", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        getContentPane().add(titleLabel, BorderLayout.NORTH);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        // Ajouter les boutons
        JButton saisieParcButton = new JButton("Saisie de Parc");
        JButton locationsButton = new JButton("Locations");
        JButton retourButton = new JButton("Retour");
        JButton reservationsButton = new JButton("Réservations");
        JButton quitterButton = new JButton("Quitter");

        // Ajouter les boutons au panneau
        buttonPanel.add(saisieParcButton);
        buttonPanel.add(locationsButton);
        buttonPanel.add(retourButton);
        buttonPanel.add(reservationsButton);
        buttonPanel.add(quitterButton);

        // Positionner en haut
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0)); 

        // une image 
        ImageIcon originalIcon = new ImageIcon("./pic_scooter.jpg"); // Remplacez par le chemin de votre image
        Image scaledImage = originalIcon.getImage().getScaledInstance(700, 400, Image.SCALE_SMOOTH); // Redimensionner l'image
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        getContentPane().add(imageLabel, BorderLayout.CENTER);

        // Créer le contrôleur et connecter les boutons
        AccueilController controller = new AccueilController(this, parc);
        locationsButton.addActionListener(controller);
        retourButton.addActionListener(controller);
        reservationsButton.addActionListener(controller);
        saisieParcButton.addActionListener(controller);
        quitterButton.addActionListener(controller);

        setVisible(true);
    }

}






