import java.util.Arrays;
import java.util.List;
import java.util.*;
import java.io.*; 

public class Main {
    private static final int linePerPart = 1000;
    private static String filePath = "./input/text.txt";
    private static String pathToKeys = "./input/keys.txt";

    public static void main(String s[]) {
        int noOfThreads = Runtime.getRuntime().availableProcessors();
        List<String> keysToSearch = getKeys(pathToKeys); 
        ConcurrentTextProcessor ctp = new ConcurrentTextProcessor(filePath, keysToSearch, linePerPart);
        Aggregator aggregator = ctp.processText(noOfThreads);
        aggregator.showResult();
    }


    private static List<String> getKeys(String pathToKeys) {  
        // Create a list to store names
        List<String> keysToSearch = new ArrayList<>();

        // Try-with-resources to ensure the file is closed after reading
        try (BufferedReader br = new BufferedReader(new FileReader(pathToKeys))) {
            String name;
            while ((name = br.readLine()) != null) {
                keysToSearch.add(name.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keysToSearch;
    } 

}

 