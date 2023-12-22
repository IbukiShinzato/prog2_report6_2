package jp.ac.uryukyu.ie.e235718;

import java.util.*;
//山札とプレーヤー名の決定
//プレイするゲームの順番
//プレイできるカードの決定
//スキルが発動した際のルール変更

/**
 * "\u2660" = スペード
 * "\u2663" = クローバー
 * "\u2665" = ハート
 * "\u2666" = ダイヤ
 */

public class Gamemaster {
    public Player[] players;
    public Card[] yamahuda;
    public Card[] fieldCard;
    public ArrayList<String> names = new ArrayList<String>(Arrays.asList("1: マッシュ・バーンデッド", "2: レモン・アーヴィン",
            "3: フィン・エイムズ", "4: ランス・クラウン", "5: ドット・バレット", "6: レイン・エイムズ", "7: アベル・ウォーカー", "8: セル・ウォー"));

    Gamemaster(Card[] yamahuda, Card[] fielCards) {
        this.players = new Player[6];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < this.players.length; i++) {
            System.out.println(names);
            System.out.println("番号を入力してください。");
            int p = sc.nextInt();
            players[i] = new Player(names.get(p - 1).split(" ")[1]);
        }
        sc.close();

        String[] marks = new String[4];
        marks[0] = "\u2660";
        marks[1] = "\u2663";
        marks[2] = "\u2665";
        marks[3] = "\u2666";

        int n = 0;
        this.yamahuda = new Card[54];
        for (String mark : marks) {
            for (int i = 0; i < 13; i++) {
                this.yamahuda[i + n] = new Card(mark + (i + 1));
            }
            n += 13;
        }
        this.yamahuda[52] = new Card("Joker");
        this.yamahuda[53] = new Card("Joker");

        // for (Card card : this.yamahuda) {
        //     System.out.printf("%s %s\n", card.getType(), card.getNumber());
        // }
    }

    public void getPlayers() {
        for (int i = 0; i < this.players.length; i++) {
            System.out.println(this.players[i].getName());
        }
    }

    int count = 0;
    ArrayList<Integer> used = new ArrayList<Integer>();

    public void dealOut() {
        for (int i = 0; i < 9; i++) {
            for (Player player : players) {
                int count = 0;
                while (count < 1) {
                    Random rand = new Random();
                    int num = rand.nextInt(54);
                    if (used.contains(num)) {
                        continue;
                    } else {
                        player.addCard(yamahuda[num]);
                        count += 1;
                    }
                }
            }
        }
    }

    // public turn() {
    // //どのプレイヤーがカードをプレイするかの決定
    // }

    // 出せるカードの決定
    // public void rule(int number) {

    // }

    // スキルが出た際にルール変更
    // public void changerule(int number) {
    // if (number == 5) {
    // // 隣のプレイヤーをスキップ
    // } else if (number == 7) {
    // // 隣のプレイヤーに好きなカードを渡す
    // } else if (number == 8) {
    // // 自分の好きなカードを出せる
    // } else if (number == 10) {
    // // 自分の好きなカードを捨てる
    // } else if (number == 11) {
    // // カードの序列変行
    // } else if (number == 12) {
    // // 指定したカードを全て捨てる
    // }
    // }

}