package org.yugzan.xls.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.jxls.template.SimpleExporter;
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
        // Preconditions.checkNotNull(reports, "Report is empty");

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

        logger.error("TEST:{}", filePath);

        try (InputStream is = new BufferedInputStream(new FileInputStream(filePath));
                OutputStream os = new ByteArrayOutputStream()) {
            return testJXLSAPI(is, os);
        } catch (IOException e) { // InvalidFormatException |
            logger.error("generate faile with {}", e);
            throw new ServiceException(e);
        }
    }

    private byte[] testJXLSAPI(InputStream is, OutputStream os) throws IOException {
        List<String> headers = Arrays.asList("Name", "Age", "Time");
        List<User> data = Arrays.asList(new User("Tom", 24), new User("Joy", 15), new User("Nashito",20));
        SimpleExporter exporter = new SimpleExporter();
        // exporter.registerGridTemplate(is);
        exporter.gridExport(headers, data, "name, age, time", os);
        logger.info("Complete");
        os.flush();
        return ((ByteArrayOutputStream) os).toByteArray();
    }
}
