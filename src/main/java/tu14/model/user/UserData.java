package tu14.model.user;

/**
 * Author: Shikha Verma
 * Date: 2023-10-27
 * Description: This Java class will contain information of the logged in user
 */
public class UserData {
    private int id;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}