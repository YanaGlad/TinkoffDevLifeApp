package com.gladkikh.tinkoffsiriusapp;

import androidx.annotation.NonNull;

import com.gladkikh.tinkoffsiriusapp.api.Api;
import com.gladkikh.tinkoffsiriusapp.api.Instance;
import com.gladkikh.tinkoffsiriusapp.models.Gif;

import org.junit.Test;

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