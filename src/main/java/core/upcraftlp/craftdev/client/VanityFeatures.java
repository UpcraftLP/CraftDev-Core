package core.upcraftlp.craftdev.client;

import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.compress.utils.IOUtils;

import java.net.URL;
import java.util.Scanner;

/**
 * @author UpcraftLP
 */
public class VanityFeatures {

    private static NBTTagCompound nbt;

    public static void update() {
        new Thread(() -> {
            Scanner sc = null;
            try {
                URL url = new URL(CraftDevReference.VANITY_FEATURES_URL);
                sc = new Scanner(url.openStream());
                StringBuilder json = new StringBuilder();
                while (sc.hasNextLine()) {
                    json.append(sc.nextLine());
                }
                nbt = JsonToNBT.getTagFromJson(json.toString());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                IOUtils.closeQuietly(sc);
            }
        }).run();
    }

    public static NBTTagCompound getRootTag() { //TODO implement this properly and sort tag features
        return nbt != null ? nbt : new NBTTagCompound();
    }
}
