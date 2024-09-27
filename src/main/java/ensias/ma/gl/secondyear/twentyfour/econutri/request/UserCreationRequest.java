package ensias.ma.gl.secondyear.twentyfour.econutri.request;



public class UserCreationRequest {
    
    private String email;

    private String password;

    private String role;
    

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
