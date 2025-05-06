package Model;
import java.text.SimpleDateFormat;
import java.util.*;

public class LocationModel {
    private Date dateDebut;
    private Date dateFin;
    private ClientModel client;
    private ScooterModel scooter;
    private RetourModel retour;

    public LocationModel(Date dateDebut, Date dateFin, ClientModel client, ScooterModel scooter) {
        if (dateDebut == null || dateFin == null || client == null || scooter == null) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être nuls.");
        }
        if (dateDebut.after(dateFin)) {
            throw new IllegalArgumentException("La date de début ne peut pas être après la date de fin.");
        }
        if (!scooter.isDisponible()) {
            throw new IllegalArgumentException("Le scooter n'est pas disponible.");
        }
        if (dateFin.before(new Date())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être dans le passé.");
        }

        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.client = client;
        this.scooter = scooter;
        scooter.ajouterLocation(this);
        client.ajouterLocation(this);
        scooter.setDisponible(false);
    }
    public void setClient(ClientModel client) {
        this.client = client;
    }

    public ClientModel getClient() {
        return client;
    }
    public void setScooter(ScooterModel scooter) {
        this.scooter = scooter;
    }
    public ScooterModel getScooter() {
        return scooter;
    }
    public void setRetour(RetourModel retour) {
        if (retour != null) {
            this.retour = retour;
            scooter.setKilometrage(retour.getKilometrage_ajouter() + scooter.getKilometrage());
            scooter.setDisponible(true);
        } else {
            System.out.println("Le retour ne peut pas être nul.");
        }
    }
    public void supprimerRetour() {
        if (retour != null) {
            retour = null;
            scooter.setDisponible(false);
        } else {
            System.out.println("Aucun retour à supprimer.");
        }
    }
    public RetourModel getRetour() {
        return retour;
    }
    public void setDateDebut(Date dateDebut) {
        if (dateDebut != null && dateDebut.after(dateFin)) {
            throw new IllegalArgumentException("La date de début ne peut pas être après la date de fin.");
        }
        this.dateDebut = dateDebut;
    }
    public void setDateFin(Date dateFin) {
        if (dateFin != null && dateFin.before(dateDebut)) {
            throw new IllegalArgumentException("La date de fin ne peut pas être avant la date de début.");
        }
        this.dateFin = dateFin;
    }
    public Date getDateDebut() {
        return dateDebut;
    }
    public Date getDateFin() {
        return dateFin;
    }

    public double calculerPenalite() {
        if (retour == null){
            return 0;
        }
       if (retour.getDateR().after(dateFin)) {
            long diff = retour.getDateR().getTime() - dateFin.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            return (diffDays * scooter.getPrixJ() * 1.4); // Pénalité de 40%
        }
        return 0;
    }
    public double calculerMontant() {
        long diff = dateFin.getTime() - dateDebut.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays * scooter.getPrixJ()+ calculerPenalite(); // Montant total
    }

    public String toString() {
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return 
                "Client :" + client.getNom() +" "+ client.getPrenom()+ '\n' +
                " Scooter :" + scooter.getNumero_identification() +'\n'+
                " Date debut  :" + dateFormat.format(dateDebut) +'\n'+
                " Date fin :" + dateFormat.format(dateFin) +'\n'+
                " Retour :" + (retour != null ? "Deja retourné" : "Aucun retour") +'\n';
    }



}