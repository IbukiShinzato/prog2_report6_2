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

public class GameMaster {
    public Player[] players;
    public ArrayList<Card> yamahuda;
    public Card[] fieldCard;
    private int q;
    public int n;
    public int loop;
    public ArrayList<String> names = new ArrayList<String>(Arrays.asList("1: マッシュ・バーンデッド", "2: レモン・アーヴィン",
            "3: フィン・エイムズ", "4: ランス・クラウン", "5: ドット・バレット", "6: レイン・エイムズ", "7: アベル・ウォーカー", "8: セル・ウォー"));
    public ArrayList<Player> playTurn;
    public ArrayList<Card> nowCards;
    public ArrayList<Player> winners;
    public boolean ok;

    public GameMaster() {
        Scanner sc = new Scanner(System.in);
        System.out.println("プレイヤーの人数を選択してください。");
        q = sc.nextInt();
        loop = (54 - (54 % q)) / q;
        
        try {
            loop = (54 - (54 % q)) / q;
        } catch (Exception e) {
            System.err.println("正しい数字を入力してください。");
            return;
        }

        this.players = new Player[q];
        System.out.println("名前を入力してください。");
        String name = sc.next();
        this.players[0] = new Player(name, 1);
        for (int i = 1; i < this.players.length; i++) {
            for (String chName : names) {
                System.out.println(chName);
            }
            System.out.println("番号を入力してください。");
            int p = sc.nextInt();
            players[i] = new Player(names.get(p - 1).split(" ")[1], i + 1);
        }

        String[] marks = new String[4];
        marks[0] = "\u2660";
        marks[1] = "\u2663";
        marks[2] = "\u2665";
        marks[3] = "\u2666";

        n = 0;
        this.yamahuda = new ArrayList<>();
        for (String mark : marks) {
            for (int i = 0; i < 13; i++) {
                this.yamahuda.add(new Card(mark + (i + 1)));
            }
            n += 13;
        }
        this.yamahuda.add(new Card("Joker"));
        this.yamahuda.add(new Card("Joker"));

        this.winners = new ArrayList<>();
        // this.winPlayers = new ArrayList<>();
        // for (Card card : this.yamahuda) {
        //     System.out.printf("%s %s\n", card.getType(), card.getNumber());
        // }
    }

    public void getPlayers() {
        for (int i = 1; i < this.players.length + 1; i++) {
            System.out.printf("プレイヤー%dの名前は%sです。\n",i ,this.players[i - 1].getName());
        }
    }

    public void dealOut() {
        for (int i = 0; i < loop; i++) {
            for (Player player : players) {
                Random rand = new Random();
                int num = rand.nextInt(yamahuda.size());
                player.addCard(yamahuda.get(num));
                yamahuda.remove(num);
            }
        }

        for (int i = 0; i < 54 % q; i ++) {
            Random rand = new Random();
            int num = rand.nextInt(yamahuda.size());
            players[i].addCard(yamahuda.get(num));
            yamahuda.remove(num);
        }
    }

    public Player startPlayer() {
        for(Player player : this.players) {
            for (Card card : player.hands) {
                if (card.getType().equals("♦") & card.getNumber() == 3) {
                    player.hands.remove(card);
                    return player;
                }
            }
        }
        return null;
    }

    public ArrayList<Player> createTurn(int startNum) {
        playTurn = new ArrayList<>();
        int count = startNum;
        for (int i = startNum; i < startNum + q; i++) {
            if (i == q + 1) {
                count = 1;
            } else if (i > q + 1) {
                count += 1;
            } else {
                count = i;
            }
            playTurn.add(players[count - 1]);
        }
        return playTurn;
    }

    // どのプレイヤーがカードをプレイするかの決定
    // public void turn() {
    //     int count = 1;
    //     ArrayList<Card> cards = null;
    //     while (playTurn.size() > 0) {
    //         for (Player player : playTurn) {
    //             if (player.judge(cards)) {
    //                 //カードを出す
    //                 for (Card card : cards) {
    //                     changeRule(card);
    //                 }
    //             } else {
    //                 cards = null;
    //             }

    //             if (player.hands.size() == 0) {
    //                 playTurn.remove(player);
    //                 player.setWinNum(count);
    //                 count += 1;
    //             }
    //         }
    //     }
    // }

    public void turn() {
        int winNum = 1;
        ArrayList<Card> pastCards = new ArrayList<>();
        pastCards.add(new Card("00"));
        ArrayList<Player> used = new ArrayList<>();
        ok = true;
        while (true) {
            for(int i = 0; i < q ; i ++ ) {
                if (used.contains(playTurn.get(i))) {
                    continue;
                }
                nowCards = playTurn.get(i).selectCards();
                if (ok) {
                    //カードを出す
                    for (Card card : nowCards) {
                        changeRule(card);
                    }
                    //カードを消去
                    for (Card card : nowCards) {
                        playTurn.get(i).hands.remove(card);
                    }
                } else {
                    pastCards = nowCards;
                    pastCards.add(new Card("00"));
                }
                if (playTurn.get(i).hands.size() == 0) {
                    System.out.printf("プレイヤー%d上がり！\n", players[i].getNumber());
                    used.add(playTurn.get(i));
                    playTurn.get(i).setWinNum(winNum);
                    this.winners.add(playTurn.get(i));
                    winNum += 1;
                }
                if (used.size() >= q - 1) {
                    for (int j = 0; j < q; j ++ ){
                        if (!used.contains(playTurn.get(j))) {
                            playTurn.get(j).setWinNum(winNum);
                            this.winners.add(playTurn.get(j));
                        }
                    }
                    showWin();
                    System.exit(0);
                }
            }
        }
    }

    // 出せるカードの決定
    // public void rule(int number) {

    // }

    // スキルが出た際にルール変更
    public void changeRule(Card card) {
        if (card.getNumber() == 5) {
            
            // 隣のプレイヤーをスキップ
        } else if (card.getNumber() == 7) {
        // 隣のプレイヤーに好きなカードを渡す
        } else if (card.getNumber() == 8) {
        // 自分の好きなカードを出せる
        } else if (card.getNumber() == 10) {
        // 自分の好きなカードを捨てる
        } else if (card.getNumber() == 11) {
        // カードの序列変行
        } else if (card.getNumber() == 12) {
        // 指定したカードを全て捨てる
        } else if (card.getNumber() == -1) {
            
        } else {

        }
    }

    public void showWin() {
        System.out.println("-----------------");
        for (Player winner : this.winners) {
            System.out.printf("%d位は%sです！\n", winner.winNum, winner.getName());
        }
    }
}

