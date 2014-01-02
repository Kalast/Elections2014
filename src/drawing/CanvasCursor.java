/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package drawing;

import drawing.event.MouseClickEvent;
import drawing.event.MouseDragEvent;
import drawing.event.MouseMoveEvent;
import drawing.event.MousePressEvent;
import drawing.event.MouseReleaseEvent;
import drawing.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;


/**
 *
 * @author Kalast
 */
public class CanvasCursor {
    

    private float x;
    private float y;
    
    private Point newPosition;
    
    private Image img;
    
    private ArrayList<MouseWheelEvent> wheelEvents = new ArrayList();
    private ArrayList<MouseClickEvent> clickEvents = new ArrayList();
    private ArrayList<MousePressEvent> pressEvents = new ArrayList();
    private ArrayList<MouseReleaseEvent> releaseEvents = new ArrayList();
    private ArrayList<MouseMoveEvent> moveEvents = new ArrayList();
    private ArrayList<MouseDragEvent> dragEvents = new ArrayList();

    public CanvasCursor(Input input, String file) {
        try {
            img = new Image(file);
        } catch (SlickException ex) {
            Logger.getLogger(CanvasCursor.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        input.addMouseListener(new MouseListener() {

            @Override
            public void mouseWheelMoved(int change) {
                wheelEvents.add(new MouseWheelEvent(change));
            }

            @Override
            public void mouseClicked(int button, int x, int y, int clickCount) {
                clickEvents.add(new MouseClickEvent(button, x, y, clickCount));
            }

            @Override
            public void mousePressed(int button, int x, int y) {
                pressEvents.add(new MousePressEvent(button, x, y));
            }

            @Override
            public void mouseReleased(int button, int x, int y) {
                releaseEvents.add(new MouseReleaseEvent(button, x, y));
            }

            @Override
            public void mouseMoved(int oldx, int oldy, int newx, int newy) {
                newPosition = new Point(newx, newy);
                moveEvents.add(new MouseMoveEvent(oldx, oldy, newx, newy));
            }

            @Override
            public void mouseDragged(int oldx, int oldy, int newx, int newy) {
                newPosition = new Point(newx, newy);
                dragEvents.add(new MouseDragEvent(oldx, oldy, newx, newy));
            }

            @Override
            public void setInput(Input input) {
                
            }

            @Override
            public boolean isAcceptingInput() {
                return true;
            }

            @Override
            public void inputEnded() {
            }

            @Override
            public void inputStarted() {
                
            }
        });
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public ArrayList<MouseWheelEvent> getWheelEvents() {
        return wheelEvents;
    }

    public ArrayList<MouseClickEvent> getClickEvents() {
        return clickEvents;
    }

    public ArrayList<MousePressEvent> getPressEvents() {
        return pressEvents;
    }

    public ArrayList<MouseReleaseEvent> getReleaseEvents() {
        return releaseEvents;
    }

    public ArrayList<MouseMoveEvent> getMoveEvents() {
        return moveEvents;
    }

    public ArrayList<MouseDragEvent> getDragEvents() {
        return dragEvents;
    }
    
    public void update(){
        if(this.newPosition != null){
            this.x = this.newPosition.getX();
            this.y = this.newPosition.getY();
            this.newPosition = null;
        }
    }
    
    public void setLocation(float x, float y){
        this.x = (int) x;
        this.y = (int) y;
    }
    
    public void render(Graphics g){
        g.drawImage(img, x, y);
    }
}
