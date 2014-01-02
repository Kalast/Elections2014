/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package drawing.event;

/**
 *
 * @author Kalast
 */
public class MouseDragEvent {
    public int oldx;
    public int oldy;
    public int newx;
    public int newy;

    public MouseDragEvent(int oldx, int oldy, int newx, int newy) {
        this.oldx = oldx;
        this.oldy = oldy;
        this.newx = newx;
        this.newy = newy;
    }
}
