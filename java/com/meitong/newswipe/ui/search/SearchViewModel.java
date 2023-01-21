package com.meitong.newswipe.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.meitong.newswipe.model.NewsResponse;
import com.meitong.newswipe.repository.NewsRepository;

public class SearchViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<String> searchInput = new MutableLiveData<>();

    public SearchViewModel(NewsRepository repository) {
        this.repository = repository;
    }

    public void setSearchInput(String query) {
        searchInput.setValue(query);
    }

    public LiveData<NewsResponse> searchNews() {
        return Transformations.switchMap(searchInput, repository::searchNews);
    }
}