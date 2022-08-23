package com.example.demo.qiita;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Component
public interface QiitaHolder {

    Map<String, Qiita> get(Collection<String> ids);

}
