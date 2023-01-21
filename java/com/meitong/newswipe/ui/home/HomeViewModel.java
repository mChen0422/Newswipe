package com.meitong.newswipe.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.meitong.newswipe.model.Article;
import com.meitong.newswipe.model.NewsResponse;
import com.meitong.newswipe.repository.NewsRepository;

// Data Sore
// observable ui state: liveData<NewsRespoonse>
public class HomeViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<String> countryInput = new MutableLiveData<>();

    public HomeViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    // event
    public void setCountryInput(String country) {
        // data store change logic
        countryInput.setValue(country);
    }

    public LiveData<NewsResponse> getTopHeadlines() {
        // country input livedata -> switch/translate -> top headline livedata
        return Transformations.switchMap(countryInput, repository::getTopHeadlines);
    }

    public void setFavoriteArticleInput(Article article) {
        repository.favoriteArticle(article);
    }
}
