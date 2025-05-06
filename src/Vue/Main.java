package Vue;
import Model.*;
import java.util.*;

public class Main {
public static void main(String[] args) {
        
    ParcModel parc = new ParcModel("Parc1", "adresse1");
    AccueilVue v1 = new AccueilVue(parc);
    ScooterModel s1 = new ScooterModel("modele1", true, "123", 1000, parc, 10);
    ScooterModel s2 = new ScooterModel("modele2", true, "456", 2000, parc, 20);
    parc.ajouterScooter(s1);
    parc.ajouterScooter(s2);
    Calendar cal1 = Calendar.getInstance();
    cal1.set(2004, Calendar.JULY, 24); 
    Date date1 = cal1.getTime();

    Calendar cal2 = Calendar.getInstance();
    cal2.set(2005, Calendar.FEBRUARY, 22); 
    Date date2 = cal2.getTime();

    ClientModel c1 = new ClientModel("nom1", "prenom1", date1, "id1", "mail1", "A1", parc);
    ClientModel c2 = new ClientModel("nom2", "prenom2", date2, "id2", "mail2", "A2", parc);
    parc.ajouterClient(c1);
    parc.ajouterClient(c2);

    // cree deux dates pour la location
    Calendar cal3 = Calendar.getInstance();
    cal3.set(2025, Calendar.MAY, 5);
    Date dateDebut = cal3.getTime();
    Calendar cal4 = Calendar.getInstance();
    cal4.set(2025, Calendar.MAY, 7);
    Date dateFin = cal4.getTime();
    LocationModel l1 = new LocationModel( dateDebut, dateFin, c1,s1);
    

    }
}


