package jp.ac.uryukyu.ie.e235718;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String name;
    private int number;
    public int winNum;
    public ArrayList<Card> hands;
    public ArrayList<Integer> numbers;

    Player(String name, int number) {
        // List<String> names1 = new ArrayList<String>("空条承太郎", "東方仗助", "吉良吉影", "DIO",
        // "岸辺露伴", "リサリサ", "イギー");
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

    public ArrayList<Card> selectCards(){
        Scanner sc = new Scanner(System.in);
        System.out.printf("どのカードを出しますか？\n出すカードが左から何番目かを1~%dの数字で入力してください。\nパスなら-1を入力してください。\n", hands.size());
        System.out.println(showHand());
        String inputLine = sc.nextLine();
        ArrayList<Card> cards = new ArrayList<>();

        if (inputLine.equals("-1")) {
            cards = new ArrayList<>();
            cards.add(new Card("-1"));
            return cards;
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

    // public boolean judge(boolean ok, ArrayList<Card> nowCards) {
    //     if (pastCards.contains(new Card("00"))) {
    //         return true;
    //     } else {
    //         String searchString = hands.get(0).getType();
    //         int searchNumber = hands.get(0).getNumber();
    //         int count = 0;
    //         for (int number : numbers) {
    //             if ((!hands.get(number - 1).getType().equals(searchString) || hands.get(number - 1).getNumber() != searchNumber + count) || hands.get(number).getNumber() != searchNumber) {
    //                 return false;
    //             }

    //             if (number > hands.size()) {
    //                 System.out.println("正しい数字を入力してください。");
    //                 return false;
    //             }
    
    //             if (pastCards.size() != nowCards.size()) {
    //                 return false;
    //             }
    
    //             // 手動で最大値のインデックスを探す
    //             int maxIndex = -1;
    //             int maxValue = Integer.MIN_VALUE;
    
    //             for (int i = 0; i < numbers.size(); i++) {
    //                 if (numbers.get(i) > maxValue) {
    //                     maxValue = numbers.get(i);
    //                     maxIndex = i;
    //                 }
    //             }
    
    //             if (hands.get(number).getNumber() <= cards.get(maxIndex).getNumber()) {
    //                 return false;
    //             }
    //             count += 1;
    
    //             return true;
    //         }
    //     }
    // }
}

// } else if (showHand().contains("00")) {
//     return cards;


// String searchString = hands.get(0).getType();
// int searchNumber = hands.get(0).getNumber();
// int count = 0;
// for (int number : numbers) {
//     if ((!hands.get(number - 1).getType().equals(searchString) || hands.get(number - 1).getNumber() != searchNumber + count) || hands.get(number).getNumber() != searchNumber) {
//         return false;
//     }
//     if (number > hands.size()) {
//         System.out.println("正しい数字を入力してください。");
//         return false;
//     }

//     if (hands.size() != cards.size()) {
//         return false;
//     }

//     // 手動で最大値のインデックスを探す
//     int maxIndex = -1;
//     int maxValue = Integer.MIN_VALUE;

//     for (int i = 0; i < numbers.size(); i++) {
//         if (numbers.get(i) > maxValue) {
//             maxValue = numbers.get(i);
//             maxIndex = i;
//         }
//     }

//     if (hands.get(number).getNumber() <= cards.get(maxIndex).getNumber()) {
//         return false;
//     }
//     count += 1;

//     return true;
// }
// }
// // return true;

