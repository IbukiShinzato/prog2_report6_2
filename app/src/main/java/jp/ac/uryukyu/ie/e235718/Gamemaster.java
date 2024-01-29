package jp.ac.uryukyu.ie.e235718;

import java.util.*;


/**
 * "\u2660" = スペード
 * "\u2663" = クローバー
 * "\u2665" = ハート
 * "\u2666" = ダイヤ
 */

/**
 * GameMasterクラス
 * Player[] players; //プレイヤーの配列
 * ArrayList<Card> yamahuda; //54枚のトランプ
 * int q; //プレイヤーの人数
 * int n; //山札を操作するための数字
 * int loop; //人数に対応するloopの回数
 * ArrayList<String> names; キャラクター名のリスト
 * ArrayList<Player> playTurn; //プレイヤーの順番、左から順にスタート
 * ArrayList<Card> nowCards; //現在のカード
 * ArrayList<Player> winners; //勝者のリスト
 * ArrayList<Integer> rules; //ルールのリスト
 * Scanner scanner; //入力のためのもの
 */

//Gamemaster -> GameMasterに変更しました
public class GameMaster {
    public Player[] players;
    public ArrayList<Card> yamahuda;
    private int q;
    public int n;
    public int loop;
    public ArrayList<String> names = new ArrayList<String>(Arrays.asList("1: マッシュ・バーンデッド", "2: レモン・アーヴィン",
            "3: フィン・エイムズ", "4: ランス・クラウン", "5: ドット・バレット", "6: レイン・エイムズ", "7: アベル・ウォーカー", "8: セル・ウォー"));
    public ArrayList<Player> playTurn;
    public ArrayList<Card> nowCards;
    public ArrayList<Player> winners;
    public ArrayList<Integer> rules;
    public Scanner scanner;

    /**
     * GameMasterのコンストラクタ
     * プレイヤーの人数の決定
     * 各プレイヤー名の決定
     * 54枚のトランプ生成
     * ルールの決定
     */
    public GameMaster() {
        System.out.printf("\nゲーム開始！\n");
        this.scanner = new Scanner(System.in);
        System.out.printf("\nプレイヤーの人数を選択してください。\n例：4\n");
        q = this.scanner.nextInt();
        loop = (54 - (54 % q)) / q;
        
        try {
            loop = (54 - (54 % q)) / q;
        } catch (Exception e) {
            System.err.println("正しい数字を入力してください。");
        }

        this.players = new Player[q];
        System.out.printf("\n名前を入力してください。\n");
        String name = this.scanner.next();
        this.players[0] = new Player(name, 1);

        for (int i = 1; i < this.players.length; i++) {
            System.out.printf("\n番号を入力してください。\n");
            for (String chName : names) {
                System.out.println(chName);
            }
            int p = this.scanner.nextInt();
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

        this.rules = selectRule(this.scanner);

        this.winners = new ArrayList<>();
    }

    public ArrayList<Integer> selectRule(Scanner sc) {
        ArrayList<Integer> rules = new ArrayList<>();
        System.out.printf("\nルールを決めてください。\n");
        System.out.printf("7: 7渡し\n8: 8切り\n10: 10捨て\n12: 12ボンバー\n");
        System.out.printf("\n1行で入力してください。\n例：8 10\n");

        String inputLine = sc.next();

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
            System.out.printf("\nプレイヤー%dの名前は%sです。\n",i ,this.players[i - 1].getName());
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
        System.out.printf("\nプレイヤー%dからスタートです！\n\n", startNum);
        return playTurn;
    }

    //ここから下を修正
    public void turn() {
        int winNum = 1;
        Player startPlayer = startPlayer();
        ArrayList<Player> playTurn = createTurn(startPlayer.getNumber());
        ArrayList<Player> used = new ArrayList<>();
        ArrayList<Integer> usedNum = new ArrayList<>();
        ArrayList<Card> prevCards = new ArrayList<>();
        while (true) {
            for(int i = 0; i < q ; i ++ ) {
                if (used.contains(playTurn.get(i))) {
                    continue;
                }

                //修正
                ArrayList<Card> nowCards = nextCards(prevCards, playTurn.get(i), prevCards.size());

                for (Card card : nowCards) {
                    rule(playTurn.get(i), card, i, usedNum, nowCards.size(), card.getNumber(), prevCards);
                    playTurn.get(i).hands.remove(card);
                }

                prevCards = nowCards;

                //　ここから下はok
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
        System.out.printf("プレイヤー%dのターン！\n", nowPlayer.getNumber());
        nowCards = nowPlayer.selectCards(prevCards, this.scanner);
        int ok = judge(prevCards, nowCards);
        if (ok == 1) {
            String res = "";
            for (Card card : nowCards) {
                res += card.showCard();
                res += " ";
            }
            if (res.length() == 0) {
                System.out.println("freeです。");
            } else {
                System.out.printf("プレイヤー%dが出したカードは%sです。\n\n\n", nowPlayer.getNumber(), res);
            }
            return nowCards;
        } else  if (ok == 2) {
            System.out.println("正しく入力してください。");
            nextCards(prevCards, nowPlayer, cardCount);
        } else {
            System.out.printf("プレイヤー%dはパスをしました。\n", nowPlayer.getNumber());
        }
        return nowCards;
    }

    public int judge(ArrayList<Card> prevCards, ArrayList<Card> nowCards) {
        if (prevCards.equals(nowCards)) {
            return 0;
        }
        if (prevCards.size() == 0) {
            for (int i = 0; i < nowCards.size() - 1; i ++ ) {
                if (nowCards.get(i).getNumber() != nowCards.get(i + 1).getNumber() && !nowCards.get(i).showCard().equals("Joker") || !nowCards.get(i + 1).showCard().equals("Joker")) {
                    return 2;
                }
            } 
            return 1;
        } else {
            if (prevCards.size() == nowCards.size()) {
                for (int i = 0; i < prevCards.size(); i ++ ) {
                    if (nowCards.get(i).getStrength() <= prevCards.get(i).getStrength() && !nowCards.get(i).showCard().equals("Joker")) {
                        return 2;
                    }
                } 
                return 1;
            } else {
                return 2;
            }
        }
    }

    public void rule(Player player, Card card, int playerIndex, ArrayList<Integer> used, int cardCount, int ruleNumber, ArrayList<Card> prevCards) {
        if (this.rules.contains(ruleNumber)) {
            if (card.getNumber() == 7) {
                // 隣のプレイヤーに好きなカードを渡す
                System.out.println("7渡し！");
                player.hands.remove(card);
                delivery(playTurn.get(playerIndex), playerIndex, used, prevCards);
            } else if (card.getNumber() == 8) {
                // 自分の好きなカードを出せる
                System.out.println("8切り！");
                nextCards(new ArrayList<Card>(), player, cardCount);
            } else if (card.getNumber() == 10) {
                // 自分の好きなカードを捨てる
                System.out.println("10捨て！");
                throwAway(player, playerIndex, cardCount, prevCards);
            } else if (card.getNumber() == 12) {
                // 指定したカードを全て捨てる
                System.out.println("12ボンバー！");
                bomber(player, playerIndex, cardCount, used, prevCards);
            }
        }
    }

    public void delivery(Player player, int index, ArrayList<Integer> used, ArrayList<Card> prevCards) {
        String res = "";
        int n = 0;
        for (int i = index + 1; i < q + index; i ++ ) {
            if (!used.contains(i % q)) {
                n = i % q;
                break;
            }
        }

        for (Card card : playTurn.get(index).selectCards(prevCards, this.scanner)) {
            playTurn.get((n) % q).addCard(card);
            res += card.showCard();
            res += " ";
        }
        System.out.printf("\nプレイヤー%dはプレイヤー%dに%sを渡しました。\n", player.getNumber(), playTurn.get(n).getNumber(), res);
    }

    public void throwAway(Player player, int index, int cardCount, ArrayList<Card> prevCards) {
        String res = "";
        for (Card card : playTurn.get(index).selectCards(prevCards, this.scanner)) {
            playTurn.get((index) % q).hands.remove(card);
            res += card.showCard();
            res += " ";
        }
        System.out.printf("10捨て！\nプレイヤー%dは%sを捨てました。\n", player.getNumber(), res);
    }

    public void bomber(Player player, int index, int cardCount, ArrayList<Integer> used, ArrayList<Card> prevCards) {
        String res = "";
        ArrayList<Card> throwCards = playTurn.get(index).selectCards(prevCards, this.scanner);
        for (int i = index; i < q + index; i ++ ) {
            if (used.contains(i)) {
                continue;
            }
        }
        for (Card throwCard : throwCards) {
            res += throwCard.showCard();
            for (Card card : playTurn.get(index % q).hands) {
                if (throwCard.getNumber() == card.getNumber()) {
                    playTurn.get(index % q).hands.remove(throwCard);
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
        System.out.printf("\nゲーム終了！\n");
        System.exit(0);
    }
}

