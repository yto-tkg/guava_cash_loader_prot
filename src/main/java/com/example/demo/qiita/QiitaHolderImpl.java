package com.example.demo.qiita;

import com.google.common.cache.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;

@Component
public class QiitaHolderImpl implements QiitaHolder {

    private static final int CACHE_SIZE = 500;
    private GetClient getClient;
    private Cache<String, Qiita> cache;

    @Inject
    public QiitaHolderImpl(GetClient getClient) {
        this(getClient, CACHE_SIZE);
    }

    QiitaHolderImpl(GetClient getClient, int cacheSize) {
        this.getClient = getClient;
        cache = CacheBuilder
                .newBuilder()
                .maximumSize(cacheSize)
                .concurrencyLevel(1)
                .build();
    }

    @Override
    public Map<String, Qiita> get(Collection<String> ids) {
        Map<String, Qiita> cached = cache.getAllPresent(ids);
        Map<String, Qiita> loaded = load(Sets.difference(toSet(ids), cached.keySet()));
        cache.putAll(loaded);
        return ImmutableMap.<String, Qiita> builder().putAll(cached).putAll(loaded).build();
    }

    private Map<String, Qiita> load(Collection<String> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }
        Qiita qiita = getClient.get(ids.stream().findFirst());
        return Map.of(qiita.getId(), qiita);
    }

    private static Set<String> toSet(Collection<String> ids) {
        if (ids instanceof Set) {
            return (Set<String>) ids;
        } else {
            return new HashSet<>(ids);
        }
    }
}
