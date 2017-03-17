import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by klayton on 4/24/2015.
 */
public class BoardViewModelInstance implements ReadOnlyBoardViewModel {

    public String[][] bvm;

    /**
     * Creates a BoardViewModelInstance that is initialized to match the (@code) model's
     * representation of spaces
     */
    BoardViewModelInstance(CheckersModel model) {
        bvm = new String[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Space space = model.getBoard()[x][y];
                this.bvm[x][y] = space.getChar();
            }
        }
    }

    @Override
    public String get(int row, int column, int width) {
        return bvm[column][row];
    }

    @Override
    public Iterator<Integer> rows() {
        ArrayList<Integer> anArrList  = new ArrayList<>();
        for (int i = 7; i >= 0; i--) {
            anArrList.add(i);
        }
        return anArrList.iterator();
    }

    @Override
    public Iterator<Integer> columns() {
        ArrayList<Integer> anArrList  = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            anArrList.add(i);
        }
        return anArrList.iterator();
    }

    @Override
    public Boolean isValidPosition(int x, int y) {
        return (x >= 0) &&(x < 8) && (y >= 0) && (y < 8);
    }

    @Override
    public void flipOverXAxis() {
        String[][] temp = new String[8][8];
        for (int x = 0; x < 8; x++) {
            System.arraycopy(this.bvm[x], 0, temp[x], 0, 8);
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.bvm[x][y] = temp[x][7 - y];
            }
        }
    }

    @Override
    public void flipOverYAxis() {
        String[][] temp = new String[8][8];
        for (int x = 0; x < 8; x++) {
            System.arraycopy(this.bvm[x], 0, temp[x], 0, 8);
        }

        for (int x = 0; x < 8; x++) {
            System.arraycopy(temp[7 - x], 0, this.bvm[x], 0, 8);
        }
    }
}
