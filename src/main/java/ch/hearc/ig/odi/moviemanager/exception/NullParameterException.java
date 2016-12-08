
package ch.hearc.ig.odi.moviemanager.exception;

/**
 *
 * @author dardan.kastrati
 */
public class NullParameterException extends Exception{
    /**
     * Creates a new instance of <code>IncorrectStateException</code> without
     * detail message.
     */
    public NullParameterException() {
        
    }

    /**
     * Constructs an instance of <code>IncorrectStateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NullParameterException(String msg) {
        super(msg);
    }
}
