public class SecureResponse<T extends MedicalRecord & Confidential> {
    private final T data;
    private final boolean fullyAccessible;
    private final String message;

    public SecureResponse(T data, boolean fullyAccessible, String message) {
        this.data = data;
        this.fullyAccessible = fullyAccessible;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public boolean isFullyAccessible() {
        return fullyAccessible;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        String status = fullyAccessible ? "AMAN (FULL ACCESS)" : "TERBATAS (MASKED)";
        return "Status: " + status + "\n"
                + "Message: " + message + "\n"
                + "Data: " + data.getRecordSummary();
    }
}
