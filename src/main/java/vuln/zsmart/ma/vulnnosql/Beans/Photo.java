package vuln.zsmart.ma.vulnnosql.Beans;

import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Photo")
public class Photo {

    @Id
    private ObjectId id;

    private String title;

    private Binary image;


    public Photo(ObjectId id) {
        this.id = id;
    }

    public Photo(ObjectId id, String title, Binary image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public Photo() {
    }

    public Photo(String title) {
        this.title = title;
    }

    public Photo(String title, Binary image) {
        this.title = title;
        this.image = image;
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

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }
}

