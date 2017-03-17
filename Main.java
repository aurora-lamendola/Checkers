

import java.io.IOException;

/**
 * Provides a simple console UI for Connect Four.
 */
public final class Main {
  public static void main(String[] args) throws IOException {
    new Controller(new CheckersModel()).run();
  }
}