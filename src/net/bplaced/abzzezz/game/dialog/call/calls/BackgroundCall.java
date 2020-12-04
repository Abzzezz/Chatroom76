package net.bplaced.abzzezz.game.dialog.call.calls;

import net.bplaced.abzzezz.core.util.render.TextureLoader;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.dialog.call.BasicCall;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static net.bplaced.abzzezz.game.util.dialog.DialogUtil.*;

public class BackgroundCall implements BasicCall {

    @Override
    public String[] formatted(String in, String color, Map<String, String> args) {
        final float opacity = Float.parseFloat(args.getOrDefault(BACKGROUND_OPACITY_ARGUMENT, "100")) / 100;
        final File file = new File(args.get(PATH_ARGUMENT));
        try {
            GameMain.INSTANCE.getShaderHandler().getTextureShader().setSampler(TextureLoader.loadPNGTexture(file.toURI().toURL()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameMain.INSTANCE.getShaderHandler().getTextureShader().setOpacity(opacity);

        return new String[] {args.getOrDefault(TEXT_ARGUMENT, ""), color};
    }

    @Override
    public String tag() {
        return BACKGROUND_CALL;
    }
}
