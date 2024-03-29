package cl.emilym.form.compose.helper;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class HumanReadableBytes {

    public static String si(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }


}
