package jp.ac.uryukyu.ie.e235718;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;

public class GameMasterTest {
    @Test
    void checkDealOut() {
        String simulatedUserInput = "4\nIbuki\n1\n2\n3\n7 8\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);
        GameMaster test = new GameMaster();
        test.dealOut();
        assertEquals(test.players[0].hands.size(), 14);
    }

    @Test
    void checkCard() {
        Card numCard = new Card("♥8");
        Card jokerCard = new Card("Joker");
        assertEquals("♥", numCard.getType());
        assertEquals(8, numCard.getNumber());
        assertEquals("Joker", jokerCard.showCard());
    }
}