package com.kaiwen.base.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: liangjinyin
 * @Date: 2018-09-04
 * @Description: 全部异常类
 */
@ControllerAdvice(basePackages = "com.kaiwen.base.modles")
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    /** 此处可以加自定义异常*/
    @ResponseBody
    public ResponseEntity handleException(Exception e) {
        Map<String, Object> map = new HashMap(8);
        map.put("message", String.format("出现未知异常：%s 请联系管理员！", e.getMessage()));
        map.put("code", "999");
        log.error("【出现未知异常：{}】", e.getMessage());
        return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   /* @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity handleMyException(ServiceException e) {
        Map<String, Object> map = new HashMap(8);
        map.put("message",e.getMessage());
        map.put("code", e.getCode());
        log.error("【出现自定义异常：{}】", e.getMessage());
        return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

   /* public static void main(String[] args) {
        String str = "1,3,4,6,8,9,10";
        List<String> list = Arrays.asList(StringUtils.split(str, ","));
        List<String> collect = Stream.of(StringUtils.split(str, ",")).collect(Collectors.toList());

        IntStream.range(1,100).parallel().peek(ExceptionAdvice::pri).count();

    }

    public static void pri(int i) {
        System.out.println("哈哈"+i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

}
