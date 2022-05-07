package vuln.zsmart.ma.vulnnosql.DAO;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import vuln.zsmart.ma.vulnnosql.Beans.User;
import vuln.zsmart.ma.vulnnosql.Beans.Vulnerbilite;

import java.util.List;
import java.util.Optional;

@Repository
public interface VulnerbailityDAO extends MongoRepository<Vulnerbilite, ObjectId> {


    public List<Vulnerbilite> findAll();
    public Optional<Vulnerbilite> findById(ObjectId id);
    public void deleteById(ObjectId id);
    @Query("{title:'?0'}")
    public Vulnerbilite findByTitle(String title);
    public List<Vulnerbilite> findByVerify(Boolean verify);
    public int deleteByTitle(String title);

    public List<Vulnerbilite> findAllByAuthor(User user);
}
