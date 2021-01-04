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

    /*
    Taken syntax:
    $dir
    $#                  change text color. args: "hex color code" "text"
    #goto               Skips to next method with given name. args: methodname
    :methodname         Method
    ::endsounds         Ends all sounds. agrs "Text"
    ::question          Ask a question. args: "Question" {Option1:::do whatever} {Option2:::do whatever} ...etc.
    #import             Import a file from an url. args: FileName, URL
    #define             Define a variable eg. eg.: "$6" "Mysterious man"
    ::playsound         Play sound: args: "volume" "Text", "Location(can be $dir)"
    ::backgroundmusic   Play background music args: "volume" "Location"
    ::backround         Change the background texture. args: "location"
     */

    public static final Pattern ARGUMENT_PATTERN = Pattern.compile("(\"(.*?)=(.*?)\")");
    public static final Pattern QUESTION_PATTERN = Pattern.compile("(" + Pattern.quote("{") + "(.*?)=(.*?[^}])" + Pattern.quote("}") + ")");

    public static final String KEY = "#";
    public static final String DEFINED_KEY = "#define";
    public static final String IMPORT_KEY = "#import";
    public static final String DIALOG_KEY = "#dialog";
    public static final String COLOR_CODE_KEY = "#$";
    public static final String END_KEY = "#end";
    public static final String GOTO_KEY = "#goto";

    public static final String END_SOUNDS_CALL = "::endsounds";
    public static final String QUESTION_CALL = "::question";
    public static final String PLAY_SOUND_CALL = "::playsound";
    public static final String BACKGROUND_MUSIC_CALL = "::backgroundmusic";
    public static final String BACKGROUND_CALL = "::background";

    public static final String DIR_KEY = "$dir";
    public static final String ASSET_KEY = "$assets";

    public static final String DEFINE_KEY = "=";

    /**
     * Args.
     */
    public static final String TEXT_ARGUMENT = "t";
    public static final String COLOR_ARGUMENT = "color";
    public static final String PATH_ARGUMENT = "path";
    public static final String VOLUME_ARGUMENT = "vol";
    public static final String BACKGROUND_OPACITY_ARGUMENT = "opacity";

    public static final String VARIABLE_ARGUMENT = "v";
    public static final String DEFINE_ARGUMENT = "as";

    public static final String URL_ARGUMENT = "url";
    public static final String DESTINATION_ARGUMENT = "dest";

    public static final String PLAIN_WHITE = "0xFFFFFF";

    public static final int MAX_LINE_LENGTH = 45;
}
