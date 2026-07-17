package dbms.security;
import java.util.List;

public class SecurityManager {
    public List<User> users;
    public List<Role> roles;
    public List<Object> policies;

    public boolean authenticate(String username, String password) { return false; }
    public boolean authorize(String username, String action) { return false; }
    public void grantPermission(String username, String permission) {}
    public void revokePermission(String username, String permission) {}
}