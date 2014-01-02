/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package drawing;

import drawing.event.CanvasMouseListener;
import java.util.List;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author Kalast
 */
class PointMover {
    private int x;
    private int y;
    private PolygonObject polygon;
    
    private CanvasMouseListener listener;
    private Circle circle;
    private int index;
    private boolean selected;
    private Color colorSelect;
    private boolean dragged;
    private int ID;
    private int radius = 5;

    public PointMover(PolygonObject poly, float x, float y, final int index) {
        this.polygon = poly;
        selected = false;
        this.circle = new Circle(x, y, radius);
        this.x = (int) x;
        this.y = (int) y;
        this.index = index;
        this.colorSelect = Color.red;
        ID = this.polygon.getCanvas().generateID();
        this.listener = new CanvasMouseListener() {

            @Override
            public void mouseWheelMoved(int change) {
            }

            @Override
            public void mouseClicked(int button, int x, int y, int clickCount) {
                
            }

            @Override
            public void mousePressed(int button, int x, int y) {
                System.out.println("Click on " + definition());
                polygon.selectMover(index);
            }

            @Override
            public void mouseReleased(int button, int x, int y) {
                
            }

            @Override
            public void mouseMoved(int oldx, int oldy, int newx, int newy) {
                
            }

            @Override
            public void mouseDragged(int oldx, int oldy, int newx, int newy) {
                if(polygon.isOneMoverSelected(index)){
                    dragged = true;
                    polygon.pointMoverDragged(index);
                    //System.out.println("Mouse drag " + newx + " et " + newy);
                    System.out.println("PointMover " + index);

                    circle.setLocation(polygon.getCanvas().getInput().getAbsoluteMouseX() - circle.radius, polygon.getCanvas().getInput().getAbsoluteMouseY() - circle.radius);
                    polygon.movePoint(index, newx, newy);
                }
            }

            @Override
            public void setInput(Input input) {
            }

            @Override
            public boolean isAcceptingInput() {
                //System.out.println(getWorldIndex() + " is " + (isSelectable() || dragged));
                return isSelectable() || dragged;
            }

            @Override
            public void inputEnded() {
            }

            @Override
            public void inputStarted() {
            }  
        };
        
        polygon.getCanvas().getInput().addMouseListener(listener);
    }
    
    public void deselect(){
        this.selected = false;
    }
    
    public void select(){
        if(!this.selected){
            this.selected = true;
            this.polygon.selectMover(index);
        }
    }
    
    public boolean contains(float x, float y){
        return this.circle.contains(x, y);
    }

    public int getIndex() {
        return index;
    }
    
    public int getID(){
        return ID;
    }

    public PolygonObject getPolygon() {
        return polygon;
    }
    
    public boolean isSelectable() {
        for(PointMover p : polygon.pointsMoverHovered()){
            if(!p.isDragged()){
                if(p.getID() > this.getID()){
                    return false;
                }
            } else {
                return false;
            }
        }
        
        return this.contains(polygon.getCanvas().getInput().getAbsoluteMouseX(), polygon.getCanvas().getInput().getAbsoluteMouseY());
    }
    
    public boolean isDragged(){
        return this.dragged;
    }
    
    public void stopDrag(){
        this.dragged = false;
    }

    public boolean isSelected() {
        return selected;
    }
    
    private String definition() {
        return this.toString();
    }
    
    public void render(Graphics g){
        g.drawString("X = " + polygon.getCanvas().getInput().getAbsoluteMouseX(), 10, 30);
        g.drawString("Y = " + polygon.getCanvas().getInput().getAbsoluteMouseY(), 10, 50);
        g.drawString("Point Mover " + getID() + " : " + (isSelectable() || dragged), 10, 70 + 20 * getID());
        if (selected) {
            g.setColor(colorSelect);
        } else {
            g.setColor(Color.blue);
        }
        g.draw(circle);
    }

    @Override
    public String toString() {
        return this.polygon + " {x=" + x + ", y=" + y + "}";
    }
}
