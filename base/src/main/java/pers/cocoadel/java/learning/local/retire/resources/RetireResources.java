package pers.cocoadel.java.learning.local.retire.resources;

import java.awt.*;
import java.util.ListResourceBundle;

public class RetireResources extends ListResourceBundle {

    public static final Object[][] contents = new Object[][]{
            {"colorPre", Color.red},{"colorGain",Color.blue},{"colorLoss",Color.yellow}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
