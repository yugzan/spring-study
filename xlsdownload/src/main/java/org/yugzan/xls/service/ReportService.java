package org.yugzan.xls.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jxls.common.Context;
import org.jxls.template.SimpleExporter;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yugzan.xls.model.User;


/**
 * @author yongzan
 * @date 2015/10/19
 */
@Service
public class ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    @Value("${report.template.path}")
    private String templatePath;

    @Value("${report.template.fileExtension}")
    private String fileNameExtension;

    public byte[] report() throws ServiceException {
        try {
            return handleReport();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    private byte[] handleReport() throws Exception {

        Optional<String> pwd = Optional.empty();
        try {
            pwd = Optional.ofNullable((new java.io.File(".").getCanonicalPath()));
        } catch (IOException e1) {
            logger.error("Can not get working directory");
        }

        if (!pwd.isPresent()) {
            throw new ServiceException("Can not get working directory");
        }
        String filePath =
                String.format("%s%s%s%s", pwd.get(), templatePath, "builtin_template", fileNameExtension);

        logger.info("template path:{}", filePath);


        List<User> users = Arrays.asList(new User("Tom", 24), new User("Joy", 15), new User("Nashito",20));
        
        try (InputStream is = new BufferedInputStream(new FileInputStream(filePath));
                OutputStream os = new ByteArrayOutputStream()) {
            
            return executeByGrid(is, os, users);
            
        } catch (IOException e) { 
            logger.error("generate faile with {}", e);
            throw new ServiceException(e);
        }
    }
    /**
     * This method is using model and SimpleExporter example.
     * */
    private byte[] executeByModel(InputStream is, OutputStream os, List<User> users) throws IOException {
        List<String> headers = Arrays.asList("Name", "Age", "Time");
        SimpleExporter exporter = new SimpleExporter();
        // exporter.registerGridTemplate(is);
        exporter.gridExport(headers, users, "name, age, time", os);
        logger.info("Complete");
        os.flush();
        return ((ByteArrayOutputStream) os).toByteArray();
    }
    
    /**
     * This method is convert model  to List<Object> .
     * Or you can just use List<Object> put you want. Don't need model !!
     * */
    private  byte[] executeByGrid(InputStream is, OutputStream os, List<User> users)throws IOException {

        List<List<Object>> data = convertModel(users);

        Context context = new Context();
//        context.putVar("positions",  Arrays.asList("P1", "P2", "P3", "P4") );
        context.putVar("since", OffsetDateTime.now());
        context.putVar("until", OffsetDateTime.now().plusDays(30));
        context.putVar("createtime", OffsetDateTime.now().toString());
        context.putVar("location", "我的肚子");
        context.putVar("headers",  Arrays.asList("Name", "Age", "Time") );
        context.putVar("cells", data);
        JxlsHelper.getInstance().processTemplate(is, os, context);
        logger.info("Complete");
        os.flush();
        return ((ByteArrayOutputStream) os).toByteArray();
    }

    
    private  List<List<Object>> convertModel(List<User> users) {
        return users.stream()
        .map( ReportService::convertModelToList )
        .collect(Collectors.toList());
    }
    private  static List<Object> convertModelToList(User user){
        List<Object> list = new ArrayList<>();
        list.add(user.getName());
        list.add(user.getAge());
        list.add(user.getTime());
        return list;
    }
}
