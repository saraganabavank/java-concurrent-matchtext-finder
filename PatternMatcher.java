import java.util.*;
import java.util.regex.*;
import java.util.concurrent.*;


class PatternMatcher implements Callable<Map<String, List<MatchTextLocation>>> {
    private final String text;
    private final int lineOffset;
    private final Map<String,Pattern> patterns;

    public PatternMatcher(String text, int lineOffset,Map<String,Pattern> patterns) {
        this.text = text;
        this.lineOffset = lineOffset;
        this.patterns = patterns;
    }

    @Override
    public Map<String, List<MatchTextLocation>> call() {
        Map<String, List<MatchTextLocation>> result = new HashMap<>();
        for (Map.Entry<String, Pattern> entry : patterns.entrySet()) {
            String key = entry.getKey();
            Pattern pattern = entry.getValue(); 
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                int lineNum = lineOffset + getLineNumber(text, matcher.start());
                int charOffset = matcher.start();
                result.computeIfAbsent(key, k -> new ArrayList<>())
                      .add(new MatchTextLocation(lineNum, charOffset));
            }
        }
        return result;
    }

    private int getLineNumber(String text, int charOffset) {
        return (int) text.substring(0, charOffset).chars().filter(c -> c == '\n').count();
    }
}
