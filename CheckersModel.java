import static java.lang.Math.abs;

public class CheckersModel {

  private Player player;
  private Space[][] board;
  private int firstPlayerPieces;
  private int secondPlayerPieces;

  public CheckersModel() {
    player = Player.First;
    board = new Space[8][8];
    firstPlayerPieces = 0;
    secondPlayerPieces = 0;

    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        board[x][y] = new Empty();
      }
    }

    /*
    Initializes the board:
    Rows 1-3 First player pieces, starts at x=2, alternating, checkered
    Rows 6-8 Second player pieces, starts at x=1, alternating, checkered
     */
    for (int x = 0; x < 8; x++) {
      if (x % 2 == 0) {
        //Add First player pieces to rows 1 and 3
        board[x][0] = new Piece(Piece.PieceType.Single, Player.First);
        firstPlayerPieces = firstPlayerPieces + 1;
        board[x][2] = new Piece(Piece.PieceType.Single, Player.First);
        firstPlayerPieces = firstPlayerPieces + 1;

        //Add Second player pieces to row 7
        board[x][6] = new Piece(Piece.PieceType.Single, Player.Second);
        secondPlayerPieces = secondPlayerPieces + 1;

      } else {
        //Add First player pieces to row 2
        board[x][1] = new Piece(Piece.PieceType.Single, Player.First);
        firstPlayerPieces = firstPlayerPieces + 1;

        //Add Second player pieces to rows 6 and 8
        board[x][5] = new Piece(Piece.PieceType.Single, Player.Second);
        secondPlayerPieces = secondPlayerPieces + 1;
        board[x][7] = new Piece(Piece.PieceType.Single, Player.Second);
        secondPlayerPieces = secondPlayerPieces + 1;

      }
    }
  }

  public Space[][] getBoard() {
    return this.board.clone();
  }

  public int getSecondPlayerPieces() {
    return this.secondPlayerPieces;
  }

  public int getFirstPlayerPieces() {
    return this.firstPlayerPieces;
  }

  public void losePiece(Player p) {
    if(p == Player.First) {
      this.firstPlayerPieces = this.firstPlayerPieces - 1;
    } else {
      this.secondPlayerPieces = this.secondPlayerPieces - 1;
    }
  }

  /**
   * Checks if the game is over: if either player has no pieces left
   *
   * @return true or false
   */
  public boolean isGameOver() {
    return this.getFirstPlayerPieces() == 0 || this.getSecondPlayerPieces()  == 0;
  }


  public Player getThisPlayer() {
    return this.player;
  }

  public Player getNextPlayer() {
    return this.player.other();
  }

  /**
   * Changes the player
   */
  public void nextTurn() {
    this.player = this.getThisPlayer().other();
  }

  /**
   * Checks if a position is in bounds (8x8 matrix)
   *
   * @param xTo, the x coordinate of the sapce
   * @param yTo  the y coordinate of the space.
   * @return true or false
   */
  public boolean inBounds(int xTo, int yTo) {
    return !(xTo < 0 || xTo >= 8 || yTo < 0 || yTo >= 8);
  }


  //checks if the new space is in the right direction for the piece
  public boolean isForward(int xFrom, int yFrom, int yTo) {
    return this.getBoard()[xFrom][yFrom].isKing() || yTo > yFrom;
  }

  //checks if that space is adjacent to this space
  boolean isAdjacent(int xFrom, int yFrom, int xTo, int yTo) {
    return (abs(xFrom - xTo) == 1 &&
            abs(yFrom - yTo) == 1);
  }

  //checks if this move is a simple move (no jumping)
  public boolean validSimple(int xFrom, int yFrom, int xTo, int yTo) {
    //checks Piece
    return !this.getBoard()[xFrom][yFrom].isEmpty() &&
            this.isForward(xFrom, yFrom, yTo) &&
            this.isAdjacent(xFrom, yFrom, xTo, yTo) &&
            this.getBoard()[xTo][yTo].isEmpty();
  }

  //checks if this move is a valid jumping move
  public boolean validJump(int xFrom, int yFrom, int xTo, int yTo) {
    return !this.getBoard()[xFrom][yFrom].isEmpty() &&
            this.isForward(xFrom, yFrom, yTo) &&
            this.getBoard()[xTo][yTo].isEmpty() &&
            abs(xFrom - xTo) == 2 &&
            abs(yFrom - yTo) == 2 &&
            this.getBoard()[this.jumped(xFrom, xTo)][this.jumped(yFrom, yTo)].getPlayer() == this.getNextPlayer();
  }

  //finds the coordinate of the piece to be jumped
  public int jumped(int From, int To) {
    if (From - To == 2) {
      return From - 1;
    } else {
      return From + 1;
    }
  }

  public void move(int xFrom, int yFrom, int xTo, int yTo) {
    if (this.validSimple(xFrom, yFrom, xTo, yTo)) {
      this.board[xTo][yTo] = this.getBoard()[xFrom][yFrom];
      this.board[xFrom][yFrom] = new Empty();
    }
    else if(this.validJump(xFrom, yFrom, xTo, yTo)) {
      this.board[xTo][yTo] = this.getBoard()[xFrom][yFrom];
      this.board[xFrom][yFrom] = new Empty();
      this.board[jumped(xFrom, xTo)][jumped(yFrom, yTo)] = new Empty();
      this.losePiece(this.getThisPlayer().other());
    } else {
      throw new IllegalArgumentException("Invalid Move");
    }
  }

  public boolean[][] movablePieces() {
    boolean[][] movablesJump = new boolean[8][8];
    boolean[][] movablesSimple = new boolean[8][8];

    for(int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        if(this.getBoard()[x][y].getPlayer() == this.getThisPlayer()) {
          if (this.countTrue(this.possibleJumpMoves(x,y)) > 0) {
            movablesJump[x][y] = true;
          }
          else if (this.countTrue(this.possibleSimpleMoves(x,y)) > 0) {
            movablesSimple[x][y] = true;
          }
        }
      }
    }

    if (this.countTrue(movablesJump) > 0) {
      return movablesJump;
    } else {
      return movablesSimple;
    }

  }

  public int countTrue(boolean[][] matrix) {
    int contains = 0;
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        if(matrix[x][y]) {
          contains = contains + 1;
        }
      }
    }
    return contains;
  }




  public boolean[][] possibleSimpleMoves(int xFrom, int yFrom) {
    boolean[][] simpleMoves = new boolean[8][8];


    for(int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        if(this.validSimple(xFrom, yFrom, x, y)) {
          simpleMoves[x][y] = true;
        }
      }
    }
    return simpleMoves;
  }

  public boolean[][] possibleJumpMoves(int xFrom, int yFrom) {
    boolean[][] jumpMoves = new boolean[8][8];
    for(int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        if (this.validJump(xFrom, yFrom, x, y)) {

          jumpMoves[x][y] = true;
        }
      }
    }
    return jumpMoves;
  }

  public void flipOverXAxis() {
    Space[][] temp = new Space[8][8];
    for (int x = 0; x < 8; x++) {
      System.arraycopy(this.board[x], 0, temp[x], 0, 8);
    }

    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        this.board[x][y] = temp[x][7 - y];
      }
    }
  }

  public void flipOverYAxis() {
    Space[][] temp = new Space[8][8];
    for (int x = 0; x < 8; x++) {
      System.arraycopy(this.board[x], 0, temp[x], 0, 8);
    }

    for (int x = 0; x < 8; x++) {
      System.arraycopy(temp[7 - x], 0, this.board[x], 0, 8);
    }
  }
}
