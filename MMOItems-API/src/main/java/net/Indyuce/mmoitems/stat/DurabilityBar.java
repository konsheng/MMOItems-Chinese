package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.stat.type.BooleanStat;
import org.bukkit.Material;

public class DurabilityBar extends BooleanStat {
    public DurabilityBar() {
        super("DURABILITY_BAR", Material.DAMAGED_ANVIL, "隐藏耐久条",
                new String[] { "启用后当使用自定义耐久后", "隐藏原版耐久条." }, new String[] { "!block", "all"});
    }
}
