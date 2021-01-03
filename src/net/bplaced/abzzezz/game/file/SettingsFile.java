package net.bplaced.abzzezz.game.file;

import net.bplaced.abzzezz.core.file.BasicFile;
import net.bplaced.abzzezz.core.util.data.FileUtil;
import net.bplaced.abzzezz.game.GameMain;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class SettingsFile extends BasicFile {

    public SettingsFile() {
        super("Settings.data");
    }

    @Override
    public void read() {
        try {
            final String readString = FileUtil.readFromFile(thisFile);
            if (readString.isEmpty()) return;
            final JSONArray settingsJSONArray = new JSONArray(readString);
            for (int i = 0; i < settingsJSONArray.length(); i++) {
                final JSONObject readObject = settingsJSONArray.getJSONObject(i);
                GameMain.INSTANCE.getSettingsHandler().getSettingByTag(readObject.getString("tag")).ifPresent(setting -> {
                    switch (setting.getSettingsType()) {
                        case BOOL:
                            setting.setState(readObject.getBoolean("val"));
                            break;
                        case LIST:
                        case SWITCHER:
                            setting.setSelected(readObject.getString("val"));
                            break;
                        case SLIDER:
                            setting.setCurrent(readObject.getFloat("val"));
                            break;
                        default:
                            break;
                    }
                });
            }

        } catch (final IOException e) {
            e.printStackTrace();
        }
        super.read();
    }

    @Override
    public void write() {
        final JSONArray settingsJSONArray = new JSONArray();
        GameMain.INSTANCE.getSettingsHandler().getSettings().forEach(setting -> {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("tag", setting.getTag());
            switch (setting.getSettingsType()) {
                case SWITCHER:
                case LIST:
                    jsonObject.put("val", setting.getSelected());
                    break;
                case SLIDER:
                    jsonObject.put("val", setting.getCurrent());
                    break;
                case BOOL:
                    jsonObject.put("val", setting.isState());
                    break;
                default:
                    break;
            }
            settingsJSONArray.put(jsonObject);
        });
        try {
            FileUtil.writeStringToFile(settingsJSONArray.toString(), thisFile);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        super.write();
    }
}
