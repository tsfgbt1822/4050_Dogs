package assignment.dogs;

public class OrderedDictionary implements OrderedDictionaryADT {

    Node root;

    OrderedDictionary() {
        root = new Node();
    }

    /**
     * Returns the Record object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/dogs/DictionaryException.java
     */
    @Override
    public DogRecord find(DataKey k) throws DictionaryException {
        Node current = root;
        int comparison;
        if (root.isEmpty()) {         
            throw new DictionaryException("There is no record matches the given key");
        }

        while (true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison == 0) { // key found
                return current.getData();
            }
            if (comparison == 1) {
                if (current.getLeftChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getLeftChild();
            } else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getRightChild();
            }
        }

    }

    /**
     * Inserts r into the ordered dictionary. It throws a DictionaryException if
     * a record with the same key as r is already in the dictionary.
     *
     * @param r
     * @throws DictionaryException
     */
    @Override
    public void insert(DogRecord r) throws DictionaryException {
        root = insertRec(root, r);
    }

    private Node insertRec(Node current, DogRecord r) throws DictionaryException {
        if (current == null || current.isEmpty()) {
            return new Node(r); // Creates a new node with the DogRecord
        }

        int comparison = current.getData().getDataKey().compareTo(r.getDataKey());
        if (comparison == 0 && !current.isEmpty()) {
            throw new DictionaryException("A record with the same key already exists");
        } else if (comparison > 0) {
            current.setLeftChild(insertRec(current.getLeftChild(), r));
        } else {
            current.setRightChild(insertRec(current.getRightChild(), r));
        }

        return current;
    }

    /**
     * Removes the record with Key k from the dictionary. It throws a
     * DictionaryException if the record is not in the dictionary.
     *
     * @param k
     * @throws DictionaryException
     */
    @Override
    public void remove(DataKey k) throws DictionaryException {
        root = removeRec(root, k);
    }

    private Node removeRec(Node current, DataKey k) throws DictionaryException {
        if (current == null) {
            throw new DictionaryException("No such record key exists");
        }

        int comparison = current.getData().getDataKey().compareTo(k);
        if (comparison == 0) { // Node to be deleted found
            // Node with only one child or no child
            if (current.getLeftChild() == null)
                return current.getRightChild();
            else if (current.getRightChild() == null)
                return current.getLeftChild();

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            DogRecord successorData = smallest(current.getRightChild()).getData();
            current.setData(successorData);
            // Delete the inorder successor
            current.setRightChild(removeRec(current.getRightChild(), successorData.getDataKey()));
        } else if (comparison > 0) {
            current.setLeftChild(removeRec(current.getLeftChild(), k));
        } else {
            current.setRightChild(removeRec(current.getRightChild(), k));
        }

        return current;
    }

    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws DictionaryException
     */
    @Override
    public DogRecord successor(DataKey k) throws DictionaryException {
        Node successor = null;
        Node current = root;
        while (current != null) {
            int comparison = current.getData().getDataKey().compareTo(k);
            if (comparison > 0) {
                successor = current;
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }

        if (successor == null) {
            throw new DictionaryException("There is no successor for the given record key");
        }

        return successor.getData();
    }

   
    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * largest key smaller than k; it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws DictionaryException
     */
    @Override
    public DogRecord predecessor(DataKey k) throws DictionaryException {
        Node predecessor = null;
        Node current = root;
        while (current != null) {
            int comparison = current.getData().getDataKey().compareTo(k);
            if (comparison < 0) {
                predecessor = current;
                current = current.getRightChild();
            } else {
                current = current.getLeftChild();
            }
        }

        if (predecessor == null) {
            throw new DictionaryException("There is no predecessor for the given record key");
        }

        return predecessor.getData();
    }

    /**
     * Returns the record with smallest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     *
     * @return
     */
    @Override
    public DogRecord smallest() throws DictionaryException {
        if (isEmpty()) {
            throw new DictionaryException("Dictionary is empty");
        }
        return smallest(root).getData();
    }

    private Node smallest(Node root) {
        Node current = root;
        while (current.hasLeftChild()) {
            current = current.getLeftChild();
        }
        return current;
    }

    /*
	 * Returns the record with largest key in the ordered dictionary. Returns
	 * null if the dictionary is empty.
     */
    @Override
    public DogRecord largest() throws DictionaryException {
        if (isEmpty()) {
            throw new DictionaryException("Dictionary is empty");
        }
        return largest(root).getData();
    }

    private Node largest(Node root) {
        Node current = root;
        while (current.hasRightChild()) {
            current = current.getRightChild();
        }
        return current;
    }
      
    /* Returns true if the dictionary is empty, and true otherwise. */
    @Override
    public boolean isEmpty (){
        return root.isEmpty();
    }
}

