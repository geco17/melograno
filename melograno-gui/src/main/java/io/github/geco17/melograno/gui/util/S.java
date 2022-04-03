package io.github.geco17.melograno.gui.util;

import java.util.ResourceBundle;

public class S {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("gui");

    private S() {}

    public static ResourceBundle bundle() {
        return BUNDLE;
    }

    public static String val(String key) {
        return BUNDLE.getString(key);
    }

}
