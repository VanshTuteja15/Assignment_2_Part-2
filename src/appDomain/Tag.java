package appDomain;

/**
 * {@code Tag} represents a single XML tag occurrence found while scanning a
 * document. It records the tag's name (used to match opening and closing
 * tags), the exact raw text of the tag as it appeared in the file (used for
 * error reporting), the line number on which it was found, and whether it is
 * a start tag or an end tag.
 *
 * @author Vansh
 * @version 1.0
 */
public class Tag
{
    /**
     * Enumerates the two kinds of tags this parser deals with. Self-closing
     * tags and processing instructions are handled before a {@code Tag}
     * object is ever created for them, so they do not need their own type.
     */
    public enum Type
    {
        /** An opening tag, e.g. {@code <Submission>}. */
        START,
        /** A closing tag, e.g. {@code </Submission>}. */
        END
    }

    /** The tag's name, ignoring any attributes (used for matching). */
    private final String name;

    /** The exact raw text of the tag as it appeared in the document. */
    private final String rawText;

    /** The 1-based line number on which this tag was found. */
    private final int line;

    /** Whether this is a start tag or an end tag. */
    private final Type type;

    /**
     * Whether this tag has already been printed as an error while it was
     * popped off the stack during the main scanning loop (i.e. as part of
     * an intercrossing/out-of-order tag pair). Tags flagged this way must
     * not be reported a second time during the final errorQ/extrasQ
     * reconciliation step, since they were already reported once.
     */
    private boolean reported;

    /**
     * Constructs a new {@code Tag}.
     *
     * @param name    The tag's name, without attributes or angle brackets.
     * @param rawText The exact raw text of the tag, as it appeared in the document.
     * @param line    The 1-based line number on which the tag was found.
     * @param type    Whether the tag is a start tag or an end tag.
     */
    public Tag( String name, String rawText, int line, Type type )
    {
        this.name = name;
        this.rawText = rawText;
        this.line = line;
        this.type = type;
    }

    /**
     * Returns the tag's name, ignoring attributes.
     *
     * @return The tag's name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the exact raw text of the tag as it appeared in the document.
     *
     * @return The raw tag text.
     */
    public String getRawText()
    {
        return rawText;
    }

    /**
     * Returns the 1-based line number on which this tag was found.
     *
     * @return The line number.
     */
    public int getLine()
    {
        return line;
    }

    /**
     * Returns whether this tag is a start tag or an end tag.
     *
     * @return The type of this tag.
     */
    public Type getType()
    {
        return type;
    }

    /**
     * Returns whether this tag has already been printed as an error.
     *
     * @return {@code true} if this tag was already reported.
     */
    public boolean isReported()
    {
        return reported;
    }

    /**
     * Marks this tag as having already been printed as an error, so it is
     * not reported a second time during final queue reconciliation.
     */
    public void markReported()
    {
        this.reported = true;
    }

    /**
     * Two tags are considered equal, for the purposes of the parsing
     * algorithm, if they have the same name. Case sensitivity is preserved
     * since XML tags are case-sensitive.
     *
     * @param obj The object to compare against.
     * @return {@code true} if {@code obj} is a {@code Tag} with the same name.
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( !( obj instanceof Tag ) )
            return false;
        return this.name.equals( ( (Tag) obj ).name );
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    /**
     * Returns a string representation of this tag, formatted for use in the
     * parser's error output, e.g. {@code "line: 8 <i>"}.
     *
     * @return A formatted string describing this tag's line and raw text.
     */
    @Override
    public String toString()
    {
        return "line: " + line + " " + rawText;
    }
}
