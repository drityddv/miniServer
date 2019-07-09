package utils;

import java.util.Collection;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author : ddv
 * @since : 2019/7/8 下午11:00
 */

public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        Collection<Object> values = dataMap.values();
        for (Object value : values) {
            System.out.println(value);
        }
    }
}
