package com.gladkikh.tinkoffsiriusapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    private MutableLiveData<Boolean> canLoadPrevious = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> canLoadNext = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getCanLoadPrevious() {
        return canLoadPrevious;
    }

    public void setCanLoadPrevious(boolean canLoadPrevious) {
        this.canLoadPrevious.setValue(canLoadPrevious);
    }

    public MutableLiveData<Boolean> getCanLoadNext() {
        return canLoadNext;
    }

    public void setCanLoadNext(boolean canLoadNext) {
        this.canLoadNext.setValue(canLoadNext);
    }

//    public void observeButtons(LifecycleOwner lifecycleOwner,
//                               boolean isOnScreen, ExtendedFloatingActionButton btnNext,
//                               ExtendedFloatingActionButton btnPrev){
//        this.getCanLoadNext().observe(lifecycleOwner, (Boolean enabled) -> {
//            if (isOnScreen)
//                btnNext.setEnabled(enabled);
//        });
//
//        this.getCanLoadPrevious().observe(lifecycleOwner, enabled -> {
//            if (isOnScreen)
//                btnPrev.setEnabled(enabled);
//        });
//    }
}
