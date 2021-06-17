package pers.cocoadel.java.learning.local.retire.resources;

import java.awt.*;
import java.util.ListResourceBundle;

public class RetireResources_en extends ListResourceBundle {

    public static final Object[][] contents = new Object[][]{
            {"colorPre", Color.blue},{"colorGain",Color.white},{"colorLoss",Color.red}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
