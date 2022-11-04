package net.Indyuce.mmoitems.stat;

import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.StringStat;
import net.Indyuce.mmoitems.stat.type.TemplateOption;

public class CraftingPermission extends StringStat implements TemplateOption, GemStoneStat {
    public CraftingPermission() {
        super("CRAFT_PERMISSION", VersionMaterial.OAK_SIGN.toMaterial(), "合成配方权限",
                new String[]{"合成该物品所需要的权限.", "更改后需要该指令才能生效: &o/mi reload recipes&7."},
                new String[]{"all"});
    }
}