package com.github.ethanicuss.astraladditions.entities.blackhole;

import com.github.ethanicuss.astraladditions.util.ModUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;



public class BlackholeEntity extends HostileEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public BlackholeEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 20;
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 12.0f));
        this.goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.75, 1f));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 0.8D, false));
        this.goalSelector.add(5, new FlyGoal(this, 0.8D));

        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createBlackholeAttributes(){
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20f)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.55)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0);
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ILLUSIONER_PREPARE_MIRROR;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR;
    }


    @Override
    public void tickMovement() {
        if (this.world.isClient) {
            this.world.addParticle(ParticleTypes.GLOW, this.getParticleX(2), this.getRandomBodyY(), this.getParticleZ(2), 0.0, 0.0, 0.0);
        }
        super.tickMovement();
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("regularLoop.blackhole.new", true));
        return PlayState.CONTINUE;
    }


    int pullCooldown = 30;

    @Override
    public void tick() {
        pullCooldown--;
        PlayerEntity p = this.world.getClosestPlayer(this, 32);
        if (p != null) {
            //this.getMoveControl().moveTo(p.getX(), p.getY(), p.getZ(), 1.0);
            if (pullCooldown == 0){
            pullCooldown=30;
            if (!p.isCreative()) {
                ModUtils.pullPlayer(this, this.world, true, 0.35, 0.02, this.getX(), this.getZ(), this.getX() - 12, this.getY() - 3, this.getZ() - 12, this.getX() + 12, this.getY() + 3, this.getZ() + 12);
            }}
        }
        super.tick();
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

}
