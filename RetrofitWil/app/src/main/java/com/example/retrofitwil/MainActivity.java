package com.example.retrofitwil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.retrofitwil.adapter.MoviesAdapter;
import com.example.retrofitwil.model.MovieModel;
import com.example.retrofitwil.model.MovieResult;
import com.example.retrofitwil.rest.ApiClient;
import com.example.retrofitwil.rest.ApiInterface;
import com.example.retrofitwil.util.RxUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;
    private ProgressDialog pDialog;

    /**
     * Subscription that represents a group of Subscriptions that are unsubscribed together.
     */
    private CompositeSubscription _subscriptions = new CompositeSubscription();
    // https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_main);
        initialRecyclerView();
        intialaizeProgress();
        displayMoviesList();
    }

    public void displayMoviesList() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("sort_by", "popularity");
        parameters.put("api_key", ApiClient.API_KEY);

        ApiInterface myApi = ApiClient.getClient().create(ApiInterface.class);
        myApi.getMovieDetails(parameters)
                // operators (the rx java methods ie. subscribeOn, observeOn)
                .subscribeOn(Schedulers.io())
                // ^ upstream methods from observeOn
                .observeOn(AndroidSchedulers.mainThread())
                // \/ downstream methods from observeOn
                .subscribe(new Observer<MovieResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(MovieResult movieResult) {
                        if ( mRecyclerView != null) {
                            List<MovieModel> movies = movieResult.getResults();
                            mAdapter = new MoviesAdapter(movies, R.layout.card_row, getApplicationContext());
                            mRecyclerView.setAdapter(mAdapter);
                            hidePDialog();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hidePDialog();
                        Log.d("DEBUG", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        _subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(_subscriptions);
    }

    @Override
    public void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(_subscriptions);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    public void initialRecyclerView(){
        mRecyclerView = (RecyclerView)findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    public void intialaizeProgress(){
        pDialog = new ProgressDialog(this);
        // display progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}