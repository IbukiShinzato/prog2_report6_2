package jp.ac.uryukyu.ie.e235718;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameMasterTest {
    @Test
    void checkDealOut() {
        GameMaster test = new GameMaster();
        test.dealOut();
        assertEquals(test.players[0].hands.size(), 10);
    }

    @Test
    void checkHandsSize() {
        Card numCard = new Card("♥8");
        Card jokerCard = new Card("Joker");
        assertEquals("♥", numCard.getType());
        assertEquals(8, numCard.getNumber());
        assertEquals("Joker", jokerCard.showCard());
    }
}