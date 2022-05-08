package vuln.zsmart.ma.vulnnosql.Beans;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import vuln.zsmart.ma.vulnnosql.Securityy.Models.Role;

import java.util.List;


@Document(collection = "Users")
public class User{

    @Id
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String speciality;
    private String adress;
    @DBRef(db = "xvuln")
    private Photo photo;
    public String token;
    @DBRef(db = "xvuln")
    public List<Vulnerbilite> vulnerbilites;
    @DBRef(db = "xvuln")
    public SocialMedia socialMedia;
    @DBRef(db = "xvuln")
    List<Role> roles;


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }

    public Photo getPhoto() {
        return photo;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User() {
    }

    public List<Vulnerbilite> getVulnerbilites() {
        return vulnerbilites;
    }

    public void setVulnerbilites(List<Vulnerbilite> vulnerbilites) {
        this.vulnerbilites = vulnerbilites;
    }

    public User(ObjectId id, String firstName, String lastName, String email, String username, String password, String adress, String token, List<Vulnerbilite> vulnerbilites) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.adress = adress;
        this.token = token;
        this.vulnerbilites = vulnerbilites;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", adress=" + adress +
                '}';
    }
}
