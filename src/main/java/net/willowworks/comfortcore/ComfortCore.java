package net.willowworks.comfortcore;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("comfortcore")
public class ComfortCore
{
    public static final String MOD_ID = "comfortcore";
    private static final Logger LOGGER = LogManager.getLogger("ComfortCore");

    public ComfortCore() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::onCommonSetupEvent);
        eventBus.addListener(this::onClientSetupEvent);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommonSetupEvent(final FMLCommonSetupEvent event) {}

    @SubscribeEvent
    public void onClientSetupEvent(FMLClientSetupEvent event) {}


}
