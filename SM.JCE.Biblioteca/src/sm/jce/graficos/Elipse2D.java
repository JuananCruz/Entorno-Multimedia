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
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author juanan
 */
public class Elipse2D extends MiShape {

    private Ellipse2D elipse;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Elipse2D(int x, int y, int width, int height, Color color, float grosor, boolean transparencia, boolean alisado, boolean relleno) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color; 
    this.grosor = grosor; 
    this.transparencia = transparencia; 
    this.alisado = alisado; 
    this.elipse = new Ellipse2D.Float(x, y, width, height);
    this.relleno = relleno;
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
    
    @Override
    public boolean contains(Point2D p) {
        return elipse.contains(p);
    }
    
    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
    
    @Override
    public Point2D getLocation() {
        return new Point2D.Double(elipse.getX(), elipse.getY());
    }

    @Override
    public void setLocation(Point2D pos) {
        double width = elipse.getWidth();
        double height = elipse.getHeight();
        elipse.setFrame(pos.getX(), pos.getY(), width, height);
    }
    
    public void setFrameFromDiagonal(Point2D inicio, Point2D finalPoint) {
        if (elipse instanceof Ellipse2D) {
            Ellipse2D elip = (Ellipse2D) elipse;
            elip.setFrameFromDiagonal(inicio, finalPoint);
        }
    }
   


    @Override
    public void draw(Graphics2D g2d) {
        if (this.elipse == null) {
            this.elipse = new Ellipse2D.Double(x, y, 0, 0);
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
            g2d.fill(elipse);
        } else {
            g2d.draw(elipse);
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