package cis.exception;

/**
 * Base exception class for all Mechanic Tracker / Inventory System exceptions.
 * All other custom exceptions should extend this class.
 *
 * Author: Hamza
 * Since: 2025-01
 */
public class MechanicTrackerException extends RuntimeException {
    public MechanicTrackerException(String message) {
        super(message);
    }
}
