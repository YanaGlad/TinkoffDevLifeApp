package com.gladkikh.tinkoffsiriusapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.gladkikh.tinkoffsiriusapp.models.GifModel;
import com.gladkikh.tinkoffsiriusapp.values.ErrorHandler;
import com.gladkikh.tinkoffsiriusapp.values.PageOperation;
import com.gladkikh.tinkoffsiriusapp.models.Gif;
import java.util.ArrayList;

public class RecyclerFragmentViewModel extends PageViewModel {

    private MutableLiveData<String> type = new MutableLiveData<>(null);
    private MutableLiveData<Integer> currentPage = new MutableLiveData<>(0);
    private MutableLiveData<ErrorHandler> error = new MutableLiveData<>(new ErrorHandler());
    private MutableLiveData<ArrayList<GifModel>> gifModels = new MutableLiveData<>(null);

    public void setCanLoadNext(boolean canLoadNext) {
        super.setCanLoadNext(canLoadNext);
    }

    public void updateCanLoadPrevious() {
        super.setCanLoadPrevious(getCurrentPage().getValue() > 0 &&
                getError().getValue().getCurrentError().equals(ErrorHandler.success()));
    }

    public MutableLiveData<String> getType() {
        return type;
    }

    public MutableLiveData<Integer> getCurrentPage() {
        return currentPage;
    }

    public MutableLiveData<Boolean> getCanLoadNext() {
        return super.getCanLoadNext();
    }

    public MutableLiveData<Boolean> getCanLoadPrevious() {
        return super.getCanLoadPrevious();
    }

    public void setType(String type) {
        this.type.setValue(type);
    }

    public void setCurrentPage(Integer currentPage, PageOperation pageOperation) {
        this.currentPage.setValue(currentPage + pageOperation.getPos());
    }

    public MutableLiveData<ErrorHandler> getError() {
        return error;
    }

    public void setError(ErrorHandler error) {
        this.error.setValue(error);
    }

    public void createListOfGifModels(ArrayList<Gif> gifs) {
        ArrayList<GifModel> result = new ArrayList<>();

        for (Gif gif : gifs)
            result.add(gif.createGifModel());

        setGifModels(result);
    }

    public MutableLiveData<ArrayList<GifModel>> getGifModels() {
        return gifModels;
    }

    private void setGifModels(ArrayList<GifModel> gifModels) {
        this.gifModels.setValue(gifModels);
    }
}