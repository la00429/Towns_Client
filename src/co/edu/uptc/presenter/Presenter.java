package co.edu.uptc.presenter;

import co.edu.uptc.model.Inhabitant;
import co.edu.uptc.net.Request;
import co.edu.uptc.net.Responsive;
import co.edu.uptc.view.View;
import co.edu.uptc.net.Connection;
import com.google.gson.Gson;

import java.io.IOException;

public class Presenter {
    private Connection connection;
    private View view;

    public Presenter() {
        try {
            this.connection = new Connection();
            this.view = new View();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

    }

    public int showMenu() {
        int option = 0;
        try {
            option = Integer.parseInt(view.readData(view.showMenu()));
        } catch (NumberFormatException e) {
            view.showMessage("Input a valid option");
        }
        return option;
    }

    public void start() throws IOException {
        establishConnection();
        int option = 0;
        do {
            option = showMenu();
            switch (option) {
                case 1:
                    addInhabitant();
                    break;
                case 2:
                    showInhabitants();
                    break;
                case 3:
                    calculateMostPopulous();
                    break;
                case 4:
                    calculateMinorPopulous();
                    break;
                case 6:
                    closeConnection();
                    break;
            }
        } while (option != 6);
    }

    private void establishConnection() throws IOException {
        try {
            connection.connect();
            view.showMessage(new Gson().fromJson(connection.receive(), Responsive.class).getMessage());
            connection.send(new Gson().toJson(new Responsive("Client connected to server")));
        } catch (IOException e) {
            view.showMessage(e.getMessage());
        }
    }

    private void addInhabitant() throws IOException {
        sendDataInhabitant();
        Responsive response = new Gson().fromJson(connection.receive(), Responsive.class);
        if (response.getVerification()) {
            view.showMessage("Inhabitant added successfully");
        } else {
            view.showMessage("The inhabitant already exists");
        }
    }

    private void sendDataInhabitant() throws IOException {
        String town = view.readData("Input the name of the town: ");
        String name = view.readData("Input the name of the inhabitant: ");
        String id = view.readData("Input the id of the inhabitant: ");
        connection.send(new Gson().toJson(new Request("1", town, name, id)));
    }

    private void showInhabitants() throws IOException {
        sendNameTown();
        Responsive response = new Gson().fromJson(connection.receive(), Responsive.class);
        verificationTown(response);
    }

    private void sendNameTown() throws IOException {
        String town = view.readData("Input the name of the town: ");
        connection.send(new Gson().toJson(new Request("2", town)));
    }

    private void verificationTown(Responsive response) {
        if (response.getVerification()) {
            view.showMessage("The town does not exist");
        } else {
            showEachInhabitant(response);
        }
    }

    private void showEachInhabitant(Responsive response) {
        view.showMessage("Inhabitants of the town: " + response.getTown());
        for (Inhabitant inhabitant : response.getInhabitants()) {
            view.showMessage("Inhabitant: " + inhabitant.getName() + " ID: " + inhabitant.getId());
        }
    }


    private void calculateMostPopulous() throws IOException {
        connection.send(new Gson().toJson(new Request("3")));
        view.showMessage("The town with the most inhabitants is: " + new Gson().fromJson(connection.receive(), Responsive.class).getMessage());
    }

    private void calculateMinorPopulous() throws IOException {
        connection.send(new Gson().toJson(new Request("4")));
        view.showMessage("The town with the least inhabitants is: " + new Gson().fromJson(connection.receive(), Responsive.class).getMessage());
    }

    private void closeConnection() throws IOException {
        connection.send(new Gson().toJson(new Request("6")));
        view.showMessage(new Gson().fromJson(connection.receive(), Responsive.class).getMessage());
        connection.disconnect();
    }
}
