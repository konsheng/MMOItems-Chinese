package net.Indyuce.mmoitems.stat;

import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class DisplayedType extends StringStat implements GemStoneStat {
    public DisplayedType() {
        super("DISPLAYED_TYPE", VersionMaterial.OAK_SIGN.toMaterial(), "物品类型", new String[]{"物品的显示类型."}, new String[]{"all"});
    }
}
