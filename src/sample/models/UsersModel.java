package sample.models;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UsersModel {

    Class<? extends User> userFilter = User.class;

    ArrayList<User> userList = new ArrayList<>();
    private int counter = 1;

    public interface DataChangedListener {
        void dataChanged(ArrayList<User> user);
    }

    private ArrayList<DataChangedListener> dataChangedListeners = new ArrayList<>();
    public void addDataChangedListener(DataChangedListener listener) {

        this.dataChangedListeners.add(listener);
    }
    public void setUserFilter(Class<? extends User> userFilter) {
        this.userFilter = userFilter;
        this.emitDataChanged();
    }


    public void add(User user, boolean emit) {
        user.setId(counter);
        counter += 1;

        this.userList.add(user);

        if (emit) {
            this.emitDataChanged();
        }
    }

    public void add(User user) {
        add(user, true);
    }

    private void emitDataChanged() {
        for (DataChangedListener listener : dataChangedListeners) {
            ArrayList<User> filteredList = new ArrayList<>(
                    userList.stream()
                            .filter(user -> userFilter.isInstance(user))
                            .collect(Collectors.toList())
            );
            listener.dataChanged(filteredList);
        }
    }

    public void edit(User user) {

        for (int i = 0; i< this.userList.size(); ++i) {

            if (this.userList.get(i).getId() == user.getId()) {

                this.userList.set(i, user);
                break;
            }
        }
        this.emitDataChanged();
    }

    public void delete(int id)
    {
        for (int i = 0; i< this.userList.size(); ++i) {

            if (this.userList.get(i).getId() == id) {
                this.userList.remove(i);
                break;
            }
        }
        this.emitDataChanged();
    }

    public void saveToFile(String path) {

        try (Writer writer =  new FileWriter(path)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerFor(new TypeReference<ArrayList<User>>() { })
                    .withDefaultPrettyPrinter()
                    .writeValue(writer, userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String path) {
        try (Reader reader =  new FileReader(path)) {
            ObjectMapper mapper = new ObjectMapper();
            userList = mapper.readerFor(new TypeReference<ArrayList<User>>() { })
                    .readValue(reader);

            this.counter = userList.stream()
                    .map(user -> user.getId())
                    .max(Integer::compareTo)
                    .orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.emitDataChanged();
    }




}
