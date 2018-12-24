import javafx.util.Pair;

import java.util.*;

public class DHT<K,V> implements Map<K,V> {
    public Cell<K, V>[] getDoublehash() {
        return doublehash;
    }

    private Cell<K,V> doublehash[];
    private int numberOfElements = 0;
    private int tableSize = 4;

    public DHT(){
        this.doublehash=new Cell[tableSize];
    }

//    public DHT(int tableSize){
//        this.tableSize = tableSize;
//        this.doublehash=new Cell[tableSize];
//    }

    private int hash1(K key){
        return key.hashCode() % tableSize;
    }

    // д.б. вз. простым в разм. т.
    private int hash2(K key){
        int t = key.hashCode() / 2 % (tableSize - 1);
        return t % 2 == 0 ? t + 1 : t;
    }

    private int generalHash(int h1, int h2, int count){
        return (h1+h2*count) % tableSize;
    }

    private Set<K> keySet;
    private Collection<V> values;

    private void extendTable() {
        if (numberOfElements == tableSize) {
            tableSize *= 2;
            Cell doubleHash2[] = new Cell[tableSize];
            for (int i = 0; i < doublehash.length; i++) {
                int hashFun1 = hash1(doublehash[i].getKey());
                int hashFun2 = hash2(doublehash[i].getKey());
                for (int j = 0; j < doublehash.length; j++) {
                    int genHashFun = generalHash(hashFun1,hashFun2,j);
                    if(doubleHash2[genHashFun]==null){
                        doubleHash2[genHashFun]=new Cell<>(doublehash[i].getKey(),doublehash[i].getValue());
                        break;
                    }
                }
            }
            doublehash = doubleHash2;
        }
    }

    private Set<Map.Entry<K, V>> entrySet;

    private Pair<K, Integer> checkKey(K key, boolean acceptEmpty) {
        int hashFunction1 = hash1(key);
        int hashFunction2 = hash2(key);
        int generalHashFunction;

        for (int i = 0; i < tableSize; i++) {
            generalHashFunction = generalHash(hashFunction1, hashFunction2, i);
            if (Objects.isNull(doublehash[generalHashFunction])) {
                return new Pair<>(null, generalHashFunction);
            }
            boolean check = (!acceptEmpty)
                    ? !doublehash[generalHashFunction].getIsEmpty() && doublehash[generalHashFunction].getKey().equals(key)
                    : doublehash[generalHashFunction].getKey().equals(key) || doublehash[generalHashFunction].getIsEmpty();
            if (check) {
                return new Pair<>(doublehash[generalHashFunction].getKey(), generalHashFunction);
            }
        }
        return null;
    }

    @Override
    public int size() {
        return numberOfElements;
    }

    public int getCapacity() {
        return tableSize;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public V get(Object key) {

        Pair<K, Integer> tp = checkKey((K) key, false);
        return (tp == null || tp.getKey() == null) ? null : doublehash[tp.getValue()].getValue();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }

    }

    @Override
    public void clear() {
        if (size() == 0) {
            return;
        }
        for (int i = 0; i < tableSize; i++) {
            doublehash[i] = null;
        }
        numberOfElements = 0;
    }

    @Override
    public V put(K key, V value) {
        extendTable();

        Pair<K, Integer> tp = checkKey(key, true);
        if (tp.getKey() == null) {
            doublehash[tp.getValue()] = new Cell<>(key, value);
            numberOfElements++;
            return null;
        } else {
            V v = doublehash[tp.getValue()].getValue();
            doublehash[tp.getValue()].setValue(value);
            if (doublehash[tp.getValue()].getIsEmpty()) {
                doublehash[tp.getValue()].changeEmpty();
                numberOfElements++;
                doublehash[tp.getValue()].setKey(key);
            }
            return v;
        }
    }

    @Override
    public V remove(Object key) {

        Pair<K, Integer> tp = checkKey((K) key, false);
        if (tp != null && tp.getKey() != null) {
            doublehash[tp.getValue()].changeEmpty();
            numberOfElements--;
            return doublehash[tp.getValue()].getValue();
        } else return null;
    }

    @Override
    public boolean containsValue(Object value) {
        if (isEmpty()){
            return false;
        }
        for (int i=0;i<tableSize;i++){
            if (Objects.nonNull(doublehash[i])
                    && !doublehash[i].getIsEmpty()
                    && doublehash[i].getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new KeySet();
            keySet = ks;
        }
        return ks;
    }

    @Override
    public Collection<V> values() {
        Collection<V> vs = values;
        if (values == null) {
            vs = new Values();
            values = vs;
        }
        return vs;
    }


    /// ITERATORS

    @Override
    public Set<Entry<K, V>> entrySet() {

        Set<Map.Entry<K, V>> es = entrySet;
        if (es == null) {
            es = new EntrySet();
            entrySet = es;
        }
        return es;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof Map)) return false;

        Map<K, V> dht = (Map<K, V>) o;
        Set entrySetTwo = dht.entrySet();
        return (this.entrySet().equals(entrySetTwo));
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Cell<K,V> cell : doublehash) {
            result = result + ((cell == null || cell.getIsEmpty()) ? 0 : cell.hashCode());
        }
        return result;
    }

    abstract class Iterator {
        int index;
        boolean mayRemove = false;
        Cell<K, V> currentElement;
        Cell<K, V> hashTable[];

        private Iterator() {
            hashTable = doublehash;
            index = 0;
        }

        public boolean hasNext() {
            int i = index;
            currentElement = null;
            while (i <= hashTable.length - 1 && currentElement == null) {
                if (hashTable[i] != null && !hashTable[i].getIsEmpty()) {
                    currentElement = hashTable[i];
                } else
                    i++;
            }
            if (i > index)
                index = i;
            return currentElement != null;
        }

        public void remove() {
            if (!mayRemove)
                throw new IllegalStateException("Element can't be removed until calling next()");
            if (!hashTable[index].getIsEmpty()) {
                hashTable[index].changeEmpty();
                numberOfElements--;
                mayRemove = false;
            }
        }
    }

    class KeyIterator extends Iterator implements java.util.Iterator<K> {
        public final K next() {
            if (hasNext()) {
                index++;
                mayRemove = true;
                return currentElement.getKey();
            } else throw new NoSuchElementException("No next key");
        }
    }

    class ValueIterator extends Iterator implements java.util.Iterator<V> {
        public final V next() {
            if (hasNext()) {
                index++;
                mayRemove = true;
                return currentElement.getValue();
            } else throw new NoSuchElementException("No next value");
        }
    }

    class EntryIterator extends Iterator implements java.util.Iterator<Map.Entry<K, V>> {
        public final Map.Entry<K, V> next() {
            if (hasNext()) {
                index++;
                mayRemove = true;
                return currentElement;
            } else throw new NoSuchElementException("No next entry");
        }
    }

    final class KeySet extends AbstractSet<K> {

        public final java.util.Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public boolean contains(Object obj) {
            return DHT.this.containsKey(obj);
        }

        @Override
        public void clear() {
            DHT.this.clear();
        }

        @Override
        public int size() {
            return DHT.this.size();
        }

        @Override
        public boolean remove(Object key) {
            return DHT.this.remove(key) != null;
        }
    }

    final class Values extends AbstractCollection<V> {

        public final java.util.Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public boolean contains(Object obj) {
            return DHT.this.containsValue(obj);
        }

        @Override
        public void clear() {
            DHT.this.clear();
        }

        @Override
        public int size() {
            return DHT.this.size();
        }

        @Override
        public boolean remove(Object value) {
            java.util.Iterator<V> iteratorForValues = iterator();
            V values = (V) value;
            do {
                V i = iteratorForValues.next();
                if (i.equals(values)) {
                    iteratorForValues.remove();
                    return true;
                }

            }
            while (iteratorForValues.hasNext());
            return false;
        }
    }

    final class EntrySet extends AbstractSet<Map.Entry<K, V>> {

        public final java.util.Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public boolean contains(Object obj) {
            Cell<K, V> cell = (Cell<K, V>) obj;
            return DHT.this.get(cell.getKey()).equals(cell.getValue());
        }

        @Override
        public void clear() {
            DHT.this.clear();
        }

        @Override
        public int size() {
            return DHT.this.size();
        }

        @Override
        public boolean remove(Object entry) {
            return DHT.this.remove(((Map.Entry<K, V>) entry).getKey()) != null;
        }
    }
}
