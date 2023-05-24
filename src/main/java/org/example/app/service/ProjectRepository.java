package org.example.app.service;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retrieveAll();
    void store(T book);
    public boolean removeByRegex(String regex);
    boolean removeItemById(Integer bookIdToRemove);
}
