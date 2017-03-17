import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by auroralamendola on 4/23/15.
 */
public class CheckersModelTest {

  CheckersModel initialBoard = new CheckersModel();

  void reset() {
    initialBoard = new CheckersModel();
  }



  @Test
  public void testIsGameOver() {
    assertEquals(initialBoard.isGameOver(), false);
  }

  @Test
  public void testGetThisPlayer() {
    assertEquals(initialBoard.getThisPlayer(), Player.First);

  }

  @Test
  public void testGetNextPlayer() {
    assertEquals(initialBoard.getNextPlayer(), Player.Second);
  }

  @Test
  public void testEmpty() {
    assertEquals(initialBoard.getBoard()[0][0].isEmpty(), false);
    assertEquals(initialBoard.getBoard()[0][1].isEmpty(), true);
    assertEquals(initialBoard.getBoard()[0][2].isEmpty(), false);
    assertEquals(initialBoard.getBoard()[4][4].isEmpty(), true);

  }

  @Test
  public void testPiece() {
    assertEquals(initialBoard.getBoard()[0][0].isPiece(), true);
    assertEquals(initialBoard.getBoard()[2][0].isPiece(), true);
    assertEquals(initialBoard.getBoard()[4][0].isPiece(), true);
    assertEquals(initialBoard.getBoard()[6][0].isPiece(), true);
    assertEquals(initialBoard.getBoard()[1][1].isPiece(), true);
    assertEquals(initialBoard.getBoard()[3][1].isPiece(), true);
    assertEquals(initialBoard.getBoard()[5][1].isPiece(), true);
    assertEquals(initialBoard.getBoard()[7][1].isPiece(), true);
    assertEquals(initialBoard.getBoard()[0][2].isPiece(), true);
    assertEquals(initialBoard.getBoard()[2][2].isPiece(), true);
    assertEquals(initialBoard.getBoard()[4][2].isPiece(), true);
    assertEquals(initialBoard.getBoard()[6][2].isPiece(), true);

    assertEquals(initialBoard.getBoard()[0][6].isPiece(), true);
    assertEquals(initialBoard.getBoard()[2][6].isPiece(), true);
    assertEquals(initialBoard.getBoard()[4][6].isPiece(), true);
    assertEquals(initialBoard.getBoard()[6][6].isPiece(), true);
    assertEquals(initialBoard.getBoard()[1][5].isPiece(), true);
    assertEquals(initialBoard.getBoard()[3][5].isPiece(), true);
    assertEquals(initialBoard.getBoard()[5][5].isPiece(), true);
    assertEquals(initialBoard.getBoard()[7][5].isPiece(), true);
    assertEquals(initialBoard.getBoard()[1][7].isPiece(), true);
    assertEquals(initialBoard.getBoard()[3][7].isPiece(), true);
    assertEquals(initialBoard.getBoard()[5][7].isPiece(), true);
    assertEquals(initialBoard.getBoard()[7][7].isPiece(), true);


  }


  @Test
  public void testInBounds() {
    assertEquals(initialBoard.inBounds(0, 0), true);
    assertEquals(initialBoard.inBounds(-1, 0), false);
    assertEquals(initialBoard.inBounds(0, -1), false);
    assertEquals(initialBoard.inBounds(8, 0), false);
    assertEquals(initialBoard.inBounds(0, 8), false);

  }



  @Test
  public void testIsAdjacent() {
    assertEquals(initialBoard.isAdjacent(1, 1, 2, 2), true);
    assertEquals(initialBoard.isAdjacent(1, 1, 0, 2), true);
    assertEquals(initialBoard.isAdjacent(1, 1, 2, 1), false);

  }



  @Test
  public void testIsForward() {
    reset();
    //Player 1
    assertEquals(initialBoard.isForward(2, 2, 1, 1), false);
    assertEquals(initialBoard.isForward(1, 1, 2, 2), true);

    //Player 2
    initialBoard.nextTurn();
    assertEquals(initialBoard.isForward(3, 5, 2, 6), false);
    assertEquals(initialBoard.isForward(2, 6, 3, 5), true);
  }


  @Test
  public void testJumpedSpace() {
    reset();
    assertEquals(initialBoard.jumped(0, 2), 1);
    assertEquals(initialBoard.jumped(1, 2), 1);
  }



  @Test
  public void testMove() {
    reset();
    assertEquals(initialBoard.getBoard()[2][2].isEmpty(), false);
    assertEquals(initialBoard.getBoard()[2][2].getPlayer(), Player.First);
    assertEquals(initialBoard.validSimple(2, 2, 3, 3), true);

    initialBoard.move(2, 2, 3, 3);
    assertEquals(true, initialBoard.getBoard()[3][3].isPiece());
    assertEquals(false, initialBoard.getBoard()[2][2].isPiece());

    initialBoard.move(5, 5, 4, 4);
    assertEquals(true, initialBoard.getBoard()[3][3].isPiece());

    assertEquals(initialBoard.jumped(3, 5), 4);

    assertEquals(initialBoard.validJump(3, 3, 5, 5), true);

    assertEquals(initialBoard.possibleJumpMoves(3, 3)[5][5], true);

    initialBoard.move(3, 3, 5, 5);
    assertEquals(initialBoard.getBoard()[4][4].isEmpty(), true);
    assertEquals(initialBoard.getSecondPlayerPieces(), 11);
    assertEquals(initialBoard.getFirstPlayerPieces(), 12);


  }

  @Test
  public void testPossibleMoves() {
    reset();
    for(int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        assertEquals(initialBoard.possibleSimpleMoves(0, 0)[x][y], false);
      }
    }
    assertEquals(initialBoard.validJump(0, 0, 2, 3), false);

    for(int x = 0; x < 8; x++) {
      assertEquals(initialBoard.possibleJumpMoves(0, 0)[x][3], false);
    }

    assertEquals(initialBoard.possibleSimpleMoves(0, 0)[2][5], false);
    assertEquals(initialBoard.possibleSimpleMoves(0, 0)[1][1], false);

    assertEquals(initialBoard.possibleSimpleMoves(2, 2)[3][3], true);
    assertEquals(initialBoard.validSimple(2, 2, 1, 3), true);
    assertEquals(initialBoard.possibleSimpleMoves(2, 2)[1][3], true);
    assertEquals(initialBoard.possibleSimpleMoves(2, 2)[3][1], false);
  }

  @Test
  public void testMovablePieces() {
    reset();
    assertEquals(initialBoard.getThisPlayer(), Player.First);
    assertEquals(initialBoard.getBoard()[2][2].getPlayer(), Player.First);
    //assertEquals(initialBoard.movablePieces()[0][0], false);
    assertEquals(initialBoard.movablePieces()[2][2], true);
    assertEquals(initialBoard.movablePieces()[0][7], false);
  }

  /*
  @Test
  public void testJumpable() {
    assertEquals(initialBoard.jumpable(initialBoard.getBoard()[1][1], initialBoard.getBoard()[2][2]),
    initialBoard.getBoard()[3][3]);

    assertEquals(initialBoard.jumpable(initialBoard.getBoard()[0][0], initialBoard.getBoard()[2][2]),
    initialBoard.getBoard()[0][0]);
  }*/
}