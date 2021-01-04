/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.dialog.call;

import net.bplaced.abzzezz.game.GameMain;

import java.util.Map;
import java.util.regex.Matcher;

import static net.bplaced.abzzezz.game.util.dialog.DialogUtil.DEFINE_KEY;
import static net.bplaced.abzzezz.game.util.dialog.DialogUtil.QUESTION_PATTERN;

public interface BasicCall {

    GameMain GAME_MAIN = GameMain.INSTANCE;

    String[] formatted(String in, String color, Map<String, String> args);

    String tag();

    /**
     * Formats a question given a string.
     *
     * @param string string to get questions from
     */
    default void question(final String string) {
        final Matcher matcher = QUESTION_PATTERN.matcher(string);
        GAME_MAIN.getDialogHandler().getOptions().clear();
        for (int i = 0; matcher.find(); i++) {
            final String[] optionResultSplit = matcher.group().split(DEFINE_KEY);
            final String result = optionResultSplit[1];

            GAME_MAIN.getDialogHandler().getOptions().add(result.substring(0, result.length() - 1));
            GAME_MAIN.getDialogHandler().addToDialog(optionResultSplit[0].concat("(" + i + ")"));
        }
        GAME_MAIN.getDialogHandler().setPending(true);
    }

}
