/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jce.imagenes;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 *
 * @author juanan
 */
public class RojoOp extends BufferedImageOpAdapter {

    private int umbral;

    public RojoOp(int umbral) {
        this.umbral = umbral;
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        int[] pixelComp = new int[srcRaster.getNumBands()];
        int[] pixelCompDest = new int[srcRaster.getNumBands()];

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, pixelComp);

                // Calcula la media
                int media = (pixelComp[0] + pixelComp[1] + pixelComp[2]) / 3;

                // Si el pixel es predominantemente rojo
                if (pixelComp[0] - pixelComp[1] - pixelComp[2] >= umbral) {
                    pixelCompDest = pixelComp.clone();
                } else {
                    // Usamos la media de los colores para convertirlo a escala de grises
                    pixelCompDest[0] = media;
                    pixelCompDest[1] = media;
                    pixelCompDest[2] = media;
                }

                destRaster.setPixel(x, y, pixelCompDest);
            }

        }
        return dest;
    }
}
