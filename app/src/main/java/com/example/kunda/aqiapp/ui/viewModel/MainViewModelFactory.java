package com.example.kunda.aqiapp.ui.viewModel;

import com.example.kunda.aqiapp.data.AirQualityRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Kundan on 01-10-2018.
 */
public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AirQualityRepository repository;

    public MainViewModelFactory(AirQualityRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(repository);
    }
}
