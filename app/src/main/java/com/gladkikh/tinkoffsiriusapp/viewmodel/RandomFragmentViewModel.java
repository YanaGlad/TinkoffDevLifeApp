package com.gladkikh.tinkoffsiriusapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.gladkikh.tinkoffsiriusapp.models.GifModel;
import com.gladkikh.tinkoffsiriusapp.values.ErrorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RandomFragmentViewModel extends PageViewModel {

    private MutableLiveData<Boolean> isCurrentGifLoaded = new MutableLiveData<>(false);
    private MutableLiveData<GifModel> currentGif = new MutableLiveData<>(null);
    private MutableLiveData<ErrorHandler> error = new MutableLiveData<>(new ErrorHandler());

    private List<GifModel> gifModels = new ArrayList<>();
    private static GifModel cachedCurrentGif = null;
    private static int pos = -1;


    public MutableLiveData<ErrorHandler> getError() {
        return error;
    }

    public void setAppError(ErrorHandler error) {
        this.error.setValue(error);
    }

    public MutableLiveData<Boolean> getIsCurrentGifLoaded() {
        return isCurrentGifLoaded;
    }

    public void setIsCurrentGifLoaded(boolean isCurrentGifLoaded) {
        this.isCurrentGifLoaded.setValue(isCurrentGifLoaded);
    }

    public void updateCanLoadPrevious() {
        boolean hasErrors = !Objects.requireNonNull(this.error.getValue()).getCurrentError().equals(ErrorHandler.success());
        Log.d("CAN LOAD PREV", "Pos " + (pos - 1) + " noerrors " + !hasErrors);
        super.setCanLoadPrevious(!hasErrors && pos - 1 >= 0);
    }

    public LiveData<GifModel> getCurrentGif() {
        return currentGif;
    }

    public void addGifModel(GifModel gifModel) {
        Log.d("Add", "POS " + pos);
        currentGif.setValue(gifModel);
        gifModels.add(gifModel);
        pos++;
        cachedCurrentGif = gifModels.get(gifModels.size() - 1);
        updateCanLoadPrevious();
    }

    public boolean goNext() {
        Log.d("Next", "POS " + pos);
        if (cachedCurrentGif != null && pos < gifModels.size() - 1) {
            pos++;
            cachedCurrentGif = gifModels.get(pos);
            currentGif.setValue(cachedCurrentGif);
            updateCanLoadPrevious();
            return true;
        }
        updateCanLoadPrevious();

        return false;
    }

    public boolean goBack() {
        Log.d("Prev", "POS " + pos);

        if (pos - 1 >= 0) {
            pos--;
            cachedCurrentGif = gifModels.get(pos);
            currentGif.setValue(cachedCurrentGif);
            updateCanLoadPrevious();
            return true;
        }

        updateCanLoadPrevious();
        return false;
    }

}
