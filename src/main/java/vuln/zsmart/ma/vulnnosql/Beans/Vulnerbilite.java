package vuln.zsmart.ma.vulnnosql.Beans;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Vulnerabilities")
public class Vulnerbilite {
    @Id
    private ObjectId id;
    @Field(value = "title")
    private String title;
    @Field(value = "verify")
    private Boolean verify=false;
    @Field(value = "date")
    private String date;
    @Field(value = "platform")
    private Platform platform;
    @Field(value = "description")
    private String description;
    @Field(value = "author")
    @DBRef(db = "xvuln")
    private User author;
    public String exploit;

    public String getExploit() {
        return exploit;
    }

    public void setExploit(String exploit) {
        this.exploit = exploit;
    }

    public Vulnerbilite() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Vulnerbilite{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", verify=" + verify +
                ", date='" + date + '\'' +
                ", platform=" + platform +
                ", description='" + description + '\'' +
                ", author=" + author +
                '}';
    }

}
