package net.bplaced.abzzezz.core.util;

public class AllowedCharacter {

    private static final String[][] UMLAUT_REPLACEMENTS = { { "Ä", "Ae" }, { "Ü", "Ue" }, { "Ö", "Oe" }, { "ä", "ae" }, { "ü", "ue" }, { "ö", "oe" }, { "ß", "ss" } };

    public static boolean isAllowedCharacter(char character) {
        return character != 167 && character >= 32;
    }


    public static String replaceUmlaute(String orig) {
        String result = orig;

        for (final String[] umlautReplacement : UMLAUT_REPLACEMENTS) {
            result = result.replace(umlautReplacement[0], umlautReplacement[1]);
        }
        return result;
    }
}
