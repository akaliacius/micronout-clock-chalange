package com.andrius.clock.service;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Singleton
public class ClockJob {
    public static final int MIN = 5;
    public static final int MAX = 14_400;

    private final DataStorage storage;
    private int frequencySec = MIN;

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public ClockJob(DataStorage storage) {
        this.storage = storage;
    }

    public void update(int seconds){
        if(seconds < MIN || seconds > MAX){
            throw new IllegalArgumentException("frequency must be only between " + MIN + " and " + MAX + " seconds");
        }
        frequencySec = seconds;
    }


    // TODO: add logging here
    public void run(){
        Thread thread = new Thread(() -> {
            while (true){
                var results = storage.listAll().stream()
                        .map(url -> {
                            HttpRequest request = null;
                            try {
                                request = HttpRequest.newBuilder()
                                        .version(HttpClient.Version.HTTP_2)
                                        .uri(new URI(url))
                                        .header("Content-Type", "application/json")
                                        .POST(HttpRequest.BodyPublishers.ofString(message()))
                                        .build();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }

                            return HttpClient.newBuilder()
                                    .executor(executor)
                                    .connectTimeout(Duration.ofMinutes(1))
                                    .build()
                                    .sendAsync(request, HttpResponse.BodyHandlers.ofString());

                        }).collect(Collectors.toList());

                results.forEach(f -> {
                    try {
                        System.out.println(f.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                });

                // TODO: swallow exceptions just like that is terrible, but lack of time is even worse
                try {
                    Thread.sleep(frequencySec * 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new Error();
                }
            }
        });
        thread.start();
    }

    private String message(){
        LocalDateTime time = LocalDateTime.now();
        return "{\"time\":\"" + time.toString() + "\"}";
    }
}
