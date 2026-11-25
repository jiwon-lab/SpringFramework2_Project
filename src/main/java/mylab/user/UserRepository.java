package mylab.user;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class UserRepository {

    @Value("MySQL")
    private String dbType;

    public UserRepository() {}

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public boolean saveUser(String userId, String name) {
        System.out.println("사용자 저장: " + userId 
                + ", " + name + " (DB: " + dbType + ")");
        return true;
    }

    @Override
    public String toString() {
        return "UserRepository [dbType=" + dbType + "]";
    }
}