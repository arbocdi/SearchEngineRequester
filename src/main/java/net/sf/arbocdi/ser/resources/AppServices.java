/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.arbocdi.ser.resources;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import java.lang.annotation.Annotation;
import lombok.extern.slf4j.Slf4j;
import net.sf.arbocdi.ser.Utils;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.LoggerFactory;

/**
 *
 * @author arbocdi
 */
@Slf4j
public class AppServices implements AutoCloseable {

    public static final String LOGGER_CONFIG_FILE = "config/logger.xml";

    //logger======
    LoggerContext loggerContext;
    //weld========
    private Weld weld;
    private WeldContainer container;

    private void startLogger() throws Exception {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        // Call context.reset() to clear any previous configuration, e.g. default 
        // configuration. For multi-step configuration, omit calling context.reset().
        context.reset();
        configurator.doConfigure(LOGGER_CONFIG_FILE);
    }

    private void startWeld() throws Exception {
        System.getProperties().setProperty(Weld.SHUTDOWN_HOOK_SYSTEM_PROPERTY, "false");
        this.weld = new Weld();
        this.weld.property(Weld.SHUTDOWN_HOOK_SYSTEM_PROPERTY, false);
        this.container = weld.initialize();
    }

    public void start() throws Exception {
        this.startLogger();
        log.info(String.format("Starting %s", this.getClass().getName()));
        this.startWeld();
    }

    @Override
    public void close() {
        log.info(String.format("Stopping %s", this.getClass().getName()));
        try {
            this.container.shutdown();
            this.weld.shutdown();
        } catch (Exception igonre) {

        }
        this.loggerContext.stop();
    }

    public static void main(String[] args) throws Exception {
        AppServices appServices = new AppServices();
        try {
            appServices.start();

        } finally {
            Utils.close(appServices);
        }
    }

    public <T> T getCDIBean(Class<T> beanClass, Annotation... qualifiers) {
        return this.container.select(beanClass, qualifiers).get();
    }

}
