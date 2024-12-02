public class Account {
    // Database connection parameters

    private String url;
    private String username;
    private String password;
    public Account(String url, String user, String pass){
        this.url = url;
        this.username = user;
        this.password = pass;
    }

    public String getUrl(){
        return url;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
}
