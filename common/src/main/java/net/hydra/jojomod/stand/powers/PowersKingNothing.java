package net.hydra.jojomod.stand.powers;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.block.FogBlock;
import net.hydra.jojomod.client.StandIcons;
import net.hydra.jojomod.entity.ModEntities;
import net.hydra.jojomod.entity.stand.StandEntity;
import net.hydra.jojomod.event.index.PowerIndex;
import net.hydra.jojomod.event.powers.CooldownInstance;
import net.hydra.jojomod.event.powers.StandPowers;
import net.hydra.jojomod.stand.powers.elements.PowerContext;
import net.hydra.jojomod.stand.powers.presets.NewDashPreset;
import net.hydra.jojomod.util.MainUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

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

    public ItemStack getMemorizedItemStack() {
        return new ItemStack(memorizedBlock.asItem());
    }

    @Override
    public void renderIcons(GuiGraphics context, int x, int y) {
        if (isHoldingSneak()) {
            setSkillIcon(context, x, y,1, StandIcons.THE_WORLD_GRAB_ITEM, PowerIndex.SKILL_1_SNEAK);
            setSkillIcon(context, x, y,2, StandIcons.STAR_PLATINUM_PHASE_GRAB, PowerIndex.SKILL_2_SNEAK);
            setSkillIcon(context, x, y,3, StandIcons.THE_WORLD_GRAB_ITEM, PowerIndex.SKILL_3);
            renderItem(context, x, y,4);
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
                if (slot == 3) return getFormPrediction(getSelf().getMainHandItem().getItem()) == null;
            }
        }
        return super.isAttackIneptVisually(activeP, slot);
    }

    public void renderItem(GuiGraphics context, int x, int y, int slot){
        RenderSystem.enableBlend();
        context.setColor(1f, 1f, 1f, 1f);
        CooldownInstance cd = null;
        x += slot * 25;
        y-=1;
        RenderSystem.enableBlend();
        context.blit(StandIcons.NOVELTY_ICON,x-3,y-3,0, 0, squareWidth, squareHeight, squareWidth, squareHeight);

        RenderSystem.enableBlend();
        ItemStack clock = getMemorizedItemStack();
        context.renderItem(clock, x+1, y+1);  // Draw the item itself
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
            case SKILL_2_NORMAL -> {
                //TODO implement this
            }
            case SKILL_2_CROUCH -> {
                //TODO implement this
            }
            case SKILL_3_CROUCH -> {
                this.tryFormPredict();
            }
            case SKILL_4_NORMAL, SKILL_4_CROUCH -> {
                this.tryMemorize();
            }
        }
        super.powerActivate(context);
    }

    public void tryFormPredict() {
        getStandUserSelf().roundabout$tryPower(PowerIndex.POWER_3_SNEAK, true);
        tryPower(PowerIndex.POWER_3_SNEAK);
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
            case PowerIndex.POWER_3_SNEAK -> {
                return formPredict();
            }
        }
        return super.setPowerOther(move, lastMove);
    }

    public boolean formPredict() {
        Item held = this.getSelf().getMainHandItem().getItem();
        Block potentialBlock = getFormPrediction(held);
        if (potentialBlock != null) setMemorizedScent(potentialBlock);
        return true;
    }

    public Block getFormPrediction(Item item) {
        if (MainUtil.KING_NOTHING_FORM_PREDICT.get(item) != null) {
            return MainUtil.KING_NOTHING_FORM_PREDICT.get(item);
        }
        else if (item instanceof BlockItem bi) {
            Block potentialBlock = bi.getBlock();
            if (isMemorizableBlock(potentialBlock)) return potentialBlock;
        }
        return null;
    }

    private void memorizeBlock(BlockPos blockPos) {
        Block block = this.getSelf().level().getBlockState(blockPos).getBlock();
        if (!isMemorizableBlock(block)) return;
        setMemorizedScent(block);
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
