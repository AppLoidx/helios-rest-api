package com.apploidxxx;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * Точка входа для запуска на сервисе heroku.com
 *
 * @author Arthur Kupriyanov
 */
public class HerokuMain {

    // Getting port from JAVA_OPTS
    private static String port = System.getenv("PORT");
    // HEROKU URI Setting up
    private static final String BASE_URI_HEROKU = "http://0.0.0.0:" + port + "/";



    private static URI getBaseURI(int port) {
        return UriBuilder.fromUri("http://0.0.0.0/").port(port).build();
    }

    /**
     * Starts Heroku Grizzly HTTP server
     * @return Grizzly HTTP server
     */
    private static HttpServer startServerHeroku(URI uri) {

        // create a resource config that scans for JAX-RS resources and providers
        // in com.apploidxxx package
        final ResourceConfig rc = new ResourceConfig().packages("com.apploidxxx");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(uri, rc);
    }



    public static void main(String[] args) throws IOException {
        HttpServer server = startServerHeroku(getBaseURI(Integer.parseInt(System.getenv("PORT"))));


        CLStaticHttpHandler handler = new CLStaticHttpHandler(Main.class.getClassLoader(), "/static/");
        server.getServerConfiguration().addHttpHandler(handler, "/html/");

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI_HEROKU));
        while(true) {
            System.in.read();
        }
    }
}
