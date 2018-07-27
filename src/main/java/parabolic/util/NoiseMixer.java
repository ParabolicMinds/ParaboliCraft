package parabolic.util;

import java.util.ArrayList;
import java.util.List;

public class NoiseMixer implements INoise {

    private List<NoiseSet> sets = new ArrayList<NoiseSet>();
    private double[] sumbuffer;

    public void add(INoise noise, double weight) {
        NoiseSet set = new NoiseSet();
        set.noise = noise;
        set.weight_original = weight;
        sets.add(set);

        sumbuffer = new double[sets.size()];

        double weight_max = 0;
        for (NoiseSet s : sets) weight_max += s.weight_original;
        for (NoiseSet s : sets) s.weight_normalized = s.weight_original / weight_max;
    }

    @Override
    public double generate(double x, double y) {
        double weighted_value = 0;
        for (NoiseSet s : sets) weighted_value += s.noise.generate(x, y) * s.weight_normalized;
        return weighted_value;
    }

    @Override
    public double generate(double x, double y, double z) {
        double weighted_value = 0;
        for (NoiseSet s : sets) weighted_value += s.noise.generate(x, y, z) * s.weight_normalized;
        return weighted_value;
    }

    @Override
    public double generate(double x, double y, double z, double w) {
        double weighted_value = 0;
        for (NoiseSet s : sets) weighted_value += s.noise.generate(x, y, z, w) * s.weight_normalized;
        return weighted_value;
    }

    private class NoiseSet {
        INoise noise;
        double weight_original;
        double weight_normalized;
    }
}
