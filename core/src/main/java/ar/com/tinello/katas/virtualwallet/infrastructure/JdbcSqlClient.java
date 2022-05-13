package ar.com.tinello.katas.virtualwallet.infrastructure;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.reactivex.ext.sql.SQLClient;

public class JdbcSqlClient {
    private final SQLClient client;

    public JdbcSqlClient(final SQLClient client) {
        this.client = client;
    }

    public Maybe<JsonArray> rxQuerySingleWithParams(final String sql, final JsonArray arguments) {
        return client.rxQuerySingleWithParams(sql, arguments);
    }

    public Single<ResultSet> rxQueryWithParams(final String sql, final JsonArray arguments) {
        return client.rxQueryWithParams(sql, arguments);
    }

    public Single<UpdateResult> rxUpdateWithParams(final String sql, final JsonArray arguments) {
        return client.rxUpdateWithParams(sql, arguments);
    }
}
