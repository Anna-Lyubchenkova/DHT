import javafx.util.Pair;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;


public class Tests {
    @Test
    public void put(){

        Integer key1 = 2;
        Integer value1  = 34;
        Integer key2 = 0;
        Integer value2 = 123;
        Integer key3 = 12;
        Integer value3 = 1000;
        Integer key4 = 8;
        Integer value4 = 345;
        Integer key5 = 5;
        Integer value5 = 345;
        Integer key6 = 2;
        Integer value6 = 35;
        DHT<Integer, Integer> table1 = new  DHT();
        table1.put(key1,value1);
        table1.put(key2, value2);
        table1.put(key3, value3);
        table1.put(key4, value4);
        Integer value5_n = table1.put(key5, value5);
        Integer value1_6 = table1.put(key6, value6);
        assertEquals(5, table1.size());
        assertEquals(value1, value1_6);
        assertEquals(null, value5_n);


        Set<Integer> ks = table1.keySet();
        assertEquals(5, ks.size());
        Iterator<Integer> iter = ks.iterator();

        Collection<Integer> vs = table1.values();
        assertEquals(5, vs.size());
        Iterator<Integer> iter2 = vs.iterator();

        Collection<Map.Entry<Integer, Integer>> es = table1.entrySet();
        assertEquals(5, es.size());
        Iterator<Map.Entry<Integer, Integer>> iter3 = es.iterator();

        DHT<Integer, String> table2 = new DHT<>();

        Integer keys1 = 2;
        String word1  = "34";
        Integer keys2 = 0;
        String word2 = "123";
        Integer keys3 = 12;
        String word3 = "123";
        table2.put(keys1,word1);
        table2.put(keys2, word2);
        table2.put(keys3, word3);
        assertEquals(3, table2.size());
    }

    @Test
    public void get(){

        Integer key1 = 2;
        Integer value1  = 34;
        Integer key2 = 0;
        Integer value2 = 123;
        Integer key3 = 12;
        Integer value3 = 1000;
        Integer key4 = 8;
        Integer value4 = 345;
        Integer key5 = 5;
        Integer value5 = 345;
        DHT<Integer, Integer> table1 = new DHT();
        table1.put(key1,value1);
        table1.put(key2, value2);
        table1.put(key3, value3);
        table1.put(key4, value4);
        table1.put(key5, value5);

        Integer get1 =  table1.get(key4);

        assertEquals(value4, get1);

        assertEquals(null,table1.get(128));
        assertEquals(value3, table1.get(key3));

        DHT<Integer, String> table2 = new DHT();

        Integer keys1 = 2;
        String word1  = "34";
        Integer keys2 = 0;
        String word2 = "123";
        Integer keys3 = 12;
        String word3 = "12";
        table2.put(keys1,word1);
        table2.put(keys2, word2);
        table2.put(keys3, word3);
        String get2 = table2.get(keys2);
        assertEquals("123", get2 );
    }

    @Test
    public void remove(){

        Integer key1 = 2;
        Integer value1  = 34;
        Integer key2 = 0;
        Integer value2 = 123;
        Integer key3 = 12;
        Integer value3 = 1000;
        Integer key4 = 8;
        Integer value4 = 345;
        Integer key5 = 5;
        Integer value5 = 345;
        DHT<Integer, Integer> table1 = new DHT();
        table1.put(key1,value1);
        table1.put(key2, value2);
        table1.put(key3, value3);
        table1.put(key4, value4);
        table1.put(key5, value5);

        Integer valueRemove =  table1.remove(key1);
        assertEquals(value1, valueRemove);
        assertEquals(4, table1.size());

        Integer get1 = table1.get(key1);
        assertEquals(null,get1);

        DHT<Integer, String> table2 = new DHT();

        Integer keys1 = 2;
        String word1  = "34";
        Integer keys2 = 0;
        String word2 = "123";
        Integer keys3 = 12;
        String word3 = "123";
        table2.put(keys1,word1);
        table2.put(keys2, word2);
        table2.put(keys3, word3);

        String valueRemoves =  table2.remove(keys2);
        assertEquals(word2 , valueRemoves);
        assertEquals(2, table2.size());
    }

    @Test
    public void testEquals(){

        Integer key1 = 2;
        Integer value1  = 34;
        Integer key2 = 0;
        Integer value2 = 123;
        Integer key3 = 12;
        Integer value3 = 1000;
        Integer key4 = 8;
        Integer value4 = 345;
        Integer key5 = 5;
        Integer value5 = 345;

        DHT<Integer, Integer> table1 = new DHT<>();
        table1.put(key1,value1);
        table1.put(key2, value2);
        table1.put(key3, value3);
        table1.put(key4, value4);
        table1.put(key5, value5);

        DHT<Integer, Integer> table2 = new DHT<>();
        table2.put(key1,value1);
        table2.put(key2, value2);
        table2.put(key3, value3);
        table2.put(key4, value4);
        table2.put(key5, value5);

        Set<Map.Entry<Integer, Integer>> es1 = table1.entrySet();
        Set<Map.Entry<Integer, Integer>> es2 = table2.entrySet();

        assertEquals(true, es1.equals(es2));
        assertEquals(true, table1.equals(table2));


        DHT<Integer, String> table3 = new DHT();

        Integer keys1 = 2;
        String word1  = "34";
        Integer keys2 = 0;
        String word2 = "123";
        Integer keys3 = 12;
        String word3 = "123";
        table3.put(keys1,word1);
        table3.put(keys2, word2);
        table3.put(keys3, word3);

        assertEquals(false, table1.equals(table3));

        Map.Entry<Integer, Integer> i = es1.iterator().next();
        Cell<Integer, Integer> c = new Cell<>(2, 34);
        assertEquals(false, c.equals(i));
        assertEquals(false, c.equals(new Integer(5)));
        assertEquals(false, table1.equals(new Integer(10)));
    }

    @Test
    public void testhashCode() {
        Integer key1 = 2;
        Integer value1 = 34;
        Integer key2 = 0;
        Integer value2 = 123;
        Integer key3 = 12;
        Integer value3 = 1000;

        DHT<Integer, Integer> table1 = new DHT<>();
        table1.put(key1, value1);
        table1.put(key2, value2);
        table1.put(key3, value3);

        DHT<Integer, Integer> table2 = new DHT();

        table2.put(key1, value1);
        table2.put(key2, value2);
        table2.put(key3, value3);

        DHT<Integer, Integer> table3 = new DHT();

        table3.put(key1, value3);
        table3.put(key2, value2);
        table3.put(key3, value1);


        int h1 = table1.hashCode();
        int h2 = table2.hashCode();
        int h3 = table3.hashCode();

        assertEquals(true, h1 == h2);
        assertEquals(false, h1 == h3);
    }

    @Test
    public void testSet() {
        int key1 = 2;
        int value1 = 34;
        int key2 = 0;
        int value2 = 123;
        int key3 = 12;
        int value3 = 1000;
        int key4 = 8;
        int value4 = 345;
        int key5 = 5;
        int value5 = 345;

        DHT<Integer, Integer> table1 = new DHT();
        table1.put(key1, value1);
        table1.put(key2, value2);
        table1.put(key3, value3);
        table1.put(key4, value4);
        table1.put(key5, value5);

        HashSet<Integer> keySet1 = new HashSet<>();
        keySet1.add(key1);
        keySet1.add(key2);
        keySet1.add(key3);
        keySet1.add(key4);
        keySet1.add(key5);

        HashSet<Map.Entry<Integer, Integer>> entryset = new HashSet<>();
        entryset.add(new Cell(key1, value1));
        entryset.add(new Cell(key2, value2));
        entryset.add(new Cell(key3, value3));
        entryset.add(new Cell(key4, value4));
        entryset.add(new Cell(key5, value5));

        assertEquals(keySet1, table1.keySet());
        assertEquals(entryset, table1.entrySet());

        table1.keySet().remove(key1);
        keySet1.remove(key1);
        assertEquals(keySet1, table1.keySet());
        assertEquals(4, table1.size());

        DHT<Integer, String> table2 = new DHT();

        int keys1 = 2;
        String word1 = "34";
        int keys2 = 0;
        String word2 = "123";
        int keys3 = 12;
        String word3 = "123";
        table2.put(keys1, word1);
        table2.put(keys2, word2);
        table2.put(keys3, word3);

        HashSet<Integer> keySet2 = new HashSet<>();
        keySet2.add(keys1);
        keySet2.add(keys2);
        keySet2.add(keys3);

        assertEquals(keySet2, table2.keySet());
    }

    @Test
    public void TestContains() {
        Integer key1 = 2;
        Integer value1 = 34;
        Integer key2 = 0;
        Integer value2 = 123;
        Integer key3 = 12;
        Integer value3 = 1000;
        DHT<Integer, Integer> table1 = new DHT();
        table1.put(key1, value1);
        table1.put(key2, value2);
        table1.put(key3, value3);
        assertEquals(true, table1.containsKey(key1));
        assertEquals(false, table1.containsKey(999));
        assertEquals(true, table1.containsValue(value1));
        assertEquals(false, table1.containsValue(key1));
    }

}