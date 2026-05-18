import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

interface Vehicle {
    String getMake();
    String getModel();
    int getYear();
}

interface CarVehicle {
    void setNumDoors(int doors);
    int getNumDoors();
    void setFuelType(String fuelType);
    String getFuelType();
}

interface MotorVehicle {
    void setNumWheels(int wheels);
    int getNumWheels();
    void setMotorcycleType(String type);
    String getMotorcycleType();
}

interface TruckVehicle {
    void setCargoCapacity(double capacity);
    double getCargoCapacity();
    void setTransmissionType(String transmission);
    String getTransmissionType();
}

class Car implements Vehicle, CarVehicle {
    private String make;
    private String model;
    private int year;
    private int numDoors;
    private String fuelType;

    public Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    @Override public String getMake() { return make; }
    @Override public String getModel() { return model; }
    @Override public int getYear() { return year; }
    @Override public void setNumDoors(int doors) { this.numDoors = doors; }
    @Override public int getNumDoors() { return numDoors; }
    @Override public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    @Override public String getFuelType() { return fuelType; }
}

class Motorcycle implements Vehicle, MotorVehicle {
    private String make;
    private String model;
    private int year;
    private int numWheels;
    private String motorcycleType;

    public Motorcycle(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    @Override public String getMake() { return make; }
    @Override public String getModel() { return model; }
    @Override public int getYear() { return year; }
    @Override public void setNumWheels(int wheels) { this.numWheels = wheels; }
    @Override public int getNumWheels() { return numWheels; }
    @Override public void setMotorcycleType(String type) { this.motorcycleType = type; }
    @Override public String getMotorcycleType() { return motorcycleType; }
}

class Truck implements Vehicle, TruckVehicle {
    private String make;
    private String model;
    private int year;
    private double cargoCapacity;
    private String transmissionType;

    public Truck(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    @Override public String getMake() { return make; }
    @Override public String getModel() { return model; }
    @Override public int getYear() { return year; }
    @Override public void setCargoCapacity(double capacity) { this.cargoCapacity = capacity; }
    @Override public double getCargoCapacity() { return cargoCapacity; }
    @Override public void setTransmissionType(String transmission) { this.transmissionType = transmission; }
    @Override public String getTransmissionType() { return transmissionType; }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Vehicle> fleet = new ArrayList<>();
        
        System.out.println("=== Car Rental System Internal Menu ===");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a Car");
            System.out.println("2. Add a Motorcycle");
            System.out.println("3. Add a Truck");
            System.out.println("4. Exit and Print Fleet Report");
            System.out.print("Select an option (1-4): ");
            
            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Please input a number between 1 and 4.");
                scanner.nextLine(); 
                continue;
            }

            if (choice == 4) {
                break;
            }

            if (choice < 1 || choice > 3) {
                System.out.println("Invalid number choice. Try again.");
                continue;
            }

            System.out.print("Enter Make: ");
            String make = scanner.nextLine().trim();
            System.out.print("Enter Model: ");
            String model = scanner.nextLine().trim();
            
            int year = 0;
            while (true) {
                System.out.print("Enter Year: ");
                try {
                    year = scanner.nextInt();
                    scanner.nextLine();
                    if (year < 1886 || year > 2027) {
                        System.out.println("Enter a real year value.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Please use numbers for the year.");
                    scanner.nextLine();
                }
            }

            if (choice == 1) {
                Car car = new Car(make, model, year);
                while (true) {
                    System.out.print("Enter Number of Doors: ");
                    try {
                        int doors = scanner.nextInt();
                        scanner.nextLine();
                        if (doors <= 0) {
                            System.out.println("Doors must be a positive number.");
                            continue;
                        }
                        car.setNumDoors(doors);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input format.");
                        scanner.nextLine();
                    }
                }
                while (true) {
                    System.out.print("Enter Fuel Type (Petrol, Diesel, Electric): ");
                    String fuel = scanner.nextLine().trim();
                    if (fuel.equalsIgnoreCase("Petrol") || fuel.equalsIgnoreCase("Diesel") || fuel.equalsIgnoreCase("Electric")) {
                        car.setFuelType(fuel);
                        break;
                    }
                    System.out.println("Please pick Petrol, Diesel, or Electric.");
                }
                fleet.add(car);
                
            } else if (choice == 2) {
                Motorcycle moto = new Motorcycle(make, model, year);
                while (true) {
                    System.out.print("Enter Number of Wheels: ");
                    try {
                        int wheels = scanner.nextInt();
                        scanner.nextLine();
                        if (wheels <= 0) {
                            System.out.println("Wheels must be a positive number.");
                            continue;
                        }
                        moto.setNumWheels(wheels);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input format.");
                        scanner.nextLine();
                    }
                }
                while (true) {
                    System.out.print("Enter Motorcycle Type (Sport, Cruiser, Off-road): ");
                    String type = scanner.nextLine().trim();
                    if (type.equalsIgnoreCase("Sport") || type.equalsIgnoreCase("Cruiser") || type.equalsIgnoreCase("Off-road")) {
                        moto.setMotorcycleType(type);
                        break;
                    }
                    System.out.println("Please pick Sport, Cruiser, or Off-road.");
                }
                fleet.add(moto);
                
            } else if (choice == 3) {
                Truck truck = new Truck(make, model, year);
                while (true) {
                    System.out.print("Enter Cargo Capacity (Tons): ");
                    try {
                        double cap = scanner.nextDouble();
                        scanner.nextLine();
                        if (cap <= 0) {
                            System.out.println("Capacity must be a positive number.");
                            continue;
                        }
                        truck.setCargoCapacity(cap);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input format.");
                        scanner.nextLine();
                    }
                }
                while (true) {
                    System.out.print("Enter Transmission Type (Manual, Automatic): ");
                    String trans = scanner.nextLine().trim();
                    if (trans.equalsIgnoreCase("Manual") || trans.equalsIgnoreCase("Automatic")) {
                        truck.setTransmissionType(trans);
                        break;
                    }
                    System.out.println("Please pick Manual or Automatic.");
                }
                fleet.add(truck);
            }
            System.out.println("Vehicle recorded successfully.");
        }

        System.out.println("\n=== Current Rental Fleet Inventory ===");
        for (Vehicle v : fleet) {
            System.out.println("Classification: " + v.getClass().getSimpleName());
            System.out.println("Make and Model: " + v.getMake() + " " + v.getModel());
            System.out.println("Year: " + v.getYear());
            
            if (v instanceof Car) {
                Car c = (Car) v;
                System.out.println("Doors: " + c.getNumDoors());
                System.out.println("Fuel: " + c.getFuelType());
            } else if (v instanceof Motorcycle) {
                Motorcycle m = (Motorcycle) v;
                System.out.println("Wheels: " + m.getNumWheels());
                System.out.println("Type: " + m.getMotorcycleType());
            } else if (v instanceof Truck) {
                Truck t = (Truck) v;
                System.out.println("Cargo Capacity: " + t.getCargoCapacity() + " Tons");
                System.out.println("Transmission: " + t.getTransmissionType());
            }
            System.out.println("====================================");
        }
        scanner.close();
    }
}