package org.projectsw.Exceptions;

/**
 * The MinimumRedeemedPointsException is an exception that is thrown when the redeemed points are below the minimum threshold.
 */
public class MinimumRedeemedPointsException extends Exception{

    /**
     * Constructs a new MinimumRedeemedPointsException with no detail message.
     */
    public MinimumRedeemedPointsException(){ super(); }

    /**
     * Constructs a new MinimumRedeemedPointsException with the specified detail message.
     * @param s the detail message
     */
    public MinimumRedeemedPointsException(String s){ super(s); }
}
