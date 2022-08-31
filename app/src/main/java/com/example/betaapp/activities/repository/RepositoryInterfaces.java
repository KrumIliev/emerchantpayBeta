package com.example.betaapp.activities.repository;

import com.example.betaapp.utils.ReceiverBinding;
import com.example.betaapp.utils.Lifecycle;

public interface RepositoryInterfaces {

    interface View {
        void disableStarButton();

        void enableStarButton();

        void updateStarStatus(boolean isStarred);

        void showStarError();
    }

    interface Model extends ReceiverBinding {
        interface onStaringCompletedListener {
            void onStarSuccessful(boolean isStarred);

            void onStarFailed();
        }

        void starRepo();
    }

    interface Presenter extends Lifecycle {
        void onStarClicked();
    }
}
