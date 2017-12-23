package de.linzn.xeonInventory.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Pattern;

public class I18n {

    private static final Pattern NODOUBLEMARK = Pattern.compile("''");
    private static final String INDEPENDENT_NEW_LINE = System.getProperty("line.separator");
    private static I18n INSTANCE;
    private File defaultLocaleFile;
    private File customLocaleFile;
    private YamlConfiguration defaultLocaleConfig;
    private YamlConfiguration customLocaleConfig;
    private HashMap<String, String> translationsDefault = new HashMap<>();
    private HashMap<String, String> translationsCustom = new HashMap<>();

    public I18n(Plugin plugin, String locale) {
        INSTANCE = this;

        File localeFolder = new File(plugin.getDataFolder(), "locale");

        if (!localeFolder.exists()) {
            localeFolder.mkdir();
        }

        defaultLocaleFile = new File(localeFolder, "locale_enEN.yml");
        customLocaleFile = new File(localeFolder, "locale_" + locale + ".yml");

        if (!defaultLocaleFile.exists()) {
            loadLocaleFile(plugin, "locale_enEN", defaultLocaleFile);
        }

        defaultLocaleConfig = YamlConfiguration.loadConfiguration(defaultLocaleFile);

        if (!customLocaleFile.exists()) {
            if (!loadLocaleFile(plugin, "locale_" + locale, customLocaleFile)) {
                customLocaleConfig = defaultLocaleConfig;
                plugin.getLogger().info("Custom locale not found (or not different to default) - using default ...");
            } else {
                customLocaleConfig = YamlConfiguration.loadConfiguration(customLocaleFile);
            }
        } else {
            customLocaleConfig = YamlConfiguration.loadConfiguration(customLocaleFile);
        }

        translationsDefault = loadTranslations(defaultLocaleConfig);
        translationsCustom = loadTranslations(customLocaleConfig);
    }

    public static String translate(final String key, final Object... replace) {
        if (INSTANCE == null) {
            return "TranslationsNotLoaded";
        }

        String translation = INSTANCE.translationsCustom.get(key);
        if (translation == null) {
            translation = INSTANCE.translationsDefault.get(key);
        }

        if (translation == null) {
            return "TranslationsNotLoaded";
        }

        translation = NODOUBLEMARK.matcher(translation).replaceAll("'");
        translation = translation.replace("\\n", INDEPENDENT_NEW_LINE);
        translation = ChatColor.translateAlternateColorCodes('§', translation);

        return String.format(translation, replace);
    }

    private static void copy(InputStream in, File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean loadLocaleFile(Plugin plugin, String locale, final File to) {
        String resource = locale.endsWith(".yml") ? locale : locale + ".yml";

        final InputStream resourceStream;
        try {
            resourceStream = plugin.getResource(resource);
        } catch (Exception e) {
            return false;
        }

        if (!to.exists()) {
            try {
                to.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        if (resourceStream != null) {
            copy(resourceStream, to);
        } else {
            return false;
        }
        return true;
    }

    private HashMap<String, String> loadTranslations(YamlConfiguration localeConfig) {
        HashMap<String, String> tmp = new HashMap<>();

        if (localeConfig == null) {
            return tmp;
        }

        for (String key : localeConfig.getKeys(true)) {
            tmp.put(key, localeConfig.getString(key));
        }

        return tmp;
    }
}
