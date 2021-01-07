/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.util.dialog;

import java.util.regex.Pattern;

/**
 * Contains keywords
 */

public class DialogUtil {
    /**
     * Pattern to get all arguments
     */
    public static final Pattern ARGUMENT_PATTERN = Pattern.compile("(\"(.*?)=(.*?)\")");
    /**
     * Pattern to get all options from a question
     */
    public static final Pattern QUESTION_PATTERN = Pattern.compile("\\Q{\\E.*?=.*?\\Q}\\E");
    /*
     * Keys
     */
    public static final String KEY = "#";
    public static final String DEFINED_KEY = KEY + "define";
    public static final String IMPORT_KEY = KEY + "import";
    public static final String DIALOG_KEY = KEY + "dialog";
    public static final String GOTO_KEY = KEY + "goto";
    public static final String END_KEY = KEY + "end";
    public static final String COLOR_CODE_KEY = KEY + "$";
    /*
     * Calls
     */
    public static final String CALL = "::";
    public static final String END_SOUNDS_CALL = CALL + "endsounds";
    public static final String QUESTION_CALL = CALL + "question";
    public static final String PLAY_SOUND_CALL = CALL + "playsound";
    public static final String BACKGROUND_MUSIC_CALL = CALL + "backgroundmusic";
    public static final String BACKGROUND_CALL = CALL + "background";
    /*
     * Keys
     */
    public static final String DIR_KEY = "$dir";
    public static final String ASSET_KEY = "$assets";
    public static final String DEFINE_KEY = "=";
    /**
     * Different arguments
     */
    public static final String TEXT_ARGUMENT = "t";
    public static final String COLOR_ARGUMENT = "color";
    public static final String PATH_ARGUMENT = "path";
    public static final String VOLUME_ARGUMENT = "vol";
    public static final String OPACITY_ARGUMENT = "opacity";
    public static final String VARIABLE_ARGUMENT = "v";
    public static final String DEFINE_ARGUMENT = "as";
    public static final String URL_ARGUMENT = "url";
    public static final String DESTINATION_ARGUMENT = "dest";
    /**
     * String with hex white
     */
    public static final String PLAIN_WHITE = "0xFFFFFF";
    /**
     * Defines the max length a line can have (in characters)
     */
    public static final int MAX_LINE_LENGTH = 45;
}
