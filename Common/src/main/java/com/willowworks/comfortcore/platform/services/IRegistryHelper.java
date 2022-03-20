package com.willowworks.comfortcore.platform.services;

import com.willowworks.comfortcore.entities.SeatEntity;
import net.minecraft.world.entity.EntityType;

public interface IRegistryHelper {

    EntityType<SeatEntity> getSeatEntity();
}
