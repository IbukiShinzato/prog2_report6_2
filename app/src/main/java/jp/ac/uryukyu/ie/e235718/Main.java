package jp.ac.uryukyu.ie.e235718;

/**
 * Mainクラス
 * ゲームを実行する
 */
public class Main {
    /**
     * getPlayers() プレイヤー名の確認
     * dealout() 各プレイヤーにトランプを配る
     * turn() ゲームを開始
     * @param args 使用しない
     */
    public static void main(String[] args) {
        GameMaster test = new GameMaster();
        test.getPlayers();
        test.dealOut();
        test.turn();
    }
}

