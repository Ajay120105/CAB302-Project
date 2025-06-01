package org.example.grade_predictor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SettingTest {

    private Setting setting;

    @BeforeEach
    public void setUp() {
        setting = new Setting("theme", "dark");
    }

    @Test
    public void testSettingConstructor() {
        assertEquals("theme", setting.getKey());
        assertEquals("dark", setting.getValue());
    }

    @Test
    public void testSettersAndGetters() {
        setting.setKey("language");
        assertEquals("language", setting.getKey());

        setting.setValue("english");
        assertEquals("english", setting.getValue());
    }

    @Test
    public void testNullKey() {
        setting.setKey(null);
        assertNull(setting.getKey());
    }

    @Test
    public void testNullValue() {
        setting.setValue(null);
        assertNull(setting.getValue());
    }

    @Test
    public void testEmptyKey() {
        setting.setKey("");
        assertEquals("", setting.getKey());
    }

    @Test
    public void testEmptyValue() {
        setting.setValue("");
        assertEquals("", setting.getValue());
    }

    @Test
    public void testWhitespaceKey() {
        setting.setKey("   ");
        assertEquals("   ", setting.getKey());

        setting.setKey("\t\n");
        assertEquals("\t\n", setting.getKey());
    }

    @Test
    public void testWhitespaceValue() {
        setting.setValue("   ");
        assertEquals("   ", setting.getValue());

        setting.setValue("\t\n");
        assertEquals("\t\n", setting.getValue());
    }

    @Test
    public void testSpecialCharactersInKey() {
        setting.setKey("app.config.database.url");
        assertEquals("app.config.database.url", setting.getKey());

        setting.setKey("user-preference");
        assertEquals("user-preference", setting.getKey());

        setting.setKey("UI_THEME");
        assertEquals("UI_THEME", setting.getKey());

        setting.setKey("setting@home");
        assertEquals("setting@home", setting.getKey());
    }

    @Test
    public void testSpecialCharactersInValue() {
        setting.setValue("http://localhost:8080");
        assertEquals("http://localhost:8080", setting.getValue());

        setting.setValue("#FF5733");
        assertEquals("#FF5733", setting.getValue());

        setting.setValue("user@example.com");
        assertEquals("user@example.com", setting.getValue());

        setting.setValue("password!@#$%");
        assertEquals("password!@#$%", setting.getValue());
    }

    @Test
    public void testNumericStringValue() {
        setting.setValue("12345");
        assertEquals("12345", setting.getValue());

        setting.setValue("3.14159");
        assertEquals("3.14159", setting.getValue());

        setting.setValue("-42");
        assertEquals("-42", setting.getValue());
    }

    @Test
    public void testBooleanStringValue() {
        setting.setValue("true");
        assertEquals("true", setting.getValue());

        setting.setValue("false");
        assertEquals("false", setting.getValue());

        setting.setValue("TRUE");
        assertEquals("TRUE", setting.getValue());

        setting.setValue("False");
        assertEquals("False", setting.getValue());
    }

    @Test
    public void testNullConstructorValues() {
        Setting nullSetting = new Setting(null, null);
        assertNull(nullSetting.getKey());
        assertNull(nullSetting.getValue());
    }

    @Test
    public void testEmptyConstructorValues() {
        Setting emptySetting = new Setting("", "");
        assertEquals("", emptySetting.getKey());
        assertEquals("", emptySetting.getValue());
    }
} 