
/**
 * Perceptron interface for creating a Naive Bayes model.
 *
 * @version 1.0
 * @author JACK C. ROSE
 *
 * @param <T>
 *            - Type of data (usually String)
 *
 */
public interface Perceptron<T> {

    @Override
    String toString();

    /**
     * Creates an object with the same <i>dynamic type</i> as {@code this}.
     *
     * @return <pre>a new object with default values</pre>
     */
    Perceptron<T> newInstance();

    /**
     * Sets the names of a model's <i>fail</i> and <i>success</i>.
     *
     * @param success
     *            - name of a success (cannot be {@code null})
     * @param fail
     *            - name of a fail (cannot be {@code null})
     */
    void setSuccessAndFail(String success, String fail);

    /**
     * The size of {@code this}, modeled by a {@code HashMap} of training set
     * items.
     *
     * @return <pre>size of the training set</pre>
     */
    int size();

    /**
     * A ratio of {@code this.succeed} divided by {@code this.fail}.
     *
     * @param arr
     *            - the array of items to find the total success ratio with
     * @return <pre>a total ratio of success to fail,
     * calculated via <b>Naive Bayes</b></pre>
     */
    double ratio(T[] arr);

}
