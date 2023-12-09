package fr.pandaguerrier.conodia.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String prefix = "§8[§cConodiaSantion§8] ";
    public static boolean containsLetter(String str){
        return Pattern.matches("[a-zA-Z]+", str);
    }

    public static int countMatches(String text, String str) {
        if (text.isEmpty() || str.isEmpty()) { return 0; }
        Matcher matcher = Pattern.compile(str).matcher(text);
        int count = 0;
        while (matcher.find()) { count++; }
        return count;
    }

    /**
     *
     * @param message : Le message dans lequel un placeholder doit être remplacé
     * @param toreplace : La sous-chaîne de caractère contenu dans {} qui doit être remplacés
     * @param replacement : La chaîne de caractère qui va remplacé la sous-chaîne de caractère dans les {}
     * @return La nouvelle chaîne de caractère qui contient la sous-chaîne remplacés par la valeur replacement.
     * @usage Utilisation : String str =replace("Le joueur {target} est mort", "target", target.getName());
     */
    public static String replace(String message, String toreplace, String replacement){
        return message.replaceAll("\\{" + toreplace + "}", replacement);
    }

    public static String target(String message, String targetName){
        return message.replaceAll("\\{target}", targetName);
    }
}

