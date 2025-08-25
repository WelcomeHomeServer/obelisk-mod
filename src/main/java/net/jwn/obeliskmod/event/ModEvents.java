package net.jwn.obeliskmod.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.jwn.obeliskmod.ObeliskMod;
import net.jwn.obeliskmod.obelisk.PlayerObeliskPos;
import net.jwn.obeliskmod.obelisk.PlayerObeliskPosProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = ObeliskMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("obelisk")
                        .requires(source -> source.hasPermission(2)) // 권한 레벨

                        // /obelisk add <number>
                        .then(Commands.literal("add")
                                .then(Commands.argument("number", IntegerArgumentType.integer(1))
                                        .executes(context -> {
                                            int number = IntegerArgumentType.getInteger(context, "number");
                                            ServerPlayer player = context.getSource().getPlayer();
                                            if (player != null) {
                                                PlayerObeliskPos obeliskPos = player.getData(PlayerObeliskPosProvider.PLAYER_OBELISK_POS);
                                                obeliskPos.saveObeliskPos(number, context.getSource().getLevel(), player.getOnPos());
                                            }

                                            context.getSource().sendSuccess(() -> Component.literal("오벨리스크 번호 " + number + "이(가) 추가되었습니다!"), false);

                                            return 1;
                                        })
                                )
                        )

                        // /obelisk list
                        .then(Commands.literal("list")
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayer();
                                    if (player != null) {
                                        PlayerObeliskPos obeliskPos = player.getData(PlayerObeliskPosProvider.PLAYER_OBELISK_POS);
                                        for (int i = 1; i <= 4; i++) {
                                            int[] target = obeliskPos.getObeliskPos(i);
                                            String dimensionName = switch (target[0]) {
                                                case 1 -> "Overworld";
                                                case 2 -> "Nether";
                                                case 3 -> "End";
                                                default -> "Unknown";
                                            };

                                            String message = String.format("Obelisk %d: [%s] (%d, %d, %d)",
                                                    i, dimensionName, target[1], target[2], target[3]);

                                            player.sendSystemMessage(Component.literal(message));
                                        }
                                    }
                                    return 1;
                                })
                        )
        );
    }
}
