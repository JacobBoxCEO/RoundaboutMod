package net.hydra.jojomod.stand.powers;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.access.IGravityEntity;
import net.hydra.jojomod.block.FancyLighterBlock;
import net.hydra.jojomod.block.FogBlock;
import net.hydra.jojomod.client.ClientNetworking;
import net.hydra.jojomod.client.KeyInputRegistry;
import net.hydra.jojomod.client.StandIcons;
import net.hydra.jojomod.entity.ModEntities;
import net.hydra.jojomod.entity.stand.StandEntity;
import net.hydra.jojomod.event.ModGamerules;
import net.hydra.jojomod.event.index.OffsetIndex;
import net.hydra.jojomod.event.index.PacketDataIndex;
import net.hydra.jojomod.event.index.PowerIndex;
import net.hydra.jojomod.event.index.PowerTypes;
import net.hydra.jojomod.event.powers.*;
import net.hydra.jojomod.item.RoadRollerItem;
import net.hydra.jojomod.sound.ModSounds;
import net.hydra.jojomod.stand.powers.elements.PowerContext;
import net.hydra.jojomod.stand.powers.presets.NewDashPreset;
import net.hydra.jojomod.util.MainUtil;
import net.hydra.jojomod.util.S2CPacketUtil;
import net.hydra.jojomod.util.gravity.RotationUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;

import java.util.List;
import java.util.Objects;

public class PowersKingNothing extends NewDashPreset {

    public PowersKingNothing(LivingEntity self) {
        super(self);
    }

    @Override
    public StandPowers generateStandPowers(LivingEntity entity) {
        return new PowersKingNothing(entity);
    }

    @Override
    public StandEntity getNewStandEntity() {
        return ModEntities.KING_NOTHING.create(this.getSelf().level());
    }

    @Override
    public boolean isSecondaryStand() {
        return true;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag $$0) {
        super.addAdditionalSaveData($$0);
        $$0.putString("memorizedBlock", BuiltInRegistries.BLOCK.getKey(memorizedBlock).toString());
        if (!$$0.getString("memorizedBlock").equals(BuiltInRegistries.BLOCK.getKey(memorizedBlock).toString())) {
            Roundabout.LOGGER.debug("SOMETHING WENT WRONG SAVING DATA FUCK");
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag $$0) {
        super.readAdditionalSaveData($$0);
        if ($$0.contains("memorizedBlock")) {
            memorizedBlock = BuiltInRegistries.BLOCK.get(
                    new ResourceLocation($$0.getString("memorizedBlock")));
        }
        else resetMemorizedScentData();
    }

    private void resetMemorizedScentData() {
        this.memorizedBlock = Blocks.IRON_ORE;
    }

    @Override
    public List<Byte> getPosList() {
        List<Byte> $$1 = Lists.newArrayList();
        $$1.add((byte) 0);
        $$1.add((byte) 1);
        $$1.add((byte) 2);
        $$1.add((byte) 3);
        $$1.add((byte) 4);
        $$1.add((byte) 5);
        $$1.add((byte) 6);
        $$1.add((byte) 7);
        $$1.add((byte) 8);
        $$1.add((byte) 9);
        $$1.add((byte) 10);
        $$1.add((byte) 11);
        $$1.add((byte) 12);
        $$1.add((byte) 13);
        $$1.add((byte) 14);
        $$1.add((byte) 15);
        $$1.add((byte) 16);
        $$1.add((byte) 17);
        $$1.add((byte) 18);
        $$1.add((byte) 19);
        $$1.add((byte) 20);
        $$1.add((byte) 21);
        $$1.add((byte) 22);
        $$1.add((byte) 23);
        $$1.add((byte) 24);
        $$1.add((byte) 25);
        $$1.add((byte) 26);
        $$1.add((byte) 27);
        $$1.add((byte) 28);
        $$1.add((byte) 29);
        $$1.add((byte) 30);
        $$1.add((byte) 31);
        $$1.add((byte) 32);
        $$1.add((byte) 33);
        $$1.add((byte) 34);
        return $$1;
    }

    private Block memorizedBlock = null;

    public ItemStack getMemorizedScentIcon() {
        return MainUtil.KING_NOTHING_SCENT_ICON_OVERRIDES.get(memorizedBlock) == null ?
                new ItemStack(memorizedBlock.asItem()) :
                new ItemStack(MainUtil.KING_NOTHING_SCENT_ICON_OVERRIDES.get(memorizedBlock));
    }

    @Override
    public void renderIcons(GuiGraphics context, int x, int y) {
        if (isHoldingSneak()) {
            setSkillIcon(context, x, y,1, StandIcons.THE_WORLD_GRAB_ITEM, PowerIndex.SKILL_1_SNEAK);
            setSkillIcon(context, x, y,2, StandIcons.STAR_PLATINUM_PHASE_GRAB, PowerIndex.SKILL_2);
            setSkillIcon(context, x, y,3, StandIcons.THE_WORLD_GRAB_ITEM, PowerIndex.SKILL_3);
            renderScentIcon(context, x, y);
        }
        else {
            setSkillIcon(context, x, y,1, StandIcons.THE_WORLD_GRAB_BLOCK, PowerIndex.SKILL_1);
            setSkillIcon(context, x, y,2, StandIcons.DIVER_DOWN_PHASE_PUNCH, PowerIndex.SKILL_2);
            setSkillIcon(context, x, y,3, StandIcons.DODGE, PowerIndex.GLOBAL_DASH);
            setSkillIcon(context, x, y, 4, StandIcons.STEAL_MEMORIES, PowerIndex.SKILL_4);
        }
        super.renderIcons(context, x, y);
    }

    @Override
    public boolean isAttackIneptVisually(byte activeP, int slot) {
        if (slot != 4) {
            if (isHoldingSneak()) {
                if (slot == 2) return !isGrabbable(getSelf().getMainHandItem());
                if (slot == 3) return getFormPrediction(getSelf().getMainHandItem().getItem()) == null;
            }
        }
        return super.isAttackIneptVisually(activeP, slot);
    }

    public boolean powersAreUnusable() {
        return getActivePower() == PowerIndex.POWER_2;
    }

    public void renderScentIcon(GuiGraphics context, int x, int y){
        RenderSystem.enableBlend();
        context.setColor(1f, 1f, 1f, 1f);
        CooldownInstance cd = null;
        if (!getPowerCooldowns().isEmpty() && getPowerCooldowns().size() >= PowerIndex.SKILL_4){
            cd = getPowerCooldowns().get(PowerIndex.SKILL_4);
        }
        x += 100;
        y-=1;

        RenderSystem.enableBlend();
        context.blit(getIconYes(4),x-3,y-3,0, 0, squareWidth, squareHeight, squareWidth, squareHeight);

        ItemStack block = getMemorizedScentIcon();
        RenderSystem.enableBlend();
        Font renderer = Minecraft.getInstance().font;
        Component special4Key = KeyInputRegistry.abilityFourKey.getTranslatedKeyMessage();
        special4Key = fixKey(special4Key);
        //true? false? it doesn't matter, for this text will render below the item no matter what i try :(
        context.drawString(renderer, special4Key,x-1,y+11,0xffffff,false);
        RenderSystem.enableBlend();
        if ((cd != null && (cd.time >= 0)) || isAttackIneptVisually(PowerIndex.SKILL_4,4)){
            context.setColor(0.62f, 0.62f, 0.62f, 0.8f);
            context.renderFakeItem(block, x+1, y+1);
            if ((cd != null && (cd.time >= 0))) {
                float blit = (20*(1-((float) (1+cd.time) /(1+cd.maxTime))));
                int b = Math.round(blit);
                RenderSystem.enableBlend();
                context.setColor(1f, 1f, 1f, 1f);

                ResourceLocation COOLDOWN_TEX = StandIcons.COOLDOWN_ICON;

                if (cd.isFrozen())
                    COOLDOWN_TEX = StandIcons.FROZEN_COOLDOWN_ICON;

                context.blit(COOLDOWN_TEX, x - 1, y - 1 + b, 0, b, 20, 20-b, 20, 20);
                int num = ((int)(Math.floor((double) cd.time /20)+1));
                int offset = x+3;
                if (num <=9){
                    offset = x+7;
                }
                //If 100 or more seconds long shift to the left
                if (num >= 100){
                    offset = offset-3;
                }

                if (!cd.isFrozen())
                    context.drawString(Minecraft.getInstance().font, ""+num,offset,y,0xffffff,true);

            }
            context.setColor(1f, 1f, 1f, 0.9f);
        } else context.renderItem(block, x + 1, y + 1);
    }

    public Vec3 moveVec = Vec3.ZERO;

    @Override
    public void tickPowerEnd() {
        super.tickPowerEnd();
        if (this.getActivePower() == PowerIndex.POWER_2){
            /**Tick Phase Grab*/
            if (!this.getSelf().level().isClientSide()) {
                if (this.attackTimeDuring == 108) {
                    ((StandUser) this.getSelf()).roundabout$tryPower(PowerIndex.NONE, true);
                } else if (this.attackTimeDuring >= 0) {
                    StandEntity stand = getStandEntity(this.self);
                    if (Objects.nonNull(stand)) {
                        AABB BB1 = stand.getBoundingBox();
                        Vec3 vec3d = this.getSelf().getEyePosition(0);
                        Vec3 vec3d2 = this.getSelf().getViewVector(0);
                        Vec3 vec3d3 = vec3d.add(vec3d2.x * 20, vec3d2.y * 20, vec3d2.z * 20);
                        double mag = 0.18F;

                        moveVec = moveVec.add(
                                vec3d3.subtract(this.getSelf().position().add(moveVec)).normalize().scale(mag)
                        );
                        Vec3 yes = this.getSelf().position().add(moveVec);
                        double post = stand.position().distanceTo(vec3d3);
                        if (post< 1.5){
                            stand.setYRot(this.getSelf().getYHeadRot() % 360);
                            stand.setXRot(this.getSelf().getXRot());
                        } else {
                            Direction gdir = ((IGravityEntity)this.self).roundabout$getGravityDirection();
                            Vec2 grot = new Vec2(getLookAtPlaceYaw(stand,vec3d3),
                                    getLookAtPlacePitch(stand,vec3d3)
                            );
                            grot =  RotationUtil.rotWorldToPlayer(grot,gdir);
                            stand.setYRot(grot.x);
                            stand.setXRot(grot.y);
                        }
                        if (post < 0.4){
                            stand.setPos(vec3d3);
                        } else {
                            stand.setPos(yes);
                        }

                        boolean isHoldingItem = !stand.getHeldItem().equals(ItemStack.EMPTY);

                        if ((stand.isTechnicallyInImpassableWall()) ||
                                stand.position().distanceTo(this.getSelf().position()) > 15){
                            S2CPacketUtil.sendCooldownSyncPacket(((ServerPlayer) this.getSelf()), PowerIndex.SKILL_2, 7);
                            this.setCooldown(PowerIndex.SKILL_2, 5);
                            ((StandUser) this.getSelf()).roundabout$tryPower(PowerIndex.EXTRA, true);
                            return;
                        }


                        if (this.getSelf().level().getGameRules().getBoolean(ModGamerules.ROUNDABOUT_STAND_REDSTONE_INTERFERENCE)
                                && !isHoldingItem)
                        {
                            Vec3 vec3d2ST = stand.getViewVector(0);
                            Vec3 vec3dST = stand.getEyePosition(0);
                            Vec3 vec3d3ST = vec3dST.add(vec3d2ST.x * 3, vec3d2ST.y * 3, vec3d2ST.z * 3);
                            BlockHitResult blockHit = stand.level().clip(new ClipContext(vec3dST, vec3d3ST,
                                    ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, stand));
                            BlockPos bpos = blockHit.getBlockPos();
                            BlockState state = stand.level().getBlockState(bpos);
                            if (!state.isAir()) {
                                Block blk = state.getBlock();
                                if (((blk instanceof LeverBlock) || (blk instanceof ButtonBlock)
                                        || (blk instanceof DoorBlock) || (blk instanceof TrapDoorBlock)
                                        || (blk instanceof FenceGateBlock))
                                        && this.getSelf() instanceof Player PE) {
                                    blk.use(state, this.getSelf().level(), bpos, PE, PE.getUsedItemHand(), blockHit);
                                    ((StandUser) this.getSelf()).roundabout$tryPower(PowerIndex.EXTRA, true);
                                    return;
                                }
                            }
                        }

                        AABB BB2 = stand.getBoundingBox();
                        if (this.attackTimeDuring > 2) {
                            if (!isHoldingItem) {
                                tryPhaseItemGrab(stand, BB1, BB2);
                            }
                            else {
                                tryPhaseAttack(stand, BB1, BB2);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void powerActivate(PowerContext context) {
        switch (context) {
            case SKILL_1_NORMAL -> {
                //TODO implement this
            }
            case SKILL_1_CROUCH -> {
                //TODO implement this
            }
            case SKILL_2_NORMAL -> phaseGrabClient();
            case SKILL_2_CROUCH -> phaseInteractClient();
            case SKILL_3_CROUCH -> this.tryFormPredict();
            case SKILL_4_NORMAL, SKILL_4_CROUCH -> this.tryMemorize();
        }
        super.powerActivate(context);
    }

    public void tryFormPredict() {
        getStandUserSelf().roundabout$tryPower(PowerIndex.POWER_3_SNEAK, true);
        tryPowerPacket(PowerIndex.POWER_3_SNEAK);
    }

    public void phaseGrabClient(){
        if ((this.getActivePower() != PowerIndex.POWER_2)) {
            if (!this.onCooldown(PowerIndex.SKILL_2)) {
                this.getStandUserSelf().roundabout$tryPower(PowerIndex.POWER_2, true);
                tryPowerPacket(PowerIndex.POWER_2);
            }
        }
        else {
            this.getStandUserSelf().roundabout$tryPower(PowerIndex.EXTRA, true);
            tryPowerPacket(PowerIndex.EXTRA);
        }
    }

    public void phaseInteractClient() {
        if (this.getActivePower() != PowerIndex.POWER_2) {
            if (!this.onCooldown(PowerIndex.SKILL_2)) {
                getStandUserSelf().roundabout$tryPower(PowerIndex.POWER_2_SNEAK, true);
                tryPowerPacket(PowerIndex.POWER_2_SNEAK);
            }
        }
        else {
            this.getStandUserSelf().roundabout$tryPower(PowerIndex.EXTRA, true);
            tryPowerPacket(PowerIndex.EXTRA);
        }
    }

    public boolean phaseGrab() {
        StandEntity stand = getStandEntity(this.self);
        if (Objects.nonNull(stand)) {
            animateStand(StandEntity.PHASE_GRAB);
            this.attackTimeDuring = 0;
            stand.setFadePercent(50);
            this.setActivePower(PowerIndex.POWER_2);
            this.poseStand(OffsetIndex.LOOSE);

            Vec2 twoVec = new Vec2((this.getSelf().getYHeadRot() % 360),(this.getSelf().getXRot()));
            Direction gdir = ((IGravityEntity)this.self).roundabout$getGravityDirection();
            Vec2 twoVecGrav = RotationUtil.rotPlayerToWorld(twoVec,gdir);
            Vec3 threeVec = new Vec3(0,0.25,0);
            threeVec = RotationUtil.vecPlayerToWorld(threeVec,gdir);

            stand.setYRot(twoVec.x);
            stand.setXRot(twoVec.y);
            moveVec = DamageHandler.getRotationVector(
                    twoVecGrav.y, (float) (twoVecGrav.x)).scale(1.8).add(threeVec.x,threeVec.y,threeVec.z);
            stand.setPos(this.getSelf().position().add(moveVec));
            return true;
        }
        return false;
    }

    public boolean phaseInteract() {
        StandEntity stand = getStandEntity(this.self);
        if (Objects.nonNull(stand)) {
            if (this.inventoryGrab()) {
                animateStand(StandEntity.PHASE_GRAB);
                this.attackTimeDuring = 0;
                stand.setFadePercent(50);
                this.setActivePower(PowerIndex.POWER_2);
                this.poseStand(OffsetIndex.LOOSE);

                Vec2 twoVec = new Vec2((this.getSelf().getYHeadRot() % 360),(this.getSelf().getXRot()));
                Direction gdir = ((IGravityEntity)this.self).roundabout$getGravityDirection();
                Vec2 twoVecGrav = RotationUtil.rotPlayerToWorld(twoVec,gdir);
                Vec3 threeVec = new Vec3(0,0.25,0);
                threeVec = RotationUtil.vecPlayerToWorld(threeVec,gdir);

                stand.setYRot(twoVec.x);
                stand.setXRot(twoVec.y);
                moveVec = DamageHandler.getRotationVector(
                        twoVecGrav.y, (float) (twoVecGrav.x)).scale(1.8).add(threeVec.x,threeVec.y,threeVec.z);
                stand.setPos(this.getSelf().position().add(moveVec));
                return true;
            }
        }
        return false;
    }

    public boolean inventoryGrab() {
        if (!this.getSelf().level().isClientSide()) {
            StandEntity standEntity = ((StandUser) this.getSelf()).roundabout$getStand();
            if (standEntity != null && standEntity.isAlive() && !standEntity.isRemoved() &&
                    this.getSelf() instanceof Player) {
                ItemStack stack = ((Player)this.getSelf()).getMainHandItem();
                if (isGrabbable(stack)) {
                    standEntity.canAcquireHeldItem = true;
                    standEntity.setHeldItem(stack.copyWithCount(1));
                    playSoundIfPossible(self.level(),null, this.getSelf().blockPosition(), ModSounds.BLOCK_GRAB_EVENT, SoundSource.PLAYERS, 1.7F, 1.3F);
                    stack.shrink(1);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isGrabbable(ItemStack stack) {
        return !stack.isEmpty() &&
                !(MainUtil.isItemGrabBlacklisted(stack)) &&
                !(stack.getItem() instanceof BlockItem
                        && (MainUtil.isBlockBlacklisted(((BlockItem) stack.getItem()).getBlock().defaultBlockState()) ||
                        ((BlockItem) stack.getItem()).getBlock() instanceof ShulkerBoxBlock ||
                        ((BlockItem) stack.getItem()).getBlock() instanceof FancyLighterBlock)) &&
                !(stack.getItem() instanceof BoatItem) &&
                !(stack.getItem() instanceof MinecartItem) &&
                !(stack.getItem() instanceof RoadRollerItem);
    }

    public void tryPhaseItemGrab(StandEntity stand, AABB bb1, AABB bb2){
        bb1 = bb1.inflate(1.6F);
        bb2 = bb2.inflate(1.6F);

        AABB $$2 = bb1.minmax(bb2);
        List<Entity> $$3 = stand.level().getEntities(stand, $$2);
        if (!$$3.isEmpty()) {
            for (int $$4 = 0; $$4 < $$3.size(); $$4++) {
                Entity $$5 = $$3.get($$4);
                if ($$5 instanceof ItemEntity IE && stand.getSensing().hasLineOfSight($$5)){
                    if (!(IE.getItem().getItem() instanceof BlockItem BI &&
                            (BI.getBlock() instanceof ShulkerBoxBlock ||
                                    BI.getBlock() instanceof FancyLighterBlock))){
                        stand.canAcquireHeldItem = true;
                        stand.setHeldItem(IE.getItem().copyWithCount(1));
                        playSoundIfPossible(self.level(),null, this.getSelf().blockPosition(), ModSounds.BLOCK_GRAB_EVENT, SoundSource.PLAYERS, 1.7F, 1.3F);
                        this.getStandUserSelf().roundabout$tryPower(PowerIndex.EXTRA, true);
                        this.setAttackTimeDuring(0);
                        poseStand(OffsetIndex.FOLLOW_NOLEAN);
                        animateStand(StandEntity.IDLE);

                        IE.getItem().shrink(1);
                        itemNearby(IE.getId());
                        return;
                    }
                }
            }
        }
    }

    public void tryPhaseAttack(StandEntity stand, AABB bb1, AABB bb2) {
        bb1 = bb1.inflate(1.6F);
        bb2 = bb2.inflate(1.6F);

        AABB $$2 = bb1.minmax(bb2);
        List<Entity> $$3 = stand.level().getEntities(stand, $$2);
        if (!$$3.isEmpty()) {
            for (int $$4 = 0; $$4 < $$3.size(); $$4++) {
                Entity $$5 = $$3.get($$4);
                if (!$$5.equals(stand) && !$$5.equals(getSelf()) &&
                        stand.getSensing().hasLineOfSight($$5)){
                    if ($$5.isAttackable()){
                        ItemStack held = stand.getHeldItem();

                        $$5.hurt(ModDamageTypes.of(stand.level(), DamageTypes.PLAYER_ATTACK, this.getSelf(), this.getSelf()),
                                getDamage(held, $$5, stand));
                        playSoundIfPossible(self.level(),null, this.getSelf().blockPosition(), SoundEvents.PLAYER_ATTACK_WEAK, SoundSource.PLAYERS, 1.7F, 1.3F);
                        if (held.isDamageableItem()) {
                            held.setDamageValue(held.getDamageValue() + 1);
                        }
                        this.getStandUserSelf().roundabout$tryPower(PowerIndex.EXTRA, true);
                        this.setAttackTimeDuring(0);
                        poseStand(OffsetIndex.FOLLOW_NOLEAN);
                        animateStand(StandEntity.IDLE);

                        return;
                    }
                }
            }
        }
    }

    private float getDamage(ItemStack stack, Entity ent, StandEntity stand) {
        float damage = 1;
        if (stand != null) damage = (float) stand.getAttributeValue(Attributes.ATTACK_DAMAGE) - 1;
        if (damage != 1) {
            if (ent instanceof LivingEntity){
                damage += EnchantmentHelper.getDamageBonus(stack, ((LivingEntity)ent).getMobType());
            } else {
                damage += EnchantmentHelper.getDamageBonus(stack, MobType.UNDEFINED);
            }
        }
        return damage;
    }

    public void addItem(StandEntity standEntity){
        if (!this.getSelf().level().isClientSide()) {
            addItemLight(standEntity);
            S2CPacketUtil.sendCooldownSyncPacket(((ServerPlayer) this.getSelf()), PowerIndex.SKILL_2,
                    ClientNetworking.getAppropriateConfig().generalStandSettings.objectPocketCooldown);
            this.setCooldown(PowerIndex.SKILL_2,
                    ClientNetworking.getAppropriateConfig().generalStandSettings.objectPocketCooldown);
        }
        this.getStandEntity(this.getSelf()).setHeldItem(ItemStack.EMPTY);
    }

    public void addItemLight(StandEntity standEntity){
        if (canAddItem(standEntity.getHeldItem(), ((Player) this.getSelf()).getInventory())) {
            ((Player) this.getSelf()).addItem(standEntity.getHeldItem());
        } else {
            ItemEntity $$4 = new ItemEntity(this.getSelf().level(), this.getSelf().getX(),
                    this.getSelf().getY() + this.getSelf().getEyeHeight(), this.getSelf().getZ(),
                    standEntity.getHeldItem());
            $$4.setPickUpDelay(40);
            $$4.setThrower(this.getSelf().getUUID());
            PowerTypes.copyPlaneOfExisting(self,$$4);
            this.getSelf().level().addFreshEntity($$4);
        }
    }

    public boolean canAddItem(ItemStack itemStack, Inventory inventory) {
        boolean bl = false;
        for (ItemStack itemStack2 : inventory.items) {
            if (!itemStack2.isEmpty() && (!ItemStack.isSameItemSameTags(itemStack2, itemStack) || itemStack2.getCount() >= itemStack2.getMaxStackSize())) continue;
            bl = true;
            break;
        }
        return bl;
    }

    public final void itemNearby(int id) {
        if (!this.self.level().isClientSide) {
            ServerLevel serverWorld = ((ServerLevel) this.self.level());
            Vec3 userLocation = new Vec3(this.self.getX(),  this.self.getY(), this.self.getZ());
            for (int j = 0; j < serverWorld.players().size(); ++j) {
                ServerPlayer serverPlayerEntity = ((ServerLevel) this.self.level()).players().get(j);

                if (((ServerLevel) serverPlayerEntity.level()) != serverWorld) {
                    continue;
                }

                BlockPos blockPos = serverPlayerEntity.blockPosition();
                if (blockPos.closerToCenterThan(userLocation, 100)) {
                    S2CPacketUtil.sendGenericIntToClientPacket(serverPlayerEntity, PacketDataIndex.S2C_INT_GRAB_ITEM,id);
                }
            }
        }
    }

    public void tryMemorize() {
        float range = 5f;
        Vec3 vec3d = this.getSelf().getEyePosition(0);
        Vec3 vec3d2 = this.getSelf().getViewVector(0);
        Vec3 vec3d3 = vec3d.add(vec3d2.x * range, vec3d2.y * range, vec3d2.z * range);
        BlockHitResult blockHit = this.getSelf().level().clip(new ClipContext(vec3d, vec3d3,
                ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, this.getSelf()));
        if (blockHit.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = blockHit.getBlockPos();
            getStandUserSelf().roundabout$tryBlockPosPower(PowerIndex.POWER_4, true, pos);
            tryBlockPosPowerPacket(PowerIndex.POWER_4, pos);
        }
    }

    @Override
    public boolean tryBlockPosPower(int move, boolean forced, BlockPos blockPos) {
        if (move == PowerIndex.POWER_4) {
            memorizeBlock(blockPos);
        }
        return super.tryBlockPosPower(move, forced, blockPos);
    }

    @Override
    public boolean setPowerOther(int move, int lastMove) {
        switch (move) {
            case PowerIndex.POWER_2 -> {
                return phaseGrab();
            }
            case PowerIndex.POWER_2_SNEAK -> {
                return phaseInteract();
            }
            case PowerIndex.POWER_3_SNEAK -> {
                return this.formPredict();
            }
            case PowerIndex.EXTRA -> {
                return this.exitPhaseGrab();
            }
        }
        return super.setPowerOther(move, lastMove);
    }

    public boolean exitPhaseGrab() {
        if (!getSelf().level().isClientSide()) {
            addItem(getStandEntity(getSelf()));
        }
        else getStandEntity(getSelf()).setHeldItem(ItemStack.EMPTY);
        this.getStandUserSelf().roundabout$tryPower(PowerIndex.NONE, true);
        tryPowerPacket(PowerIndex.NONE);
        return true;
    }

    @Override
    public boolean setPowerNone() {
        return super.setPowerNone();
    }

    public boolean formPredict() {
        Item held = this.getSelf().getMainHandItem().getItem();
        Block potentialBlock = getFormPrediction(held);
        if (potentialBlock != null) this.setMemorizedScent(potentialBlock);
        return true;
    }

    public Block getFormPrediction(Item item) {
        if (MainUtil.KING_NOTHING_FORM_PREDICT.get(item) != null) {
            return MainUtil.KING_NOTHING_FORM_PREDICT.get(item);
        }
        return null;
    }

    private void memorizeBlock(BlockPos blockPos) {
        Block block = this.getSelf().level().getBlockState(blockPos).getBlock();
        if (isMemorizableBlock(block)) setMemorizedScent(block);
    }

    private static boolean isMemorizableBlock(Block block) {
        return !block.asItem().equals(Items.AIR) &&
                !(block instanceof FogBlock) &&
                block.defaultDestroyTime() >= 0;
    }

    private void setMemorizedScent(Block memorizedBlock) {
        this.memorizedBlock = memorizedBlock;
        saveDiscAndSync();
    }

    @Override
    public boolean isWip() {
        return true;
    }

    @Override
    public Component ifWipListDev() {
        return Component.literal("JacobBoxCEO").withStyle(ChatFormatting.DARK_GREEN);
    }

    @Override
    public Component ifWipListDevStatus() {
        return Component.translatable(  "roundabout.dev_status.active").withStyle(ChatFormatting.DARK_PURPLE);
    }
}
