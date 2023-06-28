package org.projectsw.Exceptions;

/**
 * The UnselectableTileException is an exception that is thrown when attempting to select an unselectable tile.
 */
public class UnselectableTileException extends Exception{

    /**
     * Constructs a new UnselectableTileException with no detail message.
     */
    public UnselectableTileException(){ super(); }

    /**
     * Constructs a new UnselectableTileException with the specified detail message.
     * @param s the detail message
     */
    public UnselectableTileException(String s){ super(s); }
}
