package com.willowworks.comfortcore.platform;

import com.willowworks.comfortcore.ForgeComfortCore;
import com.willowworks.comfortcore.entities.SeatEntity;
import com.willowworks.comfortcore.platform.services.IRegistryHelper;
import net.minecraft.world.entity.EntityType;

public class ForgeRegistryHelper implements IRegistryHelper {
    @Override
    public EntityType<SeatEntity> getSeatEntity() {
        return ForgeComfortCore.SEAT_ENTITY_TYPE.get();
    }
}
