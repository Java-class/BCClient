package ir.javaclass.job;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzSubmitJobs {
    private static final String CRON_EVERY_10SECONDS = "0/10 * * * * ?";

    @Bean(name = "sync")
    public JobDetailFactoryBean jobSync() {
        return QuartzConfig.createJobDetail(SyncJob.class, "Sync Uploader Statistics Job");
    }

    @Bean(name = "syncTrigger")
    public CronTriggerFactoryBean triggerSyncs(@Qualifier("sync") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_10SECONDS, "Sync Uploader  Statistics Trigger");
    }
}