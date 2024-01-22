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
    public ArrayList<Integer> rules;

    public GameMaster() {
        Scanner sc = new Scanner(System.in);
        System.out.println("プレイヤーの人数を選択してください。");
        q = sc.nextInt();
        loop = (54 - (54 % q)) / q;
        
        try {
            loop = (54 - (54 % q)) / q;
        } catch (Exception e) {
            System.err.println("正しい数字を入力してください。");
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

        this.rules = selectRule();

        this.winners = new ArrayList<>();
    }

    public ArrayList<Integer> selectRule() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> rules = new ArrayList<>();
        System.out.println("ルールを決めてください。");
        System.out.printf("1: 7渡し\n2: 8切り\n3: 10捨て\n4: 12ボンバー\n");
        System.out.printf("1行で入力してください。\n例：2 3 4\n");

        String inputLine = sc.nextLine();

        if (inputLine.equals("")) {
            rules = new ArrayList<>();
        } else {
            // スペースで分割して文字列配列に変換
            String[] numbersAsString = inputLine.split(" ");

            // 文字列配列を整数配列に変換
            for (int i = 0; i < numbersAsString.length; i++) {
                rules.add(Integer.parseInt(numbersAsString[i]));
            }
        }
        return rules;
    }

    public void getPlayers() {
        for (int i = 1; i < this.players.length + 1; i++) {
            System.out.printf("プレイヤー%dの名前は%sです。\n",i ,this.players[i - 1].getName());
        }
    }

    public void dealOut() {
        Random rand = new Random();
        for (int i = 0; i < loop; i++) {
            for (Player player : players) {
                int num = rand.nextInt(yamahuda.size());
                player.addCard(yamahuda.get(num));
                yamahuda.remove(num);
            }
        }

        for (int i = 0; i < 54 % q; i ++) {
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

    public void turn() {
        int winNum = 1;
        ArrayList<Player> used = new ArrayList<>();
        ArrayList<Integer> usedNum = new ArrayList<>();
        ArrayList<Card> prevCards = new ArrayList<>();
        while (true) {
            for(int i = 0; i < q ; i ++ ) {
                if (used.contains(playTurn.get(i))) {
                    continue;
                }

                nowCards = nextCards(prevCards, playTurn.get(i), prevCards.size());

                for (Card card : nowCards) {
                    rule(playTurn.get(i), card, i, usedNum, nowCards.size(), card.getNumber());
                }

                prevCards = nowCards;

                for (Card card : nowCards) {
                    playTurn.get(i).hands.remove(card);
                }

                if (playTurn.get(i).hands.size() == 0) {
                    System.out.printf("プレイヤー%d上がり！\n", players[i].getNumber());
                    used.add(playTurn.get(i));
                    usedNum.add(i);
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
                }
            }
        }
    }

    public ArrayList<Card> nextCards(ArrayList<Card> prevCards, Player nowPlayer, int cardCount) {
        if (prevCards.size() == 0) {
            System.out.println("freeです。");
            return nowPlayer.selectCards();
        } else {
            ArrayList<Card> nowCards = new ArrayList<>();

            if (prevCards.size() == nowCards.size()) {
                for (int i = 0; i < prevCards.size(); i ++ ) {
                    if (nowCards.get(i).getNumber() <= prevCards.get(i).getNumber() && !nowCards.get(i).showCard().equals("Joker")) {
                        System.out.println("正しく入力してください。");
                        nextCards(prevCards, nowPlayer, cardCount);
                    }
                } return nowCards;
            } else {
                System.out.println("正しく入力してください。");
                nextCards(prevCards, nowPlayer, cardCount);
            }
        }
        return prevCards;
    }

    public void rule(Player player, Card card, int playerIndex, ArrayList<Integer> used, int cardCount, int ruleNumber) {
        if (this.rules.contains(ruleNumber)) {
            if (card.getNumber() == 7) {
                // 隣のプレイヤーに好きなカードを渡す
                delivery(playTurn.get(playerIndex), playerIndex, used);
            } else if (card.getNumber() == 8) {
                // 自分の好きなカードを出せる
                nextCards(new ArrayList<Card>(), player, cardCount);
            } else if (card.getNumber() == 10) {
                // 自分の好きなカードを捨てる
                throwAway(player, playerIndex, cardCount);
            } else if (card.getNumber() == 12) {
                bomber(player, playerIndex, cardCount, used);
                // 指定したカードを全て捨てる
            }
        }
    }

    public void delivery(Player player, int index, ArrayList<Integer> used) {
        String res = "";
        int n = 0;
        for (int i = index; i < q + index; i ++ ) {
            if (!used.contains(i % q)) {
                n = i % q;
            }
        }

        for (Card card : playTurn.get(index).selectCards()) {
            playTurn.get((n) % q).addCard(card);
            res += card.showCard();
            res += " ";
        }
        System.out.printf("7渡し！\nプレイヤー%dはプレイヤー%dに%sを渡しました。\n", player.getNumber(), playTurn.get(n).getNumber(), res);
    }

    public void throwAway(Player player, int index, int cardCount) {
        String res = "";
        for (Card card : playTurn.get(index).selectCards()) {
            playTurn.get((index) % q).hands.remove(card);
            res += card.showCard();
            res += " ";
        }
        System.out.printf("8切り！\nプレイヤー%dは%sを捨てました。\n", player.getNumber(), res);
    }

    public void bomber(Player player, int index, int cardCount, ArrayList<Integer> used) {
        String res = "";
        ArrayList<Card> throwCards = playTurn.get(index).selectCards();
        for (int i = index; i < q + index; i ++ ) {
            if (used.contains(i)) {
                continue;
            }
        }
        for (Card throwCard : throwCards) {
            res += throwCard.showCard();
            for (Card card : playTurn.get(index).hands) {
                if (throwCard.getNumber() == card.getNumber()) {
                    playTurn.get(index).hands.remove(throwCard);
                }
            }
        }
        System.out.printf("12ボンバー！\n%sを全プレイヤーから消しました\n", res);
    }

    public void showWin() {
        System.out.println("-----------------");
        for (Player winner : this.winners) {
            System.out.printf("%d位は%sです！\n", winner.winNum, winner.getName());
        }
        System.exit(0);
    }
}

