package com.willowworks.comfortcore;

import com.willowworks.comfortcore.entities.SeatEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class FabricComfortCore implements ModInitializer {

    public static final EntityType<SeatEntity> MOUNTABLE_HIDDEN_ENTITY_ENTITY_TYPE = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(Constants.MOD_ID, "mountable_hidden_entity"), FabricEntityTypeBuilder.<SeatEntity>create(MobCategory.MISC, SeatEntity::new).dimensions(EntityDimensions.fixed(0.001F, 000.1F)).build());

    @Override
    public void onInitialize() {
    }
}
