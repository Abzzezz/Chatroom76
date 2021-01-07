/*
 * @author Roman
 * Last modified: 07.01.21, 23:08 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.util.Basic;

public class Game extends Core implements Basic {

    //TODO: Handlers, etc.

    public static final Game GAME = new Game();

    public void initialise() {
        final int width = 1920;
        final int height = 1080;
        this.initialiseGL(width, height);
    }


    @Override
    public void draw() {
        super.draw();
        //TODO: Draw stuff

    }

    @Override
    public void keyPressed(int keyCode, char keyCharacter) {

        super.keyPressed(keyCode, keyCharacter);
    }

}
