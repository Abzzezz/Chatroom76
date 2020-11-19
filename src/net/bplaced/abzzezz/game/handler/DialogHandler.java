package net.bplaced.abzzezz.game.handler;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.utils.LogType;
import net.bplaced.abzzezz.engine.utils.Logger;
import net.bplaced.abzzezz.engine.utils.data.FileUtil;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.dialog.Dialog;
import net.bplaced.abzzezz.game.dialog.DialogLine;
import net.bplaced.abzzezz.game.dialog.DialogUtil;
import net.bplaced.abzzezz.game.screen.GameScreen;
import net.bplaced.abzzezz.game.screen.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class DialogHandler {

    private final List<DialogLine> displayDialogLine;
    private final HashMap<String, String> defined;
    private List<String> dialog;
    private int lastLine;
    private boolean pending;
    private String[] options;
    private Dialog dialogObject;


    public DialogHandler() {
        this.dialog = new ArrayList<>();
        this.displayDialogLine = new CopyOnWriteArrayList<>();
        this.defined = new HashMap<>();
    }

    public void getNextDialog() {
        //if no input expected
        if (!pending) {
            //Next = last + 1 (obviously?)
            int next = lastLine + 1;
            //If next bigger or equal to size return. Dialog finished
            if (next >= dialog.size()) return;
            //If the dialog started goto "startpoint"
            next = next(dialog.get(next));

            if (!format(next).isEmpty())
                addToDialog(format(next));

            lastLine = next;
        }
    }

    private int next(final String nextString) {
        final String[] split = nextString.split(" ");
        switch (split[0]) {
            //"Goto" command
            case DialogUtil.GOTO_KEY:
                return dialog.indexOf(":" + split[1]) + 1;
            case DialogUtil.END_KEY:
                GameMain.getInstance().getGlslShaderUtil().texture = -1;
                lastLine = 0;
                savePreviousDialog();
                EngineCore.getInstance().setScreen(new MainMenu());
                return 0;
            case DialogUtil.BACKGROUND_CALL:
                try {
                    GameMain.getInstance().setShaderTexture(new File(getArguments(nextString)[0]).toURI().toURL());
                } catch (final MalformedURLException e) {
                    Logger.log("Background texture not applied: " + e.getMessage(), LogType.ERROR);
                }
                return (lastLine + 2);
            default:
                break;
        }
        //pass through last line
        return (lastLine + 1);
    }

    //TODO: Add color support for text in actions eg. text in "playsound" etc.

    private String format(int index) {
        String format = dialog.get(index);
        String[] args = getArguments(format);
        switch (format.split(" ")[0]) {
            case DialogUtil.PLAY_SOUND_CALL:
                return playSound(format);
            case DialogUtil.QUESTION_CALL:
                addToDialog(args[0], Color.decode("#FA734D"));
                question(format);
                return "";
            case DialogUtil.COLOR_CODE_KEY:
                addToDialog(args[1], Color.decode(args[0]));
                return "";
            case DialogUtil.END_SOUNDS_CALL:
                GameMain.getInstance().getSoundPlayer().stopSounds();
                return args[0];
            default:
                break;
        }
        return format;
    }

    private String[] getArguments(final String string) {
        final String[] args = new String[Math.toIntExact(string.chars().filter(value -> value == '\"').count() / 2)];

        int lastIndex = 0;
        for (int i = 0; i < args.length; i++) {
            final int begin = string.indexOf("\"", lastIndex) + 1;
            final int end = string.indexOf("\"", begin);

            args[i] = string.substring(begin, end);
            lastIndex = begin + 1;
        }
        return args;
    }

    private void question(final String string) {
        final int blockSize = Math.toIntExact(string.chars().filter(value -> value == '{' || value == '}').count()) / 2;
        this.options = new String[blockSize + 1];

        int lastIndex = 0;
        for (int i = 0; i < blockSize; i++) {
            final int begin = string.indexOf("{", lastIndex) + 1;
            final int end = string.indexOf("}", begin);
            final String[] option = string.substring(begin, end).split(DialogUtil.DEFINE_KEY);
            options[i] = option[1];
            addToDialog(option[0].concat("(" + i + ")"));
            lastIndex = begin + 1;
        }
        pending = true;
    }

    public void selectOption(final char keyChar) {
        if (pending) {
            if (!Character.isDigit(keyChar)) return;

            for (int i = 0; i < options.length; i++) {
                if (i == Integer.parseInt(String.valueOf(keyChar))) {
                    addToDialog(String.valueOf(keyChar), Color.decode("#FB7E3F"));
                    pending = false;
                    lastLine = next(options[i]) - 1;
                    getNextDialog();
                }
            }
        }
    }

    private String playSound(final String format) {
        final String[] args = getArguments(format);
        if (args.length >= 3) {
            GameMain.getInstance().getSoundPlayer().playSound(new File(args[2].replace("\\", "\\\\")), Float.parseFloat(args[0]));
            return args[1];
        } else {
            GameMain.getInstance().getSoundPlayer().playSound(new File(args[1].replace("\\", "\\\\")), Float.parseFloat(args[0]));
        }
        return "";
    }

    /*
     * Preparation etc.
     */

    /**
     * Add line to dialog with color
     *
     * @param string
     * @param color
     */
    public void addToDialog(final String string, final Color color) {
        getDisplayDialog().add(new DialogLine(string, color));
    }

    /**
     * Color will just stay white
     *
     * @param string
     */
    public void addToDialog(final String string) {
        getDisplayDialog().add(new DialogLine(string, Color.WHITE));
    }

    private void prepare() {
        final String[] remove = new String[dialog.size()];
        for (int i = 0; i < dialog.size(); i++) {
            final String stringAt = dialog.get(i);
            if (stringAt.isEmpty()) {
                remove[i] = stringAt;
                continue;
            }

            final String[] split = stringAt.split(" ");

            switch (split[0]) {
                case DialogUtil.IMPORT_KEY:
                    final File file = new File(this.dialogObject.getAssetDir(), split[1]);
                    if (!file.exists()) {
                        try {
                            FileUtil.copyFileFromUrl(file, new URL(split[2]));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    remove[i] = stringAt;
                    break;
                case DialogUtil.DEFINED_KEY:
                    final String[] args = getArguments(stringAt);
                    this.defined.put(args[0], args[1]);
                    remove[i] = stringAt;
                    break;
                default:
                    break;
            }
        }

        for (int i = 0; i < dialog.size(); i++) {
            final String s1 = this.dialog.get(i);
            for (final Map.Entry<String, String> entry : defined.entrySet()) {
                if (s1.contains(entry.getKey()) && !s1.startsWith(DialogUtil.KEY))
                    replaceInDialog(i, entry.getKey(), entry.getValue());
            }

            if (s1.contains(DialogUtil.ASSET_KEY))
                replaceInDialog(i, DialogUtil.ASSET_KEY, this.dialogObject.getAssetDir().getAbsolutePath());
            if (s1.contains(DialogUtil.DIR_KEY))
                replaceInDialog(i, DialogUtil.DIR_KEY, this.dialogObject.getDialogDir().getAbsolutePath());
        }


        for (final String j : remove) {
            dialog.remove(j);
        }
        System.out.println(dialog);

        this.defined.clear();
        this.lastLine = this.dialog.indexOf(":start");
        Logger.log("Start line defined: " + lastLine, LogType.INFO);
    }

    /**
     * TODO: Add screen
     */
    public void downloadDialog() {
        new Thread(() -> {
            final String input = JOptionPane.showInputDialog("URL to download");
            final String fileName = input.substring(input.lastIndexOf('/') + 1);

            final Dialog newDialog = new Dialog(new File(EngineCore.getInstance().getMainDir(), fileName.substring(0, fileName.lastIndexOf('.'))));
            newDialog.createMetaData();
            try {
                FileUtil.copyFileFromUrl(newDialog.getDialogFile(), new URL(input));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Load new dialog, save old one etc.
     *
     * @param file
     */
    public void loadDialog(final File file) {
        this.savePreviousDialog();
        this.unloadPreviousDialog();
        //Set Dialog Object
        this.dialogObject = new Dialog(file);
        // this.lastLine = dialogObject.getLastLine();

        try {
            this.setDialog(FileUtil.readListFromFile(this.dialogObject.getDialogFile()));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        EngineCore.getInstance().setScreen(new GameScreen());
    }

    /**
     * Save the previous dialog
     */
    public void savePreviousDialog() {
        if (this.dialogObject != null) {
            dialogObject.save();
            Logger.log("Saved previous state", LogType.INFO);
        }
    }

    /**
     * Replace string in index
     *
     * @param index
     * @param old
     * @param replacement
     */
    private void replaceInDialog(final int index, final String old, final String replacement) {
        dialog.set(index, dialog.get(index).replace(old, replacement));
    }

    /**
     * Delete all files
     *
     * @param file
     */
    public void deleteDialog(final File file) {
        final Dialog dialog = new Dialog(file);
        for (final File listFile : Objects.requireNonNull(dialog.getDialogDir().listFiles()))
            Logger.log("Deleting dialog files: " + listFile.delete(), LogType.INFO);
        Logger.log("Deleting dialog dir:" + dialog.getDialogDir().delete(), LogType.INFO);
        Logger.log("Deleting dialog config:" + dialog.getCfgFile().delete(), LogType.INFO);
    }

    public boolean isPending() {
        return pending;
    }

    /**
     *
     */
    private void unloadPreviousDialog() {
        lastLine = 0;
        displayDialogLine.clear();
        dialog.clear();
    }

    /**
     * @param dialog
     */
    public void setDialog(final List<String> dialog) {
        this.dialog = dialog;
        this.prepare();
    }

    public List<DialogLine> getDisplayDialog() {
        return displayDialogLine;
    }
}
