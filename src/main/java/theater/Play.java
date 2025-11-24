
package theater;

/**
 * Represents a play performed in the theater.
 */
public class Play {

    /** The name of the play. */
    private final String name;

    /** The type of the play, for example "tragedy" or "comedy". */
    private final String type;

    /**
     * Creates a new play with a name and type.
     *
     * @param name the play's name
     * @param type the play's type
     */
    public Play(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Returns the name of the play.
     *
     * @return play name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the play type.
     *
     * @return play type
     */
    public String getType() {
        return type;
    }
}