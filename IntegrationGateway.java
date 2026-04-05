public class IntegrationGateway<T extends MedicalRecord & Versioned & Confidential> {
    private final T data;

    public IntegrationGateway(T data) {
        this.data = data;
    }

    public SecureResponse<T> fetchData(String patientId, int requesterClearanceLevel) {
        if (!data.getPatientId().equals(patientId)) {
            return new SecureResponse<>(
                    data,
                    false,
                    "Data tidak ditemukan untuk patientId: " + patientId
            );
        }

        int minimumLevel = data.getSecurityLevel();
        if (requesterClearanceLevel < minimumLevel) {
            data.maskSensitiveData();
            return new SecureResponse<>(
                    data,
                    false,
                    "Akses rendah: data versi V" + data.getVersion() + " dimasking"
            );
        }

        return new SecureResponse<>(
                data,
                true,
                "Akses tinggi: data versi V" + data.getVersion() + " ditampilkan penuh"
        );
    }
}
