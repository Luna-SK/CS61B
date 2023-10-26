package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

import java.util.HashMap;

public class GuitarHero {
    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static HashMap<Character, GuitarString> charFrequencyTable;
    public static void main(String[] args) {
        charFrequencyTable = new HashMap<>();
        for (int i = 0; i < keyboard.length(); i++) {
            double f = 440 * Math.pow(2.0, (double) (i - 24) / 12);
            charFrequencyTable.put(keyboard.charAt(i), new GuitarString(f));
        }
        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (charFrequencyTable.containsKey(key)) {
                    charFrequencyTable.get(key).pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (GuitarString gs: charFrequencyTable.values()) {
                sample += gs.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString gs: charFrequencyTable.values()) {
                gs.tic();
            }
        }
    }
}
