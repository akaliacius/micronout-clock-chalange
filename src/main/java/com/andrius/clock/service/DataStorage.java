package com.andrius.clock.service;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class DataStorage {
    private Set<String> endpoints = new HashSet<>();

    boolean insertEndpoint(String endpoint){
        return endpoints.add(endpoint);
    }

    boolean removeEndpoint(String endpoint){
        return endpoints.remove(endpoint);
    }

    List<String> listAll(){
        return endpoints.stream()
                .collect(Collectors.toList());
    }

    void reset(){
        endpoints.clear();
    }
}
