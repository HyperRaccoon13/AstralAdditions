package com.github.ethanicuss.astraladditions.entities.cometball;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class CometballEntity extends ThrownItemEntity {

    /*public CometballEntity(EntityType<? extends CometballEntity> entityType, World world) {
        super((EntityType<? extends ThrownItemEntity>)entityType, world);
    }*/

    /*public CometballEntity(World world, LivingEntity owner) {
        super((EntityType<? extends ThrownItemEntity>)ModEntities.COMETBALL, owner, world);
    }*/

    public CometballEntity(EntityType<? extends Entity> entityType, World world) {
        super((EntityType<? extends ThrownItemEntity>)entityType, world);
        this.setNoGravity(true);
    }


    @Override
    protected Item getDefaultItem() {
        return Items.IRON_HOE;
    }

    private ParticleEffect getParticleParameters() {
        return ParticleTypes.GLOW_SQUID_INK;
    }
    @Override
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();
            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 8);
        entity.setVelocity(0.0f, 1.0f, 0.0f);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }
}