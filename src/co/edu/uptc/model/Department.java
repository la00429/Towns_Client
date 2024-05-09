package co.edu.uptc.model;

import co.edu.uptc.structures.BinaryTree;

import java.util.Comparator;

public class Department {
    private String name;
    private BinaryTree<Township> townships;

    public Department(String name) {
        this.name = name;
        this.townships = new BinaryTree<>(Comparator.comparing(Township::getName));
    }

    public boolean addInhabitantInTown(String townshipName, Inhabitant inhabitant) {
        boolean added = false;
        if(searchTownship(townshipName) == null){
            Township township = new Township(townshipName.toUpperCase().replace(" ", ""));
            this.townships.insert(township);
            added = township.addInhabitant(inhabitant);
        }else {
            added = searchTownship(townshipName).addInhabitant(inhabitant);
        }
        return added;
    }

    public String calculateMostPopulus(){
        return this.townships.preOrder().stream().max(Comparator.comparing(Township::calculateNumberInhabitants)).get().getName();
    }

    public Township searchTownship(String townshipName) {
        return this.townships.searchData(new Township(townshipName.toUpperCase().replace(" ", "")));
    }

    public String getName() {
        return name;
    }
}
