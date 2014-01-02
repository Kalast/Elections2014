/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elections2014;

import elections2014.states.TestState;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Kalast
 */
public class Elections2014 extends StateBasedGame{

    public Elections2014(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        this.addState(new TestState());
        this.enterState(TestState.ID);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Elections2014("Elections 2014"));
            app.setAlwaysRender(true);
            app.setDisplayMode(800, 600, false);
            //app.setMouseGrabbed(true);
            
            app.start();
        } catch (SlickException ex) {
            Logger.getLogger(Elections2014.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
