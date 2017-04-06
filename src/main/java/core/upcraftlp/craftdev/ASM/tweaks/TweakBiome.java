package core.upcraftlp.craftdev.ASM.tweaks;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.FLOAD;
import static org.objectweb.asm.Opcodes.FSTORE;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

import java.util.Iterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import core.upcraftlp.craftdev.API.event.BiomeTemperatureEvent;
import core.upcraftlp.craftdev.API.util.asm.ClassTransform;
import core.upcraftlp.craftdev.API.util.asm.DeobfuscationHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;

public class TweakBiome extends ClassTransform {

    @Override
    public String getDeobfClassName() {
        return DeobfuscationHelper.Biome;
    }

    @Override
    public void transform(Iterator<MethodNode> methods) {
        String name = DeobfuscationHelper.getName("getTemperature");
        while(methods.hasNext()) {
            MethodNode node = methods.next();
            if(node.name.equals(name)) {
                for(int i = 0; i < node.instructions.size(); i++) {
                    AbstractInsnNode current = node.instructions.get(i);
                    if(current instanceof FieldInsnNode) {
                        log.println("adding BiomeTemperatureEvent");
                        InsnList toAdd = new InsnList();
                        toAdd.add(new VarInsnNode(FSTORE, 1));
                        toAdd.add(new VarInsnNode(ALOAD, 0));
                        toAdd.add(new VarInsnNode(FLOAD, 1));
                        toAdd.add(new MethodInsnNode(INVOKESTATIC, "core/upcraftlp/craftdev/ASM/tweaks/TweakBiome", "getBiomeTemperature", "(Lnet/minecraft/world/biome/Biome;F)F", false));
                        node.instructions.insert(current, toAdd);
                        break;
                    }
                            
                }
                
            }
        }
    }
    
    public static float getBiomeTemperature(Biome biomeIn, float temperatureIn) {
        BiomeTemperatureEvent event = new BiomeTemperatureEvent(biomeIn, temperatureIn);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getTemperature();
    }

}
