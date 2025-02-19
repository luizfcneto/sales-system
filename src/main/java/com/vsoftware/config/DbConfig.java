package com.vsoftware.config;

import java.util.ResourceBundle;

public class DbConfig {
	private static final ResourceBundle bundle = ResourceBundle.getBundle("application");

    public static String getDbUrl() {
        return bundle.getString("db.url");
    }

    public static String getDbUser() {
        return bundle.getString("db.user");
    }

    public static String getDbPassword() {
        return bundle.getString("db.password");
    }
}
