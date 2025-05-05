package Model;
import java.util.*;


public class ScooterModel {

    private String modele;
    private boolean disponible;
    private String numero_identification;
    private double kilometrage;
    private double prixJ;
    private ParcModel parc;


    Vector<LocationModel> locations = new Vector<LocationModel>();


    public ScooterModel(String modeleScooter, boolean dispo,String idScooter, double km, ParcModel p, double prix) {
        modele = modeleScooter;
        numero_identification = idScooter;
        kilometrage = km;
        parc = p;
        disponible = dispo;
        prixJ = prix;
    }
    public void setKilometrage(double km) {
        kilometrage = km;
    }
    public double getKilometrage() {
        return kilometrage;
    }
    public void setNumero_identification(String id) {
        numero_identification = id;
    }
    public String getNumero_identification() {
        return numero_identification;
    }
    public void setParc(ParcModel p) {
        parc = p;
    }
    public ParcModel getParc() {
        return parc;
    }
    public void setModele(String m) {
        modele = m;
    }
    public String getModele() {
        return modele;
    }
    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    public void setLocation(Vector<LocationModel> l) {
        locations = l;
    }
    public void setPrixJ(double prix) {
        prixJ = prix;
    }
    public double getPrixJ() {
        return prixJ;
    }

    public Vector<LocationModel> getLocation() {
        return locations;
    }
    public void ajouterLocation(LocationModel l) {
        if (l != null) {
            if (!locations.contains(l)) {
                locations.add(l);
            } else {
                System.out.println("La location existe déjà dans le scooter.");
            }
        }
    }
    public void supprimerLocation(LocationModel l) {
        if (l != null) {
            if (locations.contains(l)) {
                // synchroniser la location avec le client
                l.getClient().getLocation().remove(l);
                locations.remove(l);
                this.setDisponible(true);
            } else {
                System.out.println("La location n'existe pas dans le scooter.");
            }
        }
    }
   
    public String toString() {
        return "Scooter N°" + numero_identification + " | " +
                "modele :" + modele + " | " +
                "Prix/Jour :" + prixJ + " | " +
                " kilometrage :" + kilometrage +" | "+
                " disponible :" + (isDisponible() ? "oui": "non") +" | "+
                " parc :" + parc.getAdresse() ;
    }

        
}
