package com.example.animeappjava.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.chuckerteam.chucker.api.BodyDecoder;
import com.chuckerteam.chucker.api.Chucker;
import com.chuckerteam.chucker.api.ChuckerCollector;
import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.chuckerteam.chucker.api.RetentionManager;
import com.example.animeappjava.R;
import com.example.animeappjava.data.local.database.AnimeDetailDatabase;
import com.example.animeappjava.data.local.database.AnimeRecommendationsDatabase;
import com.example.animeappjava.data.remote.api.RetrofitInstance;
import com.example.animeappjava.databinding.ActivityMainBinding;
import com.example.animeappjava.repository.AnimeDetailRepository;
import com.example.animeappjava.repository.AnimeRecommendationsRepository;
import com.example.animeappjava.ui.providerfactories.AnimeDetailViewModelProviderFactory;
import com.example.animeappjava.ui.providerfactories.AnimeRecommendationsViewModelProviderFactory;
import com.example.animeappjava.ui.viewmodels.AnimeDetailViewModel;
import com.example.animeappjava.ui.viewmodels.AnimeRecommendationsViewModel;
import com.example.animeappjava.utils.NetworkUtils;
import com.example.animeappjava.utils.Resource;
import com.example.animeappjava.utils.ShakeDetector;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import okhttp3.Request;
import okhttp3.Response;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AnimeRecommendationsViewModel animeRecommendationsViewModel;
    private AnimeDetailViewModel animeDetailViewModel;
    private SensorManager sensorManager;
    private ShakeDetector shakeDetector;
    private boolean isDataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this).setKeepOnScreenCondition(() -> !isDataLoaded);
        setupSensor();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.animeRecommendationsFragment);
        if (fragment == null) {
            setupNavigation();
            setupViewModels();
            observeAnimeRecommendations();
            setupChucker();
        } else {
            getSupportFragmentManager().beginTransaction().hide(fragment).commit();
        }
    }

    private void setupSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        shakeDetector = new ShakeDetector(() -> startActivity(Chucker.getLaunchIntent(this)));
    }

    private void setupNavigation() {
        BottomNavigationView navView = binding.navView;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.animeRecommendationsFragment, R.id.aboutFragment)
                .build();
        NavigationUI.setupActionBarWithNavController(this, Navigation.findNavController(this, R.id.nav_host_fragment_activity_main), appBarConfiguration);
        NavigationUI.setupWithNavController(navView, Navigation.findNavController(this, R.id.nav_host_fragment_activity_main));
    }

    private void setupViewModels() {
        // Initialize animeRecommendationsViewModel
        AnimeRecommendationsRepository recommendationsRepository = new AnimeRecommendationsRepository(AnimeRecommendationsDatabase.getDatabase(this));
        AnimeRecommendationsViewModelProviderFactory recommendationsFactory = new AnimeRecommendationsViewModelProviderFactory(recommendationsRepository);
        animeRecommendationsViewModel = new ViewModelProvider(this, recommendationsFactory).get(AnimeRecommendationsViewModel.class);

        // Initialize animeDetailViewModel
        AnimeDetailRepository detailRepository = new AnimeDetailRepository(AnimeDetailDatabase.getDatabase(this).getAnimeDetailDao());
        AnimeDetailViewModelProviderFactory detailFactory = new AnimeDetailViewModelProviderFactory(detailRepository);
        animeDetailViewModel = new ViewModelProvider(this, detailFactory).get(AnimeDetailViewModel.class);
    }

    private void observeAnimeRecommendations() {
        animeRecommendationsViewModel.getAnimeRecommendations().observe(this, new Observer<Resource<?>>() {
            @Override
            public void onChanged(Resource<?> resource) {
                if (resource instanceof Resource.Success) {
                    isDataLoaded = true;
                } else if (resource instanceof Resource.Error && !isDataLoaded) {
                    showSnackbar("Error loading data. Please check your connection.");
                }
            }
        });

        if (!NetworkUtils.isNetworkAvailable(this)) {
            showSnackbar("No internet connection. Please check your network settings.", Snackbar.LENGTH_INDEFINITE);
        }
    }

    private void showSnackbar(String message) {
        showSnackbar(message, Snackbar.LENGTH_SHORT);
    }

    private void showSnackbar(String message, int duration) {
        Snackbar.make(binding.getRoot(), message, duration).show();
    }

    public AnimeRecommendationsViewModel getAnimeRecommendationsViewModel() {
        return animeRecommendationsViewModel;
    }

    public AnimeDetailViewModel getAnimeDetailViewModel() {
        return animeDetailViewModel;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getAction() != null && intent.getAction().equals(Intent.ACTION_VIEW) && intent.getScheme() != null && intent.getScheme().equals("animeappjava") && intent.getData() != null) {
            handleAnimeUrl(intent.getData());
        }
    }

    private void setupChucker() {
        BodyDecoder decoder = new BodyDecoder() {
            @Override
            public String decodeRequest(Request request, ByteString body) {
                return body.utf8();
            }

            @Override
            public String decodeResponse(Response response, ByteString body) {
                return body.utf8();
            }
        };

        ChuckerCollector chuckerCollector = new ChuckerCollector(
                this,
                true,
                RetentionManager.Period.ONE_HOUR
        );

        ChuckerInterceptor chuckerInterceptor = new ChuckerInterceptor.Builder(this)
                .collector(chuckerCollector)
                .maxContentLength(250_000L)
                .redactHeaders("Auth-Token", "Bearer")
                .alwaysReadResponseBody(true)
                .addBodyDecoder(decoder)
                .createShortcut(true)
                .build();

        RetrofitInstance.addInterceptor(chuckerInterceptor);
    }

    private void handleAnimeUrl(Uri uri) {
        if (uri != null && uri.getPathSegments() != null && uri.getPathSegments().size() >= 2 && uri.getPathSegments().get(0).equals("detail")) {
            Integer id = null;
            try {
                id = Integer.parseInt(uri.getPathSegments().get(1));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (id != null) {
                NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                if (navHostFragment != null) {
                    NavController navController = navHostFragment.getNavController();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    NavOptions navOptions = new NavOptions.Builder()
                            .setEnterAnim(R.anim.slide_in_right)
                            .setExitAnim(R.anim.slide_out_left)
                            .setPopEnterAnim(R.anim.slide_in_left)
                            .setPopExitAnim(R.anim.slide_out_right)
                            .build();
                    navController.navigate(R.id.action_global_animeDetailFragment, bundle, navOptions);
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            return navController.navigateUp() || super.onSupportNavigateUp();
        } else {
            return super.onSupportNavigateUp();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeDetector);
    }
}