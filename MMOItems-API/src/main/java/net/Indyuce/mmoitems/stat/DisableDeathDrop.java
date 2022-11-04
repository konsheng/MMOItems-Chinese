package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.stat.type.BooleanStat;
import org.bukkit.Material;

public class DisableDeathDrop extends BooleanStat {
    public DisableDeathDrop() {
        super("DISABLE_DEATH_DROP", Material.BONE, "关闭死亡掉落",
                new String[] { "启用后物品持有者死亡后该物品将不会", "掉落." }, new String[] { "all" });
    }
}
