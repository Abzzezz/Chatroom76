package net.bplaced.abzzezz.core.util.io;

import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class KeyboardUtil {

    public static boolean ctrlADown() {
        return (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && Keyboard.isKeyDown(Keyboard.KEY_A);
    }

    public static boolean shiftReturn() {
        return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) && Keyboard.isKeyDown(Keyboard.KEY_BACK);
    }

    public static boolean ctrlVDown() {
        return (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && Keyboard.isKeyDown(Keyboard.KEY_V);
    }

    public static String getClipboard() {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        try {
            return toolkit.getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
