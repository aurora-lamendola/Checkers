import java.io.InputStream;

public class AGameRunner implements NetworkClientTester.GameRunner {

    public static final String NOT_A_NUMBER =
            "That isn't a number! Please try again.";

    @Override
    public void runGame(InputStream input, Appendable output) throws Exception {
        CheckersModel model = new CheckersModel();
        new Controller(model, new BoardView(output),
                IntReader.create(input, System.out, NOT_A_NUMBER),
                output, new BoardViewModelInstance(model)).run();
    }
}
