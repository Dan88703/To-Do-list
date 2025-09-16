package Nad.Sonork.todo;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class ErrorResponse {
    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
public class GoalHandler implements HttpHandler {
    private final Gson gson = new Gson();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String response = "lol";
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");


        if("GET".equalsIgnoreCase(requestMethod)){
        String responsJSON = gson.toJson(Main.myGoal.values());
        exchange.sendResponseHeaders(200,responsJSON.getBytes(StandardCharsets.UTF_8).length);
        }else if("POST".equalsIgnoreCase(requestMethod)){
            try(InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(),StandardCharsets.UTF_8)){
                Goals newGoal = gson.fromJson(reader,Goals.class);
                if (newGoal.getId() <= 0 || newGoal.getTitle() == null || newGoal.getTitle().isEmpty() ||
                        newGoal.getDescription() == null || newGoal.getDescription().isEmpty()) {
                    sendError(exchange, 400, "Invalid goal data");
                    return;
                }
                if(Main.myGoal.containsKey(newGoal.getId())){
                    sendError(exchange,409, "This Goal alredy exist");
                    return;
                }
                Main.myGoal.put(newGoal.getId(), newGoal);
                String responsJSON = gson.toJson(newGoal);
                exchange.sendResponseHeaders(201,responsJSON.getBytes(StandardCharsets.UTF_8).length);
                try(OutputStream os = exchange.getResponseBody()){
                    os.write(responsJSON.getBytes(StandardCharsets.UTF_8));
                }
            }catch (Exception e){
                sendError(exchange, 405, "Method Not Allowed");
            }
        }
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try(OutputStream os = exchange.getResponseBody()){
            os.write(response.getBytes());
        }
    }

    private void sendError(HttpExchange exchange, int statusCode, String message) throws IOException {
        String jsonResponse = gson.toJson(new ErrorResponse(message));
        exchange.sendResponseHeaders(statusCode, jsonResponse.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
        }
    }
}
