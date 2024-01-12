package jp.ac.uryukyu.ie.e235718;

//カードのナンバー、タイプを判別する
//自分の手札を保存

public class Card {
    private int number;
    private String type;

    Card(String card) {
        if(card.equals("Joker")) {
            this.setType(card);
        }else{
            if (card.length() == 2) {
                this.setNumber(Integer.parseInt(card.split("")[1]));
            } else {
                this.setNumber(Integer.parseInt(card.split("")[1] + card.split("")[2]));
            }
            this.setType(card.split("")[0]);
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

    public String showCard() {
        if (type.equals("Joker")) {
            return this.type;
        } else {
            return this.type + this.number;
        }
    }
}