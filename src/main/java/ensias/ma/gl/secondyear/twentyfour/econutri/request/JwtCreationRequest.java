package ensias.ma.gl.secondyear.twentyfour.econutri.request;


public class JwtCreationRequest {
    
    private String email;

    private String password;

    
    public JwtCreationRequest() {

    }

    public JwtCreationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    
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


}
