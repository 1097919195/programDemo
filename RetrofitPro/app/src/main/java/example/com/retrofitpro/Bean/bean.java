package example.com.retrofitpro.Bean;

/**
 * Created by asus-pc on 2017/7/12.
 */

public class bean {

    /**
     * rcode : null
     * title : 登陆认证
     * data : 1
     * list : null
     * success : true
     * message : 验证通过..
     * exceptionMessage : null
     */

    private Object rcode;
    private String title;
    private String data;
    private Object list;
    private boolean success;
    private String message;
    private Object exceptionMessage;

    public void setRcode(Object rcode) {
        this.rcode = rcode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setList(Object list) {
        this.list = list;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setExceptionMessage(Object exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public Object getRcode() {
        return rcode;
    }

    public String getTitle() {
        return title;
    }

    public String getData() {
        return data;
    }

    public Object getList() {
        return list;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getExceptionMessage() {
        return exceptionMessage;
    }
}
