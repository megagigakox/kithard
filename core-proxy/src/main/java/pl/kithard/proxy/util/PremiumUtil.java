package pl.kithard.proxy.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public final class PremiumUtil {

    private PremiumUtil() {}

    public static boolean isPremium(String name) {
        try {
            return ((HttpURLConnection) new URL("https://api.ashcon.app/mojang/v2/user/" + name)
                    .openConnection())
                    .getResponseCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
