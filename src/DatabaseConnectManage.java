public class DatabaseConnectManage{
    // Database connection parameters
    private String url = "jdbc:sqlserver://swa11op.ddns.net:1433;databaseName=VariedInsurance;encrypt=true;trustServerCertificate=true;";
    private String username = "SA";
    private String password = "Thinh@1607";

    public String getUrl(){
        return url;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
