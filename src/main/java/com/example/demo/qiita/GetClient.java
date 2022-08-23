package com.example.demo.qiita;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetClient {

    private static final String QIITA_URL = "https://qiita.com/api/v2/items/";

    private final RestTemplate restTemplate;

    public Qiita get(Optional<String> id) {
        return restTemplate.getForObject(QIITA_URL + id.get(), Qiita.class);
    }
}
