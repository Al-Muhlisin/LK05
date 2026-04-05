import java.util.Scanner;

public class MainSimulation {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            PatientProfileV1 patientV1 = new PatientProfileV1(
                "P001", "Budi Santoso", "Hipertensi", "3275019988776655", Confidential.RESTRICTED
            );

            PatientProfileV2 patientV2 = new PatientProfileV2(
                "P002", "Siti Aminah", 42, "Diabetes Tipe 2", "Seafood", "081234567890", Confidential.SECRET
            );

            IntegrationGateway<PatientProfileV1> gatewayV1 = new IntegrationGateway<>(patientV1);
            IntegrationGateway<PatientProfileV2> gatewayV2 = new IntegrationGateway<>(patientV2);

            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Pilih menu: ");
                String choice = readLineOrNull(scanner);
                if (choice == null) {
                    System.out.println("\nInput berakhir. Program selesai.");
                    break;
                }

                switch (choice) {
                    case "1" -> requestData(scanner, gatewayV1, "P001");
                    case "2" -> requestData(scanner, gatewayV2, "P002");
                    case "3" -> runAutoDemo();
                    case "0" -> {
                        running = false;
                        System.out.println("Program selesai.");
                    }
                    default -> System.out.println("Pilihan tidak valid. Coba lagi.\n");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("====================================");
        System.out.println(" MediGuard Integration Gateway LK05 ");
        System.out.println("====================================");
        System.out.println("1. Request data PatientProfile V1");
        System.out.println("2. Request data PatientProfile V2");
        System.out.println("3. Demo otomatis (akses rendah vs tinggi)");
        System.out.println("0. Keluar");
    }

    private static <T extends MedicalRecord & Versioned & Confidential>
    void requestData(Scanner scanner, IntegrationGateway<T> gateway, String expectedPatientId) {
        System.out.print("Masukkan patientId: ");
        String patientId = readLineOrNull(scanner);
        if (patientId == null) {
            System.out.println("Input patientId tidak tersedia.");
            return;
        }

        System.out.print("Masukkan level clearance requester (angka): ");
        int level = readInt(scanner);

        SecureResponse<T> response = gateway.fetchData(patientId, level);
        System.out.println("--- HASIL RESPONSE ---");
        System.out.println(response);

        if (!patientId.equals(expectedPatientId)) {
            System.out.println("Catatan: Gunakan patientId yang benar: " + expectedPatientId);
        }

        System.out.println();
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            String raw = readLineOrNull(scanner);
            if (raw == null) {
                return 0;
            }
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.print("Input harus angka. Ulangi: ");
            }
        }
    }

    private static String readLineOrNull(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine();
    }

    private static void runAutoDemo() {
        System.out.println("\n===== DEMO OTOMATIS =====");

        PatientProfileV1 lowV1Data = new PatientProfileV1(
            "P001", "Budi Santoso", "Hipertensi", "3275019988776655", Confidential.RESTRICTED
        );
        IntegrationGateway<PatientProfileV1> lowV1Gateway = new IntegrationGateway<>(lowV1Data);
        SecureResponse<PatientProfileV1> lowV1 = lowV1Gateway.fetchData("P001", 1);
        System.out.println("[V1 - Akses Rendah]");
        System.out.println(lowV1);

        PatientProfileV1 highV1Data = new PatientProfileV1(
            "P001", "Budi Santoso", "Hipertensi", "3275019988776655", Confidential.RESTRICTED
        );
        IntegrationGateway<PatientProfileV1> highV1Gateway = new IntegrationGateway<>(highV1Data);
        SecureResponse<PatientProfileV1> highV1 = highV1Gateway.fetchData("P001", 4);
        System.out.println("\n[V1 - Akses Tinggi]");
        System.out.println(highV1);

        PatientProfileV2 lowV2Data = new PatientProfileV2(
            "P002", "Siti Aminah", 42, "Diabetes Tipe 2", "Seafood", "081234567890", Confidential.SECRET
        );
        IntegrationGateway<PatientProfileV2> lowV2Gateway = new IntegrationGateway<>(lowV2Data);
        SecureResponse<PatientProfileV2> lowV2 = lowV2Gateway.fetchData("P002", 2);
        System.out.println("\n[V2 - Akses Rendah]");
        System.out.println(lowV2);

        PatientProfileV2 highV2Data = new PatientProfileV2(
            "P002", "Siti Aminah", 42, "Diabetes Tipe 2", "Seafood", "081234567890", Confidential.SECRET
        );
        IntegrationGateway<PatientProfileV2> highV2Gateway = new IntegrationGateway<>(highV2Data);
        SecureResponse<PatientProfileV2> highV2 = highV2Gateway.fetchData("P002", 7);
        System.out.println("\n[V2 - Akses Tinggi]");
        System.out.println(highV2);

        System.out.println("=========================\n");
    }
}
