package ir.javaclass.job;

import ir.javaclass.config.Commons;
import ir.javaclass.entity.FileItem;
import ir.javaclass.service.FileService;
import org.quartz.*;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@DisallowConcurrentExecution
public class SyncJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("### SYNC JOB STARTED...");
        try {
            File syncFolder = new File(Commons.SYNC_FOLDER_ADDRESS);
            if (syncFolder.exists()) {
                if (syncFolder.isDirectory()) {
                    FileService.sync(Commons.USER_PUBLIC_KEY, Commons.USER_PRIVATE_KEY, syncFolder.getAbsolutePath());
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
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
