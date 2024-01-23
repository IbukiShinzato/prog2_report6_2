package jp.ac.uryukyu.ie.e235718;

/**
 * Cardクラス
 * int number; //カードの番号
 * String type; // カードの記号
 * int strength; // カードの強さ
 */

public class Card {
    private int number;
    private String type;
    private int strength;

    /**
     * Cardクラスのコンストラクタ
     * @param card カード名
     */
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
    
    /**
     * setterメソッド
     * @param _number カードの番号
     */
    public void setNumber(int _number) {
        this.number = _number;
        if (_number == 1) {
            this.strength = 14;
        } else if (_number == 2) {
            this.strength = 15;
        } else {
            this.strength = _number;
        }
    }

    /**
     * setterメソッド
     * @param _type　 カードの記号
     */
    public void setType(String _type) {
        this.type = _type;
    }

    /**
     * getterメソッド
     * @return　　number カードの番号 
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * getterメソッド
     * @return type カードの記号
     */
    public String getType() {
        return this.type;
    }

    /**
     * getterメソッド
     * @return strength　　カードの強さ
     */
    public int getStrength() {
        return this.strength;
    }

    /**
     * カードをString型に変換
     * @return カードの記号とカードの番号の文字列
     */
    public String showCard() {
        if (this.type.equals("Joker")) {
            return this.type;
        } else {
            return this.type + this.number;
        }
    }
}