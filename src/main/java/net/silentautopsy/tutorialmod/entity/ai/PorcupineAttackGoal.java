package net.silentautopsy.tutorialmod.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;
import net.silentautopsy.tutorialmod.entity.custom.PorcupineEntity;

public class PorcupineAttackGoal extends MeleeAttackGoal
{
    private final PorcupineEntity entity;
    private int attackDelay = 20;
    private int ticksUntilNextAttack = 20;
    private boolean shouldCountTillNextAttack = false;

    public PorcupineAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle)
    {
        super(mob, speed, pauseWhenMobIdle);
        entity = ((PorcupineEntity) mob);
    }

    @Override
    public void start()
    {
        super.start();
        this.attackDelay = 20;
        this.ticksUntilNextAttack = 20;
    }

    @Override
    public void stop()
    {
        entity.setAttacking(false);
        super.stop();
    }

    @Override
    public void tick()
    {
        super.tick();
        if(shouldCountTillNextAttack)
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
    }

    protected void performAttack(LivingEntity enemy)
    {
        resetAttackCooldown();
        this.mob.swingHand(Hand.MAIN_HAND);
        this.mob.tryAttack(enemy);
    }

    protected void resetAttackCooldown()
    {
        this.ticksUntilNextAttack = this.getTickCount(attackDelay * 2);
    }

    protected boolean isTimeToStartAttackAnimation()
    {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected boolean isTimeToAttack()
    {
        return this.ticksUntilNextAttack <= 0;
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity enemy, double pDistToEnemySqr)
    {
        return pDistToEnemySqr <= this.getSquaredMaxAttackDistance(enemy);
    }

    @Override
    protected void attack(LivingEntity enemy, double pDistToEnemySqr)
    {
        if (isEnemyWithinAttackDistance(enemy, pDistToEnemySqr))
        {
            this.shouldCountTillNextAttack = true;

            if(isTimeToStartAttackAnimation())
                entity.setAttacking(true);


            if(isTimeToAttack())
            {
                this.mob.getLookControl().lookAt(enemy.getX(), enemy.getEyeY(), enemy.getZ());
                performAttack(enemy);
            }
        }
        else
        {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }
}
