class MatchTextLocation {
    private final int lineOffset;
    private final int charOffset;

    public MatchTextLocation(int lineOffset, int charOffset) {
        this.lineOffset = lineOffset;
        this.charOffset = charOffset;
    }
 
    public String toString() {
        return "[lineOffset=" + lineOffset + ", charOffset=" + charOffset + "]";
    }
}
