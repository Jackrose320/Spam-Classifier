package perceptrons;
import java.util.HashMap;

/**
 * Extends {@link Perceptron1L} and Implements {@link Perceptron}. Has added
 * implementations for checking if a sentence is 'spammy'.
 */
public class SpamChecking extends Perceptron1L<String> {

    /**
     * Sets up default spam checker. <br>
     * default success name is "spam", <br>
     * default fail name is "fail".
     *
     * {@code this.rep} = new HashMap, <br>
     * {@code this.succeedTotal} = 0
     */
    public SpamChecking() {
        this.rep = new HashMap<>();
        this.setSuccessAndFail("spam", "ham");
        this.succeedTotal = 0;
    }

    /**
     * Sets up a spam checker with custom names.
     *
     * @param successName
     *            - name of {@code this.succeed}
     * @param failName
     *            - name of {@code this.fail}
     */
    public SpamChecking(String successName, String failName) {
        this.rep = new HashMap<>();
        this.setSuccessAndFail(successName, failName);
        this.succeedTotal = 0;
        this.failTotal = 0;
    }

    /**
     * Sets up a spam checker with custom names and data.
     *
     * @param succName
     *            - name of {@code this.succeed}
     * @param failName
     *            - name of {@code this.fail}
     * @param loader
     *            - another <b>Perceptron</b> to load data from
     */
    public SpamChecking(String succName, String failName, Perceptron1L<String> loader) {
        assert loader != null : "cannot load null value.";

        this.setSuccessAndFail(succName, failName);
        this.rep = loader.getRep();
        this.succeedTotal = loader.succeedTotal;
        this.failTotal = loader.failTotal;
    }

    @Override
    public Perceptron1L<String> newInstance() {
        // TODO Auto-generated method stub
        Perceptron1L<String> x = new SpamChecking();

        return x;
    }

    /**
     * Turns a String into an array of String words
     *
     * @param s
     *            - String of word(s)
     * @return s.split(\\W+)
     */
    public String[] sentenceToWords(String s) {
        assert s != null : "Violates: != null";
        s = s.toLowerCase();

        return s.split("\\W+");
    }

}
