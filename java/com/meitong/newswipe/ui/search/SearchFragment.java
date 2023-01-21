package com.meitong.newswipe.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meitong.newswipe.R;
import com.meitong.newswipe.databinding.FragmentSearchBinding;
import com.meitong.newswipe.model.Article;
import com.meitong.newswipe.repository.NewsRepository;
import com.meitong.newswipe.repository.NewsViewModelFactory;
import com.meitong.newswipe.util.LogUtils;

public class SearchFragment extends Fragment {
    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;
    private boolean isAuto = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_search, container, false);
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAuto) {
            menuItem.setTitle("lazy mode: off");
            startSmooth();
        } else {
            menuItem.setTitle("lazy mode: off");
            stopSmooth();
        }

        binding.newsResultsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (binding.newsResultsRecyclerView.canScrollVertically(1)) {
                    LogUtils.INSTANCE.e("direction 1: true");
                } else {
                    LogUtils.INSTANCE.e("direction 1: false");
                    // 到底部了
                    menuItem.setTitle("lazy mode: off");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.auto_open: {
                // navigate to settings screen
                if (isAuto) {
                    menuItem.setTitle("lazy mode: off");
                    isAuto = false;
                    stopSmooth();
                } else {
                    menuItem.setTitle("lazy mode: on");
                    isAuto = true;
                    startSmooth();
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    MenuItem menuItem;

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menuItem = menu.findItem(R.id.auto_open);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchNewsAdapter newsAdapter = new SearchNewsAdapter();
        newsAdapter.setItemCallback(new SearchNewsAdapter.ItemCallback() {
            @Override
            public void onOpenDetails(Article article) {
                SearchFragmentDirections.ActionNavigationSearchToNavigationDetails direction =
                        SearchFragmentDirections.actionNavigationSearchToNavigationDetails(article);
                NavHostFragment.findNavController(SearchFragment.this).navigate(direction);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        binding.newsResultsRecyclerView.setLayoutManager(gridLayoutManager);
        binding.newsResultsRecyclerView.setAdapter(newsAdapter);

        binding.newsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("start search", "" + query);
                if (!query.isEmpty()) {
                    viewModel.setSearchInput(query);
                }
                binding.newsSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        NewsRepository newsRepository = new NewsRepository();
//        viewModel = new SearchViewModel(newsRepository);
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(newsRepository))
                .get(SearchViewModel.class);

        viewModel.searchNews().observe(getViewLifecycleOwner(), newsResponse -> {
            LogUtils.INSTANCE.e("start search", "" + newsResponse.articles.size());
            if (newsResponse != null) {
                Log.d("SearchFragment", newsResponse.toString());
                newsAdapter.setArticles(newsResponse.articles);
//                startSmooth();
//                binding.newsResultsRecyclerView.start();
//                startSmooth();
            }
        });
    }

    private void startSmooth() {
        binding.newsResultsRecyclerView.start();
    }

    private void stopSmooth() {
        binding.newsResultsRecyclerView.stop();
    }
}