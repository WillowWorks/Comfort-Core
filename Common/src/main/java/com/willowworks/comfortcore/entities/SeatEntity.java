package com.willowworks.comfortcore.entities;

import com.willowworks.comfortcore.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SeatEntity extends Entity {

    private static final Map<BlockPos, UUID> OCCUPIED_SEATS = new HashMap<>();

    public SeatEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public SeatEntity(Level level, BlockPos pos) {
        super(Services.REGISTRY.getSeatEntity(), level);
        OCCUPIED_SEATS.put(pos, this.uuid);
    }

    @Override
    public boolean isVehicle() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return true;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
        removeThis();
        this.discard();
        return super.getDismountLocationForPassenger(livingEntity);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        if(getPassengers().isEmpty()) {
            this.discard();
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {}

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public void remove(RemovalReason removalReason) {
        removeThis();
        super.remove(removalReason);
    }

    public static boolean hasLocation(BlockPos pos) {
        return OCCUPIED_SEATS.containsKey(pos);
    }
    public void removeThis() {
        OCCUPIED_SEATS.entrySet().stream()
                .filter(blockPosUUIDEntry -> blockPosUUIDEntry.getValue().equals(this.uuid))
                .findFirst()
                .ifPresent(blockPosUUIDEntry -> OCCUPIED_SEATS.remove(blockPosUUIDEntry.getKey()));
    }
}
