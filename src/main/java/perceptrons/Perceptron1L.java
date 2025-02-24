package perceptrons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Implements {@link Perceptron} for creating a Naive Bayes model that allows
 * for the addition of and removal of probabilities, useful for finding
 * <i>Naive-Bayes</i> ratios and reporting success/fails of <i>Naive-Bayes</i>
 * <a href="https://www.ibm.com/think/topics/naive-bayes">classifier</a> trials.
 * Gets extended by {@link SpamChecking}.
 *
 * @param <T>
 *            - Type of data (usually String)
 */
public abstract class Perceptron1L<T> implements Perceptron<T> {

    /**
     * VARIABLES
     */

    /**
     * Representation of data.
     */
    protected Map<T, int[]> rep;
    /**
     * Number of successes in data.
     */
    protected int succeedTotal;
    /**
     * Number of fails in data.
     */
    protected int failTotal;

    /**
     * The 'success' name for the model.
     */
    public String success = "Success";
    /**
     * The 'failure' name for the model.
     */
    public String fail = "Fail";

    /**
     * CONSTRUCTORS
     */

    /**
     * {@code this.rep}= new {@code HashMap<>()},<br>
     * {@code (this.succeedTotal, this.failTotal)} = 0
     */
    public Perceptron1L() {
        this.rep = new HashMap<>();
        this.succeedTotal = 0;
    }

    /**
     * {@code this.rep}= new {@code HashMap<>()},<br>
     * {@code (this.succeedTotal, this.failTotal)} = 0, <br>
     * {@code this.success} = successName,<br>
     * {@code this.fail} = failName
     *
     * @param successName
     *            - the name for a 'succeed' classification
     * @param failName
     *            - the name for a 'fail' classification
     */
    public Perceptron1L(String successName, String failName) {
        this.rep = new HashMap<>();
        this.setSuccessAndFail(successName, failName);
        this.succeedTotal = 0;
        this.failTotal = 0;
    }

    /**
     * {@code this.rep} = new {@code HashMap<>()}, <br>
     * {@code (this.succeedTotal, this.failTotal) = (loader.succeedTotal, loader.failTotal)},
     * <br>
     * {@code this.success} = successName, <br>
     * {@code this.fail} = failName
     *
     * @param successName
     *            - the name for a 'succeed' classification
     * @param failName
     *            - the name for a 'fail' classification
     * @param loader
     *            - another {@code Perceptron1L} to load from
     */
    public Perceptron1L(String successName, String failName, Perceptron1L<T> loader) {
        assert loader != null : "cannot load null value.";

        this.setSuccessAndFail(successName, failName);
        this.rep = loader.getRep();
        this.succeedTotal = loader.succeedTotal;
        this.failTotal = loader.failTotal;
    }

    /**
     * KERNEL METHODS
     */

    /**
     * Cannot be called (this is an <i>abstract</i> class).
     */
    @Override
    public abstract Perceptron1L<T> newInstance();

    /**
     * Transfers data from this2 to {@code this}.
     *
     * @param this2
     *            - another Perceptron1L
     */
    protected void transferFrom(Perceptron1L<T> this2) {
        assert this2 != null : "cannot transfer null";

        this.success = this2.success;
        this.fail = this2.fail;
        this.rep = this2.rep;
        this.succeedTotal = this2.succeedTotal;
        this.failTotal = this2.failTotal;

    }

    @Override
    public double ratio(T[] arr) {
        // TODO Auto-generated method stub

        double[] counts = new double[this.rep.size()];
        int indexEntry = 0;

        for (Entry<T, int[]> entry : this.rep.entrySet()) {
            int n = 0;
            for (T item : arr) {
                if (entry.getKey().equals(item)) {
                    n++;
                }
            }
            counts[indexEntry] = Math
                    .pow(((entry.getValue()[0] + 1.0) / (this.succeedTotal + 1))
                            / ((entry.getValue()[1] + 1.0) / (this.failTotal + 1)), n);
            if (counts[indexEntry] < 0) {
                System.out.println(
                        "Word fail: " + entry.getKey() + " - " + counts[indexEntry]);
            }
            indexEntry++;
        }

        double ratio = 1;
        for (double x : counts) {
            ratio *= x;
        }

        return ratio;
    }

    @Override
    public void setSuccessAndFail(String succ, String fail) {
        assert succ != null : "success name cannot be null.";
        assert fail != null : "fail name cannot be null.";
        // TODO Auto-generated method stub
        this.success = succ;
        this.fail = fail;
    }

    @Override
    public int size() {
        return this.rep.size();
    }

    /**
     * Overrides {@code Object.toString()} in the format:<br>
     * <i>{key(success_count,fail_count), ...}</i>
     */
    @Override
    public String toString() {
        String s = "{";

        for (Map.Entry<T, int[]> m : this.rep.entrySet()) {
            s = s.concat(m.getKey().toString());
            s = s.concat("(" + m.getValue()[0] + ", " + m.getValue()[1] + "),");
        }
        if (this.rep.size() > 0) {
            s = s.substring(0, s.length() - 1);
        }
        s = s.concat("}");

        return s;
    }

    /**
     * SECONDARY METHODS
     */

    /**
     * Uses {@code ratio(arr)} to tell if it is a success.
     *
     * @param arr
     *            - an array of items to test
     * @return <pre>true if ratio(arr) > 1, else false</pre>
     */
    public boolean success(T[] arr) {
        // TODO Auto-generated method stub
        return this.ratio(arr) > 1;
    }

    /**
     * Uses {@code ratio(arr)} to tell if it is a success. <br>
     * {@code this.rep} is <b>UPDATED</b> based on {@code success}
     *
     * @param arr
     *            - an array of items to test
     * @param success
     *            - the true classification
     * @return <pre>true if ratio(arr) > 1, else false</pre>
     */
    public boolean success(T[] arr, boolean success) {
        // TODO Auto-generated method stub
        return this.ratio(arr, success) > 1;
    }

    /**
     * Must be used to update the {@code this.successTotal} and
     * {@code this.failTotal} if calls to addProb() were made.
     *
     * @deprecated
     */
    @Deprecated
    public void updateTotal() {
        for (Entry<T, int[]> entry : this.rep.entrySet()) {
            this.succeedTotal += entry.getValue()[1];
            this.failTotal += entry.getValue()[0];
        }
    }

    /**
     * Returns the top {@code num} items in {@code this.rep}.
     *
     * @param num
     *            - length of the List&lt;T&gt; returned (num > 0)
     * @return <pre>a list of the n highest items</pre>
     */
    public List<T> topItems(int num) {
        assert num > 0 : "Cannot return nothing!";

        List<T> topItems = new ArrayList<>(num);

        for (Entry<T, int[]> entry : this.rep.entrySet()) {
            double ratio = (entry.getValue()[0] + 1.0) / (entry.getValue()[1] + 1.0);
            int i = 0;
            boolean entered = false;

            while (!entered && i < num) {

                int[] compared = { 0, 0 };
                if (topItems.size() > i) {
                    compared = this.rep.get(topItems.get(i));
                }

                double comparedRatio = (compared[0] + 1.0) / (compared[1] + 1.0);

                if (comparedRatio < ratio) {
                    if (topItems.size() > i) {
                        topItems.set(i, entry.getKey());
                    } else {
                        topItems.add(i, entry.getKey());
                    }
                    entered = true;
                }

                i++;
            }
        }

        return topItems;
    }

    /**
     *
     * Overloaded version of {@code ratio(T[] arr)} with 'as-it-goes' training.
     * <br>
     * Updates {@code this.rep} based on {@code success}
     *
     * @param arr
     *            - the array of items to find the total success ratio with
     * @param success
     *            - the true classifiction of <i>arr</i>
     * @return <pre>total ratio of success to fail</pre>
     */
    public double ratio(T[] arr, boolean success) {
        // TODO Auto-generated method stub

        double[] counts = new double[this.rep.size()];
        List<T> newEntries = new ArrayList<>();
        int indexEntry = 0;

        for (Entry<T, int[]> entry : this.rep.entrySet()) {
            int n = 0;
            for (T item : arr) {
                if (entry.getKey().equals(item)) {
                    n++;
                }
            }
            counts[indexEntry] = Math
                    .pow((entry.getValue()[0] + 1.0 / (this.succeedTotal + 1))
                            / (entry.getValue()[1] + 1.0 / (this.failTotal + 1)), n);
            indexEntry++;
        }
        for (T item : arr) {
            if (!this.rep.entrySet().contains(item)) {
                newEntries.add(item);
            }

        }

        double ratio = 1;
        for (double x : counts) {
            ratio *= x;
        }

        if (this.rep.size() > 0) {
            this.updateCounts(success, arr);
        }

        for (T item : newEntries) {
            if (success) {
                this.addProb(item, 1, 0);
            } else {
                this.addProb(item, 0, 1);
            }
        }

        return ratio;
    }

    /**
     * Takes an element with fails and successes and adds it to this.rep's
     * corresponding k/v pair.
     *
     * @param name
     *            - name of the element
     * @param count1
     *            - number of successes
     * @param count2
     *            - number of fails
     *
     */
    public void addProb(T name, int count1, int count2) {
        int[] counts = { count1, count2 };

        if (this.rep.containsKey(name)) {
            int[] values = this.rep.get(name);
            values[0] += counts[0];
            values[1] += counts[1];

            this.rep.replace(name, values);
        } else {
            this.rep.putIfAbsent(name, counts);
        }
    }

    /**
     * Outputs the success fail ratio of {@code name}
     *
     * @param name
     *            - name of the element
     * @return <pre>(name.successes)/(name.fails)
     * if #this.contains(name),
     * else (1.0)</pre>
     */
    public double getItemRatio(T name) {

        Double n1 = null;
        Double n2 = null;
        if (this.rep.containsKey(name)) {
            n1 = (double) this.rep.get(name)[0];
            n2 = (double) this.rep.get(name)[1];
        }

        if (n1 == null) {
            n1 = 1.0;
        }
        if (n2 == null) {
            n2 = 1.0;
        }

        return ((n1 + 1.0) / (n2 + 1.0));
    }

    /**
     * Returns the complete dataset representation. <br>
     * Ensures each key is type "T" and is paired <br>
     * with an int[2] where the int[0] is number of successes <br>
     * and int[1] is number of failures.
     *
     * @return <pre>a copy of the representation</pre>
     */
    public Map<T, int[]> getRep() {
        return this.rep;
    }

    /**
     * HELPER METHODS
     */

    private void updateCounts(boolean trueSuccess, T[] sample) {
        assert sample != null : "cannot be null";

        for (int i = 0; i < sample.length; i++) {
            int[] newArr = new int[2];
            if (this.rep.containsKey(sample[i])) {
                newArr = this.rep.get(sample[i]);
                if (trueSuccess) {
                    newArr[0]++;
                } else {
                    newArr[1]++;
                }
                this.rep.replace(sample[i], newArr);
            } else {
                if (trueSuccess) {
                    newArr[0]++;
                } else {
                    newArr[1]++;
                }
                this.rep.putIfAbsent(sample[i], newArr);
            }
        }
    }

}
