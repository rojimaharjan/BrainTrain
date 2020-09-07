package com.robotz.braintrain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class GoogleDriverServiceHelper {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private Drive driveService;
    String dbID;

    public GoogleDriverServiceHelper(Drive driveService) {
        this.driveService = driveService;
    }

    public Task<String> callUploader(String filePath) {
        return  Tasks.call(executor, () -> {
//            import from gdrive
            File fileMetaData = new File();
            fileMetaData.setName("fileName");

//            import from io
            java.io.File file = new java.io.File(filePath);
//            import from gdrive
            FileContent dbFile = new FileContent("application/vnd.sqlit3", file);
            File myFile = null;

            try {
                myFile = driveService.files().create(fileMetaData, dbFile).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (myFile == null) {
                throw new IOException("Null result when requesting callUpoader");
            }
//            every uploaded file have uniqueID
            System.out.println("File ID: " + myFile.getId());

            this.dbID = myFile.getId();
            return  this.dbID;
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Task<String> callDownload(String uploadedFiledId, String fileId) {
//        String fileId = "11RIqrEladA8uwaSB_4rUz7bmU10EeVQm";
//        fileName ID: 11RIqrEladA8uwaSB_4rUz7bmU10EeVQm
        String filePath = "/storage/emulated/0/";
        OutputStream outputStream = new ByteArrayOutputStream();

        return  Tasks.call(executor, () -> {
            try {
                driveService.files().get(this.dbID)
                        .executeMediaAndDownloadTo(outputStream);
                /*Files.copy(Paths.get(filePath), outputStream);*/
                System.out.println(outputStream.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "hello";
        });
    }





}
