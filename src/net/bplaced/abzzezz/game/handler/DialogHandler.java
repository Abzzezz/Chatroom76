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
import java.util.regex.Matcher;

public class DialogHandler {

    private final List<DialogLine> displayDialog;
    private final HashMap<String, String> defined;
    private final List<String> options, dialog;

    private int lastLine;
    private boolean pending;
    private Dialog dialogHolder;

    public DialogHandler() {
        this.dialog = new ArrayList<>();
        this.displayDialog = new ArrayList<>();
        this.defined = new HashMap<>();
        this.options = new ArrayList<>();
    }

    /**
     * Get the next available dialog text to add
     */
    public void getNextDialog() {
        //if no input expected
        if (!pending) {
            //Next = last + 1 (obviously?)
            int next = lastLine + 1;
            //If next bigger or equal to size return. Dialog finished
            if (next >= dialog.size()) return;
            //If the dialog started goto "startpoint"
            next = next(dialog.get(next));

            final String[] format = formatNext(next);

            if (format[0] != null && !format[0].isEmpty())
                addToDialog(format[0], Color.decode(format[1]));

            lastLine = next;
        }
    }

    /**
     * Get the next index for the next String
     *
     * @param nextString String that's is supposed to come next
     * @return index to read string from
     */
    private int next(final String nextString) {
        final String[] split = nextString.split(" ");
        switch (split[0]) {
            case DialogUtil.GOTO_KEY:
                return dialog.indexOf(":".concat(split[1])) + 1;

            case DialogUtil.END_KEY:
                GameMain.getInstance().getGlslShaderUtil().texture = -1;
                lastLine = 0;
                savePreviousDialog();
                EngineCore.getInstance().setScreen(new MainMenu());
                return 0;

            case DialogUtil.BACKGROUND_CALL:
                try {
                    GameMain.getInstance().setShaderTexture(new File(getArguments(nextString).get(DialogUtil.PATH_ARGUMENT)).toURI().toURL());
                } catch (final MalformedURLException e) {
                    Logger.log("Background texture not applied: " + e.getMessage(), LogType.ERROR);
                }
                return lastLine + 2;
            default:
                return lastLine + 1;
        }
    }

    /**
     * formats the next string from an index
     *
     * @param index index to format String from
     * @return String array index 0 containing the string and 1 containing the color
     */
    private String[] formatNext(int index) {
        String format = dialog.get(index);
        final Map<String, String> args = getArguments(format);
        final String[] formatted = new String[]{format, getColor(args)};

        switch (format.split(" ")[0]) {
            case DialogUtil.PLAY_SOUND_CALL:
                formatted[0] = playSound(format);
                return formatted;

            case DialogUtil.QUESTION_CALL:
                formatted[0] = args.get(DialogUtil.TEXT_ARGUMENT);
                question(format);
                return formatted;

            case DialogUtil.COLOR_CODE_KEY:
                formatted[0] = args.get(DialogUtil.TEXT_ARGUMENT);
                return formatted;

            case DialogUtil.END_SOUNDS_CALL:
                GameMain.getInstance().getSoundPlayer().stopSounds();
                formatted[0] = args.getOrDefault(DialogUtil.TEXT_ARGUMENT, "");
                return formatted;
            default:
                return formatted;
        }
    }

    /**
     * Returns all arguments given a string. Matches the argument pattern against the given string. After that all matches are added to a map
     *
     * @param string String to pull arguments from
     * @return Map containing the keyword as the key and the argument as the value
     */
    private Map<String, String> getArguments(final String string) {
        final Map<String, String> argumentValueMap = new HashMap<>();
        final Matcher matcher = DialogUtil.ARGUMENT_PATTERN.matcher(string);
        while (matcher.find()) {
            final String[] argumentValueSplit = matcher.group().split(DialogUtil.DEFINE_KEY);
            final String value = argumentValueSplit[1];
            argumentValueMap.put(argumentValueSplit[0].substring(1), value.substring(0, value.length() - 1));
        }
        return argumentValueMap;
    }

    /**
     * Formats a question given a string.
     *
     * @param string
     */
    private void question(final String string) {
        final Matcher matcher = DialogUtil.QUESTION_PATTERN.matcher(string);
        this.options.clear();

        for (int i = 0; matcher.find(); i++) {
            final String[] optionResultSplit = matcher.group().split(DialogUtil.DEFINE_KEY);
            final String result = optionResultSplit[1];

            options.add(result.substring(0, result.length() - 1));
            addToDialog(optionResultSplit[0].concat("(" + i + ")"));
        }
        pending = true;
    }

    public void selectOption(final char keyChar) {
        if (pending) {
            if (!Character.isDigit(keyChar)) return;
            final String valueOf = String.valueOf(keyChar);
            final int num = Integer.parseInt(valueOf);
            if (num < options.size() && num > 0) {
                addToDialog(valueOf, Color.decode("#FB7E3F"));
                lastLine = next(options.get(num - 1));
                pending = false;
                getNextDialog();
            }

        }
    }

    private String playSound(final String format) {
        final Map<String, String> args = getArguments(format);
        final File file = new File(args.getOrDefault(DialogUtil.PATH_ARGUMENT, "").replace("\\", "\\\\"));
        final float volume = Float.parseFloat(args.getOrDefault(DialogUtil.VOLUME_ARGUMENT, "0"));
        GameMain.getInstance().getSoundPlayer().playSound(file, volume);
        return args.getOrDefault(DialogUtil.TEXT_ARGUMENT, "");
    }

    /*
     * Preparation etc.
     */

    public void addToDialog(final String string, final Color color) {
        getDisplayDialog().add(new DialogLine(string, color));
    }

    public void addToDialog(final String string) {
        getDisplayDialog().add(new DialogLine(string, Color.WHITE));
    }

    private void prepare() {
        dialog.removeIf(String::isEmpty);

        final String[] remove = new String[dialog.size()];
        for (int i = 0; i < dialog.size(); i++) {
            final String stringAt = dialog.get(i);
            final String[] split = stringAt.split(" ");

            switch (split[0]) {
                case DialogUtil.IMPORT_KEY:
                    final File file = new File(this.dialogHolder.getAssets(), split[1]);
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
                    final Map<String, String> args = getArguments(stringAt);
                    this.defined.put(args.get(DialogUtil.VARIABLE_ARGUMENT), args.get(DialogUtil.DEFINE_ARGUMENT));
                    remove[i] = stringAt;
                    break;
                default:
                    break;
            }
        }

        for (int i = 0; i < dialog.size(); i++) {
            final String stringAt = dialog.get(i);
            for (final Map.Entry<String, String> entry : defined.entrySet()) {
                if (stringAt.contains(entry.getKey()) && !stringAt.startsWith(DialogUtil.KEY))
                    replaceInDialog(i, entry.getKey(), entry.getValue());
            }

            if (stringAt.contains(DialogUtil.ASSET_KEY))
                replaceInDialog(i, DialogUtil.ASSET_KEY, this.dialogHolder.getAssets().getAbsolutePath());
            if (stringAt.contains(DialogUtil.DIR_KEY))
                replaceInDialog(i, DialogUtil.DIR_KEY, this.dialogHolder.getDialogDir().getAbsolutePath());
        }


        for (final String s : remove) dialog.remove(s);

        this.defined.clear();
        this.lastLine = this.dialog.indexOf(":start");
        Logger.log("Start line defined: " + lastLine, LogType.INFO);
    }

    /**
     * TODO: Add screen
     */
    public void downloadDialog(final String input) {
        new Thread(() -> {
            if (input == null) return;
            Logger.log("Starting dialog download", LogType.INFO);
            final String fileName = input.substring(input.lastIndexOf('/') + 1);

            final Dialog newDialog = new Dialog(new File(EngineCore.getInstance().getMainDir(), fileName.substring(0, fileName.lastIndexOf('.'))));
            newDialog.createMetaData();
            newDialog.save();
            try {
                FileUtil.copyFileFromUrl(newDialog.getDialogFile(), new URL(input));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void loadDialog(final Dialog dialog) {
        if (dialog == null) return;
        this.savePreviousDialog();
        this.unloadPreviousDialog();
        //Set Dialog Object
        this.dialogHolder = dialog;
        // this.lastLine = dialogObject.getLastLine();

        try {
            this.setDialog(FileUtil.readListFromFile(this.dialogHolder.getDialogFile()));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        EngineCore.getInstance().setScreen(new GameScreen());
    }

    public String getColor(final Map<String, String> args) {
        return args.getOrDefault(DialogUtil.COLOR_ARGUMENT, DialogUtil.PLAIN_WHITE);
    }

    public Color getColor0(final Map<String, String> args) {
        return Color.decode(args.getOrDefault(DialogUtil.COLOR_ARGUMENT, DialogUtil.PLAIN_WHITE));
    }

    public void savePreviousDialog() {
        if (this.dialogHolder != null) {
            dialogHolder.save();
            Logger.log("Saved previous state", LogType.INFO);
        }
    }

    private void replaceInDialog(final int index, final String old, final String replacement) {
        dialog.set(index, dialog.get(index).replace(old, replacement));
    }

    public void deleteDialog(final Dialog dialog) {
        if (dialog == null) return;
        for (final File listFile : Objects.requireNonNull(dialog.getDialogDir().listFiles()))
            Logger.log("Deleting dialog files: " + listFile.delete(), LogType.INFO);
        Logger.log("Deleting dialog dir:" + dialog.getDialogDir().delete(), LogType.INFO);
        Logger.log("Deleting dialog config:" + dialog.getCfgFile().delete(), LogType.INFO);
    }

    public boolean isPending() {
        return pending;
    }

    private void unloadPreviousDialog() {
        lastLine = 0;
        displayDialog.clear();
        dialog.clear();
    }

    public void setDialog(final List<String> dialog) {
        this.dialog.clear();
        this.dialog.addAll(dialog);
        this.prepare();
    }

    public List<DialogLine> getDisplayDialog() {
        return displayDialog;
    }
}
