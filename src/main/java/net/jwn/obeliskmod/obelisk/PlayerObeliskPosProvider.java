package net.jwn.obeliskmod.obelisk;

import net.jwn.obeliskmod.ObeliskMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class PlayerObeliskPosProvider {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ObeliskMod.MOD_ID);

    public static final Supplier<AttachmentType<PlayerObeliskPos>> PLAYER_OBELISK_POS = ATTACHMENT_TYPES.register(
            "player_obelisk_pos", () -> AttachmentType.serializable(PlayerObeliskPos::new).build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
