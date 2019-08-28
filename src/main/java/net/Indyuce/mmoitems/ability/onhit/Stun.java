package net.Indyuce.mmoitems.ability.onhit;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Ability;
import net.Indyuce.mmoitems.api.AttackResult;
import net.Indyuce.mmoitems.api.player.PlayerStats.TemporaryStats;
import net.Indyuce.mmoitems.stat.data.AbilityData;

public class Stun extends Ability {
	public Stun() {
		super(CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK);

		addModifier("cooldown", 10);
		addModifier("duration", 2);
		addModifier("mana", 0);
		addModifier("stamina", 0);
	}

	@Override
	public void whenCast(TemporaryStats stats, LivingEntity target, AbilityData data, AttackResult result) {
		target = target == null ? MMOItems.plugin.getVersion().getVersionWrapper().rayTrace(stats.getPlayer(), 50).getHit() : target;
		if (target == null) {
			result.setSuccessful(false);
			return;
		}
		
		target.getWorld().playSound(target.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 2);
		target.getWorld().playEffect(target.getLocation(), Effect.STEP_SOUND, 42);
		target.getWorld().playEffect(target.getLocation().add(0, 1, 0), Effect.STEP_SOUND, 42);
		target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) (data.getModifier("duration") * 20), 254));
	}
}