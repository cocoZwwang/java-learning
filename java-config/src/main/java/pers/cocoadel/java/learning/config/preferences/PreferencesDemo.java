package pers.cocoadel.java.learning.config.preferences;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PreferencesDemo {

    public static void main(String[] args) throws BackingStoreException {
        Preferences preferences = Preferences.userRoot();
        //windows 会存储到注册表 路径： Software\JavaSoft\Prefs 下
        preferences.put("u_name","cocoAdel");
        preferences.flush();

        String msg = preferences.get("u_name",null);
        System.out.println(msg);
    }
}
