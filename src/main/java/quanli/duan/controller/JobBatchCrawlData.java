package quanli.duan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import quanli.duan.service.CrawlDataService;

@Configuration
@EnableBatchProcessing
@EnableScheduling
@RequiredArgsConstructor
public class JobBatchCrawlData {
    final CrawlDataService crawlDataService;

//    @Scheduled(cron = "*/60 * * * * *")
     public void CrawlData(){
//        JobParameters param = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
        this.crawlDataService.crawlDataToWeb();
     }
}
