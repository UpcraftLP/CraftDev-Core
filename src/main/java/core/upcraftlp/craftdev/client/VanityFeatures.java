package core.upcraftlp.craftdev.client;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import core.upcraftlp.craftdev.common.CraftDevCore;
import core.upcraftlp.craftdev.common.CraftDevReference;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.compress.utils.IOUtils;

import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author UpcraftLP
 */
public class VanityFeatures {

    private static final Map<UUID, NBTTagCompound> playerData = Maps.newConcurrentMap();

    public static synchronized void update() {
        new Thread(() -> {
            Scanner sc = null;
            try {
                URL url = new URL(CraftDevReference.VANITY_FEATURES_URL);
                sc = new Scanner(url.openStream());
                StringBuilder json = new StringBuilder();
                while (sc.hasNextLine()) {
                    json.append(sc.nextLine());
                }
                NBTTagCompound nbt = JsonToNBT.getTagFromJson(json.toString());
                NBTTagList playerList = nbt.getTagList("players", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < playerList.tagCount(); i++) {
                    NBTTagCompound tag = playerList.getCompoundTagAt(i);
                    UUID uuid = UUID.fromString("uuid");
                    playerData.put(uuid, tag);
                }
                //TODO read and write save file
            }
            catch (Exception e) {
                CraftDevCore.log.error("Exception reading vanity data, discarding data!");
                playerData.clear();
            }
            finally {
                IOUtils.closeQuietly(sc);
            }
        }).run();
    }

    public static boolean hasVanityFeatures(String username) {
        GameProfile gp = new GameProfile(null, username);
        gp = TileEntitySkull.updateGameprofile(gp);
        return hasVanityFeatures(gp.getId());
    }

    public static NBTTagCompound getPlayerData(String username) {
        GameProfile gp = new GameProfile(null, username);
        gp = TileEntitySkull.updateGameprofile(gp);
        return getPlayerData(gp.getId());
    }

    public static boolean hasVanityFeatures(UUID uuid) {
        return playerData.containsKey(uuid);
    }

    public static NBTTagCompound getPlayerData(UUID uuid) {
        return hasVanityFeatures(uuid) ? playerData.get(uuid) : new NBTTagCompound();
    }

}
