package com.example.demo.qiita;

import com.google.common.base.Stopwatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QiitaController {

    private final QiitaHolder qiitaHolder;

    @RequestMapping({ "/qiita/{postId}" })
    public Qiita run(@PathVariable String postId) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        Map<String, Qiita> qiitaMap = qiitaHolder.get(List.of(postId));

        stopwatch.stop();
        log.info("Elapsed time: " + stopwatch);

        return qiitaMap.get(postId);
    }
}
