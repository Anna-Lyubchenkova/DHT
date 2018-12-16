import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class Tests {
    //Тест добавления элементов в таблицу
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


         DHT<Integer, String> table2 = new  DHT<>();

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
    }


}