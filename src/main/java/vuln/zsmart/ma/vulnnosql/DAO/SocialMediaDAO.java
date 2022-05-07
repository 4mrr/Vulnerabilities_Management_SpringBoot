package vuln.zsmart.ma.vulnnosql.DAO;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vuln.zsmart.ma.vulnnosql.Beans.SocialMedia;
import vuln.zsmart.ma.vulnnosql.Beans.User;

@Repository
public interface SocialMediaDAO extends MongoRepository<SocialMedia, ObjectId> {


    public SocialMedia findByUserProfie(User user);
}
