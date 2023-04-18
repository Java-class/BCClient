package ir.javaclass.job;

import ir.javaclass.config.Commons;
import ir.javaclass.service.FileService;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class SyncJob implements Job {

    private final FileService fileService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("### SYNC JOB STARTED...");
        try {
            File syncFolder = new File(Commons.SYNC_FOLDER_ADDRESS);
            if (syncFolder.exists()) {
                if (syncFolder.isDirectory()) {
                    fileService.sync(Commons.USER_PUBLIC_KEY, Commons.USER_PRIVATE_KEY, syncFolder.getAbsolutePath());
                }
            } else {
                System.out.println(" sync folder not found..");
                try {
                    jobExecutionContext.getScheduler().shutdown();
                    System.out.println("### Sync Job Shutdown...");
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
