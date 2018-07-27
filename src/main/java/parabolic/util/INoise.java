package parabolic.util;

public interface INoise {

    // ( Y * XSIZE + X )
    default double[] generate(double[] data,  int xSize, int ySize, double xOffs, double yOffs) {
        if (data == null) data = new double[xSize * ySize];
        for(int y = 0; y < ySize; y++) for (int x = 0; x < xSize; x++)
            data[y * xSize + x] = generate(xOffs + x, yOffs + y);
        return data;
    }

    // ( X * ZSIZE * YSIZE + Z * YSIZE + Y )
    default double[] generate(double[] data, int xSize, int ySize, int zSize, double xOffs, double yOffs, double zOffs) {
        if (data == null) data = new double[xSize * ySize * zSize];
        for(int x = 0; x < xSize; x++) for (int z = 0; z < zSize; z++) for (int y = 0; y < ySize; y++)
            data[x * zSize * ySize + z * ySize + y] = generate(xOffs + x, yOffs + y, zOffs + z);
        return data;
    }

    // ( W * XSIZE * YSIZE * ZSIZE + X * ZSIZE * YSIZE + Z * YSIZE + Y )
    default double[] generate(double[] data, int xSize, int ySize, int zSize, int wSize, double xOffs, double yOffs, double zOffs, double wOffs) {
        if (data == null) data = new double[xSize * ySize * zSize * wSize];
        for (int w = 0; w < wSize; w++) for(int x = 0; x < xSize; x++) for (int z = 0; z < zSize; z++) for (int y = 0; y < ySize; y++)
            data[w * xSize * zSize * ySize + x * zSize * ySize + z * ySize + y] = generate(xOffs + x, yOffs + y, zOffs + z, wOffs + w);
        return data;
    }

    double generate(double xOffs, double yOffs);
    double generate(double xOffs, double yOffs, double zOffs);
    double generate(double xOffs, double yOffs, double zOffs, double wOffs);
}
