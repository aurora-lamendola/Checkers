import org.junit.Test;

import java.io.IOException;

public class NetworkClientTest {

    public static final String NOT_A_NUMBER =
            "That isn't a number! Please try again.";

    NetworkClientTester.GameRunner gameRunner = (input, output) -> {
        CheckersModel model = new CheckersModel();
        BoardView view = new BoardView(output);
        BoardViewModelInstance bvm = new BoardViewModelInstance(model);
        IntReader in = IntReader.create(input, output, NOT_A_NUMBER);
        Controller c = new Controller(model, view, in, output, bvm);
        while(!c.isGameOver()){
            c.step();
        }
    };

    @Test
    public void testGameMatch1() throws IOException {
        NetworkClientTester.assertGameMatches(gameRunner, 1, 2, 1, 2, 1, 1, 2, 3, 4, 1);
    }


    @Test
    public void testGameMatch2() throws IOException {
        NetworkClientTester.assertGameMatches(gameRunner, 1, 2, 1, 2, 1, 1, 2, 3, 4, 1,
                1, 2, 1, 2, 1, 1, 2, 3, 4, 1, 1, 2, 1, 2, 1, 1, 2, 3, 4, 1,
                1, 2, 1, 2, 1, 1, 2, 3, 4, 1, 1, 2, 1, 2, 1, 1, 2, 3, 4, 1,
                1, 2, 1, 2, 1, 1, 2, 3, 4, 1, 1, 2, 1, 2, 1, 1, 2, 3, 4, 1);
    }

    @Test
    public void testGameMatch3() throws IOException {
        NetworkClientTester.assertGameMatches(gameRunner, 1, 2, 0.5, 2, 1, 1, 2, 3, 4, 1,
                1, 2, 1, 2, 1, -1, 0, 2, 3, 4, 1, "b", 2, 1, 2, 1, 1, 2, 3, 4, 1,
                1, 2, "a", 2, 1, 1, new Empty(), 3, 4, 1, 0, 2, 1, 134, 1, 1, 2, 3, 4, 1,
                1, -152, 1, 2, -1, 1, 2, 3, 4, 1, 1093, 2, 1.0, 2, 1.5, 1, 2.0, 3, 4, 1,
                1, 2, 0.5, 2, 1, 1, 2, 3, 4, 1,
                1, 2, 1, 2, 1, -1, 0, 2, 3, 4, 1, "b", 2, 1, 2, 1, 1, 2, 3, 4, 1,
                1, 2, "a", 2, 1, 1, new Empty(), 3, 4, 1, 0, 2, 1, 134, 1, 1, 2, 3, 4, 1,
                1, -152, 1, 2, -1, 1, 2, 3, 4, 1, 1093, 2, 1.0, 2, 1.5, 1, 2.0, 3, 4, 1,
                1, 2, 0.5, 2, 1, 1, 2, 3, 4, 1,
                1, 2, 1, 2, 1, -1, 0, 2, 3, 4, 1, "b", 2, 1, 2, 1, 1, 2, 3, 4, 1,
                1, 2, "a", 2, 1, 1, new Empty(), 3, 4, 1, 0, 2, 1, 134, 1, 1, 2, 3, 4, 1,
                1, -152, 1, 2, -1, 1, 2, 3, 4, 1, 1093, 2, 1.0, 2, 1.5, 1, 2.0, 3, 4, 1,
                1, 2, 0.5, 2, 1, 1, 2, 3, 4, 1,
                1, 2, 1, 2, 1, -1, 0, 2, 3, 4, 1, "b", 2, 1, 2, 1, 1, 2, 3, 4, 1,
                1, 2, "a", 2, 1, 1, new Empty(), 3, 4, 1, 0, 2, 1, 134, 1, 1, 2, 3, 4, 1,
                1, -152, 1, 2, -1, 1, 2, 3, 4, 1, 1093, 2, 1.0, 2, 1.5, 1, 2.0, 3, 4, 1);
    }

    @Test
    public void testGameMatch4() throws IOException {
        NetworkClientTester.assertGameMatches(gameRunner, 1, 2, 0.5, 2, 1, 1, 2, 3, 4, 1,
                342, 2, -15, 2, 1, -1, 16, "5", 3, '1', 1, "b", 2, 1, 2, 1, 1, 2, 3, 4, 1,
                '3', 2, "a", 5, 1, 1, new Empty(), 3, 4, 1, 0, 2, 1, 134, 1, 1, 2, 3, 4, 1,
                1, -152, 1, 2, -1, 1, 2, 3, 4, 1, 1093, 2, 1.0, 2, 1.5, 1, 2.0, 3, 4, 1,
                1, 2, 0.5, 2, 1, 1, 2, 3, 4, 1,
                1, 2, 1, 2, 1, -1, 0, 2, 3, 4, 1, "b", 2, 1, 2, 1, 1, 2, 3, 4, 1,
                1, 2, "a", 2, 1, 1, 1, 3, 4, 1, 0, 2, 1, 134, 1, 1, 2, 3, 4, 1,
                1, -152, 1, 2, -1, 1, 2, 3, 4, 1, 1093, 2, 1.0, 2, 1.5, 1, 2.0, 3, 4, 1,
                1, 2, 0.5, 2, 1, 1, 2, 3, 4, 1,
                1, 2, 1, 2, 1, -1, 0, 2, 3, 4, 1, "b", 2, 1, 2, 1, 1, 2, 3, 4, 1,
                1, 2, "a", 2, 1, 1, new Empty(), 3, 4, 1, 0, 2, 1, 134, 1, 1, 2, 3, 4, 1,
                1, -152, 1, 2, -1, 1, 2, 3, 4, 1, 1093, 2, 1.0, 2, 1.5, 1, 2.0, 3, 4, 1,
                1, 2, 0.5, 2, 8, 1, 2, 3, 4, 1,
                1, 2, 1, 2, 1, -1, 0, 2, 3, 4, 1, "b", 2, 1, 2, 1, 1, 2, 3, 4, 1,
                1, 2, "a", 2, 1, 1, new Empty(), 3, 4, 1, 0, 2, 1, 134, 1, 1, 2, 3, 4, 1,
                1, -152, 1, 2, -1, 1, 2, 3, 4, 1, 1093, 2, 1.0, 2, 1.5, 1, 2.0, 3, 4, 1);
    }

    @Test
    public void testGameMatch() throws IOException {
        NetworkClientTester.assertGameMatches(gameRunner, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    }

    @Test
    public void testGameMatch5() throws IOException {
        NetworkClientTester.assertGameMatches(gameRunner, "Not an int");
    }
}
