package com.file.upload.okta.advices;

import jakarta.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TrackExecutionTimeAdvice {

  StringBuilder stringBuilder = new StringBuilder();
  private static final Integer SLA = 5;

  @PostConstruct
  void init() {
    stringBuilder.append("<html><head><style>#timetrack {font-family: Arial, Helvetica, sans-serif;border-collapse: collapse;width: 100%;}#timetrack td, #timetrack th {border: 1px solid #ddd;padding: 8px;}#timetrack tr:nth-child(even){background-color: #f2f2f2;}#timetrack tr:hover {background-color: #ddd;}#timetrack th {padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #04AA6D;color: white;}</style></head><body><table id='timetrack'><tr><th>Method</th><th>Execution Time</th></tr>");
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        stringBuilder.append("</table><script>const rows = document.querySelectorAll('td');rows.forEach((row) => {let str = row.innerHTML.replace('ms','');var x = Number(str);if(x >= "+SLA+") {const parent = row.parentNode;parent.style.backgroundColor = 'red';}});</script></body></html>");
        System.out.println(stringBuilder.toString());
        var path = new FileSystemResource("").getFile().getAbsolutePath();
        try (FileOutputStream fileOutputStream =
            new FileOutputStream(path+"/perf.html")) {
          fileOutputStream.write(stringBuilder.toString().getBytes());
        } catch (final IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  @Around("@annotation(com.file.upload.okta.advices.TrackExecutionTime)")
  @SneakyThrows
  public Object trackTime(ProceedingJoinPoint proceedingJoinPoint) {
    long start = System.currentTimeMillis();
    Object obj = proceedingJoinPoint.proceed();
    long end = System.currentTimeMillis();
    log.info("Method : "+proceedingJoinPoint.getSignature()+" time taken : "+(end - start));
    stringBuilder.append("<tr><td>"+proceedingJoinPoint.getSignature()+"</td><td>"+(end - start)+"ms</td></tr>");
    return obj;
  }
}
