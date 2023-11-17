package jp.ac.uryukyu.ie.e235718;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
//カードのナンバー、タイプを判別する
//自分の手札を保存

public class Card {
    private int number;
    private String type;

    Card(String _card) {
        if(_card.equals("joker")) {
            this.setType(_card);
        }else{
            this.setNumber(Integer.parseInt(_card.split(" ")[1]));
            this.setType(_card.split(" ")[0]);
        }
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


    // public void setNumber(int _number) {
    //     this.number = _number;
    // }

    // public void setType(String _type){
    //     this.type = _type;
    // }

    // public int getNumber(){
    //     return this.number;
    // }

    // public String getType() {
    //     return this.type;
    // }

    //全かーどをシャッフルしてプレイヤーに配る
    // public void shulle() {
    //     while (true) {
    //         for (int i=0; i<4; i++) {
    //             num -= 1;
                
    //         }
    //     }
    // }

