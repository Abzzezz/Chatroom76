package net.bplaced.abzzezz.game.dialog;

import net.bplaced.abzzezz.engine.utils.data.FileUtil;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class Dialog {

    private final String dialogName;
    private final File cfgFile, dialogFile, dialogDir, assetDir;
    private final JSONObject metaData;

    public Dialog(final File dialogDir) {
        this.dialogDir = dialogDir;
        this.dialogName = dialogDir.getName();
        this.cfgFile = new File(dialogDir, dialogName.concat(".cfg"));
        this.dialogFile = new File(dialogDir, dialogName.concat(".dlg"));
        this.assetDir = new File(dialogDir, "assets");
        this.metaData = new JSONObject();
        createDirectories();
    }

    public Dialog(final File dialogDir, final JSONObject metaData) {
        this.dialogDir = dialogDir;
        this.dialogName = dialogDir.getName();
        this.cfgFile = new File(dialogDir, dialogName.concat(".cfg"));
        this.dialogFile = new File(dialogDir, dialogName.concat(".dlg"));
        this.assetDir = new File(dialogDir, "assets");
        this.metaData = metaData;
        createDirectories();
    }

    private void createDirectories() {
        try {
            if (!dialogDir.exists()) dialogDir.mkdir();
            if (!cfgFile.exists()) cfgFile.createNewFile();
            if (!assetDir.exists()) assetDir.mkdir();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMetaData() {
        metaData.put("created", DateFormat.getInstance().format(new Date())).put("last", 0);
    }

    public void save() {
        try {
            FileUtil.writeStringToFile(metaData.toString(), cfgFile);
        } catch (final IOException e) {
            e.printStackTrace();
        }
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

    public File getAssetDir() {
        return assetDir;
    }
}
