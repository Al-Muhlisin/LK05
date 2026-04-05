public class PatientProfileV2 implements MedicalRecord, Versioned, Confidential {
    private final String patientId;
    private final String fullName;
    private final int age;
    private final String emergencyContact;
    private final int securityLevel;

    private String visibleName;
    private String visibleDiagnosis;
    private String visibleAllergy;
    private String visibleEmergencyContact;
    private boolean masked;

    public PatientProfileV2(String patientId, String fullName, int age, String diagnosis,
                            String allergy, String emergencyContact, int securityLevel) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.age = age;
        this.emergencyContact = emergencyContact;
        this.securityLevel = securityLevel;

        this.visibleName = fullName;
        this.visibleDiagnosis = diagnosis;
        this.visibleAllergy = allergy;
        this.visibleEmergencyContact = emergencyContact;
        this.masked = false;
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public int getVersion() {
        return 2;
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
        this.visibleAllergy = "***REDACTED***";
        this.visibleEmergencyContact = maskPhone(emergencyContact);
        this.masked = true;
    }

    @Override
    public String getRecordSummary() {
        return "[V" + getVersion() + "] "
                + "PatientID=" + patientId
                + ", Nama=" + visibleName
                + ", Umur=" + age
                + ", Diagnosa=" + visibleDiagnosis
                + ", Alergi=" + visibleAllergy
                + ", KontakDarurat=" + visibleEmergencyContact;
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

    private String maskPhone(String phone) {
        if (phone == null || phone.length() <= 4) {
            return "****";
        }
        String suffix = phone.substring(phone.length() - 4);
        return "***-***-" + suffix;
    }
}
