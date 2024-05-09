package co.edu.uptc.structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinaryTree<T> {
    private NodeDouble<T> root;
    private Comparator<T> comparator;


    public BinaryTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public boolean insert(T value) {
        return insert(this.root, value);
    }

    public boolean insert(NodeDouble<T> node, T value) {
        NodeDouble<T> nodeNew = new NodeDouble<T>(value);
        boolean inserted = false;
        if (isEmpty()) {
            this.root = nodeNew;
            inserted = true;
        } else {
            inserted = insert(node, value, nodeNew);
        }
        return inserted;
    }

    private boolean insert(NodeDouble<T> node, T value, NodeDouble<T> nodeNew) {
        boolean inserted = false;
        if(comparator.compare(value, node.getData()) != 0){
            if (comparator.compare(value, node.getData()) > 0) {
                inserted = insertRight(node, value, nodeNew);
            } else {
                if (comparator.compare(value, node.getData()) < 0) {
                    inserted = insertLeft(node, value, nodeNew);
                }
            }
        }
        return inserted;
    }

    private boolean insertRight(NodeDouble<T> node, T value, NodeDouble<T> nodeNew) {
        boolean inserted = false;
        if (node.getRight() == null) {
            node.setRight(nodeNew);
            inserted = true;
        } else {
            insert(node.getRight(), value);
        }
        return inserted;
    }

    private boolean insertLeft(NodeDouble<T> node, T value, NodeDouble<T> nodeNew) {
        boolean inserted = false;
        if (node.getLeft() == null) {
            node.setLeft(nodeNew);
            inserted = true;
        } else {
            insert(node.getLeft(), value);
        }
        return inserted;
    }

    public T searchData(T value) {
        return findData(root, value);
    }

    public T findData(NodeDouble<T> current, T value) {
        NodeDouble<T> nodeFound = new NodeDouble<T>(null);
        T dataFound = nodeFound.getData();
        if (current != null) {
            if (comparator.compare(value, current.getData()) == 0) {
                dataFound = current.getData();
            } else {
                if (comparator.compare(value, current.getData()) > 0) {
                    dataFound = findData(current.getRight(), value);
                } else {
                    dataFound = findData(current.getLeft(), value);
                }
            }
        }
        return dataFound;
    }

    public void remove(T value) throws Exception{
        remove(this.root, value);
    }

    public void remove(NodeDouble<T> current, T value)  {
        if (current == null) {
            throw new RuntimeException("Element not found");
        }
        if (comparator.compare(value, current.getData()) == 0) {
            current = remplace(current);
        } else {
            if (comparator.compare(value, current.getData()) > 0) {
                remove(current.getRight(), value);
            } else {
                remove(current.getLeft(), value);
            }
        }

    }

    private NodeDouble<T> remplace(NodeDouble<T> current) {
        NodeDouble<T> aux = current.getLeft();
        while (aux.getRight() != null) {
            aux = aux.getRight();
            current.setData(aux.getData());
        }
        aux= remplace(aux, current);
        return aux;
    }

    public NodeDouble<T> remplace(NodeDouble<T> aux, NodeDouble<T> current) {
        if (aux.equals(current)) {
            aux.setLeft(aux.getLeft());
        } else {
            aux.setRight(aux.getLeft());
        }
        return aux;
    }

    public List<T> inOrder() {
        List<T> list = new ArrayList<T>();
        return inOrder(this.root, list);
    }

    private List<T> inOrder(NodeDouble<T> node, List<T> list) {
        if (node != null) {
            inOrder(node.getLeft(), list);
            list.add(node.getData());
            inOrder(node.getRight(), list);
        }
        return list;
    }


    public List<T> preOrder() {
        List<T> list = new ArrayList<T>();
        return preOrder(this.root, list);
    }

    public List<T> preOrder(NodeDouble<T> node, List<T> list) {

        if (node != null) {
            list.add(node.getData());
            preOrder(node.getLeft(), list);
            preOrder(node.getRight(), list);
        }
        return list;
    }

    public List<T> postOrder() {
        List<T> list = new ArrayList<T>();
        return postOrder(this.root, list);
    }

    public List<T> postOrder(NodeDouble<T> node, List<T> list) {
        if (node != null) {
            postOrder(node.getLeft(), list);
            postOrder(node.getRight(), list);
            list.add(node.getData());
        }
        return list;
    }
}
