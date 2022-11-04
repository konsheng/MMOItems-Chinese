package net.Indyuce.mmoitems.stat;

import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.NBTItem;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.stat.data.StringData;
import net.Indyuce.mmoitems.stat.type.ChooseStat;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.ItemRestriction;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author Gunging
 */
public class Amphibian extends ChooseStat implements ItemRestriction, GemStoneStat {
    public static final String
            NORMAL = "UNRESTRICTED",
            DRY = "DRY",
            WET = "WET",
            DAMP = "DAMP",
            LAVA = "LAVA",
            MOLTEN = "MOLTEN",
            LIQUID = "LIQUID",
            SUBMERGED = "SUBMERGED";

    public Amphibian() {
        super("AMPHIBIAN", Material.WATER_BUCKET, "Amphibian", new String[]{"该物品是否只能在某些流体", "环境中使用?"}, new String[]{"!block", "all"});

        addChoices(NORMAL, DRY, WET, DAMP, LAVA, MOLTEN, LIQUID, SUBMERGED);

        // Put definitions
        setHint(NORMAL, "不需要任何流体");
        setHint(DRY, "该物品在玩家触碰任何流体都不会生效.");
        setHint(WET, "该物品在玩家完全呆在水中才会生效(雨不算).");
        setHint(DAMP, "该物品在玩家触碰水都才会生效.");
        setHint(LAVA, "该物品在玩家触碰岩浆才会生效.");
        setHint(MOLTEN, "该物品在玩家完全呆在岩浆中才会生效.");
        setHint(LIQUID, "该物品在玩家触碰任意流体才会生效.");
        setHint(SUBMERGED, "该物品在玩家完全呆在任意流体中才会生效.");
    }

    @NotNull
    @Override public StringData getClearStatData() { return new StringData(NORMAL); }

    @Override
    public boolean canUse(RPGPlayer player, NBTItem item, boolean message) {

        // bruh
        if (!item.hasTag(getNBTPath())) { return true; }

        // Find the relevant tags
        ArrayList<ItemTag> relevantTags = new ArrayList<>();
        if (item.hasTag(getNBTPath())) { relevantTags.add(ItemTag.getTagAtPath(getNBTPath(), item, SupportedNBTTagValues.STRING)); }

        // Generate data
        StringData data = (StringData) getLoadedNBT(relevantTags);

        // Well...
        if (data != null) {
            //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a77 Checking Amphibian: \u00a7f" + item.getType() + " \u00a7b" + data.toString());

            // Switch
            switch (data.toString()) {
                case NORMAL:
                default:
                    //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a>\u00a77 Normal \u00a7aSucceed");
                    return true;
                case DRY:
                    for (Block b : blocksTouchedByPlayer(player.getPlayer())) {
                        //BKK//MMOItems. Log(" \u00a77>\u00a77>\u00a73>\u00a77 Examining \u00a7f" + b.getType().toString());
                        if (b.isLiquid()) {
                            //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7cFail");
                        return false; } }
                    //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7aSucceed");
                    return true;
                case WET:
                    for (Block b : blocksTouchedByPlayer(player.getPlayer())) {
                        //BKK//MMOItems. Log(" \u00a77>\u00a77>\u00a73>\u00a77 Examining \u00a7f" + b.getType().toString());
                        if (b.getType().equals(Material.WATER)) {
                            //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7aSucceed");
                            return true; } }
                    //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7cFail");
                    return false;
                case DAMP:
                    for (Block b : blocksTouchedByPlayer(player.getPlayer())) {
                        //BKK//MMOItems. Log(" \u00a77>\u00a77>\u00a73>\u00a77 Examining \u00a7f" + b.getType().toString());
                        if (!b.getType().equals(Material.WATER)) {
                            //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7cFail");
                            return false; } }
                    //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7aSucceed");
                    return true;
                case LAVA:
                    for (Block b : blocksTouchedByPlayer(player.getPlayer())) {
                        //BKK//MMOItems. Log(" \u00a77>\u00a77>\u00a73>\u00a77 Examining \u00a7f" + b.getType().toString());
                        if (b.getType().equals(Material.LAVA)) {
                            //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7aSucceed");
                            return true; } }
                    //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7cFail");
                    return false;
                case MOLTEN:
                    for (Block b : blocksTouchedByPlayer(player.getPlayer())) {
                        //BKK//MMOItems. Log(" \u00a77>\u00a77>\u00a73>\u00a77 Examining \u00a7f" + b.getType().toString());
                        if (!b.getType().equals(Material.LAVA)) {
                            //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7cFail");
                            return false; } }
                    //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7aSucceed");
                    return true;
                case LIQUID:
                    for (Block b : blocksTouchedByPlayer(player.getPlayer())) {
                        //BKK//MMOItems. Log(" \u00a77>\u00a77>\u00a73>\u00a77 Examining \u00a7f" + b.getType().toString());
                        if (b.isLiquid()) {
                            //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7aSucceed");
                        return true; } }
                    //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7cFail");
                    return false;
                case SUBMERGED:
                    for (Block b : blocksTouchedByPlayer(player.getPlayer())) {
                        //BKK//MMOItems. Log(" \u00a77>\u00a77>\u00a73>\u00a77 Examining \u00a7f" + b.getType().toString());
                        if (!b.isLiquid()) {
                            //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7cFail");
                        return false; } }
                    //BKK//MMOItems. Log(" \u00a7a>\u00a73>\u00a7a> \u00a7aSucceed");
                    return true;
            }
        }
        return true;
    }

    @Override
    public boolean isDynamic() { return true; }

    // Yes
    ArrayList<Block> blocksTouchedByPlayer(@NotNull Player p) {

        // Get Bounding Box
        BoundingBox box = p.getBoundingBox();

        // Ret
        ArrayList<Block> ret = new ArrayList<>();

        // Perform Summation
        for (double dx = box.getMinX(); dx <= box.getMaxX(); dx += Math.min(1, Math.max(box.getWidthX() - (dx - box.getMinX()), 0.001))) {
            for (double dy = box.getMinY(); dy <= box.getMaxY(); dy += Math.min(1, Math.max(box.getHeight() - (dy - box.getMinY()), 0.001))) {
                for (double dz = box.getMinZ(); dz <= box.getMaxZ(); dz += Math.min(1, Math.max(box.getWidthZ() - (dz - box.getMinZ()), 0.001))) {

                    // Exclusion
                    int dxI =SilentNumbers.floor(dx);
                    int dyI =SilentNumbers.floor(dy);
                    int dzI =SilentNumbers.floor(dz);

                    //BKK//MMOItems. Log("  \u00a77at \u00a76" + dxI + " " + dyI + " " + dzI + "\u00a78 (" + dx + " " + dy + " " + dz + ")");

                    // Get block
                    Block bkk = p.getLocation().getWorld().getBlockAt(dxI, dyI, dzI);

                    // Contained already?
                    if (!ret.contains(bkk)) {
                        //BKK//MMOItems. Log("  \u00a7a  > \u00a77Added");

                        // Get block at
                        ret.add(bkk);
                    }
                }
            }
        }

        //BKK//MMOItems. Log("Total: \u00a7e" + ret.size() + "\u00a77 blocks.");
        return ret;
    }
}
