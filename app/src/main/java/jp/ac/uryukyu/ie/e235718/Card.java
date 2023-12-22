package jp.ac.uryukyu.ie.e235718;

import java.util.ArrayList;
import java.util.Arrays;

//カードのナンバー、タイプを判別する
//自分の手札を保存

public class Card {
    private int number;
    private String type;
    // public ArrayList<String> yamahuda;
    Card(String _card) {
        if(_card.equals("Joker")) {
            this.setType(_card);
        }else{
            if (_card.length() == 2) {
                this.setNumber(Integer.parseInt(_card.split("")[1]));
            } else {
                this.setNumber(Integer.parseInt(_card.split("")[1] + _card.split("")[2]));
            }
            this.setType(_card.split("")[0]);
        }
        // yamahuda = new ArrayList<String>(Arrays.asList("H 1", "H 2", "H 3", "H 4", "H 5", "H 6", "H 7", "H 8", "H 9", "H 10", "H 11", "H 12", "H 13", "D 1", "D 2", "D 3", "D 1", "D 2", "D 3", "D 4", "D 5", "D 6", "D 7", "D 8", "D 9", "D 10", "D 11", "D 12", "D 13", "K 1", "K 2", "K 3", "K 4", "K 5", "K 6", "K 7", "K 8", "K 9", "K 10", "K 11", "K 12", "K 13", "S 1", "S 2", "S 4", "S 5", "S 6", "S 7", "S 8", "S 9", "S 10", "S 11", "S 12", "S 13", "joker", "joker"));
    }
    
    public void setNumber(int _number) {
        this.number = _number;
    }

    public void setType(String _type) {
        this.type = _type;
    }

    public int getNumber() {
        return this.number;
    }

    public String getType() {
        return this.type;
    }
}