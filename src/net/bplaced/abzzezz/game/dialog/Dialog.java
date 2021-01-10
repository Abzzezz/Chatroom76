/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.dialog;

import net.bplaced.abzzezz.core.util.data.FileUtil;
import net.bplaced.abzzezz.core.util.log.LogType;
import net.bplaced.abzzezz.core.util.log.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Dialog {

    private final String dialogName;
    private final String dialogID;

    private final File cfgFile, dialogFile, dialogDir, assetsDir;
    private JSONObject metaData;

    public Dialog(final String dialogName) {
        //Set dialog name and generate dialog id
        this.dialogName = dialogName;
        this.dialogID = UUID.randomUUID().toString();

        this.dialogDir = new File(DialogLoader.DIALOG_LOADER.getDialogDir(), dialogID);
        this.cfgFile = new File(dialogDir, dialogID.concat(".cfg"));
        this.dialogFile = new File(dialogDir, dialogID.concat(".dlg"));
        this.assetsDir = new File(dialogDir, "assets");
        this.metaData = new JSONObject();
        this.createDirectories();
    }

    public Dialog(final String dialogName, final String dialogID) {
        //Set dialog name and generate dialog id
        this.dialogName = dialogName;
        this.dialogID = dialogID;

        this.dialogDir = new File(DialogLoader.DIALOG_LOADER.getDialogDir(), dialogID);
        this.cfgFile = new File(dialogDir, dialogID.concat(".cfg"));
        this.dialogFile = new File(dialogDir, dialogID.concat(".dlg"));
        this.assetsDir = new File(dialogDir, "assets");
        this.metaData = new JSONObject();
        this.createDirectories();
        this.loadMetaData();
    }

    private void createDirectories() {
        try {
            if (!dialogDir.exists()) Logger.log("Creating dialog directory: " + dialogDir.mkdir(), LogType.INFO);
            if (!cfgFile.exists()) Logger.log("Creating config file: " + cfgFile.createNewFile(), LogType.INFO);
            if (!assetsDir.exists()) Logger.log("Creating asset directory: " + assetsDir.mkdir(), LogType.INFO);
        } catch (final IOException e) {
            Logger.log("Error creating dialog-necessary directories ", LogType.WARNING);
            e.printStackTrace();
        }
    }

    public void createMetaData() {
        metaData.put("created", DateFormat.getInstance().format(new Date())).put("last", 0);
    }

    public void updateLine(final int last) {
        metaData.put("last", last);
    }

    public void save() {
        try {
            FileUtil.writeStringToFile(metaData.toString(), cfgFile);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMetaData() {
        try {
            metaData = new JSONObject(FileUtil.readFromFile(cfgFile));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        if (!dialogDir.delete()) {
            for (final File listFile : Objects.requireNonNull(dialogDir.listFiles()))
                Logger.log("Deleting dialog files: " + listFile.delete(), LogType.INFO);
        }

        if (!assetsDir.delete() && assetsDir.listFiles() != null) {
            for (final File listFile : Objects.requireNonNull(assetsDir.listFiles()))
                Logger.log("Deleting assets: " + listFile.delete(), LogType.INFO);
        }

        Logger.log("Deleting asset dir:" + assetsDir.delete(), LogType.INFO);
        Logger.log("Deleting dialog dir:" + dialogDir.delete(), LogType.INFO);
    }

    public String getCreationDate() {
        return metaData.getString("created");
    }

    public int getLastLine() {
        return metaData.getInt("last");
    }

    public String getDialogName() {
        return dialogName;
    }

    public File getCfgFile() {
        return cfgFile;
    }

    public File getDialogFile() {
        return dialogFile;
    }

    public File getDialogDir() {
        return dialogDir;
    }

    public File getAssets() {
        return assetsDir;
    }

    public String getDialogID() {
        return dialogID;
    }
}
