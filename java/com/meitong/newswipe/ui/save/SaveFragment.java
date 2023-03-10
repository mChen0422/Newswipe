package com.meitong.newswipe.ui.save;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meitong.newswipe.R;
import com.meitong.newswipe.databinding.FragmentSaveBinding;
import com.meitong.newswipe.model.Article;
import com.meitong.newswipe.repository.NewsRepository;
import com.meitong.newswipe.repository.NewsViewModelFactory;
import com.meitong.newswipe.util.LogUtils;
import com.meitong.newswipe.util.ShareUtil;


public class SaveFragment extends Fragment {
    private FragmentSaveBinding binding;
    private SaveViewModel viewModel;
    private Article myArticle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSaveBinding.inflate(inflater, container, false);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_save, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SavedNewsAdapter adapter = new SavedNewsAdapter();
        adapter.setItemCallback(new SavedNewsAdapter.ItemCallback() {
            @Override
            public void onOpenDetails(Article article) {
                SaveFragmentDirections.ActionNavigationSaveToNavigationDetails direction =
                        SaveFragmentDirections.actionNavigationSaveToNavigationDetails(article);
                NavHostFragment.findNavController(SaveFragment.this).navigate(direction);
            }

            @Override
            public void onRemoveFavorite(Article article) {
                viewModel.deleteSavedArticle(article);
            }

            @Override
            public void onLongItem(View view1, Article article) {
                myArticle = article;
                myPopupMenu(view1);
            }
        });
//        adapter.setItemCallback(this);

        binding.newsSavedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.newsSavedRecyclerView.setAdapter(adapter);

        NewsRepository newsRepository = new NewsRepository();
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(newsRepository))
                .get(SaveViewModel.class);
//        adapter.setViewModel(viewModel);
//        adapter.setFragment(this);
        viewModel.getAllSavedArticles().observe(getViewLifecycleOwner(), savedArticles -> {
            if (savedArticles != null) {
                Log.d("SaveFragment", savedArticles.toString());
                adapter.setArticles(savedArticles);
            }
        });
    }

    public void deleteArticle(Article article) {
        viewModel.deleteSavedArticle(article);
    }


//    public class InnerClass implements SavedNewsAdapter.ItemCallback {
//
//        @Override
//        public void onOpenDetails(Article article) {
//            viewModel.deleteSavedArticle(article);
//        }
//
//        @Override
//        public void onRemoveFavorite(Article article) {
//
//        }
//    }

    private void myPopupMenu(View v) {
        //定义PopupMenu对象
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        //设置PopupMenu对象的布局
        popupMenu.getMenuInflater().inflate(R.menu.app_menus, popupMenu.getMenu());
        //设置PopupMenu的点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.share) {
                    ShareUtil.share(myArticle.url);
                } else if (item.getItemId() == R.id.delete) {
                    viewModel.deleteSavedArticle(myArticle);
                }
                return true;
            }
        });
        //显示菜单
        popupMenu.show();
    }
}