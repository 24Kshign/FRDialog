package cn.jakemesdg.commondialog;

/**
 * Created by manji
 * Date：2018/9/11 下午2:35
 * Desc：
 */
public class TestDataBean {

    private String name;

    private String time;

    public TestDataBean(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}