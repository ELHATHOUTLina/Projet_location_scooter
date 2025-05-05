package Model;


import java.util.*;
import java.text.SimpleDateFormat;


public class ClientModel {

    private String nom;
    private String prenom;
    private Date dateDeNaissance;
    private String id_client;
    private String mail;
    private String permis;

    ParcModel parc;
    Vector<LocationModel> location = new Vector<LocationModel>();

    public ClientModel(String n, String p, Date dateNaissance, String idClient, String m, String per, ParcModel parc) {
        nom = n;
        prenom = p;
        dateDeNaissance = dateNaissance;
        id_client = idClient;
        mail = m;
        permis = per;
        this.parc = parc;

        }
    public void setNom(String n) {
        nom = n;
    }
    public String getNom() {
        return nom;
    }
    public void setPrenom(String p) {
        prenom = p;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setDateDeNaissance(Date d) {
        dateDeNaissance = d;
    }
    public String getDateDeNaissance() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(dateDeNaissance);
    }
    public void setId_client(String id) {
        id_client = id;
    }
    public String getId_client() {
        return id_client;
    }
    public void setMail(String m) {
        mail = m;
    }
    public String getMail() {
        return mail;
    }
    public void setPermis(String p) {
        permis = p;
    }
    public String getPermis() {
        return permis;
    }
    public void setParc(ParcModel p) {
        parc = p;
    }
    public ParcModel getParc() {
        return parc;
    }
    public void setLocation(Vector<LocationModel> l) {
        location = l;
    }
    public Vector<LocationModel> getLocation() {
        return location;
    }
    public void ajouterLocation(LocationModel l) {
        if (l != null) {
            if (!location.contains(l)) {
                location.add(l);
            } else {
                System.out.println("La location existe déjà dans le client.");
            }
        }
    }
    // fais la methode Supprimer une location avec synchronisation avec le scooter (supprime la location du scooter aussi)
    public void supprimerLocation(LocationModel l) {
        if (l != null) {
            if (location.contains(l)) {
                l.getScooter().supprimerLocation(l);
            } else {
                System.out.println("La location n'existe pas dans le client.");
            }
        }
    }
    public String toString() {
        return "Client N°" + id_client + " | " +
                " nom :" + nom + " | " +
                " Prenom :" + prenom +" | "+
                " Date de naissance :" + getDateDeNaissance() +'|'+
                " Mail :" + mail +" | "+
                " Permis :" + permis +" | "+
                " parc :" + parc.getAdresse() ;
    }



}