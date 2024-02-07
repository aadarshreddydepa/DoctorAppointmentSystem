package doctor;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DoctorAppointmentScheduler {
    private static List<String> doctors = new ArrayList<>();
    private static List<List<Boolean>> appointmentSlots = new ArrayList<>();
    private static final ZoneId IST_ZONE = ZoneId.of("Asia/Kolkata");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nDoctor Appointment Scheduler");
            System.out.println("1. View Available Doctors");
            System.out.println("2. Schedule an Appointment");
            System.out.println("3. Cancel an Appointment");
            System.out.println("4. Add a Doctor");
            System.out.println("5. View Available Time Slots");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAvailableDoctors();
                    break;
                case 2:
                    scheduleAppointment();
                    break;
                case 3:
                    cancelAppointment();
                    break;
                case 4:
                    addDoctor();
                    break;
                case 5:
                    viewAvailableTimeSlots();
                    break;
                case 6:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeDoctors() {
        doctors.add("Dr. Smith");
        doctors.add("Dr. Johnson");

        for (int i = 0; i < doctors.size(); i++) {
            appointmentSlots.add(new ArrayList<>());
            for (int j = 0; j < 5; j++) {
                appointmentSlots.get(i).add(false);
            }
        }
    }

    private static void viewAvailableDoctors() {
        if (doctors.isEmpty()) {
            initializeDoctors();
        }

        System.out.println("\nAvailable Doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". " + doctors.get(i));
        }
    }

    private static void scheduleAppointment() {
        viewAvailableDoctors();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Select a doctor (enter the number): ");
        int doctorIndex = scanner.nextInt() - 1;

        if (doctorIndex < 0 || doctorIndex >= doctors.size()) {
            System.out.println("Invalid doctor selection.");
            return;
        }

        System.out.print("Select a time slot (1-5): ");
        int timeSlot = scanner.nextInt();

        if (timeSlot < 1 || timeSlot > 5 || appointmentSlots.get(doctorIndex).get(timeSlot - 1)) {
            System.out.println("Invalid or already booked time slot.");
            return;
        }

        appointmentSlots.get(doctorIndex).set(timeSlot - 1, true);
        System.out.println("Appointment scheduled with " + doctors.get(doctorIndex) +
                " at time slot " + formatTimeSlot(timeSlot) + ".");
    }

    private static void cancelAppointment() {
        viewAvailableDoctors();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Select a doctor to cancel the appointment (enter the number): ");
        int doctorIndex = scanner.nextInt() - 1;

        if (doctorIndex < 0 || doctorIndex >= doctors.size()) {
            System.out.println("Invalid doctor selection.");
            return;
        }

        System.out.print("Select the time slot to cancel (1-5): ");
        int timeSlot = scanner.nextInt();

        if (timeSlot < 1 || timeSlot > 5 || !appointmentSlots.get(doctorIndex).get(timeSlot - 1)) {
            System.out.println("Invalid or unbooked time slot.");
            return;
        }

        appointmentSlots.get(doctorIndex).set(timeSlot - 1, false);
        System.out.println("Appointment canceled with " + doctors.get(doctorIndex) +
                " at time slot " + formatTimeSlot(timeSlot) + ".");
    }

    private static void addDoctor() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the new doctor: ");
        String newDoctor = scanner.nextLine();

        doctors.add(newDoctor);
        appointmentSlots.add(new ArrayList<>());

        // Initialize appointment slots for the new doctor
        for (int i = 0; i < 5; i++) {
            appointmentSlots.get(doctors.size() - 1).add(false);
        }

        System.out.println(newDoctor + " has been added as a new doctor.");
    }

    private static void viewAvailableTimeSlots() {
        System.out.println("\nAvailable Time Slots (Indian Standard Time - IST):");

        // Define the time slots for the day
        LocalTime[] timeSlots = {
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0)
        };

        for (int i = 0; i < timeSlots.length; i++) {
            System.out.println((i + 1) + ". " + formatTimeSlot(timeSlots[i]));
        }
    }

    private static String formatTimeSlot(int timeSlot) {
        ZonedDateTime istTime = ZonedDateTime.of(LocalDate.now(), timeSlots[timeSlot - 1], IST_ZONE);
        return istTime.format(timeFormatter);
    }

    private static String formatTimeSlot(LocalTime time) {
        ZonedDateTime istTime = ZonedDateTime.of(LocalDate.now(), time, IST_ZONE);
        return istTime.format(timeFormatter);
    }
                           }
