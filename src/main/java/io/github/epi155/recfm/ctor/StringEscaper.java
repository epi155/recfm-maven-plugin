package io.github.epi155.recfm.ctor;

public class StringEscaper {

    public static String escape(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            switch (c) {
                case '\n': sb.append("\\n"); break;
                case '\t': sb.append("\\t"); break;
                case '\r': sb.append("\\r"); break;
                case '\b': sb.append("\\b"); break;
                case '\f': sb.append("\\f"); break;
                case '\"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                default:
                    // Controllo se è un surrogato non valido
                    if (Character.isHighSurrogate(c) || Character.isLowSurrogate(c)) {
                        sb.append(String.format("\\u%04x", (int) c));
                    }
                    // Controllo se è un carattere di controllo o non stampabile
                    else if (Character.isISOControl(c) || !Character.isDefined(c) || ! isPrintableChar(c)) {
                        sb.append(String.format("\\u%04x", (int) c));
                    }
                    else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }
    public static boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return (!Character.isISOControl(c)) &&
                c != '\uFFFD' &&
                block != null &&
                block != Character.UnicodeBlock.SPECIALS;
    }
}

