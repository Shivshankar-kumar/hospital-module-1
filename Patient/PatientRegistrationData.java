import java.util.Arrays;
import java.util.Scanner;

class PatientRegistrationData {
    Scanner sc = new Scanner(System.in);
    public String name;
    public String password;
    public String mobile;
    public String email;
    public String city;

    @Override
    public String toString() {
        return "[UserName: " + name + ", " + "mobile: " + mobile + ", " + "Password: " + password + ", " + "Email: "
                + " , " + email + " , " + "City: " + city + " " + "]";
    }

    String toDBString() {
        String str = "";
        str += name + "\n";
        str += password + "\n";
        str += mobile + "\n";
        str += email + "\n";
        str += city;
        return str;
    }

    void fromDBString(String str) {
        try {
            String[] data = str.split("\n");

            name = data[0].trim();
            password = data[1].trim();
            mobile = data[2].trim();
            email = data[3].trim();
            city = data[4].trim();
        } catch (Exception e) {
        }
    }
}