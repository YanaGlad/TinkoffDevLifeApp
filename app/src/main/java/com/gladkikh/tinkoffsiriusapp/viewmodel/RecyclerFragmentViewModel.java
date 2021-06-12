package com.gladkikh.tinkoffsiriusapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.gladkikh.tinkoffsiriusapp.api.Api;
import com.gladkikh.tinkoffsiriusapp.api.Instance;
import com.gladkikh.tinkoffsiriusapp.models.GifModel;
import com.gladkikh.tinkoffsiriusapp.values.ErrorHandler;
import com.gladkikh.tinkoffsiriusapp.values.PageOperation;
import com.gladkikh.tinkoffsiriusapp.models.Gif;
import com.gladkikh.tinkoffsiriusapp.models.Gifs;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerFragmentViewModel extends PageViewModel {

    private MutableLiveData<String> type = new MutableLiveData<>(null);
    private MutableLiveData<Integer> currentPage = new MutableLiveData<>(0);
    private MutableLiveData<ErrorHandler> error = new MutableLiveData<>(new ErrorHandler());
    public MutableLiveData<ArrayList<GifModel>> gifModels = new MutableLiveData<>(null);

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
        this.currentPage.setValue(currentPage + pageOperation(pageOperation));
    }

    private static int pageOperation(PageOperation pageOperation) {
        switch (pageOperation) {
            case STAND:
                return 0;
            case NEXT:
                return 1;
            case PREVIOUS:
                return -1;
        }
        return 0;
    }

    public MutableLiveData<ErrorHandler> getError() {
        return error;
    }

    public void setError(ErrorHandler error) {
        this.error.setValue(error);
    }

    public MutableLiveData<ArrayList<GifModel>> getGifModels() {
        return gifModels;
    }
}