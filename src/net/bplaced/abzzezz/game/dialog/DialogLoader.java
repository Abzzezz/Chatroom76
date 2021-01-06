/*
 * @author Roman
 * Last modified: 03.01.21, 21:52 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.dialog;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.util.data.FileUtil;
import net.bplaced.abzzezz.core.util.logging.LogType;
import net.bplaced.abzzezz.core.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialogLoader {

    private final File dialogsFile, dialogDir;
    private final List<Dialog> dialogs = new ArrayList<>();

    public DialogLoader() {
        this.dialogDir = new File(Core.getInstance().getMainDir(), "game");
        this.dialogsFile = new File(Core.getInstance().getMainDir(), "dialogs");
        this.createDirectories();
    }

    public void saveDialogs() {
        if (exists()) {
            if (dialogsFile.delete()) {
                Logger.log("Saving dialogs", LogType.INFO);
                final JSONArray dialogJSONArray = new JSONArray();
                dialogs.stream()
                        .map(dialog -> new JSONObject().put("id", dialog.getDialogID()).put("name", dialog.getDialogName()))
                        .forEach(dialogJSONArray::put);
                try {
                    FileUtil.writeStringToFile(dialogJSONArray.toString(), dialogsFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            createDirectories();
            saveDialogs();
        }
    }

    public void loadDialogs() {
        if (exists()) {
            try {
                Logger.log("Loading dialogs", LogType.INFO);
                final String readString = FileUtil.readFromFile(dialogsFile);
                if (readString.isEmpty()) return;

                final JSONArray dialogJSONArray = new JSONArray(readString);
                for (int i = 0; i < dialogJSONArray.length(); i++) {
                    final JSONObject lineJSON = dialogJSONArray.getJSONObject(i);
                    final String dialogID = lineJSON.getString("id");
                    final String dialogName = lineJSON.getString("name");
                    dialogs.add(new Dialog(dialogName, dialogID));
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } else {
            createDirectories();
            loadDialogs();
        }
    }

    public void addDialog(final Dialog dialog) {
        this.dialogs.add(dialog);
    }

    public void removeDialog(final Dialog dialog) {
        this.dialogs.remove(dialog);
    }

    public boolean exists() {
        return dialogDir.exists() && dialogsFile.exists();
    }

    public void createDirectories() {
        if (!dialogDir.exists()) Logger.log("Creating directory for dialogs: " + dialogDir.mkdir(), LogType.INFO);
        if (!dialogsFile.exists()) {
            try {
                Logger.log("Creating file for dialogs: " + dialogsFile.createNewFile(), LogType.INFO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getDialogDir() {
        return dialogDir;
    }

    public List<Dialog> getDialogs() {
        return dialogs;
    }
}
