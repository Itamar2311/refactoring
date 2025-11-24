
package theater;

/**
 * Represents a single performance of a play.
 */
public class Performance {

    private final String playID;
    private final int audience;

    /**
     * Creates a performance record.
     *
     * @param playID   the ID of the play
     * @param audience the audience size
     */
    public Performance(String playID, int audience) {
        this.playID = playID;
        this.audience = audience;
    }

    /**
     * Returns the play ID.
     *
     * @return the play ID
     */
    public String getPlayID() {
        return playID;
    }

    /**
     * Returns the audience size.
     *
     * @return audience size
     */
    public int getAudience() {
        return audience;
    }
}
