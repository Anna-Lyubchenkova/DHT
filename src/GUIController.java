import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Objects;

public class GUIController {
    DHT<Integer, Integer> dht = new DHT<>();


    @FXML
    private Button putB;

    @FXML
    private Button removeB;

    @FXML
    private Button getB;

    @FXML
    private TextField FIELDPUTKEY;

    @FXML
    private TextField FIELDPUTVALUE;

    @FXML
    private TextField FIELDREMOVE;

    @FXML
    private TextField FIELDGET;

    @FXML
    private TextArea VALUES;

    @FXML
    private Button CLEAR;

    @FXML
    private TextArea RESULT;

    @FXML
    void initialize() {

        showTable();

        putB.setOnAction(event -> {
            RESULT.clear();
            try {
                Integer key = Integer.valueOf(String.valueOf(FIELDPUTKEY.getCharacters()));
                Integer value = Integer.valueOf(String.valueOf(FIELDPUTVALUE.getCharacters()));
                FIELDPUTKEY.clear();
                FIELDPUTVALUE.clear();
                Integer res = dht.put(key, value);
                if (value != null) {
                    RESULT.appendText("Element put" + "\n");
                } else RESULT.appendText("Element is not put " + "\n");
                RESULT.appendText("Now size is: " + (dht.size()));
            } catch (NumberFormatException e) {
                FIELDPUTKEY.clear();
                FIELDPUTVALUE.clear();
                RESULT.appendText("Illegal field format" + "\n");
                RESULT.appendText("Please, use integer");
            }

            showTable();
        });

        removeB.setOnAction(event -> {
            RESULT.clear();
            try {
                Integer key = Integer.valueOf(String.valueOf(FIELDREMOVE.getCharacters()));
                FIELDREMOVE.clear();
                Integer value = dht.remove(key);
                if (value != null) {
                    RESULT.appendText("Element removed " + "\n");
                } else RESULT.appendText("Element is not removed " + "\n");
                RESULT.appendText("Now size is: " + (dht.size()));
            } catch (NumberFormatException e) {
                FIELDREMOVE.clear();
                RESULT.appendText("Illegal field format" + "\n");
                RESULT.appendText("Please, use integer");
            }
            showTable();
        });

        getB.setOnAction(event -> {
            RESULT.clear();
            try {
                Integer key = Integer.valueOf(String.valueOf(FIELDGET.getCharacters()));
                FIELDGET.clear();
                Integer value = dht.get(key);
                RESULT.appendText("Now size is: " + (dht.size()) + "\n");
                if (value != null)
                    RESULT.appendText("Key: " + (key) + " Value: " + (value));
                else
                    RESULT.appendText("There is no element in this table");
            } catch (NumberFormatException e) {
                FIELDGET.clear();
                RESULT.appendText("Illegal field format" + "\n");
                RESULT.appendText("Please, use integer");
            }
            showTable();
        });

        CLEAR.setOnAction(event -> {
            RESULT.clear();
            dht.clear();
            RESULT.appendText("Table cleared");
            showTable();
        });

        assert putB != null : "fx:id=\"putB\" was not injected: check your FXML file 'sample.fxml'.";
        assert removeB != null : "fx:id=\"removeB\" was not injected: check your FXML file 'sample.fxml'.";
        assert getB != null : "fx:id=\"getB\" was not injected: check your FXML file 'sample.fxml'.";
        assert FIELDPUTKEY != null : "fx:id=\"FIELDPUTKEY\" was not injected: check your FXML file 'sample.fxml'.";
        assert FIELDPUTVALUE != null : "fx:id=\"FIELDPUTVALUE\" was not injected: check your FXML file 'sample.fxml'.";
        assert FIELDREMOVE != null : "fx:id=\"FIELDREMOVE\" was not injected: check your FXML file 'sample.fxml'.";
        assert FIELDGET != null : "fx:id=\"FIELDGET\" was not injected: check your FXML file 'sample.fxml'.";
    }

    public void showTable() {
        String spaceInd = "\t\t";
        String tabEmpty = "\t\t\t\t";
        VALUES.clear();
        Cell<Integer, Integer> tmp[] = dht.getDoublehash();
        for (int i = 0; i < dht.getCapacity(); i++) {
            if (tmp[i] != null) {
                VALUES.appendText("\n" + i + spaceInd);
                VALUES.appendText(tmp[i].getKey() + makeTabs(tmp[i].getKey().toString().length())
                        + tmp[i].getValue() + makeTabs(tmp[i].getValue().toString().length())
                        + tmp[i].getIsEmpty() + makeTabs((tmp[i].getIsEmpty()) ? 4 : 5) + Objects.isNull(tmp[i]));
            } else {
                VALUES.appendText("\n" + i + spaceInd);
                VALUES.appendText("null" + makeTabs(4) + "null" + makeTabs(4) + "null" + makeTabs(4) + "null");
            }

        }
    }

    String makeTabs(int length) {
        String result = "                                ";
        result = result.substring(0, result.length() - length * 2);
        return result;
    }
}
