    package com.example.mauligandhi_comp228lab5;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    import oracle.jdbc.driver.*;
    import oracle.sql.*;

    public class DBUtil {
        private static Connection connection = null;

        public static void dbDisconnect() throws SQLException {
            if(connection != null && !connection.isClosed())
            {
                connection.close();
            }
        }
        private static Connection getConnection() throws SQLException {
            if (connection == null || connection.isClosed()) {
                String dbURL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
                String username = "COMP228_F24_sha_11";
                String password = "password";
                connection = DriverManager.getConnection(dbURL, username, password);
                System.out.println("DB Connected");
            }
            return connection;
        }
        public static void createTable(String tableName, String sql) throws SQLException {
            dropTableIfExists(tableName); // Drop the table first if it exists
            try (Connection conn = getConnection();
                 Statement statement = conn.createStatement()) {
                statement.execute(sql);
                System.out.println("Table: " +  tableName + " created successfully.");
                dbDisconnect();
            }

        }

        public static void dropTableIfExists(String tableName) throws SQLException {
            //dbConnect();
            //String sql = "drop table IF EXISTS " + tableName;
            //statement.execute(sql);
            //System.out.println("Table is dropped");
            //if(statement != null) statement.close();
            //dbDisconnect();

            String dropSQL = "BEGIN " +
                    "   EXECUTE IMMEDIATE 'DROP TABLE " + tableName + "'; " +
                    "EXCEPTION " +
                    "   WHEN OTHERS THEN " +
                    "       IF SQLCODE != -942 THEN " +
                    "           RAISE; " +
                    "       END IF; " +
                    "END;";
            try (Connection conn = getConnection();
                 Statement statement = conn.createStatement()) {
                statement.execute(dropSQL);
                System.out.println("Table '" + tableName + "' dropped if it existed.");
            } catch (SQLException e) {
                System.err.println("Error dropping table '" + tableName + "': " + e.getMessage());
            }
        }

        public static void updateRecord(String tableName, String setClause, String whereClause) throws SQLException {
            String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE " + whereClause;
            try (Connection conn = getConnection();
                 Statement statement = conn.createStatement()) {
                int rowsUpdated = statement.executeUpdate(sql);
                System.out.println(rowsUpdated + " record(s) updated in " + tableName);
            } catch (SQLException e) {
                System.err.println("Error updating record in " + tableName + ": " + e.getMessage());
            }
        }

        public static void deleteRecord(String tableName, String whereClause) throws SQLException {
            String sql = "DELETE FROM " + tableName + " WHERE " + whereClause;
            try (Connection conn = getConnection();
                 Statement statement = conn.createStatement()) {
                int rowsDeleted = statement.executeUpdate(sql);
                System.out.println(rowsDeleted + " record(s) deleted from " + tableName);
            } catch (SQLException e) {
                System.err.println("Error deleting record from " + tableName + ": " + e.getMessage());
            }
        }

        public static void insertData(String sql) throws SQLException {
            try (Connection conn = getConnection();
                 Statement statement = conn.createStatement()) {
                statement.executeUpdate(sql);
                System.out.println("Record inserted: " + sql);
            } catch (SQLException e) {
                System.err.println("Error inserting data: " + e.getMessage());
            }
        }

        public static void dropSequence(String sequenceName) throws SQLException {
            String sql = "DROP SEQUENCE " + sequenceName;
            try (Connection conn = getConnection();
                 Statement statement = conn.createStatement()) {
                statement.execute(sql);
                System.out.println("Sequence '" + sequenceName + "' dropped successfully.");
            } catch (SQLException e) {
                if (e.getErrorCode() == 2289) { // ORA-02289: sequence does not exist
                    System.out.println("Sequence '" + sequenceName + "' does not exist.");
                } else {
                    System.err.println("Error dropping sequence '" + sequenceName + "': " + e.getMessage());
                }
            }
        }

        public static List<Owner> getAllOwners() throws SQLException {
            String sql = "SELECT * FROM Owner";
            List<Owner> ownerList = new ArrayList<>();
            try (Connection conn = getConnection();
                 Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                System.out.println("\nContents of Owner Table:");
                System.out.println("---------------------------------------------------");
                while (resultSet.next()) {
                    int ownerID = resultSet.getInt("ownerID");
                    String name = resultSet.getString("name");
                    String address = resultSet.getString("address");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");

                    Owner owner = new Owner(ownerID, name, address, phone, email);
                    ownerList.add(owner);

                    System.out.println("OwnerID: " + ownerID +
                            ", Name: " + name +
                            ", Address: " + address +
                            ", Phone: " + phone +
                            ", Email: " + email);
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving data from Owner table: " + e.getMessage());
            }
            return ownerList;
        }
        public static List<Car> getAllCars() throws SQLException {
            String sql = "SELECT * FROM Car";
            List<Car> carList = new ArrayList<>();
            try (Connection conn = getConnection();
                 Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                System.out.println("\nContents of Car Table:");
                System.out.println("---------------------------------------------------");
                while (resultSet.next()) {
                    int carID = resultSet.getInt("carID");
                    String make = resultSet.getString("make");
                    String model = resultSet.getString("model");
                    int VIN = resultSet.getInt("VIN");
                    int buildYear = resultSet.getInt("buildYear");
                    String type = resultSet.getString("type");

                    Car car = new Car(carID, make, model, VIN, buildYear, type);
                    carList.add(car);

                    System.out.println("CarID: " + carID +
                            ", Make: " + make +
                            ", Model: " + model +
                            ", VIN: " + VIN +
                            ", Build Year: " + buildYear +
                            ", Type: " + type);
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving data from Car table: " + e.getMessage());
            }
            return carList;
        }
        public static List<Repair> getAllRepairs() throws SQLException {
            String sql = "SELECT * FROM Repair";
            List<Repair> repairList = new ArrayList<>();

            try (Connection conn = getConnection();
                 Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                System.out.println("\nContents of Repair Table:");
                System.out.println("---------------------------------------------------");
                while (resultSet.next()) {
                    int repairID = resultSet.getInt("repairID");
                    int ownerID = resultSet.getInt("ownerID");
                    int carID = resultSet.getInt("carID");
                    Date serviceDate = resultSet.getDate("serviceDate");
                    String description = resultSet.getString("description");
                    int cost = resultSet.getInt("cost");

                    Repair repair = new Repair(repairID, ownerID, carID, serviceDate, description, cost);
                    repairList.add(repair);

                    System.out.println("RepairID: " + repairID +
                            ", OwnerID: " + ownerID +
                            ", CarID: " + carID +
                            ", Service Date: " + serviceDate +
                            ", Description: " + description +
                            ", Cost: " + cost);
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving data from Repair table: " + e.getMessage());
            }
            return repairList;
        }

        public static Owner getOwnerById(int ownerID) throws SQLException {
            String sql = "SELECT * FROM Owner WHERE ownerID = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, ownerID);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String address = resultSet.getString("address");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");

                    return new Owner(ownerID, name, address, phone, email);
                }
            }
            return null; // Return null if no record is found
        }

        public static Car getCarById(int carID) throws SQLException {
            String sql = "SELECT * FROM Car WHERE carID = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, carID);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    String make = resultSet.getString("make");
                    String model = resultSet.getString("model");
                    int VIN = resultSet.getInt("VIN");
                    int buildYear = resultSet.getInt("buildYear");
                    String type = resultSet.getString("type");

                    return new Car(carID, make, model, VIN, buildYear, type);
                }
            }
            return null; // Return null if no record is found
        }

        public static List<Repair> displayRepairsBetweenDates(String startDate, String endDate) throws SQLException {
            String sql = """
        SELECT * FROM Repair
        WHERE serviceDate BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')
        """;
            List<Repair> repairList = new ArrayList<>();
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, startDate);
                pstmt.setString(2, endDate);

                ResultSet resultSet = pstmt.executeQuery();
                System.out.println("\nRepair Reports Between " + startDate + " and " + endDate + ":");
                System.out.println("---------------------------------------------------");
                while (resultSet.next()) {
                    int repairID = resultSet.getInt("repairID");
                    int ownerID = resultSet.getInt("ownerID");
                    int carID = resultSet.getInt("carID");
                    Date serviceDate = resultSet.getDate("serviceDate");
                    String description = resultSet.getString("description");
                    int cost = resultSet.getInt("cost");

                    Repair repair = new Repair(repairID, ownerID, carID, serviceDate, description, cost);
                    repairList.add(repair);

                    System.out.println("RepairID: " + repairID +
                            ", OwnerID: " + ownerID +
                            ", CarID: " + carID +
                            ", Service Date: " + serviceDate +
                            ", Description: " + description +
                            ", Cost: " + cost);
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving repair reports between dates: " + e.getMessage());
            }
            return repairList;
        }

        public static List<Repair> getRepairsByOwnerId(int ownerID) throws SQLException {
            String sql = """
                SELECT * FROM Repair
                WHERE ownerID = ?
                """;
            List<Repair> repairList = new ArrayList<>();
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, ownerID);

                ResultSet resultSet = pstmt.executeQuery();
                System.out.println("\nRepair Reports for User ID " + ownerID + ":");
                System.out.println("---------------------------------------------------");
                while (resultSet.next()) {
                    int repairID = resultSet.getInt("repairID");
                    int carID = resultSet.getInt("carID");
                    Date serviceDate = resultSet.getDate("serviceDate");
                    String description = resultSet.getString("description");
                    int cost = resultSet.getInt("cost");

                    Repair repair = new Repair(repairID, ownerID, carID, serviceDate, description, cost);
                    repairList.add(repair);

                    System.out.println("RepairID: " + repairID +
                            ", CarID: " + carID +
                            ", Service Date: " + serviceDate +
                            ", Description: " + description +
                            ", Cost: " + cost);
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving repair reports for user: " + e.getMessage());
            }
            return repairList;
        }

        public static List<Repair> getRepairsByCarId(int carID) throws SQLException {
            String sql = """
                SELECT * FROM Repair
                WHERE carID = ?
                """;

            List<Repair> repairList = new ArrayList<>();
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, carID);

                ResultSet resultSet = pstmt.executeQuery();
                System.out.println("\nRepair Reports for Car ID " + carID + ":");
                System.out.println("---------------------------------------------------");
                while (resultSet.next()) {
                    int repairID = resultSet.getInt("repairID");
                    int ownerID = resultSet.getInt("ownerID");
                    Date serviceDate = resultSet.getDate("serviceDate");
                    String description = resultSet.getString("description");
                    int cost = resultSet.getInt("cost");

                    Repair repair = new Repair(repairID, ownerID, carID, serviceDate, description, cost);
                    repairList.add(repair);

                    System.out.println("RepairID: " + repairID +
                            ", OwnerID: " + ownerID +
                            ", Service Date: " + serviceDate +
                            ", Description: " + description +
                            ", Cost: " + cost);
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving repair reports for car: " + e.getMessage());
            }

            return repairList;
        }



        public static void main(String[] args) throws SQLException {

            String ownerTableSQL = """
            CREATE TABLE Owner (
                ownerID INT NOT NULL PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                address VARCHAR(200) NOT NULL,
                phone VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL
                )""";

            // SQL for creating the Car table
            String carTableSQL = """
            CREATE TABLE Car (
                carID INT NOT NULL PRIMARY KEY,
                make VARCHAR(100) NOT NULL,
                model VARCHAR(100) NOT NULL,
                VIN INT NOT NULL,
                buildYear INT NOT NULL,
                type VARCHAR(100) NOT NULL
                )""";

            // SQL for creating the Repair table
            String repairTableSQL = """
            CREATE TABLE Repair (
                repairID INT NOT NULL PRIMARY KEY,
                ownerID INT NOT NULL,
                carID INT NOT NULL,
                serviceDate DATE NOT NULL,
                description VARCHAR(200),
                cost INT NOT NULL,
                FOREIGN KEY (ownerID) REFERENCES Owner(ownerID),
                FOREIGN KEY (carID) REFERENCES Car(carID)
            )
            """;
            String repairIdSequenceSQL = """
            CREATE SEQUENCE Repair_Seq
            START WITH 1
            INCREMENT BY 1
            NOCACHE
            """;
            String repairIdTriggerSQL = """
            CREATE OR REPLACE TRIGGER Repair_BI_Trigger
            BEFORE INSERT ON Repair
            FOR EACH ROW
            BEGIN
                :NEW.repairID := Repair_Seq.NEXTVAL;
            END;
            """;


            try {
                dropTableIfExists("Repair");
                dropSequence("Repair_Seq");
                createTable("Owner", ownerTableSQL);
                createTable("Car", carTableSQL);
                createTable("Repair", repairTableSQL);

                executeSQL(repairIdSequenceSQL); // Create the sequence
                executeSQL(repairIdTriggerSQL); // Create the trigger

                // Populate tables with data
                populateOwnerTable();
                populateCarTable();
                populateRepairTable();

                System.out.println("Printing all Records using List:");

                List<Owner> owners = getAllOwners();
                owners.forEach(owner -> System.out.println(owner.getName()));

                List<Car> cars = getAllCars();
                cars.forEach(car -> System.out.println(car.getMake() + " : " + car.getModel()));

                List<Repair> repairs = getAllRepairs();
                repairs.forEach(repair -> System.out.println(repair.getRepairID()));

                System.out.println("Printing Records For Specific Entity using List:");

                // Fetch specific owner
                Owner specificOwner = getOwnerById(1);
                System.out.println("Specific Owner: " + specificOwner.getName());

                // Fetch specific car
                Car specificCar = getCarById(1);
                System.out.println("Specific Car: " + specificCar.getModel() + " : " + specificCar.getMake());


                // Fetch repairs for a specific owner
                List<Repair> ownerRepairs = getRepairsByOwnerId(1);
                ownerRepairs.forEach(repair -> System.out.println("Repair ID: " + repair.getRepairID()));

                // Fetch repairs for a specific car
                List<Repair> carRepairs = getRepairsByCarId(1);
                carRepairs.forEach(repair -> System.out.println("Repair ID: " + repair.getRepairID()));

                // Fetch repairs between dates
                List<Repair> dateRepairs = displayRepairsBetweenDates("2024-01-01", "2024-03-31");
                dateRepairs.forEach(repair -> System.out.println("Service Date: " + repair.getServiceDate()));

                // Example: Update records

                //Comment out the below code that update and delete records once UI is finally ready
                updateRecord("Owner", "address = '123 Updated St'", "ownerID = 1");
                updateRecord("Car", "model = 'Updated Model'", "carID = 1");
                updateRecord("Repair", "description = 'Updated Repair Description'", "repairID = 1");

                // Example: Delete records

                deleteRecord("Repair", "repairID = 5");
                deleteRecord("Owner", "ownerID = 5");
                deleteRecord("Car", "carID = 5");

                // Display all the records
                System.out.println("Displaying Records After Update and Delete");

                owners = getAllOwners();
                owners.forEach(owner -> System.out.println(owner.getName()));

                cars = getAllCars();
                cars.forEach(car -> System.out.println(car.getMake() + " : " + car.getModel()));

                repairs = getAllRepairs();
                repairs.forEach(repair -> System.out.println(repair.getRepairID()));


                System.out.println("All tables populated with data successfully.");
            } catch (SQLException e) {
                System.err.println("Error during table creation: " + e.getMessage());
            }
        }

        public static void executeSQL(String sql) throws SQLException {
            try (Connection conn = getConnection();
                 Statement statement = conn.createStatement()) {
                statement.execute(sql);
                System.out.println("Executed SQL: " + sql);
            } catch (SQLException e) {
                System.err.println("Error executing SQL: " + e.getMessage());
            }
        }
        public static void populateOwnerTable() throws SQLException {
            insertData("INSERT INTO Owner VALUES (1, 'John Doe', '123 Main St', '555-1234', 'john.doe@example.com')");
            insertData("INSERT INTO Owner VALUES (2, 'Jane Smith', '456 Elm St', '555-5678', 'jane.smith@example.com')");
            insertData("INSERT INTO Owner VALUES (3, 'Bob Johnson', '789 Oak St', '555-9012', 'bob.johnson@example.com')");
            insertData("INSERT INTO Owner VALUES (4, 'Alice Brown', '321 Pine St', '555-3456', 'alice.brown@example.com')");
            insertData("INSERT INTO Owner VALUES (5, 'Charlie Davis', '654 Cedar St', '555-7890', 'charlie.davis@example.com')");
        }
        public static void populateCarTable() throws SQLException {
            insertData("INSERT INTO Car VALUES (1, 'Toyota', 'Camry', 123456789, 2020, 'Sedan')");
            insertData("INSERT INTO Car VALUES (2, 'Honda', 'Civic', 987654321, 2021, 'Sedan')");
            insertData("INSERT INTO Car VALUES (3, 'Ford', 'F-150', 456123789, 2019, 'Truck')");
            insertData("INSERT INTO Car VALUES (4, 'Chevrolet', 'Malibu', 789456123, 2022, 'Sedan')");
            insertData("INSERT INTO Car VALUES (5, 'Tesla', 'Model 3', 321789654, 2023, 'Electric')");
        }
        public static void populateRepairTable() throws SQLException {
            insertData("INSERT INTO Repair (repairID, ownerID, carID, serviceDate, description, cost) VALUES (1, 1, 1, TO_DATE('2024-01-01', 'YYYY-MM-DD'), 'Oil Change', 50)");
            insertData("INSERT INTO Repair (repairID, ownerID, carID, serviceDate, description, cost) VALUES (2, 2, 2, TO_DATE('2024-02-01', 'YYYY-MM-DD'), 'Tire Rotation', 40)");
            insertData("INSERT INTO Repair (repairID, ownerID, carID, serviceDate, description, cost) VALUES (3, 3, 3, TO_DATE('2024-03-01', 'YYYY-MM-DD'), 'Brake Replacement', 200)");
            insertData("INSERT INTO Repair (repairID, ownerID, carID, serviceDate, description, cost) VALUES (4, 4, 4, TO_DATE('2024-04-01', 'YYYY-MM-DD'), 'Battery Replacement', 150)");
            insertData("INSERT INTO Repair (repairID, ownerID, carID, serviceDate, description, cost) VALUES (5, 5, 5, TO_DATE('2024-05-01', 'YYYY-MM-DD'), 'Engine Repair', 500)");
        }
    }