package com.example.animeappjava.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.example.animeappjava.data.local.database.AnimeRecommendationsDatabase;
import com.example.animeappjava.data.remote.api.AnimeAPI;
import com.example.animeappjava.data.remote.api.RetrofitInstance;
import com.example.animeappjava.models.AnimeRecommendationResponse;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import retrofit2.Response;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class AnimeRecommendationsRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AnimeRecommendationsRepository repository;
    private AnimeAPI animeAPI;
    private AnimeRecommendationsDatabase database;
    private final Gson gson = new Gson();

    @Before
    public void setup() {
        animeAPI = RetrofitInstance.api;

        database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                AnimeRecommendationsDatabase.class
        ).allowMainThreadQueries().build();

        repository = new AnimeRecommendationsRepository(animeAPI, database);
    }

    @After
    public void teardown() {
        database.close();
    }

    @Test
    public void performanceTest() throws InterruptedException, IOException {
        int iterations = 10;
        long[] serializationTimes = new long[iterations];
        long[] deserializationTimes = new long[iterations];

        // Get the response synchronously on the current thread
        Response<AnimeRecommendationResponse> realApiResponse = animeAPI.getAnimeRecommendations(1).execute();
        AnimeRecommendationResponse realResponseData = realApiResponse.body();

        if (realResponseData != null) {
            // Warm-up
            for (int i = 0; i < 3; i++) {
                measureSerializationTime(realResponseData);
                measureDeserializationTime(realResponseData);
            }

            for (int i = 0; i < iterations; i++) {
                serializationTimes[i] = measureSerializationTime(realResponseData);
                deserializationTimes[i] = measureDeserializationTime(realResponseData);
                Thread.sleep(1000); // Replace delay with Thread.sleep()
            }

            System.out.println("\nResults:");
            System.out.println("Serialization Times: " + java.util.Arrays.toString(serializationTimes));
            System.out.println("Deserialization Times: " + java.util.Arrays.toString(deserializationTimes));

            System.out.println("\nMeans:");
            System.out.println("Mean Serialization Time: " + calculateAverage(serializationTimes) + " ms");
            System.out.println("Mean Deserialization Time: " + calculateAverage(deserializationTimes) + " ms");
        } else {
            System.out.println("Error fetching real API response");
        }
    }


    private long measureSerializationTime(AnimeRecommendationResponse data) {
        long startTime = System.currentTimeMillis();
        gson.toJson(data); // Gson serialization
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private long measureDeserializationTime(AnimeRecommendationResponse data) {
        String jsonString = gson.toJson(data); // Gson serialization
        long startTime = System.currentTimeMillis();
        gson.fromJson(jsonString, AnimeRecommendationResponse.class); // Gson deserialization
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private double calculateAverage(long[] array) {
        long sum = 0;
        for (long value : array) {
            sum += value;
        }
        return (double) sum / array.length;
    }
}