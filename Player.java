/**
 * Represents a draughts player.
 */
public enum Player {
  First {
    public Player other() {
      return Second;
    }

    public String toChar() {
      return "+";
    }

    public String kingChar() {
      return "#";
    }
  },

  Second {
    public Player other() {
      return First;
    }

    public String toChar() {
      return "o";
    }

    public String kingChar() {
      return "@";
    }
  };

  /**
   * Returns the player that is not this player.
   *
   * @return the other player
   */
  public abstract Player other();

  /**
   * Returns a character as a printable representation of this player.
   *
   * @return 'o' or '+'
   */
  public abstract String toChar();

  public abstract String kingChar();
}
