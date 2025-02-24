import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Does not need notations.
 */
public class spamOrHamWithTraining {

    /**
     * Default Constructor.
     */
    public spamOrHamWithTraining() {
    }

    /**
     * Tests training model
     *
     * @param args
     *            - arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {

            /**
             * MODEL TRAINING
             */
            Builder format = CSVFormat.Builder.create(CSVFormat.DEFAULT);
            File file = new File(
                    "SpamChecker\\src\\main\\resources\\spam.csv");

            CSVParser csv = CSVParser.parse(new FileReader(file), format.get());
            SpamChecking p = new SpamChecking("Spam", "Ham");

            List<CSVRecord> records = csv.getRecords();

            for (CSVRecord message : records) {
                String[] words = p.sentenceToWords(message.get(1));
                for (int i = 0; i < words.length; i++) {

                    if (message.get(0).equals("ham")) {
                        p.addProb(words[i], 0, 1);
                    } else if (message.get(0).equals("spam")) {
                        p.addProb(words[i], 1, 0);
                    }
                }
            }
            p.rep.remove(""); // There is a singular empty key

            /**
             * Words likely to be spam:
             */
            System.out.println("15 Highest spam-likelihood words: ");
            System.out.println(p.topItems(15));

            /**
             * START TESTING
             */
            System.out.println("Enter a sentence to grade:");
            System.out.println("   (\"Exit\" to exit)");
            String[] input = p.sentenceToWords(in.nextLine());

            while (!input[0].equals("exit") || input.length != 1) {
                System.out.println("Reading: " + Arrays.toString(input));

                // Reads probabilities of each word:
                System.out.print("Probabilities: [");
                for (int i = 0; i < input.length - 1; i++) {
                    System.out.print(p.getItemRatio(input[i]) + ", ");
                }
                System.out.println(p.getItemRatio(input[input.length - 1]) + "]");

                // Prediction:
                boolean prediction = p.success(input);
                System.out.print("Prediction: ");
                if (prediction) {
                    System.out.println("spam");
                } else {
                    System.out.println("ham");
                }
                // Ratio of spam to ham:
                System.out.printf("Ratio: %.5f%n", p.ratio(input));

                System.out.println("Enter a sentence to grade:" + "\n");
                input = p.sentenceToWords(in.nextLine());
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block

            // Occurs on bad CSV training*
            e.printStackTrace();
        }

        in.close();
    }
}
