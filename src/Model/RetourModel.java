package Model;
import java.util.*;

public class RetourModel {

    private int idRetour;
    private int kilometrage_ajouter;
    private Date dateR;
    private LocationModel location;


    public RetourModel(int id, int km, Date dR, LocationModel l) {
        if (l == null) {
            System.out.println("La location ne peut pas être nulle.");
        }
        if (dR == null) {
            System.out.println("La date de retour ne peut pas être nulle.");
        }
        if (km < 0) {
            System.out.println("Le kilométrage ajouté ne peut pas être négatif.");
        }
        if (dR.before(l.getDateDebut()) || dR.before(l.getDateFin())) {
            System.out.println("La date de retour est fausse.");
        }
        idRetour = id;
        kilometrage_ajouter = km;
        dateR = dR;
        location = l;
      }

    public void setIdRetour(int id) {
        idRetour = id;
    }
    public int getIdRetour() {
        return idRetour;
    }
    public void setKilometrage_ajouter(int km) {
        if (km < 0) {
            System.out.println("Le kilométrage ajouté ne peut pas être négatif.");
        }
        kilometrage_ajouter = km;
    }
    public int getKilometrage_ajouter() {
        return kilometrage_ajouter;
    }
    public void setDateR(Date dR) {
        if (dR == null) {
            System.out.println("La date de retour ne peut pas être nulle.");
        }
        if (dR.before(location.getDateDebut()) || dR.before(location.getDateFin())) {
            System.out.println("La date de retour est fausse.");
        }
        dateR = dR;
    }
    public Date getDateR() {
        return dateR;
    }
    public void setLocation(LocationModel l) {
        if (l == null) {
            System.out.println("La location ne peut pas être nulle.");
        }
        location = l;
    }
    public LocationModel getLocation() {
        return location;
    }
    public String toString() {
        return "Retour N°" + idRetour + " \n " +
                "Date Retour : " + dateR + '\n' +
                " kilometrage Effectué :" + kilometrage_ajouter +'\n'+
                " Client :" + location.getClient().getNom() +'\n'+
                " Scooter :" + location.getScooter().getNumero_identification() ;
    }
}