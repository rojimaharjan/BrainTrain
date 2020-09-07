package com.robotz.braintrain.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.robotz.braintrain.Repository.UserRepository;
import com.robotz.braintrain.Entity.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private List<User> allUsers;
    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
    }

    public void insert(User user){
       userRepository.insert(user);;
    }

    public void update(User user){
        userRepository.update(user);;
    }


    public void delete(User user){
        userRepository.delete(user);
    }

    public void deleteAllUsers(){
        userRepository.deleteAllUsers();
    }

    public List<User> getAllUsers(){
        return allUsers;
    }
}
