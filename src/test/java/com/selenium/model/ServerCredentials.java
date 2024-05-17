package com.selenium.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ServerCredentials {
    static Properties properties = new Properties();
    String user;
    String password;
    Boolean production;
    static String chromeDriver = properties.getProperty("chromeDriver");
    static String geckoDriver = properties.getProperty("geckoDriver");

    public ServerCredentials() throws IOException {
        FileInputStream input = new FileInputStream("Application.properties");
        properties.load(input);
        this.user = properties.getProperty("username");
        this.password = properties.getProperty("password");
        this.production = Boolean.parseBoolean(properties.getProperty("production"));

    }

    public String getUrl() {
        if (production) {
            return properties.getProperty("liveUrl");
        }
        return properties.getProperty("testUrl");
    }

    public String getUserName() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public static String setDriverPath(String driverName) {

        if (driverName.equalsIgnoreCase("chrome")) {
            return "/home/iramtech/iRam_Folder/web_driver/120/chrome";
            // return
            // "/home/iramtech/iRam_Folder/web_driver/opt/google/chrome/google-chrome";
        }
        // return "/home/iramtech/iRam_Folder/web_driver/firefox/117/geckodriver";
        return "/home/iramtech/iRam_Folder/web_driver/fire_fox/geckodriver";
    }

    public static WebDriver setBrowserDriver(String driverName) {
        if (driverName.equalsIgnoreCase("chrome")) {
            return new ChromeDriver();
        }
        return new FirefoxDriver();
    }
    // public static WebDriver setBrowserDriver(String driverName) {
    // if (driverName.equalsIgnoreCase("chrome")) {
    // return new ChromeDriver();
    // } else if (driverName.equalsIgnoreCase("firefox")) {
    // FirefoxOptions options = new FirefoxOptions();
    // options.setHeadless(true);
    // return new FirefoxDriver(options);
    // }
    // return null;
    // }
}
