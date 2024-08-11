import java.io.*; 
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern; 

public class ConcurrentTextProcessor { 
    private String filePath;
    private Map<String,Pattern> patterns;
    private int noOfLines;
    public ConcurrentTextProcessor(String filePath, List<String> keysToSearch,int noOfLines){
        this.filePath = filePath; 
        this.noOfLines = noOfLines;
        this.patterns = new HashMap<>();

         for (String key : keysToSearch) {
            //Regex pattern building
            patterns.put(key,Pattern.compile("\\b" + key + "\\b")); 
         }
    }

    public Aggregator  processText(int noOfThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(noOfThreads); // Create a thread pool
        List<Future<Map<String, List<MatchTextLocation>>>> futures = new ArrayList<>();
        Aggregator aggregator = new Aggregator();

        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
            StringBuilder part = new StringBuilder();
            String line;
            int lineOffset = 0; 
            int currentLineCount = 0;

            while ((line = reader.readLine()) != null) {
                part.append(line).append("\n");
                currentLineCount++; 

                if (currentLineCount == this.noOfLines) {
                    futures.add(executor.submit(new PatternMatcher(part.toString(), lineOffset, this.patterns)));
                    part = new StringBuilder();
                    lineOffset += currentLineCount;
                    currentLineCount = 0;
                }
            }

            // Handle remaining lines
            if (currentLineCount > 0) {
                futures.add(executor.submit(new PatternMatcher(part.toString(), lineOffset, this.patterns)));
            }

            // Wait till all matchers runs on individula threads to finish  and combain results
            for (Future<Map<String, List<MatchTextLocation>>> future : futures) {
                aggregator.aggregate(future.get());
            }
 
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            return aggregator;
        }
    }
}
