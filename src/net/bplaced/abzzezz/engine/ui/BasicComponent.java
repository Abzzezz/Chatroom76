package net.bplaced.abzzezz.engine.ui;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.utils.ColorUtil;
import net.bplaced.abzzezz.engine.utils.FontUtil;

import java.awt.*;

public interface BasicComponent {

    EngineCore engineCoreInstance = EngineCore.getInstance();
    FontUtil textFont = new FontUtil(ColorUtil.TEXT_FONT, 20);
    FontUtil bigFont = new FontUtil(ColorUtil.TEXT_FONT, 60);

    Color textColor = ColorUtil.TEXT_COLOR;


}
