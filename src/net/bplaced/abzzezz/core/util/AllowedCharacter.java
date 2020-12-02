package net.bplaced.abzzezz.core.util;

public class AllowedCharacter {

    public static boolean isAllowedCharacter(char character)
    {
        return character != 167 && character >= 32;
    }
}
