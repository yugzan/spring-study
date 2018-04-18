package org.yugzan.linebot.service.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Service;

import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

/**
 * @author yongzan
 * @date 2017/12/1
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@LineMessageHandler
public @interface EnableSemanticsService {

}
