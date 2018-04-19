package io.makeorbreak.hackohollics.onfrugal.data.local;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class UserRepository implements io.makeorbreak.hackohollics.onfrugal.domain.repository.UserRepository{
    private static final UserRepository ourInstance = new UserRepository();

    public static UserRepository getInstance() {
        return ourInstance;
    }

    private UserRepository() {
    }

    @Override
    public String getDisplayName() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }

    @Override
    public String getToken() {
        final StringBuilder token = new StringBuilder() ;
        final CountDownLatch countDownLatch = new CountDownLatch(1) ;
        FirebaseAuth.getInstance().getCurrentUser().getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                token.append(task.getResult().getToken());
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await(5, TimeUnit.SECONDS);
            return token.toString() ;
        } catch (InterruptedException ie) {
            return null;
        }
    }

    @Override
    public String getUID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public Uri getPhotoUrl() {
        return FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
    }

    @Override
    public String getEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
}
