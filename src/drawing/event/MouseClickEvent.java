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
public class MouseClickEvent {
    public int x;
    public int y;
    public int button;
    public int clickCount;

    public MouseClickEvent(int button, int x, int y, int clickCount) {
        this.button = button;
        this.x = x;
        this.y = y;
        this.clickCount = clickCount;
    }
}
