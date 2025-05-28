package org.example.grade_predictor.model.SQLiteDAO;

import org.example.grade_predictor.model.Setting;
import org.example.grade_predictor.model.SqliteConnection;
import org.example.grade_predictor.model.interfaces.I_Settings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteSettingsDAO implements I_Settings {

    private Connection connection;

    public SqliteSettingsDAO() {
        connection = SqliteConnection.getInstance();
    }

    @Override
    public void addSetting(Setting setting) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO settings (key, value) VALUES (?, ?)", 
                Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, setting.getKey());
            statement.setString(2, setting.getValue());
            statement.executeUpdate();
            
            // Set the generated ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                setting.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSetting(Setting setting) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE settings SET value = ? WHERE key = ?"
            );
            statement.setString(1, setting.getValue());
            statement.setString(2, setting.getKey());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSetting(Setting setting) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM settings WHERE id = ?"
            );
            statement.setInt(1, setting.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Setting getSettingByKey(String key) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM settings WHERE key = ?"
            );
            statement.setString(1, key);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String settingKey = resultSet.getString("key");
                String value = resultSet.getString("value");
                return new Setting(id, settingKey, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Setting> getAllSettings() {
        List<Setting> settings = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM settings ORDER BY key";
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String key = resultSet.getString("key");
                String value = resultSet.getString("value");
                settings.add(new Setting(id, key, value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return settings;
    }

    @Override
    public void upsertSetting(String key, String value) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT OR REPLACE INTO settings (key, value) VALUES (?, ?)"
            );
            statement.setString(1, key);
            statement.setString(2, value);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 