package vuln.zsmart.ma.vulnnosql.DAO;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vuln.zsmart.ma.vulnnosql.Beans.User;

import java.util.List;

@Repository
public interface UserDAO extends MongoRepository<User, ObjectId> {

    public User findByToken(String token);
    public int deleteByUsername(String username);
    public User findByUsername(String username);
    public List<User> findAll();
    public User findByEmail(String email);

}
