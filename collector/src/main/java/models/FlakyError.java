package models;

public class FlakyError extends Error {
    StackTrace stackTrace;

    public StackTrace getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(StackTrace stackTrace) {
        this.stackTrace = stackTrace;
    }
}
