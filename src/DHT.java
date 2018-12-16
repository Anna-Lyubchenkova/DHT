import javafx.util.Pair;

import java.util.*;

public class DHT<K,V> implements Map<K,V> {
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

      private Pair<K,Integer> checkKey (K key) {
        int hashFunction1 = hash1(key);
        int hashFunction2 = hash2(key);
        int generalHashFunction = -1;
        try {
            for (int i = 0; i < tableSize; i++) {
                generalHashFunction = generalHash(hashFunction1, hashFunction2, i);
                if (!doublehash[generalHashFunction].getIsEmpty() && doublehash[generalHashFunction].getKey().equals(key)) {
                    return new Pair<>(doublehash[generalHashFunction].getKey(), generalHashFunction);
                }
            }
            return null;
        } catch (NullPointerException e) {
            return new Pair<>(null,generalHashFunction);
        }
    }

    @Override
    public V get(Object key) {

        Pair<K,Integer> tp = checkKey((K) key);
        return (tp == null || tp.getKey() == null) ? null : doublehash[tp.getValue()].getValue();
    }

    private void extendTable() {
        if (numberOfElements == tableSize) {
            tableSize *= 2;
            Cell<K, V> doubleHash2[] = new Cell[tableSize];
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

    @Override
    public V put(K key, V value) {
        extendTable();

        Pair<K,Integer> tp = checkKey(key);
        if (tp.getKey() == null) {
            doublehash[tp.getValue()]= new Cell(key,value);
            numberOfElements++;
            return null;
        } else {
            V v = doublehash[tp.getValue()].getValue();
            doublehash[tp.getValue()].setValue(value);
            return v;
        }
    }

    @Override
    public V remove(Object key) {

        V cellValue = get(key);
        if (cellValue == null)
                return null;

        Pair<K,Integer> tp = checkKey((K) key);
        if (tp != null && tp.getKey() != null) {
            doublehash[tp.getValue()].changeEmpty();
            numberOfElements--;
            return cellValue;
        } else return null;
    }

    @Override
    public int size() {
        return numberOfElements;
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public boolean containsKey(Object key) {
        //return findKey((K) key) != null;
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        if (isEmpty()){
            return false;
        }
        for (int i=0;i<tableSize;i++){
            if(doublehash[i] != null && doublehash[i].getValue().equals(value)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(Map.Entry<? extends K, ? extends V> entry : m.entrySet()){
            put(entry.getKey(),entry.getValue());
        }

    }

    @Override
    public void clear() {
        if(size() == 0){
            return;
        }
        for(int i = 0;i<tableSize;i++){
            doublehash[i]=null;
        }
        numberOfElements = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DHT))
            return false;
        DHT dht =(DHT)o;
        if (this != dht)
            return false;
        Set entrySetTwo = dht.entrySet();
        return (this.entrySet().equals(entrySetTwo));
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Cell<K,V> cell : doublehash) {
            result = result + cell.hashCode();
        }
        return result;
    }

   /* public class IteratorValues implements Iterator<V>{
        int index = 0;
        V currentV;
        Cell<K,V> hashTable[] = doublehash;

        @Override
        public boolean hasNext() {
            int i = index;
            currentV = null;
            while (i<=hashTable.length-1 && currentV==null){
                if(hashTable[i] != null){
                    currentV = hashTable[i].getValue();
                }
                else {
                    i++;
                }
            }
            if (currentV!=null) return true;
            else return false;
        }

        @Override
        public V next() {
            if(!hasNext()){
                throw new NoSuchElementException("");
            }
            index++;
            return currentV;
        }

    }

    public class IteratorKeys implements Iterator<K>{
        int index = 0;
        K currentK;
        Cell<K,V> hashTable[] = doublehash;

        @Override
        public boolean hasNext() {
            int i = index;
            currentK = null;
            while (i<=hashTable.length-1 && currentK==null){
                if(hashTable[i] != null){
                    currentK = hashTable[i].getKey();
                }
                else {
                    i++;
                }
            }
            if (currentK!=null) return true;
            else return false;
        }

        @Override
        public K next() {
            if(!hasNext()){
                throw new NoSuchElementException("");
            }
            index++;
            return currentK;
        }

    }*/

}
