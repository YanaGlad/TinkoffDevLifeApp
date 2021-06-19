package com.gladkikh.tinkoffsiriusapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    private MutableLiveData<Boolean> canLoadPrevious = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> canLoadNext = new MutableLiveData<>(false);

    public LiveData<Boolean> getCanLoadPrevious() {
        return canLoadPrevious;
    }

    public void setCanLoadPrevious(boolean canLoadPrevious) {
        this.canLoadPrevious.setValue(canLoadPrevious);
    }

    public LiveData<Boolean> getCanLoadNext() {
        return canLoadNext;
    }

    public void setCanLoadNext(boolean canLoadNext) {
        this.canLoadNext.setValue(canLoadNext);
    }

}

