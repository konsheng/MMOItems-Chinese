package net.Indyuce.mmoitems.api.crafting.ingredient;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.crafting.ConditionalDisplay;
import net.Indyuce.mmoitems.api.crafting.ConfigMMOItem;
import net.Indyuce.mmoitems.api.item.MMOItem;
import net.Indyuce.mmoitems.api.util.AltChar;
import net.Indyuce.mmoitems.stat.Display_Name;
import net.Indyuce.mmoitems.stat.MaterialStat.MaterialData;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.mmogroup.mmolib.api.item.NBTItem;

public class MMOItemIngredient extends Ingredient {
	private Type type;
	private String id;

	public MMOItemIngredient() {
		super("mmoitem");
		setDisplay(new ConditionalDisplay("&8" + AltChar.check + " &7#amount# #level# #item#", "&c" + AltChar.cross + " &7#amount# #level# #item#"));
	}

	public MMOItemIngredient(ConfigMMOItem mmoitem) {
		super("mmoitem");

		type = mmoitem.getType();
		id = mmoitem.getId();

		setAmount(mmoitem.getAmount());
		setKey(type.getId().toLowerCase() + "_" + id.toLowerCase());
	}

	public Type getType() {
		return type;
	}

	@Override
	public Ingredient load(String[] args) {
		try {
			MMOItemIngredient ingredient = new MMOItemIngredient();
			ingredient.type = MMOItems.plugin.getTypes().get(args[0]);
			ingredient.id = args.length > 1 ? args[1].toUpperCase().replace("-", "_") : "";

			ingredient.setAmount(args.length > 2 ? Math.max(1, Integer.parseInt(args[2])) : 1);
			ingredient.setName(args.length > 3 ? args[3].replace("_", " ") : findName());
			ingredient.setLevel(args.length > 4 ? Math.max(0, Integer.parseInt(args[4])) : 0);

			String levelKey = ingredient.getLevel() != 0 ? "-" + ingredient.getLevel() : "";
			ingredient.setKey(ingredient.type.getId().toLowerCase() + levelKey + "_" + ingredient.id.toLowerCase());
			ingredient.setDisplay(getDisplay());

			return ingredient;
		} catch (IllegalArgumentException | IndexOutOfBoundsException | NullPointerException exception) {
			return null;
		}
	}

	@Override
	public String formatDisplay(String string) {
		return string.replace("#item#", getName()).replace("#level#", getLevel() != 0 ? "" + getLevel() : "").replace("#amount#", "" + getAmount());
	}

	@Override
	public boolean isValid(NBTItem item) {
		return item.hasType();
	}

	private String findName() {
		MMOItem mmoitem = MMOItems.plugin.getItems().getMMOItem(type, id);
		if (mmoitem.hasData(ItemStat.NAME))
			return ((Display_Name) ItemStat.NAME).getDisplayName(mmoitem.getData(ItemStat.NAME));
		if (mmoitem.hasData(ItemStat.MATERIAL))
			return MMOUtils.caseOnWords(((MaterialData) mmoitem.getData(ItemStat.MATERIAL)).getMaterial().name().toLowerCase().replace("_", " "));
		return "Unrecognized Item";
	}

	@Override
	public String readKey(NBTItem item) {
		final String upgradeString = item.getString("MMOITEMS_UPGRADE");
		int level = 0;
		if (upgradeString != "") {
			JsonObject upgradeStat = new JsonParser().parse(upgradeString).getAsJsonObject();
			level = upgradeStat.get("Level").getAsInt();
		}
		final String levelKey = level != 0 ? "-" + level : "";

		return item.getString("MMOITEMS_ITEM_TYPE").toLowerCase() + levelKey + "_" + item.getString("MMOITEMS_ITEM_ID").toLowerCase();
	}

	@Override
	public ItemStack generateItemStack() {
		ItemStack item = MMOItems.plugin.getItems().getItem(type, id);
		item.setAmount(getAmount());
		return item;
	}

	@Override
	public String toString() {
		return getKey();
	}
}
