package com.example.animeappjava.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.animeappjava.data.local.database.AnimeRecommendationsDatabase;
import com.example.animeappjava.data.remote.api.AnimeAPI;
import com.example.animeappjava.data.remote.api.MockAnimeAPI;
import com.example.animeappjava.models.AnimeHeader;
import com.example.animeappjava.models.AnimeRecommendation;
import com.example.animeappjava.models.AnimeRecommendationResponse;
import com.example.animeappjava.models.ImageUrl;
import com.example.animeappjava.models.Images;
import com.example.animeappjava.models.Pagination;
import com.example.animeappjava.models.User;
import com.example.animeappjava.ui.viewmodels.AnimeRecommendationsViewModel;
import com.google.gson.Gson;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import okhttp3.mockwebserver.MockResponse;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(AndroidJUnit4.class)
public class AnimeRecommendationsRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AnimeRecommendationsRepository repository;
    private AnimeRecommendationsViewModel viewModel;
    private AnimeAPI animeAPI;
    private AnimeRecommendationsDatabase database;
    private AnimeRecommendationResponse mockResponse;

    private MockWebServer mockWebServer;
    private Gson gson;

    @Before
    public void setup() throws IOException {
        mockResponse = createMockResponse();
        animeAPI = new MockAnimeAPI(mockResponse);
        gson = new Gson();

        mockWebServer = new MockWebServer();
        mockWebServer.start();
        // Initialize MockWebServer (optional - needed for specific network testing)

        // Initialize database
        database = Room.inMemoryDatabaseBuilder(
                        InstrumentationRegistry.getInstrumentation().getTargetContext(),
                        AnimeRecommendationsDatabase.class)
                .allowMainThreadQueries()
                .build();

        repository = new AnimeRecommendationsRepository(animeAPI, database);
    }

    @After
    public void teardown() {
        database.close(); // Close the database after the test
    }

    @Test
    public void testGetAnimeRecommendations() throws IOException, InterruptedException {
        // Enqueue a mock response
        AnimeRecommendationResponse mockResponse = createMockResponse();
        MockResponse mockWebServerResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(mockResponse));
        mockWebServer.enqueue(mockWebServerResponse);

        // Call the API
        Call<AnimeRecommendationResponse> call = animeAPI.getAnimeRecommendations(1);
        Response<AnimeRecommendationResponse> response = call.execute();

        // Assertions
        Assert.assertEquals(200, response.code());
        AnimeRecommendationResponse actualResponse = response.body();
        System.out.println("mockResponse: " + mockResponse);
        System.out.println("actualResponse: " + actualResponse);
        Assert.assertEquals(mockResponse, actualResponse); // Assuming you have an equals() method in AnimeRecommendationResponse
    }

    @Test
    public void repository_getAnimeRecommendations_returnsData() throws IOException {
        AnimeRecommendationResponse response = repository.getAnimeRecommendations(1).blockingGet();
        System.out.println("response: " + response);
        Assert.assertNotNull(response);
    }

    @Test
    public void performanceTest() throws InterruptedException {
        // Warm-up
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());

        // Arrays to store results
        int iterations = 10;
        long[] executionTimes = new long[iterations];
        long[] deserializationTimes = new long[iterations];
        double[] cpuUsages = new double[iterations];
        long[] memoryUsages = new long[iterations];

        for (int i = 0; i < iterations; i++) { // Run each test case 10 times
            // Execution Time
            executionTimes[i] = measureApiConsumptionTime();

            Thread.sleep(1000); // Delay between tests

            // Deserialization
            deserializationTimes[i] = measureDeserializationTime();

            Thread.sleep(1000); // Delay between tests

            // CPU Usage
            cpuUsages[i] = measureCpuUsageDuringApiConsumption();

            Thread.sleep(1000);

            // Memory Usage
            memoryUsages[i] = measureMemoryUsageDuringApiConsumption();
        }

        // Display results and calculate means
        System.out.println("\nResults:");
        System.out.println("Execution Times: " + Arrays.toString(executionTimes));
        System.out.println("Deserialization Times: " + Arrays.toString(deserializationTimes));
        System.out.println("CPU Usages: " + Arrays.toString(cpuUsages));
        System.out.println("Memory Usages: " + Arrays.toString(memoryUsages));

        System.out.println("\nMeans:");
        System.out.println("Mean Execution Time: " + calculateMean(executionTimes) + " ms");
        System.out.println("Mean Deserialization Time: " + calculateMean(deserializationTimes) + " ms");
        System.out.println("Mean CPU Usage: " + calculateMean(cpuUsages) + " %");
        System.out.println("Mean Memory Usage: " + calculateMean(memoryUsages) + " bytes");
    }

    private AnimeRecommendationResponse createMockResponse() {
        Pagination pagination = new Pagination(1, true);
        List<AnimeRecommendation> data = new ArrayList<>();

        // Create a sample AnimeRecommendation object
        // Note: Using Arrays.asList for entry as it's a List<AnimeHeader>
        AnimeRecommendation animeRecommendation = new AnimeRecommendation(
                "1",
                Arrays.asList(new AnimeHeader(
                        38524,
                        "https://myanimelist.net/anime/38524/Shingeki_no_Kyojin__The_Final_Season",
                        new Images(
                                new ImageUrl(
                                        "https://cdn.myanimelist.net/images/anime/1965/126125.jpg",
                                        "https://cdn.myanimelist.net/images/anime/1965/126125t.jpg",
                                        null,
                                        "https://cdn.myanimelist.net/images/anime/1965/126125l.jpg",
                                        null
                                ),
                                new ImageUrl(
                                        "https://cdn.myanimelist.net/images/anime/1965/126125.webp",
                                        "https://cdn.myanimelist.net/images/anime/1965/126125t.webp",
                                        null,
                                        "https://cdn.myanimelist.net/images/anime/1965/126125l.webp",
                                        null
                                )
                        ),
                        "Shingeki no Kyojin: The Final Season"
                )),
                "Amazing anime, highly recommended!",
                "2023-10-27T10:00:00.000Z",
                new User(
                        "testuser",
                        "https://myanimelist.net/profile/testuser"
                )
        );

        // Add the sample AnimeRecommendation object to the data list
        data.add(animeRecommendation);

        // You can add more AnimeRecommendation objects as needed

        return new AnimeRecommendationResponse(pagination, data);
    }

    // Helper function to calculate the mean of an array of longs
    private double calculateMean(long[] array) {
        long sum = 0;
        for (long value : array) {
            sum += value;
        }
        return (double) sum / array.length;
    }

    // Helper function to calculate the mean of an array of doubles
    private double calculateMean(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }

    private long measureApiConsumptionTime() {
        long startTime = System.currentTimeMillis();
        repository.getAnimeRecommendations(1).blockingGet(); // Focus on API call
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public <T> T getOrAwaitValue(LiveData<T> liveData) {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this); // Remove observer after getting value
            }
        };
        liveData.observeForever(observer);
        try {
            latch.await(2, TimeUnit.SECONDS); // Timeout after 2 seconds
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return (T) data[0];
    }

    private long measureDeserializationTime() {
        long deserializationStartTime = System.currentTimeMillis();
        AnimeRecommendationResponse mockResponse = createMockResponse(); // Initialize here
        String jsonResponse = gson.toJson(mockResponse); // Use the initialized mockResponse
        System.out.println("JSON Response: " + jsonResponse);  // Optional: Print for debugging
        AnimeRecommendationResponse response = gson.fromJson(jsonResponse, AnimeRecommendationResponse.class);
        long deserializationEndTime = System.currentTimeMillis();

        Assert.assertNotNull(response);

        return deserializationEndTime - deserializationStartTime;
    }

    private double measureCpuUsageDuringApiConsumption() {
        double cpuBefore = getCpuUsage();
        repository.getAnimeRecommendations(1).blockingGet(); // Simulate refresh
        double cpuAfter = getCpuUsage();

        return cpuAfter - cpuBefore;
    }

    private long measureMemoryUsageDuringApiConsumption() {
        long memoryBefore = getMemoryUsage();
        repository.getAnimeRecommendations(1).blockingGet(); // Simulate refresh
        long memoryAfter = getMemoryUsage();

        return memoryAfter - memoryBefore;
    }

    private double getCpuUsage() {
        try {
            // Get Runtime instance
            Runtime runtime = Runtime.getRuntime();
            // Get total memory
            long totalMemory = runtime.totalMemory();
            // Calculate used memory
            long usedMemory = -runtime.freeMemory();

            // Calculate CPU usage (approximation)
            double cpuUsage = (double) usedMemory / totalMemory * 100;

            return cpuUsage;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0; // Return 0 in case of error
        }
    }

    private long getMemoryUsage() {
        try {
            // Get Runtime instance
            Runtime runtime = Runtime.getRuntime();
            // Calculate used memory
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();

            return usedMemory;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Return 0 in case of error
        }
    }
}