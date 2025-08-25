package net.jwn.obeliskmod.obelisk;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class PlayerObeliskPos implements INBTSerializable<CompoundTag> {
    private int[] pos1 = new int[4];
    private int[] pos2 = new int[4];
    private int[] pos3 = new int[4];
    private int[] pos4 = new int[4];

    /*
    pos[0] : 1 for overworld, 2 for nether, 3 for ender
    pos[1, 2, 3] : block pos
     */

    public int[] getObeliskPos(int i) {
        return switch (i) {
            case 1 -> pos1;
            case 2 -> pos2;
            case 3 -> pos3;
            case 4 -> pos4;
            default -> throw new IllegalArgumentException("Invalid index: " + i);
        };
    }

    public void saveObeliskPos(int i, Level world, BlockPos blockPos) {
        int[] target;
        switch (i) {
            case 1 -> target = pos1;
            case 2 -> target = pos2;
            case 3 -> target = pos3;
            case 4 -> target = pos4;
            default -> throw new IllegalArgumentException("Invalid index: " + i);
        }
        if (world.dimension() == Level.OVERWORLD) {
            target[0] = 1;
        } else if (world.dimension() == Level.NETHER) {
            target[0] = 2;
        } else if (world.dimension() == Level.END) {
            target[0] = 3;
        }
        target[1] = blockPos.getX();
        target[2] = blockPos.getY();
        target[3] = blockPos.getZ();
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();

        tag.putIntArray("obelisk_1", pos1);
        tag.putIntArray("obelisk_2", pos2);
        tag.putIntArray("obelisk_3", pos3);
        tag.putIntArray("obelisk_4", pos4);

        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        pos1 = tag.getIntArray("obelisk_1");
        pos2 = tag.getIntArray("obelisk_2");
        pos3 = tag.getIntArray("obelisk_3");
        pos4 = tag.getIntArray("obelisk_4");
    }
}
