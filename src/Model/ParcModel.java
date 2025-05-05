package Model;
import java.util.*;


public class ParcModel {
    
    
    private String nom;
    private String adresse;
    private Vector<ScooterModel> scooters = new Vector<ScooterModel>();
    private Vector<ClientModel> clients= new Vector<ClientModel>();

    public ParcModel(String n, String a) {
        nom = n;
        adresse = a;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getAdresse() {
        return adresse;
    }
    
    public void setScooters (Vector <ScooterModel> s) {
        scooters = s;
    }
    public void setClients (Vector <ClientModel> c) {
        clients = c;
    }
    
    public Vector<ScooterModel> getScooters() {
        return scooters;
    }
    public Vector<ClientModel> getClients() {
        return clients;
    }
   
    public void ajouterScooter(ScooterModel s) {
        if (s != null) {
            if (!scooters.contains(s)) {
                scooters.add(s);
            } else {
                System.out.println("Le scooter existe déjà dans le parc.");
            }
        }
    }
    public  void ajouterClient(ClientModel c) {
        if (c != null) {
            if (!clients.contains(c)) {
                clients.add(c);
            } else {
                System.out.println("Le client existe déjà dans le parc.");
            }
        }
    }
    public ScooterModel rechercherScooter(ScooterModel s) {
        if (s != null) {
            for (ScooterModel scooter : scooters) {
                if (scooter.equals(s)) {
                    return scooter;
                }
            }
        }
        return null;
    }
    public ScooterModel rechercherScooterId(String id) {
        if (id != null) {
            for (ScooterModel scooter : scooters) {
                if (scooter.getNumero_identification().equals(id)) {
                    return scooter;
                }
            }
        }
        return null;
    }
    public ClientModel rechercherClient(ClientModel c) {
        if (c != null) {
            for (ClientModel client : clients) {
                if (client.equals(c)) {
                    return client;
                }
            }
        }
        return null;
    }
    public ClientModel rechercherClientId(String id) {
        if (id != null) {
            for (ClientModel client : clients) {
                if (client.getId_client().equals(id)) {
                    return client;
                }
            }
        }
        return null;
    }
    
    public void supprimerScooter(ScooterModel s) {
        if (s != null) {
            if (scooters.contains(s)) {
                // Créer une copie pour éviter ConcurrentModificationException
                Vector<LocationModel> locationsCopy = new Vector<>(s.getLocation());
                for (LocationModel loc : locationsCopy) {
                    s.supprimerLocation(loc);
                }
                scooters.remove(s); // Supprimer le scooter du parc
            } else {
                System.out.println("Le scooter n'existe pas dans le parc.");
            }
        }
    }
    public void supprimerClient(ClientModel c) {
        if (c != null) {
            if (clients.contains(c)) {
                // Créer une copie pour éviter ConcurrentModificationException
                Vector<LocationModel> locationsCopy = new Vector<>(c.getLocation());
                for (LocationModel loc : locationsCopy) {
                    c.supprimerLocation(loc);
                }
                clients.remove(c); // Supprimer le client du parc
            } else {
                System.out.println("Le client n'existe pas dans le parc.");
            }
        }
    }
    public Vector<ScooterModel> getScootersDispo() {
        Vector<ScooterModel> scootersDisponibles = new Vector<>();
        for (ScooterModel scooter : scooters) {
            if (scooter.isDisponible()) {
                scootersDisponibles.add(scooter);
            }
        }
        return scootersDisponibles;
    }
    public String toString() {
        return "Parc : " + nom + "\n" +
                "Adresse : " + adresse + "\n" +
                "Nombre de scooters : " + scooters.size() + "\n" +
                "Nombre de clients : " + clients.size();
    }


}