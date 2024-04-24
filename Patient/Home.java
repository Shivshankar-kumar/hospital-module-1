import java.util.Scanner;

public class Home {
    static void start() {
        System.out.println("\n\n--------------------------------------------------\n");
        System.out.println("----               1. Patient    ------");
        System.out.println("----               2. Doctor     ------");
        System.out.println("----               3. Admin      ------");
        System.out.println("----               4. Exit       ------");
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println(">>>>>>>>> ");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        switch (choice) {
            case 1: {
                PatientRepository patient = new PatientRepository();
                patient.callPatient();
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                System.exit(0);
                break;
            }
        }

        // input.close();
    }

    public static void main(String[] args) {
        start();
    }
}
