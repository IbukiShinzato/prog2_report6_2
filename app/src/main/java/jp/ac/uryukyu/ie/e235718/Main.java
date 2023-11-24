package jp.ac.uryukyu.ie.e235718;

public class Main {
    public static void main(String[] args) {
        Card testCard1 = new Card("H 13");
        System.out.printf("カードのタイプは%sです。\n",testCard1.getType());
        System.out.printf("カードのナンバーは%dです。\n", testCard1.getNumber());

        Card testCard2 = new Card("joker");
        System.out.printf("カードのタイプは%sです。\n",testCard2.getType());
        System.out.printf("カードのナンバーは%dです。\n", testCard2.getNumber());
    }
}

