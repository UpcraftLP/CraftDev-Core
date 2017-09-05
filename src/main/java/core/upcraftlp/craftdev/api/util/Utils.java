package core.upcraftlp.craftdev.api.util;

import javax.annotation.Nullable;

import static net.minecraft.util.ChatAllowedCharacters.ILLEGAL_FILE_CHARACTERS;

import java.util.List;
import java.util.Random;

/**
 * @author UpcraftLP
 */
public class Utils {

    public static final Random RANDOM = new Random();

    /**
     * filters illegal file characters out of a String.
     */
    public static String filterChars(String input) {
        for (char c : ILLEGAL_FILE_CHARACTERS) {
            if(c == ILLEGAL_FILE_CHARACTERS[0]) continue; //don't compute the '/'
            input = input.replace(c, '_');
        }
        return input;
    }

    @Nullable
    public static <T> T getRandomElementFromList(List<T> list) {
        if(list.size() == 0) return null;
        return list.get(RANDOM.nextInt(list.size()));
    }

}
