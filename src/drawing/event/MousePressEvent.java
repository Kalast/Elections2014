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
public class MousePressEvent {
    public int button;
    public int x;
    public int y;

    public MousePressEvent(int button, int x, int y) {
        this.button = button;
        this.x = x;
        this.y = y;
    }
}
