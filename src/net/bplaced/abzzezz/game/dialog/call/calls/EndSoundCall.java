package net.bplaced.abzzezz.game.dialog.call.calls;

import net.bplaced.abzzezz.game.dialog.call.BasicCall;

import java.util.Map;

import static net.bplaced.abzzezz.game.util.dialog.DialogUtil.*;

public class EndSoundCall implements BasicCall {

    @Override
    public String[] formatted(String in, String color, Map<String, String> args) {
        GAME_MAIN.getSoundPlayer().stopSounds();
        return new String[] {args.getOrDefault(TEXT_ARGUMENT, ""), color};
    }

    @Override
    public String tag() {
        return END_SOUNDS_CALL;
    }
}
