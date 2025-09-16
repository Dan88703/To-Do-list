package Nad.Sonork.todo;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Main {
    final static Map<Integer,Goals> myGoal = new HashMap<>();
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);
        Goals goal = new Goals(1,"Make a projeckt","I must end this befor Oktober start","2025:01:10");
        myGoal.put(goal.getId(), goal);
        server.createContext("/goals" ,new GoalHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server have been launching");
    }
}