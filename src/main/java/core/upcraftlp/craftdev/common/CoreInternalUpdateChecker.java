package core.upcraftlp.craftdev.common;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import core.upcraftlp.craftdev.common.main.CraftDevCore;

import java.util.Scanner;

import net.minecraftforge.common.MinecraftForge;

public class CoreInternalUpdateChecker {

	private static List<String> newVersionsAvailable = new ArrayList<String>();
	public static List<String> mods = new ArrayList<String>();
	public static Map<String, String> publicUrls = new HashMap<String, String>();
	private static Map<String, String> internalUrls = new HashMap<String, String>();
	private static Map<String, String> versions = new HashMap<String, String>();
	private static Map<String, String> latest =  new HashMap<String, String>();
	
	public static void add(String modid, String currentVersion, String internalUpdateUrl, String publicUpdateUrl) {
		versions.put(modid, currentVersion.split("-")[0]);
		mods.add(modid);
		internalUrls.put(modid, internalUpdateUrl);
		publicUrls.put(modid, publicUpdateUrl);
	}
	
	public static void init()
	{
		if(internalUrls.isEmpty() || !CoreInternalConfig.enableUpdateChecker) return;
		CraftDevCore.getLogger().println("Initializing Update-Checker...");
		Iterator<Entry<String, String>> i = internalUrls.entrySet().iterator();
		while(i.hasNext()) {
			Entry<String, String> e = i.next();
			String modid = e.getKey();
			String updateUrl = e.getValue();
			
			new Thread("Update-Checker: " + modid)
			{
				@Override
				public void run()
				{
					try {
						URL url = new URL(updateUrl);
						Scanner scanner = new Scanner(url.openStream());
						while(scanner.hasNextLine())
						{
							String contentString = scanner.nextLine();
									String[] content = contentString.split("-");
							if(content[0].equals(MinecraftForge.MC_VERSION))
							{
								latest.put(modid, content[1]);
								break;
							}
						}
						scanner.close();
					} catch (MalformedURLException e)
				{
					CraftDevCore.getLogger().println("URL ERROR: MALFORMED URL");
				} catch (Exception e) {
						e.printStackTrace();
					}
					if(latest.isEmpty() || !latest.containsKey(modid)) return;
					String latestVersion = latest.get(modid);
					String currentVersion = versions.get(modid);
					if(!currentVersion.equals(latestVersion)) setNewVersionAvailable(modid);
				};
			}.start();
			
		}
		
		
	}
	
	private static synchronized void setNewVersionAvailable(String id)
	{
		newVersionsAvailable.add(id);
	}
	
	public static synchronized boolean isNewVersionAvailable(String modid)
	{
		return newVersionsAvailable.contains(modid);
	}
	
	 public static synchronized String getLatest(String modid) {
		return latest.get(modid);
	}
	 
	public static void checkVersion(String modid) {
		if(CoreInternalConfig.enableUpdateChecker && CoreInternalUpdateChecker.isNewVersionAvailable(modid)) {
			CraftDevCore.getLogger().println("New Version available for " + modid + ": " + CoreInternalUpdateChecker.getLatest(modid));
			CraftDevCore.getLogger().println("download it here: " + publicUrls.get(modid));
		}
	}
}
