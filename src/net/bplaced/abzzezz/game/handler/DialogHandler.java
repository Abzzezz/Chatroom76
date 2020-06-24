package net.bplaced.abzzezz.game.handler;

import ga.abzzezz.util.data.FileUtil;
import ga.abzzezz.util.data.data.Block;
import ga.abzzezz.util.data.data.BlockFormatter;
import ga.abzzezz.util.data.data.DataFormat;
import ga.abzzezz.util.data.data.DataObject;
import ga.abzzezz.util.logging.Logger;
import ga.abzzezz.util.misc.DateUtil;
import ga.abzzezz.util.stringing.StringUtil;
import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.game.MainClass;
import net.bplaced.abzzezz.game.dialog.Dialog;
import net.bplaced.abzzezz.game.dialog.DialogLine;
import net.bplaced.abzzezz.game.dialog.DialogUtil;
import net.bplaced.abzzezz.game.screen.GameScreen;
import net.bplaced.abzzezz.game.screen.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class DialogHandler {

    private List<String> dialog;
    private final List<DialogLine> displayDialogLine;
    private int lastLine;
    private boolean pending;
    private String[] options;
    private final HashMap<String, String> defined;
    private final BlockFormatter blockFormatter;
    private Dialog dialogObject;


    public DialogHandler() {
        this.dialog = new ArrayList<>();
        this.displayDialogLine = new CopyOnWriteArrayList<>();
        this.blockFormatter = new BlockFormatter();
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
        String[] split = nextString.split(" ");
        switch (split[0]) {
            //"Goto" command
            case DialogUtil.GOTO_KEY:
                return dialog.indexOf(":" + split[1]) + 1;
            case DialogUtil.END_KEY:
                MainClass.getInstance().getGlslShaderUtil().texture = -1;
                lastLine = 0;
                savePreviousDialog();
                EngineCore.getInstance().setScreen(new MainMenu());
                return 0;
            case DialogUtil.BACKGROUND_CALL:
                try {
                    MainClass.getInstance().setShaderTexture(new File(getArguments(nextString)[0]).toURI().toURL());
                } catch (MalformedURLException e) {
                    Logger.log("Background texture not applied: " + e.getMessage(), Logger.LogType.ERROR);
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
                MainClass.getInstance().getSoundPlayer().stopSounds();
                return args[0];
            default:
                break;
        }
        return format;
    }

    private String[] getArguments(final String string) {
        String[] args = new String[StringUtil.getTotalCharInString(string, '\"') / 2];
        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < args.length; i++) {
            int begin = stringBuilder.indexOf("\"") + 1;
            int end = stringBuilder.indexOf("\"", begin);
            args[i] = stringBuilder.substring(begin, end);
            stringBuilder.delete(begin - 1, end + 1);
        }
        return args;
    }

    private void question(final String in) {
        int blockSize = blockFormatter.getBlockElements(in);
        StringBuilder builderLine = new StringBuilder(in);
        this.options = new String[blockSize + 1];
        for (int i = 0; i < blockSize; i++) {
            Block block = new Block(builderLine.toString());
            int[] blockBounds = block.getBlock();
            addToDialog(block.getInnerBlock().split(StringUtil.splitter)[0] + "(" + i + ")");
            options[i] = block.getInnerBlock().split(StringUtil.splitter)[1];
            builderLine.delete(blockBounds[0], blockBounds[1]);
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
        String[] args = getArguments(format);
        if (args.length >= 3) {
            MainClass.getInstance().getSoundPlayer().playSound(new File(args[2].replace("\\", "\\\\")), Float.parseFloat(args[0]));
            return args[1];
        } else {
            MainClass.getInstance().getSoundPlayer().playSound(new File(args[1].replace("\\", "\\\\")), Float.parseFloat(args[0]));
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
        String[] remove = new String[dialog.size()];
        for (int i = 0; i < dialog.size(); i++) {
            String s1 = dialog.get(i);
            String[] split = this.dialog.get(i).split(" ");
            if (dialog.get(i).isEmpty()) remove[i] = s1;
            switch (split[0]) {
                case DialogUtil.IMPORT_KEY:
                    File file = new File(this.dialogObject.getDialogDir(), split[1]);
                    if (!file.exists()) FileUtil.copyFileFromURL(file, split[2]);
                    remove[i] = s1;
                    break;
                case DialogUtil.DEFINED_KEY:
                    String[] args = getArguments(s1);
                    this.defined.put(args[0], args[1]);
                    remove[i] = s1;
                    break;
                default:
                    break;
            }
        }

        for (int i = 0; i < dialog.size(); i++) {
            String s1 = this.dialog.get(i);
            for (Map.Entry<String, String> entry : defined.entrySet()) {
                if (s1.contains(entry.getKey()) && !(s1.startsWith(DialogUtil.KEY)))
                    replaceInDialog(i, entry.getKey(), entry.getValue());
            }
            if (s1.contains(DialogUtil.DIR_KEY))
                replaceInDialog(i, DialogUtil.DIR_KEY, this.dialogObject.getDialogDir().getAbsolutePath());
        }


        try {
            Optional<String> result = this.dialog.stream().filter(s -> s.startsWith(DialogUtil.BACKGROUND_MUSIC_CALL)).findFirst();
            if (result.isPresent()) {
                String bgMusic = result.get();
                File bgMusicFile = new File(getArguments(bgMusic)[1].replace("\\", "\\\\"));
                MainClass.getInstance().getSoundPlayer().playBackgroundMusic(bgMusicFile, Float.parseFloat(getArguments(bgMusic)[0]));
                this.dialog.remove(bgMusic);
            }
        } catch (NullPointerException e) {
            Logger.log("No Background-Music selected: ", Logger.LogType.WARNING);
        }

        for (String o : remove) {
            this.dialog.remove(o);
        }

        this.defined.clear();
        this.lastLine = this.dialog.indexOf(":start");
        Logger.log("Start line defined: " + lastLine, Logger.LogType.INFO);
    }

    /**
     * TODO: Add screen
     */
    public void downloadDialog() {
        new Thread(() -> {
            String url = JOptionPane.showInputDialog("URL to download");
            String fileName = url.substring(url.lastIndexOf('/') + 1);
            saveDialogDetails(fileName);
            File file = new File(EngineCore.getInstance().getMainDir(), fileName.substring(0, fileName.lastIndexOf('.')));
            if (!file.exists()) file.mkdir();
            FileUtil.copyFileFromURL(new File(file, fileName), url);
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
        this.lastLine = Integer.parseInt(this.dialogObject.getConfig()[2]);
        try {
            this.setDialog(FileUtil.getFileContentAsList(this.dialogObject.getDialogFile()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        EngineCore.getInstance().setScreen(new GameScreen());
    }

    /**
     * Save the previous dialog
     */
    public void savePreviousDialog() {
        if (this.dialogObject != null) {
            DataObject dataObject = new DataObject();
            dataObject.addObject("LastLine", this.lastLine);
            dataObject.addObject("FileExtension", this.dialogObject.getConfig()[0]);
            dataObject.addObject("Created", this.dialogObject.getConfig()[1]);
            DataFormat.formatData(dataObject, this.dialogObject.getConfigurationFile(), false, false);
            Logger.log("Saved previous state", Logger.LogType.INFO);
        }
    }

    /**
     * Save the dialog details for the first time
     *
     * @param fileName
     */
    private void saveDialogDetails(final String fileName) {
        //First save
        DataObject dataObject = new DataObject();
        dataObject.addObject("FileExtension", fileName.substring(fileName.lastIndexOf('.')));
        dataObject.addObject("Created", DateUtil.getCurrentDay());
        dataObject.addObject("LastLine", 0);
        DataFormat.formatData(dataObject, new File(EngineCore.getInstance().getMainDir(), fileName), false, true);
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
        Dialog dialog = new Dialog(file);
        for (File listFile : Objects.requireNonNull(dialog.getDialogDir().listFiles()))
            Logger.log("Deleting dialog files: " + listFile.delete(), Logger.LogType.INFO);
        Logger.log("Deleting dialog dir:" + dialog.getDialogDir().delete(), Logger.LogType.INFO);
        Logger.log("Deleting dialog config:" + dialog.getConfigurationFile().delete(), Logger.LogType.INFO);
    }

    public boolean isPending() {
        return pending;
    }

    private void unloadPreviousDialog() {
        lastLine = 0;
        displayDialogLine.clear();
        dialog.clear();
    }

    public void setDialog(final List<String> dialog) {
        this.dialog = dialog;
        this.prepare();
    }

    public List<DialogLine> getDisplayDialog() {
        return displayDialogLine;
    }
}
