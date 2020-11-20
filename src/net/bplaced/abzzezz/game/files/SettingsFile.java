package net.bplaced.abzzezz.game.files;

import net.bplaced.abzzezz.engine.file.CustomFile;
import net.bplaced.abzzezz.engine.utils.data.FileUtil;
import net.bplaced.abzzezz.game.GameMain;
import org.json.JSONObject;

import java.io.IOException;

public class SettingsFile extends CustomFile {

    public SettingsFile() {
        super("Settings.data");
    }

    @Override
    public void read() {
        try {
            FileUtil.readListFromFile(thisFile).forEach(s -> {
                final JSONObject settings = new JSONObject(s);
                GameMain.getInstance().getSettingsHandler().setVolume(settings.getFloat("volume"));
            });
        } catch (final IOException e) {
            e.printStackTrace();
        }
        super.read();
    }

    @Override
    public void write() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("volume", GameMain.getInstance().getSettingsHandler().getVolume());
        jsonObject.put("resolutionW", GameMain.getInstance().getSettingsHandler().getResolution()[0]);
        jsonObject.put("resolutionH", GameMain.getInstance().getSettingsHandler().getResolution()[1]);
        try {
            FileUtil.writeStringToFile(jsonObject.toString(), thisFile);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        super.write();
    }
}
