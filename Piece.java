/**
 * Created by auroralamendola on 4/23/15.
 */
public class Piece implements Space {

  public static enum PieceType {Single, King};

  private PieceType pieceType;
  private final Player player;
  private String pieceChar;

  public Piece(PieceType pieceType, Player player) {
    this.pieceType = pieceType;
    this.player = player;
    this.pieceChar = player.toChar();
  }

  public Player getPlayer() {
    return this.player;
  }


  public PieceType getPieceType() {
    return this.pieceType;
  }

  public String getChar() {
    return this.pieceChar;
  }

  public void king() {
    this.pieceType = PieceType.King;
    this.pieceChar = this.player.kingChar();
  }

  public boolean isEmpty() {
    return false;
  }

  public boolean isPiece() {
    return true;
  }

  public boolean isKing() {
    return this.getPieceType() == PieceType.King;
  }








}
