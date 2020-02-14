package net.Indyuce.mmoitems.gui.edition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.ConfigFile;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.api.recipe.CraftingType;
import net.Indyuce.mmoitems.api.util.AltChar;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.mmogroup.mmolib.MMOLib;

public class CraftingEdition extends EditionInventory {
	public static Map<Integer, String> correspondingSlot = new HashMap<>();

	private static final int[] slots = { 21, 22, 23, 30, 31, 32 };

	public CraftingEdition(Player player, Type type, String id) {
		super(player, type, id);

		if (correspondingSlot.isEmpty())
			for (CraftingType ctype : CraftingType.values())
				correspondingSlot.put(ctype.getSlot(), ctype.name().toLowerCase());
	}

	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(this, MMOLib.plugin.getVersion().isStrictlyHigher(1, 14) ? 45 : 36, ChatColor.UNDERLINE + "Crafting Recipes: " + id);

		int n = 0;

		for (CraftingType ctype : CraftingType.values()) {
			if (!ctype.shouldAdd())
				continue;
			ItemStack craftingEvent = ctype.getItem();
			ItemMeta craftingEventItem = craftingEvent.getItemMeta();
			craftingEventItem.addItemFlags(ItemFlag.values());
			craftingEventItem.setDisplayName(ChatColor.GREEN + ctype.getName());
			List<String> eventLore = new ArrayList<String>();
			eventLore.add(ChatColor.GRAY + ctype.getLore());
			if (!type.getConfigFile().getConfig().contains(id + ".crafting." + ctype.name().toLowerCase())) {
				eventLore.add("");
				eventLore.add(ChatColor.RED + "No recipes found.");
			}
			eventLore.add("");
			eventLore.add(ChatColor.YELLOW + AltChar.listDash + " Click to change this recipe.");
			eventLore.add(ChatColor.YELLOW + AltChar.listDash + " Right click to remove recipe.");
			craftingEventItem.setLore(eventLore);
			craftingEvent.setItemMeta(craftingEventItem);

			inv.setItem(slots[n], craftingEvent);
			n += 1;
		}

		addEditionInventoryItems(inv, true);

		return inv;
	}

	@Override
	public void whenClicked(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();

		event.setCancelled(true);
		if (event.getInventory() != event.getClickedInventory() || !MMOUtils.isPluginItem(item, false))
			return;

		if (correspondingSlot.containsKey(event.getSlot())) {
			if (event.getAction() == InventoryAction.PICKUP_ALL) {
				if (event.getSlot() == 21 || event.getSlot() == 22)
					new RecipeEdition(player, type, id, event.getSlot() == 22).open(getPreviousPage());
				else
					new StatEdition(this, false, ItemStat.CRAFTING, "item", getTypeFromSlot(event.getSlot()).name().toLowerCase()).enable("Write in the chat the item you want.", "Format: '[MATERIAL]' or '[MATERIAL]:[DURABILITY]' or '[TYPE].[ID]'");
			}

			if (event.getAction() == InventoryAction.PICKUP_HALF) {
				ConfigFile config = type.getConfigFile();
				String ctype = correspondingSlot.get(event.getSlot());
				if (!config.getConfig().contains(id + ".crafting." + ctype))
					return;

				config.getConfig().set(id + ".crafting." + ctype, null);

				if (config.getConfig().getConfigurationSection(id + ".crafting") == null)
					config.getConfig().set(id + ".crafting", null);

				registerItemEdition(config);
			}
		}
	}

	private CraftingType getTypeFromSlot(int slot) {
		if (slot == 23)
			return CraftingType.FURNACE;
		if (slot == 30)
			return CraftingType.BLAST;
		if (slot == 31)
			return CraftingType.SMOKER;
		return CraftingType.CAMPFIRE;
	}
}