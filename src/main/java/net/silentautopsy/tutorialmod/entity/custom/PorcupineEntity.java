package net.silentautopsy.tutorialmod.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.silentautopsy.tutorialmod.entity.ModEntities;
import net.silentautopsy.tutorialmod.entity.ai.PorcupineAttackGoal;
import org.jetbrains.annotations.Nullable;

public class PorcupineEntity extends AnimalEntity
{
    public PorcupineEntity(EntityType<? extends AnimalEntity> entityType, World world)
    {
        super(entityType, world);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity)
    {
        return ModEntities.PORCUPINE.create(world);
    }

    public static DefaultAttributeContainer.Builder createPorcupineAttributes()
    {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2);
    }

    @Override
    protected void initGoals()
    {
        // Add the swim goal for not drowning
        this.goalSelector.add(0, new SwimGoal(this));
        // Attack
        this.goalSelector.add(1, new PorcupineAttackGoal(this, 1D, true));
        // Find a mate when breeding
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.15D));
        // Follow the player when holding beetroot
        this.goalSelector.add(2, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.BEETROOT), false));
        // Follow the parent
        this.goalSelector.add(3, new FollowParentGoal(this, 1.15D));
        // Wander around
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0D));
        // Look at the player
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        // Look around
        this.goalSelector.add(6, new LookAroundGoal(this));

        // Attack an entity when attacked by it
        this.targetSelector.add(1, new RevengeGoal(this));
    }

    @Override
    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.isOf(Items.BEETROOT);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_FOX_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CAT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_DOLPHIN_DEATH;
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    @Override
    protected void updateLimbs(float posDelta)
    {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    public void tick()
    {
        super.tick();
        if(this.getWorld().isClient())
        {
            setupAnimationsStates();
        }
    }

    private void setupAnimationsStates()
    {
        if(this.idleAnimationTimeout <= 0)
        {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        }
        else
        {
            --this.idleAnimationTimeout;
        }

        if(this.isAttacking() && attackAnimationTimeout <= 0)
        {
            this.attackAnimationTimeout = 40;
            this.attackAnimationState.start(this.age);
        }
        else
        {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking())
        {
            this.attackAnimationState.stop();
        }


    }

    @Override
    protected void initDataTracker()
    {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
    }

    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(PorcupineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Override
    public void setAttacking(boolean attacking)
    {
        this.dataTracker.set(ATTACKING, attacking);
    }

    @Override
    public boolean isAttacking()
    {
        return this.dataTracker.get(ATTACKING);
    }
}
