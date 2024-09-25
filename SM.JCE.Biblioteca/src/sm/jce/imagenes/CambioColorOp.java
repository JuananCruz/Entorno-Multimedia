/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jce.imagenes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 *
 * @author juanan
 */
public class CambioColorOp extends BufferedImageOpAdapter {

    private Color colorC1;
    private Color colorC2;
    private float umbral;

    public CambioColorOp(Color colorC1, Color colorC2, float umbral) {
        this.colorC1 = colorC1;
        this.colorC2 = colorC2;
        this.umbral = umbral;
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (dest == null) {
            dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        }
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();

        float[] hsbC1 = Color.RGBtoHSB(colorC1.getRed(), colorC1.getGreen(), colorC1.getBlue(), null);
        float[] hsbC2 = Color.RGBtoHSB(colorC2.getRed(), colorC2.getGreen(), colorC2.getBlue(), null);

        int[] pixelRGB = new int[3];
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                pixelRGB = srcRaster.getPixel(x, y, pixelRGB);
                float[] hsbPx = Color.RGBtoHSB(pixelRGB[0], pixelRGB[1], pixelRGB[2], null);

                if (Math.abs(hsbPx[0] - hsbC1[0]) <= umbral / 360.0 || Math.abs(hsbPx[0] - hsbC1[0]) >= (1 - umbral / 360.0)) {
                    hsbPx[0] = hsbC2[0]; // Cambia el tono al del color C2
                }

                int rgb = Color.HSBtoRGB(hsbPx[0], hsbPx[1], hsbPx[2]);
                destRaster.setPixel(x, y, new int[]{(rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF});
            }
        }
        return dest;
    }
}
