import java.util.ArrayList;

public class HashtableImplementation<K, D> {

    public static final int DEFAULT_BUCKETSIZE = 2047;
    public static final float DEFAULT_LOADLIMIT = .75f;

    private ArrayList<HashNode<K, D>> table;
    private int size, bucketSize;
    private float loadLimit;


    public HashtableImplementation(int initialCapacity, float initialLoadLimit) {
        size = 0;
        bucketSize = initialCapacity;
        table = new ArrayList<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            table.add(null);
        }
        loadLimit = initialLoadLimit;
    }

    public HashtableImplementation(int initialSize) {
        this(initialSize, DEFAULT_LOADLIMIT);
    }

    public HashtableImplementation() {
        this(DEFAULT_BUCKETSIZE, DEFAULT_LOADLIMIT);
    }

    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    public D get(K key) {
        HashNode<K, D> node = getNode(key);
        if (node != null)
            return node.data;
        return null;
    }

    public void put(K key, D data) {
        //Overwrites data if exists
        HashNode<K, D> node = getNode(key);
        if (node != null) {
            node.data = data;
        }
        else {
            if (loadFactor() > loadLimit)
                reload();

            int index = getSlot(key);
            HashNode<K, D> appended = new HashNode<>(key, data, table.get(index));
            table.set(index, appended);
            size++;
        }
    }

    public D remove(K key) {
        int slot = getSlot(key);
        HashNode<K, D> head = table.get(slot);
        HashNode<K, D> proto = head;
        if (head.key.equals(key)) {
            table.set(slot, head.next);
            size--;
            return proto.data;
        }

        while(proto.next != null && !proto.next.key.equals(key)) {
            proto = proto.next;
        }

        HashNode<K, D> meta = proto.next;
        D temp = meta.data;
        meta = meta.next;
        proto.next = meta;
        size--;
        return temp;
    }



    /*
     * Everything past here are helper methods.
     */


    public int getSlot(K key) {
        return getSlot(table, key);
    }

    private int getSlot(ArrayList<HashNode<K, D>> list, K key) {
        return Math.abs(key.hashCode())%list.size();
    }

    public float loadFactor() {
        return (float)size/bucketSize;
    }

    private HashNode<K, D> getNode(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        for (HashNode<K, D> node = table.get(getSlot(key)); node != null; node = node.next) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    private void reload() {
        reload(getNextPrime(2*bucketSize));
    }

    private void reload(int tempSize) {
        ArrayList<HashNode<K, D>> temp = new ArrayList<>(tempSize);

        for (int i = 0; i < tempSize; i++)
            temp.add(null);

        for (int i = 0; i < table.size(); i++) {
            for (HashNode<K, D> node = table.get(i); node != null; node = node.next) {
                int index = getSlot(temp, node.key);
                HashNode<K, D> appended = new HashNode<>(node.key, node.data, temp.get(index));
                temp.set(index, appended);
            }
        }

        table = temp;
        bucketSize = table.size();

    }


    public static int getNextPrime(int value) {
        while (!isPrime(value)) {
            value++;
        }
        return value;
    }

    private static boolean isPrime(int value) {
        if (value <= 1 || (value > 2 && value%2 == 0))
            return false;
        int upper = (int)Math.sqrt(value);
        for (int i = 3; i <= upper; i+=2) {
            if (value%i == 0)
                return false;
        }
        return true;
    }

    public int bucketSize() {
        return bucketSize;
    }

    public int size() {
        return size;
    }

    /**
     * Could have used java.util.LinkedList but this is fine too
     * Used to store the data in separate-chained Hashtable
     * @param <K> Key type
     * @param <D> Data storage type
     */
    private class HashNode<K, D> {
        K key;
        D data;
        HashNode<K, D> next;

        private HashNode(K key, D data, HashNode<K, D> next) {
            this.key = key;
            this.data = data;
            this.next = next;
        }

        private HashNode(K key, D data) {
            this(key, data, null);
        }

        public int hashCode() {
            return key.hashCode();
        }

    }

}

