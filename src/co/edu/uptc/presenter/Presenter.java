package co.edu.uptc.presenter;

import co.edu.uptc.model.Department;
import co.edu.uptc.model.Inhabitant;
import co.edu.uptc.view.View;

public class Presenter {
    private Department department;
    private View view;

    public Presenter() {
        department = new Department("BOYACA");
        view = new View();
    }

    public void loadData(){

        department.addInhabitantInTown("Tunja", new Inhabitant("1057978056", "Monica"));
        department.addInhabitantInTown("Villa De Leyva", new Inhabitant("1057978057", "Juan"));
        department.addInhabitantInTown("Duitama", new Inhabitant("1057978058", "Pedro"));
        department.addInhabitantInTown("Sogamoso", new Inhabitant("1057978059", "Luis"));
        department.addInhabitantInTown("Paipa", new Inhabitant("1057978060", "Carlos"));
        department.addInhabitantInTown("Tunja", new Inhabitant("1057978061", "Andres"));
        department.addInhabitantInTown("Villa De Leyva", new Inhabitant("1057978062", "Maria"));
        department.addInhabitantInTown("Duitama", new Inhabitant("1057978063", "Sofia"));
        department.addInhabitantInTown("Sogamoso", new Inhabitant("1057978064", "Camila"));
        department.addInhabitantInTown("Paipa", new Inhabitant("1057978065", "Laura"));
        System.out.println("Data loaded successfully");
    }


    public int showMenu()  {
        int option = 0;
        try {
            option = Integer.parseInt(view.readData(view.showMenu(department.getName())));
        } catch (NumberFormatException e) {
            view.showMessage("Input a valid option");
            run();
        }
        return option;
    }

    public void run() {

            int option = showMenu();
            switch (option) {
                case 1:
                    addInhabitant();
                    break;
                case 2:
                    showInhabitants();
                    break;
                case 3:
                    calculateMostPopulus();
                    break;
                case 0:
                    view.showMessage("Finishing program");
                    System.exit(0);
                    break;
                default:
                    view.showMessage("Input a valid option");
                    break;
            }
            do {
                run();
            } while (option != 4);


    }

    private void addInhabitant() {
        String town = view.readData("Input the name of the town: ");
        String name = view.readData("Input the name of the inhabitant: ");
        String id = view.readData("Input the id of the inhabitant: ");
        boolean verification =  department.addInhabitantInTown(town, new Inhabitant(id, name.toUpperCase().replace(" ", "")));
        if (verification) {
            view.showMessage("Inhabitant added successfully");
        } else {
            if(department.searchTownship(town) == null) {
                view.showMessage("The town does not exist");
            } else {
                view.showMessage("The inhabitant already exists");
            }
        }
    }

    private void showInhabitants() {
        String town = view.readData("Input the name of the town: ");
        if (department.searchTownship(town) == null) {
            view.showMessage("The town does not exist");
        }else {
            view.showMessage("Inhabitants of the town: " + department.searchTownship(town).getName());
            for (Inhabitant inhabitant : department.searchTownship(town).getInhabitants()) {
                view.showMessage("Inhabitant: " + inhabitant.getName() + " ID: " + inhabitant.getId());
            }
        }

    }

    private void calculateMostPopulus() {
        view.showMessage("The town with the most inhabitants is: " + department.calculateMostPopulus());
    }


}
