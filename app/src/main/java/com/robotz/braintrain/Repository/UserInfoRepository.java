package com.robotz.braintrain.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.robotz.braintrain.Dao.UserInfoDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.UserInfo;

import java.util.List;

public class UserInfoRepository {
    private UserInfoDao userInfoDao;
    private LiveData<List<UserInfo>> allUserInfos;
    public UserInfoRepository(Application application){
        BrainTrainDatabase database = BrainTrainDatabase.getInstance(application);
        userInfoDao = database.userInfoDao();
        allUserInfos = userInfoDao.getAllUserInfos();

    }

    public void insert(UserInfo userInfo){

        new UserInfoRepository.InsertUserInfoAsyncTask(userInfoDao).execute(userInfo);
    }
    public void update(UserInfo userInfo){
        new UserInfoRepository.UpdateUserInfoAsyncTask(userInfoDao).execute(userInfo);
    }
    public void delete(UserInfo userInfo){
        new UserInfoRepository.DeleteUserInfoAsyncTask(userInfoDao).execute(userInfo);
    }

    public void deleteAllUserInfos(){
        new UserInfoRepository.DeleteAllUserInfosAsyncTask(userInfoDao).execute();
    }

    public LiveData<List<UserInfo>> getAllUserInfos(){
        return allUserInfos;

    }


    private static class InsertUserInfoAsyncTask extends AsyncTask<UserInfo, Void, Void> {
        private UserInfoDao userInfoDao;

        private InsertUserInfoAsyncTask(UserInfoDao userInfoDao){
            this.userInfoDao = userInfoDao;
        }
        @Override
        protected Void doInBackground(UserInfo... userInfos) {
            userInfoDao.insert(userInfos[0]);
            return null;
        }
    }

    private static class UpdateUserInfoAsyncTask extends AsyncTask<UserInfo, Void, Void> {
        private UserInfoDao userInfoDao;

        private UpdateUserInfoAsyncTask(UserInfoDao userInfoDao){
            this.userInfoDao = userInfoDao;
        }
        @Override
        protected Void doInBackground(UserInfo... userInfos) {
            userInfoDao.update(userInfos[0]);
            return null;
        }
    }

    private static class DeleteUserInfoAsyncTask extends AsyncTask<UserInfo, Void, Void> {
        private UserInfoDao userInfoDao;

        private DeleteUserInfoAsyncTask(UserInfoDao userInfoDao){
            this.userInfoDao = userInfoDao;
        }
        @Override
        protected Void doInBackground(UserInfo... userInfos) {
            userInfoDao.delete(userInfos[0]);
            return null;
        }
    }

    private static class DeleteAllUserInfosAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserInfoDao userInfoDao;

        private DeleteAllUserInfosAsyncTask(UserInfoDao userInfoDao){
            this.userInfoDao = userInfoDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            userInfoDao.deleteAllUserInfos();
            return null;
        }
    }
}
