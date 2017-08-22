package core.upcraftlp.craftdev.api.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigHelper {

    public static class Categories {
        public static final String GENERAL = Configuration.CATEGORY_GENERAL;
        public static final String CLIENT = Configuration.CATEGORY_CLIENT;
        public static final String TWEAKS = "tweaks";
    }
    
    @Nonnull
    public static List<IConfigElement> getEntries(Configuration config) {
        List<IConfigElement> entries = new ArrayList<IConfigElement>();
        for(String categoryName : config.getCategoryNames()) {
            ConfigCategory category = config.getCategory(categoryName);
            entries.addAll(new ConfigElement(category).getChildElements());
        }
        return entries;
    }
}
