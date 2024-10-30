package com.example.animeappjava.data.remote.api;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class MockCall<T> implements Call<T> {

    private final T response;

    public MockCall(T response) {
        this.response = response;
    }

    @Override
    public Response<T> execute() throws IOException {
        return Response.success(response); // Return the mock response
    }

    @Override
    public void enqueue(Callback<T> callback) {
        callback.onResponse(this, Response.success(response)); // Simulate success
    }

    // ... (other methods from the Call interface that you need to implement) ...

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public void cancel() {
        // Do nothing - this is a mock
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public Call<T> clone() {
        return null;
    }

    @Override
    public Request request() {
        return null;
    }

    @Override
    public Timeout timeout() {
        return null;
    }
}