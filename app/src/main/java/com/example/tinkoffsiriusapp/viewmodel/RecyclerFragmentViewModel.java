package com.example.tinkoffsiriusapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tinkoffsiriusapp.api.Api;
import com.example.tinkoffsiriusapp.api.Instance;
import com.example.tinkoffsiriusapp.models.GifModel;
import com.example.tinkoffsiriusapp.values.ErrorHandler;
import com.example.tinkoffsiriusapp.values.PageOperation;
import com.example.tinkoffsiriusapp.models.Gif;
import com.example.tinkoffsiriusapp.models.Gifs;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerFragmentViewModel extends PageViewModel {

    private MutableLiveData<String> type = new MutableLiveData<>(null);
    private MutableLiveData<Integer> currentPage = new MutableLiveData<>(0);
    private MutableLiveData<ErrorHandler> error = new MutableLiveData<>(new ErrorHandler());
    private MutableLiveData<ArrayList<GifModel>> gifModels = new MutableLiveData<>(null);

    private Callback<Gifs> gifsCallback = new Callback<Gifs>() {
        @Override
        public void onResponse(@NonNull Call<Gifs> call, Response<Gifs> response) {
            if (response.isSuccessful()) {
                setCanLoadNext(true);
                if (response.body() != null) {
                    ArrayList<GifModel> result = new ArrayList<>();
                    for (Gif gif : response.body().getGifs())
                        result.add(gif.createGifModel());
                    gifModels.setValue(result);
                }
            } else {
                ErrorHandler errorHandler = new ErrorHandler();
                errorHandler.setLoadError();
                setError(errorHandler);
            }
        }

        @Override
        public void onFailure(@NonNull Call<Gifs> call, @NonNull Throwable t) {
            ErrorHandler errorHandler = new ErrorHandler();
            errorHandler.setLoadError();
            setError(errorHandler);
        }
    };


    public void loadGifs(PageOperation pageOperation, String type) {
        super.setCanLoadNext(getError().getValue().getCurrentError().equals(ErrorHandler.success()));
        setCurrentPage(getCurrentPage().getValue(), pageOperation);
        Api service = Instance.getInstance().create(Api.class);

        switch (type) {
            case "latest":
                service.getLatestGifs(getCurrentPage().getValue(), 10, "gif").enqueue(gifsCallback);
                break;
            case "top":
                service.getTopGifs(getCurrentPage().getValue(), 10, "gif").enqueue(gifsCallback);
                break;
        }
    }

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
        this.currentPage.setValue(currentPage + + pageOperation(pageOperation));
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