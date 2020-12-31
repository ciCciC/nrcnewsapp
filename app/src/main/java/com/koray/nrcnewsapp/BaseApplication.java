package com.koray.nrcnewsapp;

import android.app.Application;

import com.koray.nrcnewsapp.core.network.NrcScraperClient;
import com.koray.nrcnewsapp.core.network.repository.ArticleRepository;
import com.koray.nrcnewsapp.core.network.repository.CategoryRepository;
import com.koray.nrcnewsapp.core.network.viewmodel.LiveArticleModel;
import com.koray.nrcnewsapp.core.network.viewmodel.LiveCategoriesModel;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

public class BaseApplication extends Application {

    public ApplicationContext ctx;

    public BaseApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = ApplicationContext.build(NrcActivity.class, Environment.ANDROID).start();
        ctx.inject(NrcScraperClient.class);
        ctx.inject(ArticleRepository.class);
        ctx.inject(CategoryRepository.class);
        ctx.inject(LiveCategoriesModel.class);
        ctx.inject(LiveArticleModel.class);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if(ctx != null && ctx.isRunning()) {
            ctx.stop();
        }
    }
}
