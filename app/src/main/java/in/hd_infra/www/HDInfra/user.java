package in.hd_infra.www.HDInfra;

/**
 * Created by Himamsh K on 30-09-2017.
 */

public class user {


    private String clgid, email, name;

    public user(String clgid, String name, String email) {
        this.clgid = clgid;
        this.email = email;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getClgid() {
        return clgid;
    }
}
