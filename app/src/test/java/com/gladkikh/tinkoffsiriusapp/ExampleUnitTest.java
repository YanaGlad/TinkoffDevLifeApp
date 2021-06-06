package com.example.tinkoffsiriusapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tinkoffsiriusapp.api.Api;
import com.example.tinkoffsiriusapp.api.Instance;
import com.example.tinkoffsiriusapp.models.Gif;
import com.example.tinkoffsiriusapp.values.ErrorHandler;

import org.junit.Test;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void isApiCorrect() {
        Api api = Instance.getInstance().create(Api.class);
        assert api != null;
    }

    @Test
    public void randomGifTest() {
        Api api = Instance.getInstance().create(Api.class);
        api.getRandomGif().enqueue(gifCallback);
    }

    private Callback<Gif> gifCallback = new Callback<Gif>() {
        @Override
        public void onResponse(@NonNull Call<Gif> call, Response<Gif> response) {
            assertTrue(response.isSuccessful() && response.body().getGifURL() != null);
        }

        @Override
        public void onFailure(Call<Gif> call, Throwable t) {
        }
    };
}