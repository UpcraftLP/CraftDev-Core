package core.upcraftlp.craftdev.asm.tweaks;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.FRETURN;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

import java.util.Iterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import core.upcraftlp.craftdev.api.event.BiomeTemperatureEvent;
import core.upcraftlp.craftdev.api.util.asm.ClassTransform;
import core.upcraftlp.craftdev.api.util.asm.DeobfuscationHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;

public class TweakBiome extends ClassTransform {

    @Override
    public String getDeobfClassName() {
        return DeobfuscationHelper.Biome;
    }

    @Override
    public void transform(Iterator<MethodNode> methods) {
        String name = DeobfuscationHelper.getName("getFloatTemperature");
        while(methods.hasNext()) {
            MethodNode node = methods.next();
            if(node.name.equals(name)) {
                System.out.println("adding BiomeTemperatureEvent");
                for(int i = 0; i < node.instructions.size(); i++) {
                    AbstractInsnNode current = node.instructions.get(i);
                    if(current instanceof InsnNode && current.getOpcode() == FRETURN) {
                        InsnList toAdd = new InsnList();
                        toAdd.add(new VarInsnNode(ALOAD, 0));
                        toAdd.add(new VarInsnNode(ALOAD, 1));
                        toAdd.add(new MethodInsnNode(INVOKESTATIC, "core/upcraftlp/craftdev/asm/tweaks/TweakBiome", "getBiomeTemperature", "(FLnet/minecraft/world/biome/Biome;Lnet/minecraft/util/math/BlockPos;)F", false));
                        node.instructions.insertBefore(current, toAdd);
                        i += 3;
                    }
                }
                
            }
        }
    }
    
    public static float getBiomeTemperature(float temperatureIn, Biome biomeIn, BlockPos posIn) { //order of the params is to produce less additional bytecode
        BiomeTemperatureEvent event = new BiomeTemperatureEvent(biomeIn, posIn, temperatureIn);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getTemperature();
    }

}
