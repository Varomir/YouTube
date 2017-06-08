package spring.constructors;

public class DriverData {

    private String browser;
    private Integer timeout;
    private String autBaseURL;

    private String request;

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getAutBaseURL() {
        return autBaseURL;
    }

    public void setAutBaseURL(String autBaseURL) {
        this.autBaseURL = autBaseURL;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
