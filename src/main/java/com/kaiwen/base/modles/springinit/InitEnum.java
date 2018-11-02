package com.kaiwen.base.modles.springinit;

/**
 * @author: liangjinyin
 * @Date: 2018-11-01
 * @Description:
 */
public enum InitEnum {
    /**
     * 测试初始化
     */
    TEST_A("a","test a"),
    TEST_B("b","test b"),

    ;

    private String code;
    private String msg;

    InitEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
