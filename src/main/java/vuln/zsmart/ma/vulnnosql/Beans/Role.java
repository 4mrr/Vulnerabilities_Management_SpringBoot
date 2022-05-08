package vuln.zsmart.ma.vulnnosql.Beans;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Roles")
public class Role {

    @Id
    private ObjectId id;
    private String description;
    private String detail;


    public Role() {
    }

    public Role(ObjectId id, String description, String detail) {
        this.id = id;
        this.description = description;
        this.detail = detail;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
