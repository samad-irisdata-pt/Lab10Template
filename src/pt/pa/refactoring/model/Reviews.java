package pt.pa.refactoring.model;

import java.util.ArrayList;

/**
 * @author amfs
 */
public class Reviews extends ArrayList<Review> {

    private int total;
    private double avgRating;

    public int getTotal() {
        total = 0;

        for (Review r : this) {
            total += 1;
        }

        return total;
    }

    public double getAvgRating() {
        double sum = 0.0;
        for (Review r : this) {
            sum += r.getRating();
        }
        avgRating = (sum / getTotal());
        return avgRating;
    }
}
