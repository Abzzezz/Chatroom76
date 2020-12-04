package net.bplaced.abzzezz.game.dialog.call.calls;

import net.bplaced.abzzezz.game.dialog.call.BasicCall;

import java.util.Map;

import static net.bplaced.abzzezz.game.util.dialog.DialogUtil.COLOR_CODE_KEY;
import static net.bplaced.abzzezz.game.util.dialog.DialogUtil.TEXT_ARGUMENT;

public class ColorCodeCall implements BasicCall {

    @Override
    public String[] formatted(String in, String color, Map<String, String> args) {
        return new String[]{args.get(TEXT_ARGUMENT), color};
    }

    @Override
    public String tag() {
        return COLOR_CODE_KEY;
    }
}
