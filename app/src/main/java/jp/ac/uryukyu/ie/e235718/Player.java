package jp.ac.uryukyu.ie.e235718;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Playerクラス
 * String name; //プレイヤー名
 * int number; //プレイヤーの番号
 * int winNum; //順位
 * ArrayList<Card> hands; //手札
 * ArrayList<Integer> numbers; //出すカードのindex
 */
public class Player {
    private String name;
    private int number;
    public int winNum;
    public ArrayList<Card> hands;
    public ArrayList<Integer> numbers;

    /**
     * Playerのコンストラクタ
     * @param name プレイヤー名
     * @param number　　プレイヤーの番号
     */
    Player(String name, int number) {
        this.name = name;
        this.number = number;
        hands = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        hands.add(card);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setWinNum(int number) {
        this.winNum = number;
    }

    public ArrayList<String> showHand() {
        ArrayList<String> hand = new ArrayList<>();
        for (Card card : this.hands) {
            hand.add(card.showCard());
        }
        return hand;
    }

    public ArrayList<Card> selectCards(ArrayList<Card> prevCards, Scanner sc){
        System.out.println("-------------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.printf("\n出すカードが左から何番目かを1~%dの数字で入力してください。\nパスなら-1を入力してください。\n", hands.size());
        System.out.println(showHand());
        String inputLine = scanner.nextLine();
        ArrayList<Card> cards = new ArrayList<>();

        if (inputLine.equals("-1")) {
            return prevCards;
        } else {
            // スペースで分割して文字列配列に変換
            String[] numbersAsString = inputLine.split(" ");

            // 文字列配列を整数配列に変換
            numbers = new ArrayList<>();
            for (int i = 0; i < numbersAsString.length; i++) {
                numbers.add(Integer.parseInt(numbersAsString[i]));
            }

            for (int number : numbers) {
                cards.add(hands.get(number - 1));
            }
            return cards;
        }
    }
}


