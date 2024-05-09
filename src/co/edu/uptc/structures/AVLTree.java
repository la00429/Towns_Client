package co.edu.uptc.structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AVLTree<T> {
    private NodeDouble<T> root;
    private Comparator<T> comparator;

    public AVLTree(Comparator<T> comparator) {
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
            inserted = insertRight(node, nodeNew);
        } else {
            insert(node.getRight(), value);
        }
        return inserted;
    }

    private boolean insertRight(NodeDouble<T> node, NodeDouble<T> nodeNew) {
        node.setRight(nodeNew);
        node.setFactorEquilibrium(node.getFactorEquilibrium() + 1);
        updateFactorEquilibrium(node);
        equilibrate(node.getRight());
        return true;
    }

    private boolean insertLeft(NodeDouble<T> node, T value, NodeDouble<T> nodeNew) {
        boolean inserted = false;
        if (node.getLeft() == null) {
            insertLeft(node, nodeNew);
            inserted = true;
        } else {
           inserted = insert(node.getLeft(), value);
        }
        return inserted;
    }

    private boolean insertLeft(NodeDouble<T> node, NodeDouble<T> nodeNew) {
        node.setLeft(nodeNew);
        node.setFactorEquilibrium(node.getFactorEquilibrium() - 1);
        updateFactorEquilibrium(node);
        equilibrate(node.getLeft());
        return true;
    }

    public void updateFactorEquilibrium(NodeDouble<T> node) {
        while (node.getData() != this.root.getData() && (node.getFactorEquilibrium() != 2 && node.getFactorEquilibrium() != -2)) {
            NodeDouble<T> nodeFather = (searchFather(node).getData() == this.root) ? null : searchFather(node);
            if (nodeFather.getData() != null) {
                nodeFather.setFactorEquilibrium(calculateHeightTree(nodeFather.getRight()) - calculateHeightTree(nodeFather.getLeft()));
                node = nodeFather;
            } else {
                node = (nodeFather.getData() == this.root.getData()) ? nodeFather : null;
            }
        }
    }

    private int calculateHeightTree(NodeDouble<T> node) {
        int height = 0;
        if (node != null) {
            height = 1 + Math.max(calculateHeightTree(node.getLeft()), calculateHeightTree(node.getRight()));
        }
        return height;
    }


    public NodeDouble<T> searchFather(NodeDouble<T> nodeChild) {
        return searchFather(this.root, nodeChild);
    }

    public NodeDouble<T> searchFather(NodeDouble<T> node, NodeDouble<T> nodeChild) {
        NodeDouble<T> nodeFather = new NodeDouble<T>(null);
        if (node != null && nodeChild != null) {
            nodeFather = (comparator.compare(nodeChild.getData(), node.getData()) == 0) ? node : (node.getLeft() != null && node.getLeft().getData().equals(nodeChild.getData())) ? node : node.getRight() != null && node.getRight().getData().equals(nodeChild.getData()) ? node : (comparator.compare(nodeChild.getData(), node.getData()) > 0) ? searchFather(node.getRight(), nodeChild) : searchFather(node.getLeft(), nodeChild);
        }
        return nodeFather;
    }


    private void equilibrate(NodeDouble<T> nodeNew) {
        NodeDouble<T> rootSubTree = searchRootSubTree(nodeNew);
        if (rootSubTree != null) {
            searchRotation(rootSubTree);
        }
    }

    public void searchRotation(NodeDouble<T> rootSubTree){
        switch (rootSubTree.getFactorEquilibrium()) {
            case 2:
                rotationSubTreeRight(rootSubTree);
                break;
            case -2:
                rotationSubTreLeft(rootSubTree);
                break;
        }
    }

    public void rotationSubTreeRight(NodeDouble<T> rootSubTree){
        if (rootSubTree.getRight().getFactorEquilibrium() == 1) {
            rotationSimpleDD(rootSubTree);
        } else {
            rotationDoubleID(rootSubTree);
        }
    }

    public void rotationSubTreLeft(NodeDouble<T> rootSubTree){
        if (rootSubTree.getLeft().getFactorEquilibrium() == -1) {
            rotationSimpleII(rootSubTree);
        } else {
            rotationDoubleDI(rootSubTree);
        }
    }

    public void rotationSimpleDD(NodeDouble<T> rootSubTree){
        if (rootSubTree == this.root) {
            this.root = rotationSimpleDD(rootSubTree, rootSubTree.getRight());
        } else {
            searchFather(rootSubTree).setRight(rotationSimpleDD(rootSubTree, rootSubTree.getRight()));
        }
    }

    public void rotationDoubleID(NodeDouble<T> rootSubTree){
        if (rootSubTree == this.root) {
            this.root = rotationDoubleID(rootSubTree, rootSubTree.getRight());
        } else {
            searchFather(rootSubTree).setRight(rotationDoubleID(rootSubTree, rootSubTree.getRight()));
        }
    }

    public void rotationSimpleII(NodeDouble<T> rootSubTree){
        if(rootSubTree == this.root) {
            this.root = rotationSimpleII(rootSubTree, rootSubTree.getLeft());
        } else {
            searchFather(rootSubTree).setLeft(rotationSimpleII(rootSubTree, rootSubTree.getLeft()));
        }
    }

    public void rotationDoubleDI(NodeDouble<T> rootSubTree){
        if (rootSubTree == this.root) {
            this.root = rotationDoubleDI(rootSubTree, rootSubTree.getLeft());
        } else {
            searchFather(rootSubTree).setLeft(rotationDoubleDI(rootSubTree, rootSubTree.getLeft()));
        }
    }

    private NodeDouble<T> searchRootSubTree(NodeDouble<T> nodeNew){
        NodeDouble<T> rootSubTree = nodeNew;
        while (rootSubTree!= null && rootSubTree.getFactorEquilibrium() != 2 && rootSubTree.getFactorEquilibrium() != -2 && rootSubTree != this.root) {
            rootSubTree = (searchFather(rootSubTree) == null) ? null : searchFather(rootSubTree);
        }
        return rootSubTree;
    }

    private NodeDouble<T> rotationSimpleII(NodeDouble<T> nodeProblem, NodeDouble<T> nodeReference) {
        nodeProblem.setLeft(nodeReference.getRight());
        nodeReference.setRight(nodeProblem);
        nodeProblem.setFactorEquilibrium(nodeProblem.getFactorEquilibrium() + 2);
        nodeReference.setFactorEquilibrium(nodeReference.getFactorEquilibrium() + 1);
        nodeProblem = nodeReference;

        return nodeProblem;
    }


    private NodeDouble<T> rotationSimpleDD(NodeDouble<T> nodeProblem, NodeDouble<T> nodeReference) {
        nodeProblem.setRight(nodeReference.getLeft());
        nodeReference.setLeft(nodeProblem);
        nodeProblem.setFactorEquilibrium(nodeProblem.getFactorEquilibrium() - 2);
        nodeReference.setFactorEquilibrium(nodeReference.getFactorEquilibrium() - 1);
        nodeProblem = nodeReference;

        return nodeProblem;
    }

    private NodeDouble<T> rotationDoubleID(NodeDouble<T> nodeProblem, NodeDouble<T> nodeReference) {
        NodeDouble<T> nodeDescendant = nodeReference.getLeft();
        nodeProblem.setRight(nodeDescendant.getLeft());
        nodeDescendant.setLeft(nodeProblem);
        nodeReference.setLeft(nodeDescendant.getRight());
        nodeDescendant.setRight(nodeReference);
        nodeProblem.setFactorEquilibrium(nodeProblem.getFactorEquilibrium() - 2);
        nodeReference.setFactorEquilibrium(nodeReference.getFactorEquilibrium() + 1);
        nodeProblem = nodeDescendant;

        return nodeProblem;
    }

    private NodeDouble<T> rotationDoubleDI(NodeDouble<T> nodeProblem, NodeDouble<T> nodeReference) {
        NodeDouble<T> nodeDescendant = nodeReference.getRight();
        nodeProblem.setLeft(nodeDescendant.getRight());
        nodeDescendant.setRight(nodeProblem);
        nodeReference.setRight(nodeDescendant.getLeft());
        nodeDescendant.setLeft(nodeReference);
        nodeProblem.setFactorEquilibrium(nodeProblem.getFactorEquilibrium() + 2);
        nodeReference.setFactorEquilibrium(nodeReference.getFactorEquilibrium() - 1);
        nodeProblem = nodeDescendant;
        return nodeProblem;
    }


    public boolean remove(T value) throws Exception {
        return remove(this.root, value);
    }

    private boolean remove(NodeDouble<T> current, T value) {
        boolean removed = false;
        if (current != null) {
           removed = searchRemove(current, value);
        }
        return removed;
    }

    private boolean searchRemove(NodeDouble<T> current, T value){
        boolean removed = false;
        if (comparator.compare(value, current.getData()) == 0) {
            current = remove(current);
            removed = true;
        } else {
            if (comparator.compare(value, current.getData()) > 0) {
                remove(current.getRight(), value);
            } else {
                remove(current.getLeft(), value);
            }
        }
        return removed;
    }

    private NodeDouble<T> remove(NodeDouble<T> current){
       current= remplace(current);
        updateFactorEquilibrium(this.root);
        equilibrate(current);
        return current;
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

    public T searchData(T value) {
        return findData(root, value);
    }

    private T findData(NodeDouble<T> current, T value) {
        T dataFound = new NodeDouble<T>(null).getData();
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

    private List<T> preOrder(NodeDouble<T> node, List<T> list) {

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

    private List<T> postOrder(NodeDouble<T> node, List<T> list) {
        if (node != null) {
            postOrder(node.getLeft(), list);
            postOrder(node.getRight(), list);
            list.add(node.getData());
        }
        return list;
    }
}
