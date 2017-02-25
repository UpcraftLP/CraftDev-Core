package core.upcraftlp.craftdev.API.world;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.minecraftforge.oredict.OreDictionary;

@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface OreDict {

    /**
     * The {@link OreDictionary} name of the Block or Item.
     */
    String value() default "";
}
