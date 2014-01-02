package drawing;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Kalast
 */
public class Canvas extends Rectangle{
    private MouseListener mouseListener;
    private KeyListener keyListener;
    private CanvasInput input;
    
    private Color background;
    private Color color;
    
    private ArrayList<PolygonObject> polygons;
    private PolygonObject selectedPoly;
    private GameContainer container;
    private int nextID;
    private JColorChooser tcc;
    
    public Canvas(GameContainer container, int x, int y, int width, int height) {
        super(x, y, width, height);
        JFrame frame = new JFrame();
                    tcc = new JColorChooser();
                    frame.add(tcc);
                    frame.pack();
                    frame.setVisible(true);
        nextID = 0;
        this.input = new CanvasInput(container.getInput());
        this.background = Color.white;
        this.color = Color.black;

        polygons = new ArrayList();
        container.getInput().addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(int key, char c) {
            }

            @Override
            public void keyReleased(int key, char c) {
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
        
        this.mouseListener = new MouseListener() {
            

            @Override
            public void mouseWheelMoved(int change) {
            }

            @Override
            public void mouseClicked(int button, int x, int y, int clickCount) {
                boolean canAdd = true;
                for(PolygonObject p : polygons){
                    if(p.containsPointMover(x, y)){
                        canAdd = false;
                    }
                }
                
                if(canAdd){
                    if(button == Input.MOUSE_LEFT_BUTTON){
                        if(selectedPoly != null){
                            selectedPoly.addPoint(x, y);
                            selectedPoly.addPointMover(x, y);
                        } else {
                             createPolygon(x, y); 
                        }
                    } else if(button == Input.MOUSE_RIGHT_BUTTON){
                        if(selectedPoly != null){
                            if(selectedPoly.getShape().getPointCount() > 2){
                                createPolygon(x, y); 
                            }
                        } else {
                            createPolygon(x, y); 
                        }
                    }
                }
            }

            @Override
            public void mousePressed(int button, int x, int y) {
                for(PolygonObject p : polygons){
                    if(!p.contains(x, y)){
                        p.deselect();
                    } else {
                        return;
                    }
                }
            }

            @Override
            public void mouseReleased(int button, int x, int y) {
                for(PolygonObject polys : polygons){
                    polys.released();
                }
            }

            @Override
            public void mouseMoved(int oldx, int oldy, int newx, int newy) {
            }

            @Override
            public void mouseDragged(int oldx, int oldy, int newx, int newy) {
                //paintAt(oldx, oldy, newx, newy);
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
        };
        container.getInput().addMouseListener(mouseListener);
        this.container = container;
    }

    public int generateID(){
        return nextID ++;
    }

    public ArrayList<PolygonObject> getPolygons() {
        return polygons;
    }
    
    public CanvasInput getInput() {
        return input;
    }
    
    public GameContainer getContainer() {
        return container;
    }

    private void createPolygon(float x, float y) {
        polygons.add(new PolygonObject(this, "P" + polygons.size(), polygons.size()));
        selectedPoly = polygons.get(polygons.size()-1);
        selectedPoly.addPoint(x, y);
        selectedPoly.addPointMover(x, y);
    }
    
    public void render(Graphics g){
        g.setColor(background);
        g.fillRect(x, y, width, height);
        g.drawRect(x, y, width, height);
        g.setColor(color);
        
        for(PolygonObject p : polygons){
            p.render(g);
        }
        this.input.renderCursor(g);
    }
    
    public void update(int delta){
        if(this.selectedPoly != null){
            this.selectedPoly.setBackground(new Color(tcc.getColor().getRed() / 255f, tcc.getColor().getGreen() / 255f, tcc.getColor().getBlue() / 255f, tcc.getColor().getAlpha() / 255f));
        }
        this.input.update();
    }
    
    public void selectPolygon(int index){
        for(PolygonObject p : this.polygons){
            if(p.getIndex() != index){
                p.deselect();
            } else {
                p.select();
                this.selectedPoly = p;
                tcc.setColor(new java.awt.Color(selectedPoly.getBackground().r, selectedPoly.getBackground().g, selectedPoly.getBackground().b, selectedPoly.getBackground().a));
            }
        }
    }
}
