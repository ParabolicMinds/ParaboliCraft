package parabolic.util;

import java.util.Random;

public class SimplexOctaves extends NoiseMixer {
    public SimplexOctaves(Random rnd, int count, double xScale, double yScale, double zScale, double wScale) {
        super();
        double weight = 1;
        for (int i = 0; i < count; i++) {
            SimplexNoise n = new SimplexNoise(rnd, xScale / weight, yScale / weight, zScale / weight, wScale / weight);
            this.add(n, weight);
            weight /= 2;
        }
    }
    public SimplexOctaves(Random rndIn, int count) { this(rndIn, count,1, 1, 1, 1); }
    public SimplexOctaves(Random rndIn, int count, double scale) { this(rndIn, count,scale, scale, scale, scale); }
    public SimplexOctaves(Random rndIn, int count, double xScale, double yScale) { this(rndIn, count,xScale, yScale, 1, 1); }
    public SimplexOctaves(Random rndIn, int count, double xScale, double yScale, double zScale) { this(rndIn, count,xScale, yScale, zScale, 1); }
}
