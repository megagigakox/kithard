package pl.kithard.proxy.motd;

import com.google.common.base.Strings;
import eu.okaeri.configs.OkaeriConfig;

import java.util.Arrays;
import java.util.List;

public class MotdConfig extends OkaeriConfig {

    public String first = "&3&lKit&b&lHard.pl &8» &7Start serwera&8: &f&l09.09.2022";
    public String second = "&bSzykujcie ekipe, dodalismy dla was mnostwo nowosci!";

    public List<String> sample = Arrays.asList(
            "  &r&8&l&m--[&r&b&l&m---&r&3&l Kit&r&b&lHard.pl &r&b&l&m---&r&8&l&m]--&r ",
            "&r",
            " &8» &7Wersja&8: &f1.8.8&r",
            " &8» &7Strona&8: &fwww.kithard.pl&r",
            " &8» &7Facebook&8: &ffb.kithard.pl&r",
            " &8» &7Discord&8: &fdc.kithard.pl&r",
            "&r",
            "  &r&8&l&m--[&r&b&l&m---&r&3&l Kit&r&b&lHard.pl &r&b&l&m---&r&8&l&m]--&r "
    );

    public String getFormattedMotd() {
        StringBuilder formattedMotd = new StringBuilder();

        if (!Strings.isNullOrEmpty(this.first)) {
            formattedMotd.append(this.first).append('\n');
        }

        if (!Strings.isNullOrEmpty(this.second)) {
            formattedMotd.append(this.second);
        }

        return formattedMotd.toString();
    }
}

