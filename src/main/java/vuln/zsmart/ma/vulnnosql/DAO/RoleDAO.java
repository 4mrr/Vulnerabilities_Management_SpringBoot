package vuln.zsmart.ma.vulnnosql.DAO;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import vuln.zsmart.ma.vulnnosql.Beans.Role;

import java.util.List;

@Repository
public interface RoleDAO extends MongoRepository<Role, ObjectId> {


//    @Query("db.Roles.find({_id : {'$ne' : db.Users.find({_id : ?0 },{\"roles.$id\" : 1}) }})")
    @Query(value = "{'Roles._id' : {$ne : {'Users._id' : ?0 },{'Users.roles.$_id' : 1} }}" )
    public List<Role> getUserNotRoles(ObjectId userId);

}
