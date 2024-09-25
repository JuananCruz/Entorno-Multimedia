/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jce.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author juanan
 */
public abstract class MiShape {

    protected Color color;
    protected float grosor;
    protected boolean transparencia;
    protected boolean alisado;
    protected boolean relleno;
    protected boolean seleccionada = false;

    public abstract boolean isSeleccionada();

    public abstract void setSeleccionada(boolean seleccionada);

    public abstract void setColor(Color color);

    public abstract Color getColor();

    public abstract void setGrosor(float grosor);

    public abstract float getGrosor();

    public abstract void setTransparencia(boolean transparencia);

    public abstract boolean getTransparencia();
    
    public abstract void setAlisado(boolean alisado);

    public abstract boolean getAlisado();
    
    public abstract boolean getRelleno();
    
    public abstract void setRelleno(boolean relleno);
    
    public abstract void draw(Graphics2D g2d);

    public abstract boolean contains(Point2D p);
    
    public abstract Point2D getLocation();

    public abstract void setLocation(Point2D pos);
}
