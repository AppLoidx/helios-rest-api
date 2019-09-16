package com.apploidxxx;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Точка входа для локального запуска сервера
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server for tests will listen on
    public static final String BASE_URI = "http://localhost:3000/";

    /**
     * Валидация пароля на безопасность. Его можно отключить при тестировании в локальном режиме,
     * чтобы не задавать длинные пароли
     */
    public static boolean validatePassword = true;


    /**
     * Starts local Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {

        setUpLogger();
        validatePassword = false;
        // create a resource config that scans for JAX-RS resources and providers
        // in com.apploidxxx package
        final ResourceConfig rc = new ResourceConfig().packages("com.apploidxxx");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    private static void setUpLogger(){
        // Setup Hibernate Logger level
        Logger hibernateLog = Logger.getLogger("org.hibernate");
        hibernateLog.setLevel(Level.SEVERE);


        // Setup Handlers Logger level
        Logger l = Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler");
        l.setLevel(Level.FINE);
        l.setUseParentHandlers(false);

        // Adding console handler and setting level
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        l.addHandler(ch);
    }


    public static void main(String[] args) throws IOException {

        HttpServer server = startServer();

        CLStaticHttpHandler handler = new CLStaticHttpHandler(Main.class.getClassLoader(), "/static/");
        server.getServerConfiguration().addHttpHandler(handler, "/html/");

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        while(true) {
            System.in.read();
        }
    }
}

