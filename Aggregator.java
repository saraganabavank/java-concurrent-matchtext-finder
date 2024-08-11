import java.util.*;

class Aggregator {
    public final Map<String, List<MatchTextLocation>> aggregatedResults = new HashMap<>();

    public void aggregate(Map<String, List<MatchTextLocation>> partialResults) {
        for (Map.Entry<String, List<MatchTextLocation>> entry : partialResults.entrySet()) {
            aggregatedResults.computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                             .addAll(entry.getValue());
        }
    }

    public void showResult() {
        for (Map.Entry<String, List<MatchTextLocation>> entry : aggregatedResults.entrySet()) {
            System.out.println(entry.getKey() + " --> " + entry.getValue());
        }
    }
}
