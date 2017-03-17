public class Empty implements Space{



  public Empty() {};

  public Player getPlayer() {
    return null;
  }

  public String getChar() {
    return "";
  }

  public boolean isEmpty()  {
    return true;
  }

  public boolean isPiece() {
    return false;
  }

  public boolean isKing() {
    return false;
  }

  public void king() {
  }





}
