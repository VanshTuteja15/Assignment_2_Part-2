package appDomain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.EmptyQueueException;
import implementations.MyQueue;
import implementations.MyStack;
import utilities.QueueADT;
import utilities.StackADT;

/**
 * {@code XMLParser} reads an XML document supplied via the command line and
 * checks whether it is syntactically well-formed according to the rules
 * defined in the assignment specification (matching start/end tags, no
 * intercrossing of nested tags, ignored attributes, ignored self-closing
 * tags and processing instructions, and a single root tag).
 *
 * <p>The parsing logic follows "Kitty's XML Parser Algorithm", using a
 * {@link MyStack} of currently open tags and two {@link MyQueue}s
 * ({@code errorQ} and {@code extrasQ}) to reconcile mismatched tags once the
 * whole document has been scanned.</p>
 *
 * <p>This class only relies on the custom {@code utilities}/{@code implementations}
 * data structures for the actual parsing logic; standard Java I/O and regex
 * classes are used to read the file and tokenize tags, which is permitted for
 * the parser component of this assignment.</p>
 *
 * @author Vansh
 * @version 1.0
 */
public class XMLParser
{
    /** Matches any XML tag: an opening angle bracket, any non-bracket characters, a closing angle bracket. */
    private static final Pattern TAG_PATTERN = Pattern.compile( "<[^<>]+>" );

    /**
     * Program entry point. Expects exactly one command line argument: the
     * path to the XML file to parse. Prints the parsing result to the
     * console.
     *
     * @param args Command line arguments; {@code args[0]} must be the path
     *             to the XML file to parse.
     */
    public static void main( String[] args )
    {
        if ( args.length != 1 )
        {
            System.out.println( "Usage: java -jar Parser.jar <path-to-xml-file>" );
            return;
        }

        try
        {
            java.util.List<Tag> tags = scanTags( args[0] );
            MyQueue<Tag> reportQ = parse( tags );

            if ( reportQ.isEmpty() )
            {
                System.out.println( "XML document is constructed correctly." );
            }
            else
            {
                while ( !reportQ.isEmpty() )
                {
                    Tag error = reportQ.dequeue();
                    System.out.println( "Error at " + error.toString() + " is not constructed correctly." );
                }
            }
        }
        catch ( IOException e )
        {
            System.out.println( "Unable to read file: " + args[0] );
        }
        catch ( EmptyQueueException e )
        {
            // Should not occur, since isEmpty() is always checked before dequeue().
            System.out.println( "Unexpected error while parsing the document." );
        }
    }

    /**
     * Reads the file at the given path line by line and extracts every XML
     * tag it contains, in document order, ignoring processing instructions
     * (e.g. {@code <?xml version="1.0"?>}) and self-closing tags
     * (e.g. {@code <tag/>}), both of which require no further handling.
     *
     * @param filePath The path to the XML file to scan.
     * @return A list of {@link Tag} objects for every start/end tag found,
     *         in the order they appear in the document.
     * @throws IOException If the file cannot be read.
     */
    private static java.util.List<Tag> scanTags( String filePath ) throws IOException
    {
        java.util.List<Tag> tags = new java.util.ArrayList<>();

        try ( BufferedReader reader = new BufferedReader( new FileReader( filePath ) ) )
        {
            String line;
            int lineNumber = 0;

            while ( ( line = reader.readLine() ) != null )
            {
                lineNumber++;
                Matcher matcher = TAG_PATTERN.matcher( line );

                while ( matcher.find() )
                {
                    String rawText = matcher.group();

                    // Processing instructions, e.g. <?xml version="1.0"?>, are ignored entirely.
                    if ( rawText.startsWith( "<?" ) )
                        continue;

                    // Self-closing tags, e.g. <tag/>, require no closing tag and are ignored.
                    if ( rawText.endsWith( "/>" ) )
                        continue;

                    if ( rawText.startsWith( "</" ) )
                    {
                        String name = extractName( rawText, 2 );
                        tags.add( new Tag( name, rawText, lineNumber, Tag.Type.END ) );
                    }
                    else
                    {
                        String name = extractName( rawText, 1 );
                        tags.add( new Tag( name, rawText, lineNumber, Tag.Type.START ) );
                    }
                }
            }
        }

        return tags;
    }

    /**
     * Extracts the tag name from a raw tag string, ignoring any attributes
     * and the surrounding angle brackets.
     *
     * @param rawText   The raw tag text, e.g. {@code "<Submission SubmissionID=\"1\">"}.
     * @param prefixLen The number of leading characters to skip (1 for
     *                  {@code "<"}, 2 for {@code "</"}).
     * @return The tag's name, e.g. {@code "Submission"}.
     */
    private static String extractName( String rawText, int prefixLen )
    {
        String body = rawText.substring( prefixLen, rawText.length() - 1 ).trim();
        int spaceIndex = body.indexOf( ' ' );
        if ( spaceIndex != -1 )
            body = body.substring( 0, spaceIndex );
        return body;
    }

    /**
     * Applies Kitty's XML Parser Algorithm to the supplied list of tags and
     * returns the final, ordered list of tags that are not properly
     * constructed.
     *
     * @param tags The list of tags found in the document, in document order.
     * @return A {@link MyQueue} of tags representing every syntax error found,
     *         in the order the errors were determined.
     */
    private static MyQueue<Tag> parse( java.util.List<Tag> tags )
    {
        StackADT<Tag> stack = new MyStack<>();
        QueueADT<Tag> errorQ = new MyQueue<>();
        QueueADT<Tag> extrasQ = new MyQueue<>();
        MyQueue<Tag> reportQ = new MyQueue<>();

        try
        {
            for ( Tag tag : tags )
            {
                if ( tag.getType() == Tag.Type.START )
                {
                    stack.push( tag );
                    continue;
                }

                // tag is an END tag from here on.
                if ( !stack.isEmpty() && stack.peek().getName().equals( tag.getName() ) )
                {
                    // Matches the top of the stack: properly nested and closed.
                    stack.pop();
                }
                else if ( !errorQ.isEmpty() && errorQ.peek().getName().equals( tag.getName() ) )
                {
                    // Matches the head of errorQ: this closes an already-flagged tag.
                    errorQ.dequeue();
                }
                else if ( stack.isEmpty() )
                {
                    // No open tags at all: this end tag has no possible match.
                    // It has not been reported yet; that happens during reconciliation.
                    errorQ.enqueue( tag );
                }
                else if ( stack.contains( tag ) )
                {
                    // A matching start tag exists further down the stack: everything
                    // popped along the way is out of order (intercrossing tags).
                    // These are reported as errors immediately.
                    while ( !stack.peek().getName().equals( tag.getName() ) )
                    {
                        Tag popped = stack.pop();
                        popped.markReported();
                        errorQ.enqueue( popped );
                        reportQ.enqueue( popped );
                    }
                    // Consume the actual match; it is now considered (badly) closed.
                    stack.pop();
                }
                else
                {
                    // No matching start tag anywhere currently open.
                    // Not reported yet; handled during reconciliation.
                    extrasQ.enqueue( tag );
                }
            }

            // Any tags still open at EOF were never closed. Not reported yet;
            // handled during reconciliation below.
            while ( !stack.isEmpty() )
            {
                errorQ.enqueue( stack.pop() );
            }

            // Reconcile any tags left in errorQ against extrasQ. Anything already
            // reported during the main loop is discarded here (not re-printed);
            // everything else is genuinely new and gets reported now.
            while ( !errorQ.isEmpty() || !extrasQ.isEmpty() )
            {
                if ( errorQ.isEmpty() )
                {
                    reportQ.enqueue( extrasQ.dequeue() );
                }
                else if ( extrasQ.isEmpty() )
                {
                    Tag t = errorQ.dequeue();
                    if ( !t.isReported() )
                        reportQ.enqueue( t );
                }
                else if ( !errorQ.peek().getName().equals( extrasQ.peek().getName() ) )
                {
                    Tag t = errorQ.dequeue();
                    if ( !t.isReported() )
                        reportQ.enqueue( t );
                }
                else
                {
                    errorQ.dequeue();
                    extrasQ.dequeue();
                }
            }
        }
        catch ( java.util.EmptyStackException | EmptyQueueException e )
        {
            // isEmpty() is always checked before pop()/dequeue() above, so this
            // should never actually be reached.
        }

        return reportQ;
    }
}
