public class PatientProfileV1 implements MedicalRecord, Versioned, Confidential {
    private final String patientId;
    private final String fullName;
    private final String insuranceNumber;
    private final int securityLevel;

    private String visibleName;
    private String visibleDiagnosis;
    private String visibleInsuranceNumber;
    private boolean masked;

    public PatientProfileV1(String patientId, String fullName, String diagnosis,
                            String insuranceNumber, int securityLevel) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.insuranceNumber = insuranceNumber;
        this.securityLevel = securityLevel;

        this.visibleName = fullName;
        this.visibleDiagnosis = diagnosis;
        this.visibleInsuranceNumber = insuranceNumber;
        this.masked = false;
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public int getSecurityLevel() {
        return securityLevel;
    }

    @Override
    public void maskSensitiveData() {
        if (masked) {
            return;
        }

        this.visibleName = maskName(fullName);
        this.visibleDiagnosis = "***REDACTED***";
        this.visibleInsuranceNumber = maskInsuranceNumber(insuranceNumber);
        this.masked = true;
    }

    @Override
    public String getRecordSummary() {
        return "[V" + getVersion() + "] "
                + "PatientID=" + patientId
                + ", Nama=" + visibleName
                + ", Diagnosa=" + visibleDiagnosis
                + ", NoAsuransi=" + visibleInsuranceNumber;
    }

    @Override
    public String toString() {
        return getRecordSummary();
    }

    private String maskName(String name) {
        if (name == null || name.length() <= 1) {
            return "*";
        }
        return name.charAt(0) + "***";
    }

    private String maskInsuranceNumber(String number) {
        if (number == null || number.length() <= 4) {
            return "****";
        }
        String suffix = number.substring(number.length() - 4);
        return "****-****-" + suffix;
    }
}
