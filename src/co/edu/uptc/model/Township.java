package co.edu.uptc.model;

import co.edu.uptc.structures.AVLTree;

import java.util.Comparator;
import java.util.List;

public class Township {
    private String name;
    private AVLTree<Inhabitant> inhabitants;

    public Township(String name) {
        this.name = name;
        this.inhabitants = new AVLTree<>(Comparator.comparing(Inhabitant::getName));
    }

    public boolean addInhabitant(Inhabitant inhabitant) {
        boolean added = false;
        if(inhabitants.searchData(inhabitant) == null){
            added = inhabitants.insert(inhabitant);
        }
        return added;
    }

    public String getName() {
        return name;
    }

    public List <Inhabitant> getInhabitants() {
        return this.inhabitants.inOrder();
    }

    public int calculateNumberInhabitants() {
        return this.inhabitants.preOrder().size();
    }







}
