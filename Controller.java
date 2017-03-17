import java.io.IOException;
import java.util.Objects;

/**
 * Responsible for coordinating the connect_n.model and view while handling user input.
 */
public final class Controller {

    private final CheckersModel model;
    private final BoardView view;
    private final IntReader in;
    private final Appendable out;
    private final BoardViewModelInstance bvm;
    private int fromX;
    private int fromY;
    private static int minOption = 1;
    private int maxOption;

  /*
   * Messages:
   */

    public static final String NOT_A_NUMBER =
            "That isn't a number! Please try again.";

    public final String Option_DNE =
            "Not a valid choice. Choose a number between " + minOption + " and ";

    /**
     * Constructs a controller given a model. Uses the defaults for the view, input, and output.
     *
     * @param model the model to use for this controller
     */
    public Controller(CheckersModel model) {
        this(model,
                new BoardView(System.out),
                IntReader.create(System.in, System.out, NOT_A_NUMBER),
                System.out,
                new BoardViewModelInstance(model));
    }

    /**
     * Constructs a controller given the components it will control.
     *
     * @param model the model
     * @param view  the view, for rendering the model
     * @param in    the source of user moves
     * @param out   the output stream
     */
    public Controller(CheckersModel model, BoardView view, IntReader in, Appendable out,
                      BoardViewModelInstance bvm) {
        Objects.requireNonNull(model);
        Objects.requireNonNull(view);
        Objects.requireNonNull(in);
        Objects.requireNonNull(out);

        this.model = model;
        this.view = view;
        this.in = in;
        this.out = out;
        this.bvm = bvm;
    }

    /**
     * Determines whether the game is over.
     *
     * @return whether the game is over
     */
    public boolean isGameOver() {
        return model.isGameOver();
    }

    /**
     * Runs this controller on its model.
     *
     * @throws IOException if an I/O operation, such as writing to the console, fails
     */
    public void run() throws IOException {
        while (!isGameOver()) {
            step();
        }
    }

    /**
     * Runs one interaction step of this controller on its model.
     *
     * @throws IOException if an I/O operation, such as writing to the console, fails
     */
    public void step() throws IOException {

        String who = model.getThisPlayer().toChar();

        Integer count = 1;
        if (this.model.countTrue(this.model.possibleJumpMoves(fromX, fromY)) > 0) {
            if (this.model.getBoard()[fromX][fromY].isKing()) {
                this.bvm.bvm[fromX][fromY] = "[[" + count.toString() + "]]";
            } else {
                this.bvm.bvm[fromX][fromY] = "[" + count.toString() + "]";
            }
            count++;
        } else {
            for (int y = 7; y >= 0; y--) {
                for (int x = 0; x < 8; x++) {
                    if (this.model.movablePieces()[x][y]) {
                        if (this.model.getBoard()[x][y].isKing()) {
                            this.bvm.bvm[x][y] = "[[" + count.toString() + "]]";
                        } else {
                            this.bvm.bvm[x][y] = "[" + count.toString() + "]";
                        }
                        count++;
                    }
                }
            }
        }
        maxOption = count - 1;

        view.draw(bvm);

        int toX;
        int toY;

        //give view move options
        Integer where = in.nextInt("[" + who + "] " + "Choose a piece to move: ", this::validateMovable);
        fromX = this.getX(model.movablePieces(), where);
        fromY = this.getY(model.movablePieces(), where);

        for (int y = 7; y >= 0; y--){
            for (int x = 0; x < 8; x++) {
                if (this.model.movablePieces()[x][y]) {
                    this.bvm.bvm[x][y] = this.model.getBoard()[x][y].getChar();
                }
            }
        }


        if (this.model.countTrue(model.possibleJumpMoves(fromX, fromY)) > 0) {
            // give view move options
            Integer countJumps = 1;
            for (int y = 7; y >= 0; y--){
                for (int x = 0; x < 8; x++) {
                    reverseOption(x, y);
                    if (this.model.possibleJumpMoves(fromX, fromY)[x][y]) {
                        this.bvm.bvm[x][y] = "[" + countJumps.toString() + "]";
                        countJumps++;
                    }
                }
            }

            maxOption = countJumps - 1;

            drawMovingPiece();

            Integer to = in.nextInt("[" + who + "] " + "Choose where to move to: ", this::validateJumpMove);
            toX = this.getX(model.possibleJumpMoves(fromX, fromY), to);
            toY = this.getY(model.possibleJumpMoves(fromX, fromY), to);

            for (int y = 7; y >= 0; y--){
                for (int x = 0; x < 8; x++) {
                    if (this.model.possibleJumpMoves(fromX, fromY)[x][y]) {
                        this.bvm.bvm[x][y] = "";
                    }
                }
            }

            this.moveAndModel(toX, toY);
            this.bvm.bvm[this.model.jumped(fromX, toX)][this.model.jumped(fromY, toY)] = "";
            if (this.model.countTrue(this.model.possibleJumpMoves(toX, toY)) > 0) {
                this.fromX = toX;
                this.fromY = toY;
                this.step();
            } else {
                rotateModel();
                checkGameOver();
                this.model.nextTurn();
            }

        } else {
            // give view move options
            Integer countPaces = 1;
            for (int y = 7; y >= 0; y--){
                for (int x = 0; x < 8; x++) {
                    reverseOption(x, y);
                    if (this.model.possibleSimpleMoves(fromX, fromY)[x][y]) {
                        this.bvm.bvm[x][y] = "[" + countPaces.toString() + "]";
                        countPaces++;
                    }
                }
            }

            maxOption = countPaces - 1;

            drawMovingPiece();

            Integer to = in.nextInt("[" + who + "] " + "Choose where to move to: ", this::validateSimpleMove);
            toX = this.getX(model.possibleSimpleMoves(fromX, fromY), to);
            toY = this.getY(model.possibleSimpleMoves(fromX, fromY), to);

            for (int y = 7; y >= 0; y--){
                for (int x = 0; x < 8; x++) {
                    if (this.model.possibleSimpleMoves(fromX, fromY)[x][y]) {
                        this.bvm.bvm[x][y] = "";
                    }
                }
            }

            this.moveAndModel(toX, toY);
            this.rotateModel();
            checkGameOver();
            this.model.nextTurn();
        }
    }

    /**
     * Draws the piece that is being moved in a special format and then reverts
     * the board view model representation back to the model's representation
     * @throws IOException
     */
    public void drawMovingPiece() throws IOException {
        bvm.bvm[fromX][fromY] = "<" + this.model.getBoard()[fromX][fromY].getChar() + ">";
        view.draw(bvm);
        bvm.bvm[fromX][fromY] = this.model.getBoard()[fromX][fromY].getChar();
    }

    /**
     * Reverts the view representation of a piece at (@code) x and (@code) y
     * to its model representation
     * @param x, the x coordinate
     * @param y, the y coordinate
     */
    public void reverseOption(int x, int y) {
        if (this.model.movablePieces()[x][y]) {
            this.bvm.bvm[x][y] = this.model.getBoard()[x][y].getChar();
        }
    }

    /**
     * Moves the piece at (@code) from and (@code) fromY
     * to the space at (@code) toX and (@code) toY
     * PRECONDITION:  the space at toX and toY is empty
     * @param toX, the x coordinate of the empty space being moved to
     * @param toY, the y coordinate of the empty space being moved to
     */
    public void moveAndModel(int toX, int toY) {
        model.move(fromX, fromY, toX, toY);
        //kings the moved piece if it reaches last row
        if (toY == 7) {
            this.model.getBoard()[toX][toY].king();
        }
        this.bvm.bvm[toX][toY] = this.model.getBoard()[toX][toY].getChar();
        this.bvm.bvm[fromX][fromY] = "";
    }

    /**
     * Rotates the model and board view model by 180 degrees
     */
    public void rotateModel() {
        this.model.flipOverXAxis();
        this.bvm.flipOverXAxis();
        this.model.flipOverYAxis();
        this.bvm.flipOverYAxis();
    }

    /**
     * If the game is over, returns a winner message stating who won
     * @throws IOException
     */
    public void checkGameOver() throws IOException {
        if (model.isGameOver()) {
            Player winner = this.model.getThisPlayer();
            String winnerChar = "(" + winner.toChar() + ")";
            StringBuffer winnerMessage = new StringBuffer();
            winnerMessage.append(winner);
            winnerMessage.append(" player ");
            winnerMessage.append(winnerChar);
            winnerMessage.append(" wins!");
            winnerMessage.append("\n");
            this.out.append(winnerMessage);
        }
    }

    /**
     * Checks whether a particular move is a valid jump move in the current model.
     * Returns an error message if the move is invalid, or {@code null} if the move is valid.
     *
     * @param to the move to check
     * @return {@code null} or an error message
     */
    private String validateJumpMove(int to) {
        if (to > this.model.countTrue(this.model.possibleJumpMoves(fromX, fromY)) || to <= 0) {
            return Option_DNE + maxOption + ".";
        }
        // It's valid!
        return null;
    }

    /**
     * Checks whether a particular move is a valid simple move in the current model.
     * Returns an error message if the move is invalid, or {@code null} if the move is valid.
     *
     * @param to the move to check
     * @return {@code null} or an error message
     */
    private String validateSimpleMove(int to) {
        if (to > this.model.countTrue(this.model.possibleSimpleMoves(fromX, fromY)) || to <= 0) {
            return Option_DNE + maxOption + ".";
        }
        // It's valid!
        return null;
    }

    /**
     * Checks whether a particular space has a movable piece in the current model.
     * Returns an error message if the space is invalid, or {@code null} if the space is valid.
     *
     * @param where the space to check
     * @return {@code null} or an error message
     */
    private String validateMovable(int where) {
        if (where > this.model.countTrue(this.model.movablePieces()) || where <= 0) {
            return Option_DNE + maxOption + ".";
        }
        // It's valid!
        return null;
    }


    /**
     * Gets the x coordinate of the space that corresponds to the (@code) selection,
     * where options are represented from top to bottom and left to right so that
     * 1 is in the top left and the last option is in the bottom right.
     * @param movables, the 2D array of which spaces are valid options
     * @param selection, the option selected, as an int
     * @return the x coordinate of the selected option
     */
    public int getX(boolean[][] movables, int selection) {
        int count = 1;
        int result = 1;
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                if (movables[x][y]) {
                    if (count == selection) {
                        return result = x;
                    } else {
                        count++;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Gets the y coordinate of the space that corresponds to the (@code) selection,
     * where options are represented from top to bottom and left to right so that
     * 1 is in the top left and the last option is in the bottom right.
     * @param movables, the 2D array of which spaces are valid options
     * @param selection, the option selected, as an int
     * @return the y coordinate of the selected option
     */
    public int getY(boolean[][] movables, int selection) {
        int count = 1;
        int result = 1;
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                if (movables[x][y]) {
                    if (count == selection) {
                        return result = y;
                    } else {
                        count++;
                    }
                }
            }
        }
        return result;
    }
}