/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.dialog.call.calls;

import net.bplaced.abzzezz.core.sound.SoundPlayer;
import net.bplaced.abzzezz.game.dialog.call.BasicCall;

import java.util.Map;

import static net.bplaced.abzzezz.game.util.dialog.DialogUtil.END_SOUNDS_CALL;
import static net.bplaced.abzzezz.game.util.dialog.DialogUtil.TEXT_ARGUMENT;

public class EndSoundCall implements BasicCall {

    @Override
    public String[] formatted(String in, String color, Map<String, String> args) {
        SoundPlayer.SOUND_PLAYER.stopSounds();
        return new String[]{args.getOrDefault(TEXT_ARGUMENT, ""), color};
    }

    @Override
    public String tag() {
        return END_SOUNDS_CALL;
    }
}
