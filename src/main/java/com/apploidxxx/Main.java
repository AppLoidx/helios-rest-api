package com.apploidxxx;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server for tests will listen on
    public static final String BASE_URI = "http://localhost:3000/";
    public static boolean validatePassword = true;
    // Getting port from JAVA_OPTS
    private static String port = System.getenv("PORT");
    // HEROKU URI Setting up
    private static final String BASE_URI_HEROKU = "http://0.0.0.0:" + port + "/";

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

    private static URI getBaseURI(int port) {
        return UriBuilder.fromUri("http://0.0.0.0/").port(port).build();
    }

    /**
     * Starts Heroku Grizzly HTTP server
     * @return Grizzly HTTP server
     */
    private static HttpServer startServerHeroku(URI uri) {

        setUpLogger();
        // create a resource config that scans for JAX-RS resources and providers
        // in com.apploidxxx package
        final ResourceConfig rc = new ResourceConfig().packages("com.apploidxxx");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(uri, rc);
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
//        startServerHeroku(getBaseURI(Integer.parseInt(System.getenv("PORT"))));
            startServer();
//        // adding static files mapping
//        CLStaticHttpHandler handler = new CLStaticHttpHandler(Main.class.getClassLoader(), "/static/");
//        server.getServerConfiguration().addHttpHandler(handler, "/html");

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI_HEROKU));
        while(true) {
            System.in.read();
        }
    }
}

