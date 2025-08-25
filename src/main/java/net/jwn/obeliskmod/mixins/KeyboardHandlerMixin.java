package net.jwn.obeliskmod.mixins;

import net.jwn.obeliskmod.util.Keybindings;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void onKeyPress(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (key == Keybindings.GLFW_KEY_F1 && action == GLFW.GLFW_PRESS) {
            boolean cHeld = GLFW.glfwGetKey(window, Keybindings.COMMAND_KEY) == GLFW.GLFW_PRESS;
            if (cHeld) {
                // ADD HERE

                ci.cancel();
            }
        }
    }
}