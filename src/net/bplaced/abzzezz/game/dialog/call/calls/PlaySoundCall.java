/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.dialog.call.calls;

import net.bplaced.abzzezz.game.dialog.call.BasicCall;

import java.io.File;
import java.util.Map;

import static net.bplaced.abzzezz.game.util.dialog.DialogUtil.*;

public class PlaySoundCall implements BasicCall {

    @Override
    public String[] formatted(String in, String color, Map<String, String> args) {
        final File file = new File(args.getOrDefault(PATH_ARGUMENT, "").replace("\\", "\\\\"));
        final float volume = Float.parseFloat(args.getOrDefault(VOLUME_ARGUMENT, "0"));
        GAME_MAIN.getSoundPlayer().playSound(file, volume);
        return new String[]{args.getOrDefault(TEXT_ARGUMENT, ""), color};
    }

    @Override
    public String tag() {
        return PLAY_SOUND_CALL;
    }
}
