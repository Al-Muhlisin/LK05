public interface Confidential {
    int PUBLIC = 1;
    int RESTRICTED = 2;
    int SECRET = 3;

    int getSecurityLevel();
    void maskSensitiveData();
}
