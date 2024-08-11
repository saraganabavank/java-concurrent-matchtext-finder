Project structure

   - /input
         - big.txt   # input text file
   - Main.java       # program start
   - MatchLocation.java   # Class to store matching text at which character and line 
   - Aggregator.java      # Class to merge the result produced by different threads
   - ConcurrentTextProcessor.java # Read file and divide and allocate to different threads
   - PatternMatcher.java  # Compare text and with keys and locate the line of appearance and characters 


How to complile and run ?
    - javac Main.java   #complile
    - java Main         #run