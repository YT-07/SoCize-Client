package com.socize.app;

import java.io.InputStream;

import org.slf4j.LoggerFactory;

import com.socize.config.FilePath;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class Launcher {
    
    public static void main(String[] args) throws Exception {
        // Used for getting the class object then getting resources, as this app will be packaged in a jar file
        Launcher launcher = new Launcher();

        InputStream logbackFileInputStream = launcher.getClass().getResourceAsStream(FilePath.LOGBACK_CONFIG);
        configureLogback(logbackFileInputStream);
        
        MainApp.main(args);
    }

    /**
     * Perform configurations for the Logback logging framework.
     * 
     * @param inputStream the input stream of the logback xml configuration file
     * @throws JoranException
     */
    private static void configureLogback(InputStream inputStream) throws JoranException {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();

        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        configurator.doConfigure(inputStream);
    }
}
