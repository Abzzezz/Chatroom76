package net.bplaced.abzzezz.game.handler;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.util.data.FileUtil;
import net.bplaced.abzzezz.core.util.logging.LogType;
import net.bplaced.abzzezz.core.util.logging.Logger;
import net.bplaced.abzzezz.core.util.render.TextureLoader;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.dialog.Dialog;
import net.bplaced.abzzezz.game.dialog.DialogLine;
import net.bplaced.abzzezz.game.ui.screen.GameScreen;
import net.bplaced.abzzezz.game.ui.screen.MainMenu;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;

import static net.bplaced.abzzezz.game.util.dialog.DialogUtil.*;

public class DialogHandler {

    private final List<DialogLine> displayDialog;
    private final Map<String, String> defined;
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
            case GOTO_KEY:
                return dialog.indexOf(":".concat(split[1])) + 1;

            case END_KEY:
                lastLine = 0;
                savePreviousDialog();
                GameMain.INSTANCE.getSoundPlayer().stopMusic();
                dialogHolder.save();
                Core.getInstance().setScreen(new MainMenu());
                return 0;
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
            case PLAY_SOUND_CALL:
                formatted[0] = playSound(format);
                return formatted;

            case QUESTION_CALL:
                formatted[0] = args.get(TEXT_ARGUMENT);
                question(format);
                return formatted;

            case COLOR_CODE_KEY:
                formatted[0] = args.get(TEXT_ARGUMENT);
                return formatted;

            case END_SOUNDS_CALL:
                GameMain.INSTANCE.getSoundPlayer().stopSounds();
                formatted[0] = args.getOrDefault(TEXT_ARGUMENT, "");
                return formatted;

            case BACKGROUND_CALL:
                formatted[0] = args.getOrDefault(TEXT_ARGUMENT, "");
                try {
                    GameMain.INSTANCE.getShaderHandler().getTextureShader().setSampler(TextureLoader.loadPNGTexture(new File(args.get(PATH_ARGUMENT)).toURI().toURL()));
                    GameMain.INSTANCE.getShaderHandler().getTextureShader().setOpacity(Float.parseFloat(args.getOrDefault(BACKGROUND_OPACITY_ARGUMENT, "100")) / 100);
                } catch (final MalformedURLException e) {
                    Logger.log("Background texture not applied: " + e.getMessage(), LogType.ERROR);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        final Matcher matcher = ARGUMENT_PATTERN.matcher(string);
        while (matcher.find()) {
            final String[] argumentValueSplit = matcher.group().split(DEFINE_KEY);
            final String value = argumentValueSplit[1];
            argumentValueMap.put(argumentValueSplit[0].substring(1), value.substring(0, value.length() - 1));
        }
        return argumentValueMap;
    }

    /**
     * Formats a question given a string.
     *
     * @param string string to get questions from
     */
    private void question(final String string) {
        final Matcher matcher = QUESTION_PATTERN.matcher(string);
        this.options.clear();

        for (int i = 0; matcher.find(); i++) {
            final String[] optionResultSplit = matcher.group().split(DEFINE_KEY);
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
            if (num < options.size() && num >= 0) {
                addToDialog(valueOf, Color.decode("#FB7E3F"));
                lastLine = next(options.get(num)) - 1;
                pending = false;
                getNextDialog();
            }

        }
    }

    private String playSound(final String format) {
        final Map<String, String> args = getArguments(format);
        final File file = new File(args.getOrDefault(PATH_ARGUMENT, "").replace("\\", "\\\\"));
        final float volume = Float.parseFloat(args.getOrDefault(VOLUME_ARGUMENT, "0"));
        GameMain.INSTANCE.getSoundPlayer().playSound(file, volume);
        return args.getOrDefault(TEXT_ARGUMENT, "");
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
                case IMPORT_KEY:
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
                case DEFINED_KEY:
                    final Map<String, String> args = getArguments(stringAt);
                    defined.put(args.get(VARIABLE_ARGUMENT), args.get(DEFINE_ARGUMENT));
                    remove[i] = stringAt;
                    break;
                default:
                    break;
            }
        }

        for (int i = 0; i < dialog.size(); i++) {
            final String stringAt = dialog.get(i);
            for (final Map.Entry<String, String> entry : defined.entrySet()) {
                if (stringAt.contains(entry.getKey()) && !stringAt.startsWith(KEY))
                    replaceInDialog(i, entry.getKey(), entry.getValue());
            }

            if (stringAt.contains(ASSET_KEY))
                replaceInDialog(i, ASSET_KEY, this.dialogHolder.getAssets().getAbsolutePath());
            if (stringAt.contains(DIR_KEY))
                replaceInDialog(i, DIR_KEY, this.dialogHolder.getDialogDir().getAbsolutePath());
        }

        for (final String s : remove) dialog.remove(s);

        dialog.stream().filter(s -> s.startsWith(BACKGROUND_MUSIC_CALL)).findAny().ifPresent(s -> {
            final Map<String, String> args1 = getArguments(s);
            final File backgroundMusic = new File(args1.getOrDefault(PATH_ARGUMENT, "").replace("\\", "\\\\"));
            final float volume = Float.parseFloat(args1.getOrDefault(VOLUME_ARGUMENT, "0"));
            GameMain.INSTANCE.getSoundPlayer().playBackgroundMusic(backgroundMusic, volume);
            dialog.remove(s);
        });

        this.defined.clear();
        this.lastLine = this.dialog.indexOf(":start");
        Logger.log("Start line defined: " + lastLine, LogType.INFO);
    }

    private List<String> replaceInDialog(final List<String> lines, final Dialog dialog) {
        final Map<String, String> definedVars = new HashMap<>();

        for (final String line : lines) {
            final String[] split = line.split(" ");
            final Map<String, String> args = getArguments(line);

            if (DEFINED_KEY.equals(split[0])) {
                definedVars.put(args.get(VARIABLE_ARGUMENT), args.get(DEFINE_ARGUMENT));
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            final String stringAt = lines.get(i);
            for (final Map.Entry<String, String> entry : definedVars.entrySet()) {
                if (stringAt.contains(entry.getKey()) && !stringAt.startsWith(KEY))
                    lines.set(i, stringAt.replace(entry.getKey(), entry.getValue()));
            }

            if (stringAt.contains(ASSET_KEY))
                lines.set(i, stringAt.replace(ASSET_KEY, dialog.getAssets().getAbsolutePath()));

            if (stringAt.contains(DIR_KEY))
                lines.set(i, stringAt.replace(DIR_KEY, dialog.getDialogDir().getAbsolutePath()));
        }
        return lines;
    }


    public void downloadDialog(final String input, final Consumer<Integer> totalBytes, final Consumer<Integer> downloadedBytes, final Consumer<String> downloadingFile) {
        new Thread(() -> {
            if (input == null || input.isEmpty()) return;
            Logger.log("Starting dialog download", LogType.INFO);

            try {
                final List<String> urlLines = FileUtil.readListFromURL(new URL(input));
                //TODO: Whip up better solution
                final String dialogName = getArguments(urlLines.stream().filter(s -> s.startsWith(DIALOG_KEY)).findAny().orElse("")).getOrDefault("name", "unnamedDialog");

                final Dialog newDialog = new Dialog(new File(Core.getInstance().getMainDir(), dialogName));
                newDialog.createMetaData();
                newDialog.save();

                final List<String> lines = replaceInDialog(urlLines, newDialog);

                FileUtil.writeListToFile(newDialog.getDialogFile(), lines);

                final Map<URL, File> assets = new HashMap<>();
                int totalAssetSize = 0;

                for (final String line : lines) {
                    final String[] split = line.split(" ");
                    if (IMPORT_KEY.equals(split[0])) {
                        final File asset = new File(newDialog.getAssets(), split[1]);
                        final URL assetURL = new URL(split[2]);
                        assets.put(assetURL, asset);
                        totalAssetSize += assetURL.openConnection().getContentLength();
                    }
                }

                totalBytes.accept(totalAssetSize);

                assets.forEach((url, file) -> {
                    downloadingFile.accept(file.getName().substring(0, file.getName().lastIndexOf(".")));

                    try (final BufferedInputStream in = new BufferedInputStream(url.openStream());
                         final FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                        byte[] dataBuffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                            fileOutputStream.write(dataBuffer, 0, bytesRead);
                            downloadedBytes.accept(bytesRead);
                        }
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                });


                downloadingFile.accept("Done");
            } catch (IOException e) {
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
        Core.getInstance().setScreen(new GameScreen());
    }

    public String getColor(final Map<String, String> args) {
        return args.getOrDefault(COLOR_ARGUMENT, PLAIN_WHITE);
    }

    public Color getColor0(final Map<String, String> args) {
        return Color.decode(args.getOrDefault(COLOR_ARGUMENT, PLAIN_WHITE));
    }

    private void replaceInDialog(final int index, final String old, final String replacement) {
        dialog.set(index, dialog.get(index).replace(old, replacement));
    }

    public void savePreviousDialog() {
        if (this.dialogHolder != null) {
            dialogHolder.updateLine(0);
            dialogHolder.save();
            Logger.log("Saved previous state", LogType.INFO);
        }
    }

    public void deleteDialog(final Dialog dialog) {
        if (dialog == null) return;
        for (final File listFile : Objects.requireNonNull(dialog.getDialogDir().listFiles()))
            Logger.log("Deleting dialog files: " + listFile.delete(), LogType.INFO);


        for (final File listFile : Objects.requireNonNull(dialog.getAssets().listFiles()))
            Logger.log("Deleting assets: " + listFile.delete(), LogType.INFO);


        Logger.log("Deleting asset dir:" + dialog.getAssets().delete(), LogType.INFO);
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
