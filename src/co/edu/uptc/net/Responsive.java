package co.edu.uptc.net;

import co.edu.uptc.model.Inhabitant;

import java.util.List;

public class Responsive {
    private String message;
    private String town;
    private List<Inhabitant> inhabitants;
    private Boolean verification;

    public Responsive(String message, String town, List<Inhabitant> inhabitants, Boolean verification) {
        this.message = message;
        this.town = town;
        this.inhabitants = inhabitants;
        this.verification = verification;
    }

    public Responsive(String town, List<Inhabitant> inhabitants, Boolean verification) {
        this.town = town;
        this.inhabitants = inhabitants;
        this.verification = verification;
    }

    public Responsive(String message) {
        this.message = message;
    }

    public Responsive(Boolean verification) {
        this.verification = verification;
    }

    public String getMessage() {
        return message;
    }

    public String getTown() {
        return town;
    }

    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    public Boolean getVerification() {
        return verification;
    }
}
