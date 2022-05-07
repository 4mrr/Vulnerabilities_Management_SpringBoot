package vuln.zsmart.ma.vulnnosql.Beans;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SocialMedia")
public class SocialMedia {

    @Id
    private ObjectId id;
    private String facebook;
    private String instagrame;
    private String twitter;
    private String github;
    private String siteweb;
    @DBRef(db = "xvuln")
    private User userProfie;

    public SocialMedia() {
    }

    public SocialMedia(ObjectId id) {
        this.id = id;
    }

    public SocialMedia(ObjectId id, String facebook, String instagrame, String twitter, String github, String siteweb, User userProfie) {
        this.id = id;
        this.facebook = facebook;
        this.instagrame = instagrame;
        this.twitter = twitter;
        this.github = github;
        this.siteweb = siteweb;
        this.userProfie = userProfie;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagrame() {
        return instagrame;
    }

    public void setInstagrame(String instagrame) {
        this.instagrame = instagrame;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public User getUserProfie() {
        return userProfie;
    }

    public void setUserProfie(User userProfie) {
        this.userProfie = userProfie;
    }

    @Override
    public String toString() {
        return "SocialMedia{" +
                "id=" + id +
                ", facebook='" + facebook + '\'' +
                ", instagrame='" + instagrame + '\'' +
                ", twitter='" + twitter + '\'' +
                ", github='" + github + '\'' +
                ", siteweb='" + siteweb + '\'' +
                ", userProfie=" + userProfie +
                '}';
    }
}
