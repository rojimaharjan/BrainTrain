package com.robotz.braintrain.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.robotz.braintrain.Entity.UserInfo;
import com.robotz.braintrain.Repository.UserInfoRepository;

import java.util.List;

public class UserInfoViewModel extends AndroidViewModel {

    private UserInfoRepository userInfoRepository;
    private LiveData<List<UserInfo>> allUserInfos;
    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        userInfoRepository = new UserInfoRepository(application);
        allUserInfos = userInfoRepository.getAllUserInfos();
    }

    public void insert(UserInfo userInfo){
        userInfoRepository.insert(userInfo);;
    }

    public void update(UserInfo userInfo){
        userInfoRepository.update(userInfo);;
    }


    public void delete(UserInfo userInfo){
        userInfoRepository.delete(userInfo);
    }

    public void deleteAllUserInfos(){
        userInfoRepository.deleteAllUserInfos();
    }

    public LiveData<List<UserInfo>> getAllUserInfos(){
        return allUserInfos;
    }
}

