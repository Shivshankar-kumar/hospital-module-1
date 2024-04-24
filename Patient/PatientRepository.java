
/*-------------------------------------------------- */
// patient class to provide optioin

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

class PatientRepository {
    Scanner sc = new Scanner(System.in);
    List<PatientRegistrationData> patients = new ArrayList<>();

    public PatientRepository() {
        String cwd = System.getProperty("user.dir");
        File database = new File(cwd + "/database");

        if (!database.exists()) {
            return;
        }

        for (File file : database.listFiles()) {
            String data = readFile(file);
            
            if (data != null) {
                PatientRegistrationData patientData = new PatientRegistrationData();
                patientData.fromDBString(data);
                patients.add(patientData);
            }
        }
    }

    private String readFile(File file) {
        try (InputStream is = new FileInputStream(file)) {
            StringBuilder sb = new StringBuilder();

            byte[] buffer = new byte[1024];

            while (is.read(buffer) > 0) {
                sb.append(new String(buffer));
            }

            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read data from " + file.getName());
        }
    }

    private void writeToFile(File file, String data) {
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callPatient() {
        System.out.println("       1. Register      ");
        System.out.println("       2. Login         ");
        System.out.println("       3. Exit          ");
        int choice = sc.nextInt();
        if (choice == 1) {
            registerPatient();
            callPatient();
        }
        if (choice == 2) {
            loginPatient();
        }
        if (choice == 3) {
            Home callHome = new Home();
            callHome.start();
        }
    }

    // Register method for patient
    void registerPatient() {
        PatientRegistrationData newRegistration = new PatientRegistrationData();

        System.out.println("\n-------------------------------------------------");

        System.out.print(" Mobile: ");
        String mobile = sc.next();
        System.out.print(" Email: ");
        String email = sc.next();
        System.out.print(" City: ");
        String city = sc.next();

        newRegistration.name = inputRegistrationUsername();
        newRegistration.password = inputRegistrationPassword();
        newRegistration.mobile = mobile;
        newRegistration.email = email;
        newRegistration.city = city;

        System.out.println("\n" + newRegistration.toString());
        System.out.println(
                String.format("          Registration of %s is successful!              ", newRegistration.name));
        System.out.println("\n-------------------------------------------------\n");

        patients.add(newRegistration);

        String rawData = newRegistration.toDBString();

        String cwd = System.getProperty("user.dir");
        File database = new File(cwd + "/database");

        if(!database.exists()){
            database.mkdir();
        }

        writeToFile(new File(database, newRegistration.name + ".txt"), rawData);
    }

    String inputRegistrationUsername() {
        System.out.print("    User Name:       ");
        String userName = sc.next();

        if (!Pattern.compile("[a-zA-Z]+").matcher(userName).matches()) {
            System.out.print(" Please enter a valid name. Must contain only strings\n");
            userName = inputRegistrationUsername();
        }

        PatientRegistrationData foundRegistration = getUserData(userName);

        if (foundRegistration != null) {
            System.out.print(" User already exists\n");
            userName = inputRegistrationUsername();
        }

        return userName;
    }

    String inputRegistrationPassword() {
        System.out.print("    Create Password:   ");
        String password = sc.next();

        if (password.length() < 8) {
            System.out.print(" Password must be at least 8 characters long.\n");
            password = inputRegistrationPassword();
        }

        return password;
    }

    void loginPatient() {
        PatientRegistrationData foundRegistration = inputLoginUsername();
        loginWithPassword(foundRegistration);
    }

    PatientRegistrationData inputLoginUsername() {
        System.out.print("    User Name:       ");
        String username = sc.next();

        PatientRegistrationData foundRegistration = getUserData(username);

        if (foundRegistration == null) {
            System.out.println("\n      patient not registered");
            return inputLoginUsername();
        }

        return foundRegistration;
    }

    void loginWithPassword(PatientRegistrationData foundRegistration) {
        System.out.print("    Password:    ");
        String password = sc.next();

        if (password.equals(foundRegistration.password)) {
            System.out.println("\n        Login Successful!            ");
            callPatient();
        } else {
            System.out.println("\n         Incorrect Password");
            loginWithPassword(foundRegistration);
        }
    }

    PatientRegistrationData getUserData(String username) {
        PatientRegistrationData foundRegistration = null;
        for (PatientRegistrationData data : patients) {
            if (username.equals(data.name)) {
                foundRegistration = data;
                break;
            }
        }

        return foundRegistration;
    }
}
/*--------------------------------------------------------- */