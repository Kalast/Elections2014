/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package drawing;

import drawing.event.CanvasMouseListener;
import drawing.event.MouseDragEvent;
import drawing.event.MousePressEvent;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

/**
 *
 * @author Kalast
 */
public class CanvasInput {
    private CanvasCursor cursor;
    private ArrayList<CanvasMouseListener> listeners;
    
    public CanvasInput(Input input) {
        cursor = new CanvasCursor(input, "cursor.png");
        listeners = new ArrayList();
    }
    
    public void update(){
        
        this.cursor.update();
        
        for(CanvasMouseListener l : listeners){
            if(l.isAcceptingInput()){
                for (MousePressEvent e : this.cursor.getPressEvents()) {
                    l.mousePressed(
                            e.button,
                            e.x,
                            e.y
                    );
                    System.out.println("PRESS");
                }
                
                for (MouseDragEvent e : this.cursor.getDragEvents()) {
                    l.mouseDragged(
                            e.oldx, 
                            e.oldy, 
                            e.newx, 
                            e.newy
                    );
                }
                
            }
        }
        
        this.cursor.getDragEvents().clear();
        this.cursor.getPressEvents().clear();
    }
    
    public float getAbsoluteMouseX(){
        return this.cursor.getX();
    }
    
    public float getAbsoluteMouseY(){
        return this.cursor.getY();
    }
    
    public void renderCursor(Graphics g){
        cursor.render(g);
    }
    
    public void addMouseListener(CanvasMouseListener listener){
        this.listeners.add(listener);
        System.out.println("Listener Add");
    }
}
