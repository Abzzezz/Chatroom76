package net.bplaced.abzzezz.core.ui;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import net.bplaced.abzzezz.core.util.render.FontUtil;

import java.awt.*;

public interface BasicComponent {

    Core OPEN_GL_CORE_INSTANCE = Core.getInstance();
    FontUtil textFont = new FontUtil(ColorUtil.TEXT_FONT, 20);
    FontUtil bigFont = new FontUtil(ColorUtil.TEXT_FONT, 60);

    Color textColor = ColorUtil.TEXT_COLOR;


}
