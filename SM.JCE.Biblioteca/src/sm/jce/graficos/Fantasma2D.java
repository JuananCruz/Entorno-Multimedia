/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jce.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author juanan
 */
public class Fantasma2D extends MiShape {

    private Area fantasma;
    

    public Fantasma2D(float x, float y, float width, float height, Color color, float grosor, boolean transparencia, boolean alisado, boolean relleno) {
        this.color = color;
        this.grosor = grosor;
        this.transparencia = transparencia;
        this.alisado = alisado;
        this.relleno = relleno;

        // Crear las formas que componen el fantasma
        Ellipse2D.Float cabeza = new Ellipse2D.Float(x, y, width, height / 2);
        Rectangle2D.Float cuerpo = new Rectangle2D.Float(x, y + height / 4, width, height / 2);

        // Crear el área del fantasma y añadir las formas
        fantasma = new Area(cabeza);
        fantasma.add(new Area(cuerpo));

        // Ojos
        Rectangle2D.Float ojoIzquierdo = new Rectangle2D.Float(x + width / 4, y + height / 4, width / 10, height / 10);
        Rectangle2D.Float ojoDerecho = new Rectangle2D.Float(x + 3 * width / 4, y + height / 4, width / 10, height / 10);
        fantasma.subtract(new Area(ojoIzquierdo));
        fantasma.subtract(new Area(ojoDerecho));

        // Parte de abajo con lineas
        Path2D.Float parteInferior = new Path2D.Float();
        parteInferior.moveTo(x, y + 3 * height / 4);
        parteInferior.lineTo(x + width / 6, y + height);
        parteInferior.lineTo(x + width / 4, y + 3 * height / 4);
        parteInferior.lineTo(x + width / 3, y + height);
        parteInferior.lineTo(x + width / 2, y + 3 * height / 4);
        parteInferior.lineTo(x + 2 * width / 3, y + height);
        parteInferior.lineTo(x + 3 * width / 4, y + 3 * height / 4);
        parteInferior.lineTo(x + 5 * width / 6, y + height);
        parteInferior.lineTo(x + width, y + 3 * height / 4);
        fantasma.add(new Area(parteInferior));
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setGrosor(float grosor) {
        this.grosor = grosor;
    }

    @Override
    public float getGrosor() {
        return this.grosor;
    }

    @Override
    public void setTransparencia(boolean transparencia) {
        this.transparencia = transparencia;
    }

    @Override
    public boolean getTransparencia() {
        return this.transparencia;
    }

    @Override
    public void setAlisado(boolean alisado) {
        this.alisado = alisado;
    }

    @Override
    public boolean getAlisado() {
        return this.alisado;
    }

    @Override
    public boolean getRelleno() {
        return this.relleno;
    }

    @Override
    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
    }
    
    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    @Override
    public boolean contains(Point2D p) {
        return fantasma.contains(p);
    }

    @Override
    public Point2D getLocation() {
        // Obtenemos los límites del área del fantasma
        Rectangle bounds = fantasma.getBounds();
        // Devolvemos la esquina superior izquierda del área del fantasma
        return new Point2D.Double(bounds.getX(), bounds.getY());
    }

    @Override
    public void setLocation(Point2D pos) {
        // Calcular el desplazamiento necesario para mover el fantasma a la nueva posición
        double dx = pos.getX() - this.getLocation().getX();
        double dy = pos.getY() - this.getLocation().getY();

        // Crear una nueva transformación de traslación
        AffineTransform at = AffineTransform.getTranslateInstance(dx, dy);

        // Aplicar la transformación a la forma
        fantasma.transform(at);
    }
    
    
    /*public void setFrameFromDiagonal(Point2D inicio, Point2D fin) {
        float x = (float) Math.min(inicio.getX(), fin.getX());
        float y = (float) Math.min(inicio.getY(), fin.getY());
        float width = (float) Math.abs(inicio.getX() - fin.getX());
        float height = (float) Math.abs(inicio.getY() - fin.getY());

        // Crear las formas que componen el fantasma
        Ellipse2D.Float cabeza = new Ellipse2D.Float(x, y, width, height / 2);
        Rectangle2D.Float cuerpo = new Rectangle2D.Float(x, y + height / 4, width, height / 2);

        // Crear el área del fantasma y añadir las formas
        fantasma = new Area(cabeza);
        fantasma.add(new Area(cuerpo));

        // Ojos
        Rectangle2D.Float ojoIzquierdo = new Rectangle2D.Float(x + width / 4, y + height / 4, width / 10, height / 10);
        Rectangle2D.Float ojoDerecho = new Rectangle2D.Float(x + 3 * width / 4, y + height / 4, width / 10, height / 10);
        fantasma.subtract(new Area(ojoIzquierdo));
        fantasma.subtract(new Area(ojoDerecho));

        // Parte de abajo con lineas
        Path2D.Float parteInferior = new Path2D.Float();
        parteInferior.moveTo(x, y + 3 * height / 4);
        parteInferior.lineTo(x + width / 6, y + height);
        parteInferior.lineTo(x + width / 4, y + 3 * height / 4);
        parteInferior.lineTo(x + width / 3, y + height);
        parteInferior.lineTo(x + width / 2, y + 3 * height / 4);
        parteInferior.lineTo(x + 2 * width / 3, y + height);
        parteInferior.lineTo(x + 3 * width / 4, y + 3 * height / 4);
        parteInferior.lineTo(x + 5 * width / 6, y + height);
        parteInferior.lineTo(x + width, y + 3 * height / 4);
        fantasma.add(new Area(parteInferior));
    }*/ //En caso de querer redimensionar el fantasma arrastrando



    @Override
    public void draw(Graphics2D g2d) {
        if (this.fantasma == null) {
            this.fantasma = new Area();
        }

        //Trazo
        Stroke trazo = new BasicStroke(grosor);
        g2d.setStroke(trazo);

        //Color
        g2d.setColor(this.color);

        //Alisado
        if (alisado) {
            RenderingHints render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHints(render);
        } else {
            RenderingHints render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.setRenderingHints(render);
        }

        //Transparencia
        if (transparencia) {
            Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2d.setComposite(comp);
        } else {
            Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
            g2d.setComposite(comp);
        }

        if (relleno) {
            g2d.fill(fantasma);
        } else {
            g2d.draw(fantasma);
        }
        if (seleccionada) {

            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            Point2D loc = getLocation();
            double x = loc.getX() - 20;
            double y = loc.getY() - 20;
            g2d.drawOval((int) x, (int) y, 40, 40);
        }
    }

}
