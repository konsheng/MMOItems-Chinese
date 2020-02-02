package net.Indyuce.mmoitems.stat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ConfigFile;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.stat.data.StatData;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.stat.type.StringStat;
import net.mmogroup.mmolib.api.item.ItemTag;

public class Repair_Material extends StringStat {
	public Repair_Material() {
		super(new ItemStack(Material.ANVIL), "Repair Material", new String[] { "The material to be used when", "repairing this item in an anvil.", "", "Currently servers no purpose!" }, "repair-material", new String[] { "all" });
	}

	@Override
	public boolean whenClicked(EditionInventory inv, InventoryClickEvent event) {
		new StatEdition(inv, ItemStat.MATERIAL).enable("Write in the chat the material you want.");
		return true;
	}

	@Override
	public boolean whenInput(EditionInventory inv, ConfigFile config, String message, Object... info) {
		Material material = null;
		String format = message.toUpperCase().replace("-", "_").replace(" ", "_");
		try {
			material = Material.valueOf(format);
		} catch (Exception e1) {
			inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + format + " is not a valid material!");
			inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "All materials can be found here: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html");
			return false;
		}

		config.getConfig().set(inv.getItemId() + ".repair-material", material.name());
		inv.registerItemEdition(config);
		inv.open();
		inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Repair Material successfully changed to " + material.name() + ".");
		return true;
	}

	@Override
	public boolean whenApplied(MMOItemBuilder item, StatData data) {
		String path = data.toString().toUpperCase().replace("-", "_").replace(" ", "_");
		Material material = null;
		try {
			material = Material.valueOf(path);
		} catch (Exception e1) {
			MMOItems.plugin.getLogger().warning(MMOItems.plugin.getPrefix() + ChatColor.RED + path + " is not a valid material!");
			return false;
		}

		item.addItemTag(new ItemTag("MMOITEMS_REPAIR_MATERIAL", material.name()));
		return true;
	}
}
