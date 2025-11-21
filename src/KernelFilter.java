import java.awt.Color;

public class KernelFilter {

    // Kernels
    private static final double[][] IDENTITY = {
        { 0, 0, 0 },
        { 0, 1, 0 },
        { 0, 0, 0 }
    };

    private static final double[][] GAUSSIAN = {
        { 1, 2, 1 },
        { 2, 4, 2 },
        { 1, 2, 1 }
    };

    private static final double[][] SHARPEN = {
        { 0, -1, 0 },
        { -1, 5, -1 },
        { 0, -1, 0 }
    };

    private static final double[][] LAPLACIAN = {
        { -1, -1, -1 },
        { -1,  8, -1 },
        { -1, -1, -1 }
    };

    private static final double[][] EMBOSS = {
        { -2, -1, 0 },
        { -1,  1, 1 },
        {  0,  1, 2 }
    };

    private static final double[][] MOTION = {
        { 1, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 1, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 1, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 1, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 1, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 1, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 1, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 1 }
    };

    // Aplica un kernel arbitrario y devuelve la nueva imagen
    private static Picture applyKernel(Picture picture, double[][] kernel, double factor) {

        int w = picture.width();
        int h = picture.height();
        Picture out = new Picture(w, h);

        int kSize = kernel.length;         // asumimos kernel cuadrado
        int half = kSize / 2;

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {

                double r = 0.0;
                double g = 0.0;
                double b = 0.0;

                for (int i = -half; i <= half; i++) {
                    for (int j = -half; j <= half; j++) {

                        int xi = (x + i + w) % w;  // contorno periódico
                        int yj = (y + j + h) % h;

                        Color c = picture.get(xi, yj);

                        double weight = kernel[i + half][j + half];

                        r += weight * c.getRed();
                        g += weight * c.getGreen();
                        b += weight * c.getBlue();
                    }
                }

                r *= factor;
                g *= factor;
                b *= factor;

                // redondeo al entero más cercano
                int ri = (int) Math.round(r);
                int gi = (int) Math.round(g);
                int bi = (int) Math.round(b);

                // saturación 0..255
                if (ri < 0) ri = 0;
                if (gi < 0) gi = 0;
                if (bi < 0) bi = 0;
                if (ri > 255) ri = 255;
                if (gi > 255) gi = 255;
                if (bi > 255) bi = 255;

                out.set(x, y, new Color(ri, gi, bi));
            }
        }

        return out;
    }

    // Devuelve una nueva imagen que aplica el filtro de identidad
    public static Picture identity(Picture picture) {
        return applyKernel(picture, IDENTITY, 1.0);
    }

    // Devuelve una nueva imagen que aplica un desenfoque gaussiano
    public static Picture gaussian(Picture picture) {
        return applyKernel(picture, GAUSSIAN, 1.0 / 16.0);
    }

    // Devuelve una nueva imagen que aplica un filtro de nitidez
    public static Picture sharpen(Picture picture) {
        return applyKernel(picture, SHARPEN, 1.0);
    }

    // Devuelve una nueva imagen que aplica un filtro laplaciano
    public static Picture laplacian(Picture picture) {
        return applyKernel(picture, LAPLACIAN, 1.0);
    }

    // Devuelve una nueva imagen que aplica un filtro en relieve
    public static Picture emboss(Picture picture) {
        return applyKernel(picture, EMBOSS, 1.0);
    }

    // Devuelve una nueva imagen que aplica un desenfoque de movimiento
    public static Picture motionBlur(Picture picture) {
        return applyKernel(picture, MOTION, 1.0 / 9.0);
    }

    // Cliente de prueba: aplica motionBlur a la imagen dada y la muestra.
    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        Picture filtered = motionBlur(pic);
        filtered.show();
    }
}
