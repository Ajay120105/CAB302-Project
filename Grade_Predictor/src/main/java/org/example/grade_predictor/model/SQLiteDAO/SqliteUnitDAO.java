package org.example.grade_predictor.model.SQLiteDAO;

import org.example.grade_predictor.model.Unit;
import org.example.grade_predictor.model.SqliteConnection;
import org.example.grade_predictor.model.interfaces.I_Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteUnitDAO implements I_Unit {

    private Connection connection;

    public SqliteUnitDAO() {
        connection = SqliteConnection.getInstance();
        //deleteUnitsTable();
        //createTable();
        //insertDummyUnits();

    }

    public void deleteUnitsTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "DROP TABLE IF EXISTS units";
            statement.execute(query);
            System.out.println("Units table deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS units (" +
                    "unit_code TEXT PRIMARY KEY," +
                    "unit_name TEXT NOT NULL" +
                    ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUnit(Unit unit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO units (unit_code, unit_name) VALUES (?, ?)");
            statement.setString(1, unit.getUnit_code());
            statement.setString(2, unit.getUnit_name());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUnit(Unit unit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE units SET unit_name = ? WHERE unit_code = ?");
            statement.setString(1, unit.getUnit_name());
            statement.setString(2, unit.getUnit_code());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUnit(Unit unit) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM units WHERE unit_code = ?");
            statement.setString(1, unit.getUnit_code());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Unit getUnit(String unit_code) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM units WHERE unit_code = ?");
            statement.setString(1, unit_code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String unit_name = resultSet.getString("unit_name");
                return new Unit(unit_code, unit_name); // Pass null for difficulty
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Unit> getAllUnits() {
        List<Unit> units = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM units");
            while (resultSet.next()) {
                String unit_code = resultSet.getString("unit_code");
                String unit_name = resultSet.getString("unit_name");
                units.add(new Unit(unit_code, unit_name)); // Pass null for difficulty
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return units;
    }

    public void insertDummyUnits() {
        String[][] dummyUnits = {
                new String[]{"BUS125", "Introduction to Law"},
                new String[]{"BUS142", "Theories of Programming"},
                new String[]{"BUS171", "Introduction to Architecture"},
                new String[]{"BUS184", "Theories of Biology"},
                new String[]{"BUS187", "Theories of Architecture"},
                new String[]{"BUS193", "Foundations of Biology"},
                new String[]{"BUS230", "Introduction to Accounting"},
                new String[]{"BUS248", "Applications of Accounting"},
                new String[]{"BUS273", "Advanced Topics in Health Science"},
                new String[]{"BUS376", "Concepts in Biology"},
                new String[]{"BUS468", "Applications of Environmental Science"},
                new String[]{"BUS484", "Principles of Biology"},
                new String[]{"BUS484", "Theories of Statistics"},
                new String[]{"BUS493", "Concepts in Design"},
                new String[]{"CAB140", "Advanced Topics in Architecture"},
                new String[]{"CAB147", "Fundamentals of Economics"},
                new String[]{"CAB147", "Practices in Design"},
                new String[]{"CAB151", "Principles of Engineering"},
                new String[]{"CAB154", "Practices in Biology"},
                new String[]{"CAB159", "Foundations of Statistics"},
                new String[]{"CAB166", "Advanced Topics in Health Science"},
                new String[]{"CAB181", "Theories of Design"},
                new String[]{"CAB194", "Fundamentals of Statistics"},
                new String[]{"CAB202", "Foundations of Economics"},
                new String[]{"CAB210", "Practices in Architecture"},
                new String[]{"CAB247", "Principles of Health Science"},
                new String[]{"CAB268", "Applications of Programming"},
                new String[]{"CAB272", "Fundamentals of Design"},
                new String[]{"CAB341", "Foundations of Networks"},
                new String[]{"CAB354", "Foundations of Business"},
                new String[]{"CAB374", "Case Studies in Marketing"},
                new String[]{"CAB384", "Case Studies in Data Science"},
                new String[]{"CAB391", "Foundations of Data Science"},
                new String[]{"CAB431", "Fundamentals of Environmental Science"},
                new String[]{"CAB433", "Principles of Architecture"},
                new String[]{"CAB452", "Foundations of Engineering"},
                new String[]{"CAB454", "Case Studies in Economics"},
                new String[]{"CAB471", "Case Studies in Nursing"},
                new String[]{"CAB486", "Practices in Architecture"},
                new String[]{"DES120", "Principles of Engineering"},
                new String[]{"DES143", "Practices in Biology"},
                new String[]{"DES163", "Applications of Physics"},
                new String[]{"DES195", "Principles of Accounting"},
                new String[]{"DES196", "Foundations of Nursing"},
                new String[]{"DES217", "Theories of Design"},
                new String[]{"DES266", "Introduction to Marketing"},
                new String[]{"DES269", "Advanced Topics in Law"},
                new String[]{"DES337", "Fundamentals of Marketing"},
                new String[]{"DES340", "Introduction to Software Design"},
                new String[]{"DES342", "Foundations of Law"},
                new String[]{"DES366", "Applications of Programming"},
                new String[]{"DES372", "Advanced Topics in Data Science"},
                new String[]{"DES378", "Fundamentals of Education"},
                new String[]{"DES388", "Concepts in Design"},
                new String[]{"DES430", "Theories of Architecture"},
                new String[]{"DES456", "Applications of Accounting"},
                new String[]{"DES457", "Introduction to Software Design"},
                new String[]{"DES461", "Applications of Law"},
                new String[]{"DES468", "Foundations of Software Design"},
                new String[]{"DES471", "Concepts in Business"},
                new String[]{"DES494", "Practices in Architecture"},
                new String[]{"EDU100", "Applications of Health Science"},
                new String[]{"EDU106", "Fundamentals of Biology"},
                new String[]{"EDU151", "Introduction to Architecture"},
                new String[]{"EDU191", "Theories of Business"},
                new String[]{"EDU222", "Case Studies in Programming"},
                new String[]{"EDU239", "Theories of Data Science"},
                new String[]{"EDU242", "Applications of Data Science"},
                new String[]{"EDU266", "Theories of Business"},
                new String[]{"EDU301", "Case Studies in Architecture"},
                new String[]{"EDU337", "Advanced Topics in Engineering"},
                new String[]{"EDU340", "Advanced Topics in Environmental Science"},
                new String[]{"EDU345", "Theories of Programming"},
                new String[]{"EDU378", "Practices in Law"},
                new String[]{"EDU394", "Principles of Marketing"},
                new String[]{"EDU424", "Theories of Nursing"},
                new String[]{"EDU453", "Applications of Data Science"},
                new String[]{"ENG108", "Concepts in Design"},
                new String[]{"ENG111", "Principles of Software Design"},
                new String[]{"ENG139", "Principles of Architecture"},
                new String[]{"ENG164", "Principles of Software Design"},
                new String[]{"ENG187", "Applications of Software Design"},
                new String[]{"ENG189", "Foundations of Engineering"},
                new String[]{"ENG195", "Foundations of Software Design"},
                new String[]{"ENG202", "Case Studies in Physics"},
                new String[]{"ENG205", "Theories of Statistics"},
                new String[]{"ENG221", "Foundations of Statistics"},
                new String[]{"ENG232", "Practices in Statistics"},
                new String[]{"ENG288", "Applications of Networks"},
                new String[]{"ENG374", "Advanced Topics in Networks"},
                new String[]{"ENG375", "Case Studies in Statistics"},
                new String[]{"ENG387", "Case Studies in Economics"},
                new String[]{"ENG405", "Advanced Topics in Health Science"},
                new String[]{"ENG414", "Theories of Data Science"},
                new String[]{"ENG466", "Principles of Business"},
                new String[]{"ENG478", "Advanced Topics in Programming"},
                new String[]{"ENV117", "Introduction to Statistics"},
                new String[]{"ENV126", "Introduction to Nursing"},
                new String[]{"ENV127", "Principles of Biology"},
                new String[]{"ENV139", "Practices in Nursing"},
                new String[]{"ENV156", "Introduction to Law"},
                new String[]{"ENV228", "Advanced Topics in Statistics"},
                new String[]{"ENV246", "Practices in Law"},
                new String[]{"ENV262", "Theories of Programming"},
                new String[]{"ENV277", "Applications of Data Science"},
                new String[]{"ENV291", "Foundations of Education"},
                new String[]{"ENV309", "Applications of Statistics"},
                new String[]{"ENV317", "Concepts in Nursing"},
                new String[]{"ENV333", "Fundamentals of Economics"},
                new String[]{"ENV364", "Theories of Nursing"},
                new String[]{"ENV364", "Applications of Economics"},
                new String[]{"ENV378", "Principles of Architecture"},
                new String[]{"ENV380", "Concepts in Education"},
                new String[]{"ENV397", "Introduction to Marketing"},
                new String[]{"ENV409", "Concepts in Statistics"},
                new String[]{"ENV446", "Principles of Philosophy"},
                new String[]{"ENV446", "Applications of Biology"},
                new String[]{"ITB123", "Introduction to Architecture"},
                new String[]{"ITB138", "Principles of Statistics"},
                new String[]{"ITB142", "Concepts in Marketing"},
                new String[]{"ITB148", "Introduction to Physics"},
                new String[]{"ITB165", "Principles of Software Design"},
                new String[]{"ITB186", "Principles of Physics"},
                new String[]{"ITB203", "Concepts in Biology"},
                new String[]{"ITB217", "Advanced Topics in Statistics"},
                new String[]{"ITB279", "Practices in Marketing"},
                new String[]{"ITB314", "Introduction to Business"},
                new String[]{"ITB363", "Theories of Networks"},
                new String[]{"ITB408", "Principles of Education"},
                new String[]{"ITB419", "Concepts in Software Design"},
                new String[]{"ITB421", "Foundations of Statistics"},
                new String[]{"ITB432", "Fundamentals of Economics"},
                new String[]{"ITB436", "Case Studies in Engineering"},
                new String[]{"ITB449", "Principles of Architecture"},
                new String[]{"ITB461", "Fundamentals of Software Design"},
                new String[]{"ITB467", "Advanced Topics in Software Design"},
                new String[]{"ITB475", "Theories of Environmental Science"},
                new String[]{"LAW143", "Principles of Architecture"},
                new String[]{"LAW209", "Practices in Architecture"},
                new String[]{"LAW220", "Introduction to Design"},
                new String[]{"LAW245", "Case Studies in Environmental Science"},
                new String[]{"LAW249", "Concepts in Biology"},
                new String[]{"LAW265", "Practices in Physics"},
                new String[]{"LAW298", "Theories of Statistics"},
                new String[]{"LAW312", "Practices in Data Science"},
                new String[]{"LAW319", "Introduction to Architecture"},
                new String[]{"LAW324", "Concepts in Business"},
                new String[]{"LAW328", "Introduction to Economics"},
                new String[]{"LAW348", "Advanced Topics in Programming"},
                new String[]{"LAW385", "Principles of Architecture"},
                new String[]{"LAW409", "Introduction to Programming"},
                new String[]{"LAW416", "Advanced Topics in Physics"},
                new String[]{"LAW424", "Principles of Philosophy"},
                new String[]{"LAW483", "Advanced Topics in Education"},
                new String[]{"LAW496", "Advanced Topics in Architecture"},
                new String[]{"MED130", "Case Studies in Data Science"},
                new String[]{"MED171", "Applications of Programming"},
                new String[]{"MED172", "Foundations of Philosophy"},
                new String[]{"MED183", "Concepts in Philosophy"},
                new String[]{"MED215", "Fundamentals of Economics"},
                new String[]{"MED217", "Foundations of Statistics"},
                new String[]{"MED241", "Advanced Topics in Philosophy"},
                new String[]{"MED254", "Principles of Economics"},
                new String[]{"MED259", "Principles of Biology"},
                new String[]{"MED259", "Introduction to Nursing"},
                new String[]{"MED300", "Applications of Software Design"},
                new String[]{"MED310", "Foundations of Engineering"},
                new String[]{"MED333", "Case Studies in Philosophy"},
                new String[]{"MED339", "Fundamentals of Networks"},
                new String[]{"MED367", "Case Studies in Nursing"},
                new String[]{"MED382", "Fundamentals of Statistics"},
                new String[]{"MED400", "Fundamentals of Statistics"},
                new String[]{"MED416", "Theories of Business"},
                new String[]{"MED420", "Introduction to Design"},
                new String[]{"MED428", "Concepts in Architecture"},
                new String[]{"MED445", "Foundations of Design"},
                new String[]{"MED466", "Case Studies in Architecture"},
                new String[]{"MED491", "Practices in Philosophy"},
                new String[]{"SCI144", "Theories of Education"},
                new String[]{"SCI157", "Fundamentals of Programming"},
                new String[]{"SCI165", "Fundamentals of Environmental Science"},
                new String[]{"SCI179", "Principles of Data Science"},
                new String[]{"SCI195", "Principles of Networks"},
                new String[]{"SCI195", "Practices in Education"},
                new String[]{"SCI214", "Advanced Topics in Design"},
                new String[]{"SCI215", "Principles of Education"},
                new String[]{"SCI223", "Theories of Design"},
                new String[]{"SCI226", "Practices in Physics"},
                new String[]{"SCI266", "Introduction to Environmental Science"},
                new String[]{"SCI268", "Foundations of Philosophy"},
                new String[]{"SCI295", "Principles of Marketing"},
                new String[]{"SCI332", "Principles of Networks"},
                new String[]{"SCI351", "Case Studies in Design"},
                new String[]{"SCI364", "Concepts in Marketing"},
                new String[]{"SCI378", "Applications of Marketing"},
                new String[]{"SCI418", "Introduction to Environmental Science"},
                new String[]{"SCI424", "Advanced Topics in Nursing"},
                new String[]{"SCI459", "Advanced Topics in Networks"},
                new String[]{"SCI462", "Concepts in Economics"},
                new String[]{"SCI473", "Fundamentals of Philosophy"},
        };

        String insertQuery = "INSERT OR IGNORE INTO units (unit_code, unit_name) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            for (String[] unit : dummyUnits) {
                pstmt.setString(1, unit[0]);
                pstmt.setString(2, unit[1]);
                pstmt.executeUpdate();
            }
            System.out.println("Dummy units inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

