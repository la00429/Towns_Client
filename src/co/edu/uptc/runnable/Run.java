package co.edu.uptc.runnable;

import co.edu.uptc.presenter.Presenter;

public class Run {
    public static void main(String[] args) {
        Presenter presenter = new Presenter();
        presenter.loadData();
        presenter.run();
    }
}
