package zedly.zenchantments.enchantments;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import zedly.zenchantments.CustomEnchantment;
import zedly.zenchantments.EnchantArrow;
import zedly.zenchantments.Tool;
import zedly.zenchantments.Utilities;

import static zedly.zenchantments.Tool.BOW_;
import static zedly.zenchantments.Tool.SWORD;

public class Stationary extends CustomEnchantment {

    public Stationary() {
        maxLevel = 1;
        loreName = "Stationary";
        probability = 0;
        enchantable = new Tool[]{BOW_, SWORD};
        conflicting = new Class[]{};
        description = "Negates any knockback when attacking mobs, leaving them clueless as to who is attacking";
        cooldown = 0;
        power = -1.0;
        handUse = 3;
    }

    public int getEnchantmentId() {
        return 58;
    }

    @Override
    public boolean onEntityHit(EntityDamageByEntityEvent evt, int level, boolean usedHand) {
        if(!(evt.getEntity() instanceof LivingEntity) ||
           !ADAPTER.attackEntity((LivingEntity) evt.getEntity(), (Player) evt.getDamager(), 0)) {
            LivingEntity ent = (LivingEntity) evt.getEntity();
            if(evt.getDamage() < ent.getHealth()) {
                evt.setCancelled(true);
                Utilities.damageTool(((Player) evt.getDamager()), 1, usedHand);
                ent.damage(evt.getDamage());
            }
        }
        return true;
    }

    @Override
    public boolean onEntityShootBow(EntityShootBowEvent evt, int level, boolean usedHand) {
        EnchantArrow.ArrowEnchantStationary arrow =
                new EnchantArrow.ArrowEnchantStationary((Projectile) evt.getProjectile());
        Utilities.putArrow(evt.getProjectile(), arrow, (Player) evt.getEntity());
        return true;
    }

}
