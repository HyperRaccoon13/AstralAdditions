package com.github.ethanicuss.astraladditions.entities.cogfly;

import com.github.ethanicuss.astraladditions.AstralAdditions;
import com.github.ethanicuss.astraladditions.entities.boomerang.BoomerangEntity;
import com.github.ethanicuss.astraladditions.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.UUID;

public class CogflyEntity extends ThrownItemEntity {

    private static final TrackedData<String> OWNER = DataTracker.registerData(CogflyEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> ITEM = DataTracker.registerData(CogflyEntity.class, TrackedDataHandlerRegistry.STRING);

    public int attackCount = 0;
    public int damage = 3;
    PlayerEntity owner = world.getPlayerByUuid(UUID.fromString(this.getDataTracker().get(OWNER)));

    public CogflyEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.COGFLY;
    }

    public void setOwner(String UUID){
        this.getDataTracker().set(OWNER, UUID);
    }

    public ItemStack getCogflyItem(){
        return Registry.ITEM.get(new Identifier(AstralAdditions.MOD_ID, this.dataTracker.get(ITEM))).getDefaultStack();
    }

    @Override
    public void tick() {
        if (attackCount > 3) {
            kill();
        }
        if (OWNER != null) {
            LivingEntity p = owner.getAttacking();
            if (p != null) {
                pullToEntity(p);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (!Objects.equals(entity.getUuidAsString(), this.getDataTracker().get(OWNER))) {
            entity.damage(DamageSource.player(owner), damage);
            attackCount++;
            pullToEntity(owner);
        }
    }

    //Thanks Ethan for the code!
    private void pullToEntity(LivingEntity p) {
        double strength = 0.15;
        double xdiff = this.getX() - p.getX();
        double zdiff = this.getZ() - p.getZ();
        double dist = Math.sqrt(Math.pow(xdiff, 2) + Math.pow(zdiff, 2));
        //if (dist < 20) {
        if (xdiff == 0) {
            xdiff = 0.01;
        }
        if (zdiff == 0) {
            zdiff = 0.01;
        }
        double angleX = Math.atan(Math.abs(zdiff) / xdiff);
        double angleZ = Math.atan(Math.abs(xdiff) / zdiff);
        double cosX = Math.cos(angleX);
        double cosZ = Math.cos(angleZ);
        if (cosX == 0) {
            cosX = 0.01;
        }
        if (cosZ == 0) {
            cosZ = 0.01;
        }
        //dist = -dist + 20;
        //dist = -dist;
        dist = -1;

        double yVel;
        yVel = dist * 0.5;
        if (p.getY() > this.getY()){
            yVel = -dist;
        }

        this.addVelocity(dist * cosX * strength * (Math.abs(angleX) / angleX), yVel * strength, dist * cosZ * strength * (Math.abs(angleZ) / angleZ));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(OWNER, "");
        this.dataTracker.startTracking(ITEM, "minecraft:dirt");
    }

}

