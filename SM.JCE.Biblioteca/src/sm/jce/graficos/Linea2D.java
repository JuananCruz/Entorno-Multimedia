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
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author juanan
 */
public class Linea2D extends MiShape {

    private Line2D linea;
    private Point2D p1;
    private Point2D p2;

    public Linea2D(Point2D p1, Point2D p2,Color color, float grosor, boolean transparencia, boolean alisado) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
        this.grosor = grosor;
        this.alisado = alisado;
        this.transparencia = transparencia;
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
        return isNear(p);
    }

    public boolean isNear(Point2D p) {
        if (this.linea.getP1().equals(this.linea.getP2())) {
            return this.linea.getP1().distance(p) <= 8.0; //p1=p2
        }
        return this.linea.ptLineDist(p) <= 8.0; // p1!=p2
    }

    @Override
    public Point2D getLocation() {
        return linea.getP1();
    }
    
    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
    
    public void setP2(Point2D p2) {
        this.p2 = p2;
        this.linea.setLine(p1, p2);
    }

    @Override
    public void setLocation(Point2D pos) {
        double dx = pos.getX() - linea.getX1();
        double dy = pos.getY() - linea.getY1();
        Point2D nuevoInicio = new Point2D.Double(linea.getX1() + dx, linea.getY1() + dy);
        Point2D nuevoFin = new Point2D.Double(linea.getX2() + dx, linea.getY2() + dy);
        linea.setLine(nuevoInicio, nuevoFin);
    }

    /**
     *
     * @param g2d
     */
    @Override
    public void draw(Graphics2D g2d) {
        if (this.linea == null) {
            this.linea = new Line2D.Float(p1, p2);
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

        g2d.draw(linea);

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
