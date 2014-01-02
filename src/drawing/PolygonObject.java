/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package drawing;

import drawing.event.CanvasMouseListener;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author Kalast
 */
public class PolygonObject {
    
    private ArrayList<PointMover> pointsMover;
    private Canvas canvas;
    private String name;
    private Polygon shape;
    private boolean oneMoverDragged;
    private int moverSelected;
    private Color color;
    private int index;
    private Color background;

    public PolygonObject(Canvas canvas, String name, int index) {
        this.canvas = canvas;
        this.name = name;
        this.pointsMover = new ArrayList();
        this.shape = new Polygon();
        moverSelected = -1;
        this.background = new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.3f);
        color = Color.black;
        this.index = index;
        this.canvas.getInput().addMouseListener(new CanvasMouseListener() {

            @Override
            public void mouseWheelMoved(int change) {
            }

            @Override
            public void mouseClicked(int button, int x, int y, int clickCount) {
            }

            @Override
            public void mousePressed(int button, int x, int y) {
            }

            @Override
            public void mouseReleased(int button, int x, int y) {
            }

            @Override
            public void mouseMoved(int oldx, int oldy, int newx, int newy) {
            }

            @Override
            public void mouseDragged(int oldx, int oldy, int newx, int newy) {
            }

            @Override
            public void setInput(Input input) {
            }

            @Override
            public boolean isAcceptingInput() {
                return false;
            }

            @Override
            public void inputEnded() {
            }

            @Override
            public void inputStarted() {
            }
        });
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }
    
    public boolean contains(float x, float y){
        for(PointMover p : this.pointsMover){
            if(p.isSelectable()){
                return true;
            }
        }
        
        return this.shape.contains(x, y);
    }

    public int getIndex() {
        return index;
    }

    public boolean isOneMoverDragged(int index) {
        return oneMoverDragged;
    }

    public Polygon getShape() {
        return shape;
    }
    
    public void pointMoverDragged(int index){
        this.oneMoverDragged = true;
    }
    
    public void released(){
        for(PointMover p : this.pointsMover){
            p.stopDrag();
        }
        this.oneMoverDragged = false;
    }

    public void removePoint(int index){
        Polygon p = new Polygon();
        for(int i = 0; i < shape.getPointCount(); i ++) {
            if(i != index){
                p.addPoint(shape.getPoint(i)[0], shape.getPoint(i)[1]);
            }
        }

        this.shape = p;
    }
    
    public void movePoint(int index, float x, float y){
        this.removePoint(index);
        this.addPoint(x, y, index);
    }
    
    public void addPoint(float x, float y) {
        if (shape.indexOf(x, y) == -1) {
            shape.addPoint(x, y); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    public void addPoint(float x, float y, int position) {
        Polygon p = new Polygon();
        for(int i = 0; i < position; i ++) {
            p.addPoint(shape.getPoint(i)[0], shape.getPoint(i)[1]);
        }
        
        p.addPoint(x, y);
        
        for(int i = position; i < shape.getPointCount(); i ++) {
            p.addPoint(shape.getPoint(i)[0], shape.getPoint(i)[1]);
        }
        
        shape = p;
    }
    
    public void addPointMover(float x, float y) {
        int index = -1;
        if ((index = shape.indexOf(x, y)) != -1) {
            this.pointsMover.add(new PointMover(this, x, y, index));
            System.out.println("Create Point Mover " + index);
            this.pointsMover.get(this.pointsMover.size() - 1).select();
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }
    
    public void render(Graphics g) {
        Color save = g.getColor();
        
        if (shape.getPointCount() > 0) {
            g.setColor(background);
            g.fill(shape);
            g.setColor(color);
            g.draw(shape);
            for (int i = 0; i < shape.getPointCount(); i++) {
                g.setColor(Color.blue);
                this.pointsMover.get(i).render(g);
            }
        }
        g.setColor(save);
    }
    
    public String toString() {
        return "Polygon " + this.name;
    }
    
    public void selectMover(int index) {
        this.canvas.selectPolygon(this.index);
        
        for(PointMover p : this.pointsMover){
            if(p.getIndex() != index){
                p.deselect();
            } else {
                p.select();
            }
        }
    }
    
    public void deselect() {
        for(PointMover p : this.pointsMover){
            p.deselect();
        }
    }

    boolean isOneMoverSelected(int index) {
        return this.pointsMover.get(index).isSelected();
    }

    public List<PointMover> pointsMoverHovered() {
        ArrayList<PointMover> hovereds = new ArrayList();
        for(PointMover p : pointsMover){
            if(p.contains(this.canvas.getInput().getAbsoluteMouseX(), this.canvas.getInput().getAbsoluteMouseY())){
                hovereds.add(p);
            }
        }
        
        return hovereds;
    }
    
    public boolean containsPointMover(float x, float y){
        return (this.pointsMoverHovered().size() > 0);
    }

    public void select() {
        
    }
}

