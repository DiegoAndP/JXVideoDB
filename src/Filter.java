import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Filter {
    public static boolean byList(String[] listOfTags, String[] lineToRead, Field field) {
        Boolean value = false;
        for (String tag : listOfTags) {
            if (lineToRead[field.ordinal()].contains(tag)) {
                value = true;
                break;
            }
        }
        return value;
    }

    public static boolean byListRegex(String listOfTags, String[] lineToRead, Field fieldSearch) {
        ArrayList<Boolean> listOfPatterns = new ArrayList<>();
        for (String terms : listOfTags.split(",")) {
            Pattern pattern = Pattern.compile(".*" + terms + ".*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(lineToRead[fieldSearch.ordinal()]);
            listOfPatterns.add(matcher.matches());
        }
        return listOfPatterns.stream().anyMatch(item -> item == Boolean.TRUE);

    }
}
