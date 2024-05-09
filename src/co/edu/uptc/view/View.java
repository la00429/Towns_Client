package co.edu.uptc.view;

import java.util.Scanner;

public class View {
    private Scanner scanner;

    public View() {
        this.scanner = new Scanner(System.in);
    }

    public String readData(String message) {
        showMessage(message);
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String showMenu(String nameDepartment) {
        return "Department: " + nameDepartment + "\n" +
                "1. Add information (name of Town, name of Inhabitant, id of Inhabitant)" + "\n" +
                "2. Print list of inhabitants of a town" + "\n" +
                "3. Town with the most inhabitants" + "\n" +
                "0. Exit\n" +
                "Input Option: ";
    }
}
