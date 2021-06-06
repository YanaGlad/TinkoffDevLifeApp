package com.example.tinkoffsiriusapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.tinkoffsiriusapp.values.ErrorHandler;


public class GifViewModel extends ViewModel {

    private MutableLiveData<Boolean> isCurrentGifLoaded = new MutableLiveData<>(false);
    private MutableLiveData<ErrorHandler> error = new MutableLiveData<>(new ErrorHandler());

    public MutableLiveData<ErrorHandler> getError() {
        return error;
    }

    public void setError(ErrorHandler error) {
        this.error.setValue(error);
    }
    public MutableLiveData<Boolean> getIsCurrentGifLoaded() {
        return isCurrentGifLoaded;
    }

    public void setIsGifLoaded(boolean isCurrentGifLoaded) {
        this.isCurrentGifLoaded.setValue(isCurrentGifLoaded);
    }

}
