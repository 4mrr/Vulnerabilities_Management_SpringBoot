package vuln.zsmart.ma.vulnnosql.Securityy.DAOs;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vuln.zsmart.ma.vulnnosql.Securityy.Models.Role;

@Repository
public interface RoleDAO extends MongoRepository<Role, ObjectId> {


}
