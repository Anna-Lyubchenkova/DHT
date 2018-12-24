import java.util.Map;

public class Cell<K,V> implements Map.Entry<K,V> {
    private K key;
    private V value;
    private boolean isEmpty;

    public Cell(K key, V value) {
        this.key = key;
        this.value=value;
        this.isEmpty= false;
    }


    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return this.value;
    }

    public K setKey(K key) {
        this.key = key;
        return this.key;
    }
    public boolean getIsEmpty(){
        return isEmpty;
    }
    public void changeEmpty(){
        this.isEmpty = !this.isEmpty;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cell)) return false;
        Cell element = (Cell) o;
        return (this.key.equals(element.key) && this.value.equals(element.value));
    }

    @Override
    public int hashCode() {
        int res = (key == null) ? 0 : key.hashCode();
        res = res ^ ((value == null) ? 0 : value.hashCode());
        return res;
    }
    @Override
    public String toString(){
        return String.format("( %s : %s )", this.key, this.value);
    }
}
