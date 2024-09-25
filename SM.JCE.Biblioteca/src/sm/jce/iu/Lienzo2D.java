/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sm.jce.iu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import sm.jce.graficos.Elipse2D;
import sm.jce.graficos.Fantasma2D;
import sm.jce.graficos.Linea2D;
import sm.jce.graficos.MiShape;
import sm.jce.graficos.Rectangulo2D;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ComponentColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBuffer;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.WritableRaster;
import sm.image.KernelProducer;

/**
 *
 * @author juanan
 */
public class Lienzo2D extends javax.swing.JPanel {

    /**
     * Creates new form Lienzo2D
     */
    public Lienzo2D() {
        this.forma = null;
        initComponents();
    }

    public enum Forma {
        LINEA,
        RECTANGULO,
        ELIPSE,
        FANTASMA
    }

    enum Filtro {
        EMBORRONAMIENTO_MEDIA,
        EMBORRONAMIENTO_BINOMIAL,
        ENFOQUE,
        RELIEVE,
        DETECTOR_FRONTERAS_LAPLACIANO
    }

    private Forma herramienta = null;
    private MiShape forma;
    private Color color = Color.BLACK;
    private boolean transparencia;
    private boolean alisado;
    private float grosor;
    private Point inicio = null;
    private boolean relleno = false;
    private boolean mover = false;
    private boolean modoEdicion = false;

    private List<MiShape> listaFiguras = new ArrayList();

    private BufferedImage img;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        if (img != null) {
            g2d.drawImage(img, 0, 0, this);
        }

        for (MiShape s : listaFiguras) {
            s.draw(g2d);
        }
    }

    public void aplicarFiltroMedia() {
        // Obtener la imagen seleccionada
        BufferedImage imagenSeleccionada = this.getImage();

        // Crear la máscara para el filtro media
        Kernel k = KernelProducer.createKernel(KernelProducer.TYPE_MEDIA_3x3);

        // Crear el operador de convolución
        ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);

        // Aplicar el filtro a la imagen seleccionada
        BufferedImage imagenFiltrada = cop.filter(imagenSeleccionada, null);

        // Actualizar la imagen en el lienzo
        this.setImage(imagenFiltrada);
        this.repaint();
    }

    public void aplicarFiltroBinomial() {
        // Obtener la imagen seleccionada
        BufferedImage imagenSeleccionada = this.getImage();

        // Crear la máscara para el filtro media
        Kernel k = KernelProducer.createKernel(KernelProducer.TYPE_BINOMIAL_3x3);

        // Crear el operador de convolución
        ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);

        // Aplicar el filtro a la imagen seleccionada
        BufferedImage imagenFiltrada = cop.filter(imagenSeleccionada, null);

        // Actualizar la imagen en el lienzo
        this.setImage(imagenFiltrada);
        this.repaint();
    }

    public void aplicarFiltroEnfoque() {
        // Obtener la imagen seleccionada
        BufferedImage imagenSeleccionada = this.getImage();

        // Crear la máscara para el filtro media
        Kernel k = KernelProducer.createKernel(KernelProducer.TYPE_ENFOQUE_3x3);

        // Crear el operador de convolución
        ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);

        // Aplicar el filtro a la imagen seleccionada
        BufferedImage imagenFiltrada = cop.filter(imagenSeleccionada, null);

        // Actualizar la imagen en el lienzo
        this.setImage(imagenFiltrada);
        this.repaint();
    }

    public void aplicarFiltroRelieve() {
        // Obtener la imagen seleccionada
        BufferedImage imagenSeleccionada = this.getImage();

        // Crear la máscara para el filtro media
        Kernel k = KernelProducer.createKernel(KernelProducer.TYPE_RELIEVE_3x3);

        // Crear el operador de convolución
        ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);

        // Aplicar el filtro a la imagen seleccionada
        BufferedImage imagenFiltrada = cop.filter(imagenSeleccionada, null);

        // Actualizar la imagen en el lienzo
        this.setImage(imagenFiltrada);
        this.repaint();
    }

    public void aplicarFiltroLaplaciano() {
        // Obtener la imagen seleccionada
        BufferedImage imagenSeleccionada = this.getImage();

        // Crear la máscara para el filtro media
        Kernel k = KernelProducer.createKernel(KernelProducer.TYPE_LAPLACIANA_3x3);

        // Crear el operador de convolución
        ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);

        // Aplicar el filtro a la imagen seleccionada
        BufferedImage imagenFiltrada = cop.filter(imagenSeleccionada, null);

        // Actualizar la imagen en el lienzo
        this.setImage(imagenFiltrada);
        this.repaint();
    }

    // Esta máscara incrementa la luminosidad porque la suma de sus valores es mayor que 1
    public void aplicarFiltroEmborronamientoIluminado3x3() {

        BufferedImage imagenSeleccionada = this.getImage();

        // Definir la máscara para el filtro de emborronamiento iluminado
        float[] mascara = {
            2 / 9f, 2 / 9f, 2 / 9f,
            2 / 9f, 2 / 9f, 2 / 9f,
            2 / 9f, 2 / 9f, 2 / 9f
        };
        Kernel k = new Kernel(3, 3, mascara);

        // Crear el operador de convolución
        ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);

        // Aplicar el filtro a la imagen seleccionada
        BufferedImage imagenFiltrada = cop.filter(imagenSeleccionada, null);

        // Actualizar la imagen en el lienzo
        this.setImage(imagenFiltrada);
        this.repaint();
    }

    public void aplicarFiltroEmborronamientoIluminado5x5() {
        // Obtener la imagen seleccionada
        BufferedImage imagenSeleccionada = this.getImage();

        // Definir la máscara para el filtro de emborronamiento iluminado
        float[] datosMascara = {
            2 / 25f, 2 / 25f, 2 / 25f, 2 / 25f, 2 / 25f,
            2 / 25f, 2 / 25f, 2 / 25f, 2 / 25f, 2 / 25f,
            2 / 25f, 2 / 25f, 2 / 25f, 2 / 25f, 2 / 25f,
            2 / 25f, 2 / 25f, 2 / 25f, 2 / 25f, 2 / 25f,
            2 / 25f, 2 / 25f, 2 / 25f, 2 / 25f, 2 / 25f
        };
        Kernel k = new Kernel(5, 5, datosMascara);

        // Crear el operador de convolución
        ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);

        // Aplicar el filtro a la imagen seleccionada
        BufferedImage imagenFiltrada = cop.filter(imagenSeleccionada, null);

        // Actualizar la imagen en el lienzo
        this.setImage(imagenFiltrada);
        this.repaint();
    }

    public void setHerramienta(Forma herramienta) {
        this.herramienta = herramienta;
    }

    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
    }

    public boolean getRelleno() {
        return relleno;
    }

    public void setMover(boolean mover) {
        this.mover = mover;
        modoEdicion = mover;
    }

    public boolean getMover() {
        return mover;
    }

    public void limpiarLienzo() {
        listaFiguras.clear();
        repaint();
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setTransparencia(boolean transparencia) {
        this.transparencia = transparencia;
    }

    public void setAlisado(boolean alisado) {
        this.alisado = alisado;
    }

    public void setGrosor(float grosor) {
        this.grosor = grosor;
    }

    public void setImage(BufferedImage img) {
        this.img = img;
        if (img != null) {
            setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        }
    }

    public BufferedImage getImage() {
        return img;
    }

    public BufferedImage getPaintedImage() {
        BufferedImage imgout = new BufferedImage(img.getWidth(),
                img.getHeight(),
                img.getType());

        Graphics2D g2dImagen = imgout.createGraphics();
        if (img != null) {
            g2dImagen.drawImage(img, 0, 0, this);
        }
        for (MiShape s : listaFiguras) {
            s.draw(g2dImagen);
        }
        return imgout;
    }

    public void setColorFiguraSeleccionada(Color color) {
        if (forma != null && forma.isSeleccionada()) {
            forma.setColor(color);
            repaint();
        }
    }

    public void setRellenoFiguraSeleccionada(boolean relleno) {
        if (forma != null && forma.isSeleccionada()) {
            forma.setRelleno(relleno);
            repaint();
        }
    }

    public void setGrosorFiguraSeleccionada(float grosor) {
        if (forma != null && forma.isSeleccionada()) {
            forma.setGrosor(grosor);
            repaint();
        }
    }

    public void setTransparenciaSeleccionada(boolean transparencia) {
        if (forma != null && forma.isSeleccionada()) {
            forma.setTransparencia(transparencia);
            repaint();
        }
    }

    public void setAlisadoSeleccionada(boolean alisado) {
        if (forma != null && forma.isSeleccionada()) {
            forma.setAlisado(alisado);
            repaint();
        }
    }

    public void volcarFiguraSeleccionadaEnImagen() {
        if (forma != null && forma.isSeleccionada()) {
            BufferedImage img = getImage();
            if (img != null) {
                //Eliminamos el circulo antes de volcar la figura
                forma.setSeleccionada(false);
                // Obtener el contexto gráfico de la imagen
                Graphics2D g2d = img.createGraphics();
                // Dibujar la figura seleccionada en la imagen
                forma.draw(g2d);
                // Liberar recursos
                g2d.dispose();
                // Eliminar la figura seleccionada de la lista de figuras del lienzo
                listaFiguras.remove(forma);
                repaint();
            }
        }
    }

    public float[] calcularMascaraEmborronamiento(int tamañoMascara) {
        float[] mascara = new float[tamañoMascara];
        float disminucion = tamañoMascara / 2.0f; 
        float suma = 0.0f;

        for (int i = 0; i < tamañoMascara; i++) {
            if (i < tamañoMascara / 2) {
                mascara[i] = 0.0f; // Valores a la izquierda del centro son 0
            } else {
                mascara[i] = (float) Math.exp(-(i - tamañoMascara / 2) / disminucion);
                suma += mascara[i];
            }
        }

        // Normalizar los pesos para que sumen 1
        for (int i = 0; i < tamañoMascara; i++) {
            if (mascara[i] != 0) {
                mascara[i] /= suma;
            }
        }

        return mascara;
    }

    
    
    
    

    public LookupTable oscurecerZonasClaras() {
        byte[] funcionT = new byte[256];
        for (int i = 0; i < 256; i++) {
            if (i < 128) {
                // Los valores por debajo de 128 se mantienen iguales
                funcionT[i] = (byte) i;
            } else {
                // Los valores por encima de 128 se reducen a la mitad para oscurecerse
                funcionT[i] = (byte) ((i - 128) / 2 + 128);
            }
        }
        return new ByteLookupTable(0, funcionT);
    }

    /*
    Cuando ( a < 128 ), los píxeles más oscuros (valores menores que 128) se hacen más claros, y los más claros se oscurecen (Mas contraste)
    Cuando ( a > 128 ), los píxeles más oscuros se oscurecen aún más, y los más claros se hacen más claros (Menos contraste)
     */
    public LookupTable aplicarTransformacionLinealPuntoControl(float a) {
        byte[] funcionT = new byte[256];
        for (int i = 0; i < 256; i++) {
            if (i < 128) {
                // Los valores por debajo de 128 se escalan hacia arriba o hacia abajo dependiendo de 'a'
                funcionT[i] = (byte) (a * i / 128.0f);
            } else {
                // Los valores por encima de 128 se ajustan para oscurecerse 
                funcionT[i] = (byte) (((255.0f - a) * (i - 128.0f)) / 127.0f + a);
            }

        }
        return new ByteLookupTable(0, funcionT);
    }

    public BufferedImage getImageBand(BufferedImage img, int banda) {
        //Creamos el modelo de color de la nueva imagen basado en un espcio de color GRAY
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ComponentColorModel cm = new ComponentColorModel(cs, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        //Creamos el nuevo raster a partir del raster de la imagen original
        int vband[] = {banda};
        WritableRaster bRaster = (WritableRaster) img.getRaster().createWritableChild(0, 0, img.getWidth(), img.getHeight(), 0, 0, vband);
        //Creamos una nueva imagen que contiene como raster el correspondiente a la banda
        return new BufferedImage(cm, bRaster, false, null);
    }

    private MiShape figuraSeleccionada(Point2D p) {
        for (MiShape s : listaFiguras) {
            if (s.contains(p)) {
                return s;
            }
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        inicio = evt.getPoint();
        if (modoEdicion) {
            forma = figuraSeleccionada(inicio);
            // Activar/desactivar la selección de la figura
            if (forma != null) {
                forma.setSeleccionada(!forma.isSeleccionada());
                repaint();
            }
        } else {
            switch (herramienta) {
                case LINEA:
                    forma = new Linea2D(inicio, inicio, color, grosor, transparencia, alisado);
                    break;
                case RECTANGULO:
                    forma = new Rectangulo2D(inicio.x, inicio.y, 0, 0, color, grosor, transparencia, alisado, relleno);
                    break;
                case ELIPSE:
                    forma = new Elipse2D(inicio.x, inicio.y, 0, 0, color, grosor, transparencia, alisado, relleno);
                    break;
                case FANTASMA:
                    // Ajusta el punto de inicio para que el fantasma se dibuje en el centro del clic
                    Fantasma2D fantasma = new Fantasma2D(inicio.x - 40, inicio.y - 40, 80, 80, color, grosor, transparencia, alisado, relleno);
                    forma = fantasma;
                    break;
            }
            listaFiguras.add(forma);
            repaint();
        }
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if (mover) {
            if (forma != null) {
                double dx = evt.getPoint().getX() - inicio.getX();
                double dy = evt.getPoint().getY() - inicio.getY();
                Point2D newPos = new Point2D.Double(forma.getLocation().getX() + dx, forma.getLocation().getY() + dy);
                forma.setLocation(newPos);
                inicio = evt.getPoint();
                repaint();
            }
        } else {
            if (forma != null) {
                if (forma instanceof Linea2D) {
                    Linea2D linea = (Linea2D) forma;
                    linea.setP2(evt.getPoint());
                } else if (forma instanceof Rectangulo2D) {
                    Rectangulo2D rectangulo = (Rectangulo2D) forma;
                    rectangulo.setFrameFromDiagonal(inicio, evt.getPoint());
                } else if (forma instanceof Elipse2D) {
                    Elipse2D elipse = (Elipse2D) forma;
                    elipse.setFrameFromDiagonal(inicio, evt.getPoint());
                }
                repaint();
            }
        }
    }//GEN-LAST:event_formMouseDragged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
