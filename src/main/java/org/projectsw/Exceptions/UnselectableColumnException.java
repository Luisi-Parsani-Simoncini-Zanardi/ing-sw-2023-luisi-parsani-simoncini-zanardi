package org.projectsw.Exceptions;

/**
 * The UnselectableColumnException is an exception that is thrown when attempting to select an unselectable column.
 */
public class UnselectableColumnException extends Exception{

    /**
     * Constructs a new UnselectableColumnException with no detail message.
     */
    public UnselectableColumnException(){ super(); }

    /**
     * Constructs a new UnselectableColumnException with the specified detail message.
     * @param s the detail message
     */
    public UnselectableColumnException(String s){ super(s); }
}
