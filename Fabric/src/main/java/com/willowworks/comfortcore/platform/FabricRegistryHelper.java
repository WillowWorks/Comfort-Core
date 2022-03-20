package com.willowworks.comfortcore.platform;

import com.willowworks.comfortcore.FabricComfortCore;
import com.willowworks.comfortcore.entities.SeatEntity;
import com.willowworks.comfortcore.platform.services.IRegistryHelper;
import net.minecraft.world.entity.EntityType;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public EntityType<SeatEntity> getSeatEntity() {
        return FabricComfortCore.MOUNTABLE_HIDDEN_ENTITY_ENTITY_TYPE;
    }
}
