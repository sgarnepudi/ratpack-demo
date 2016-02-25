package com.github.phillbarber.service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import ratpack.rx.RxRatpack;
import rx.Observable;
import rx.observable.ListenableFutureObservable;
import rx.schedulers.Schedulers;

public class BlockingCassandraDAO {

    private Session session;

    public BlockingCassandraDAO(Session session) {
        this.session = session;
    }

    public Observable<String> getValueFromDB() {
        ResultSetFuture resultSetFuture = session.executeAsync(new SimpleStatement("select dummy from dummy"));
        Observable<ResultSet> resultSetObservable = ListenableFutureObservable.from(resultSetFuture, Schedulers.computation());
        return resultSetObservable.map(resultSet -> {
            return resultSet.one().getString("dummy");
        });
    }



}
