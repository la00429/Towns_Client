package co.edu.uptc.net;

public class Request {
    private String option;
    private String nameTown;
    private String nameInhabitant;
    private String idInhabitant;

    public Request(String option, String nameTown, String nameInhabitant, String idInhabitant) {
        this.option = option;
        this.nameTown = nameTown;
        this.nameInhabitant = nameInhabitant;
        this.idInhabitant = idInhabitant;
    }

    public Request(String option, String nameTown) {
        this.option = option;
        this.nameTown = nameTown;
    }

    public Request(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public String getNameTown() {
        return nameTown;
    }

    public String getNameInhabitant() {
        return nameInhabitant;
    }

    public String getIdInhabitant() {
        return idInhabitant;
    }
}
