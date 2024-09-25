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
public class PosterizarOp extends BufferedImageOpAdapter {

    private int niveles;

    public PosterizarOp(int niveles) {
        this.niveles = niveles;
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
        int sample;

        // Calcula K basado en el número de niveles
        float K = 256.f/niveles;

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                for (int band = 0; band < srcRaster.getNumBands(); band++) {
                    sample = srcRaster.getSample(x, y, band);
                    // Aplica la ecuación de posterización
                    // La fórmula de posterización ajusta cada muestra de color al nivel más cercano
                    // dentro de los intervalos definidos por K. Esto crea el efecto de reducir el número
                    // de colores en la imagen, lo que da como resultado una imagen con un estilo de "poster".

                    sample = (int) (K * (int) (sample/K));
                    destRaster.setSample(x, y, band, sample);
                }
            }
        }
        return dest;
    }
}
