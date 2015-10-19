package org.yugzan.xls.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.yugzan.xls.service.ReportService;
import org.yugzan.xls.service.ServiceException;

/**
 * @author yongzan
 * @date 2015/10/19
 */
@RestController
public class XlsController {
    
    private static final Logger logger = LoggerFactory.getLogger(XlsController.class);
    
    @Resource(name = "reportService")
    private ReportService reportService;
    
    @RequestMapping(method = RequestMethod.GET, value = "/download")
    @ResponseStatus(value = HttpStatus.OK)
    public void downloadXLS( HttpServletResponse response ){
        try {
            byte[] data = reportService.report();
            response.addHeader(HttpHeaders.CONTENT_TYPE, "application/force-download");
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report.xls");
            response.addHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel");
            response.addHeader(HttpHeaders.CACHE_CONTROL, "no cache");
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
        } catch ( IOException |ServiceException e) {
            logger.error(e.getMessage());
        }
    }
}
