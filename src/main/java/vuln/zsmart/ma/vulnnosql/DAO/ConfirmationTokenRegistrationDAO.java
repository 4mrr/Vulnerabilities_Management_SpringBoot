package vuln.zsmart.ma.vulnnosql.DAO;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vuln.zsmart.ma.vulnnosql.Beans.ConfirmationTokenRegistration;

@Repository
public interface ConfirmationTokenRegistrationDAO extends MongoRepository<ConfirmationTokenRegistration, ObjectId> {

    public ConfirmationTokenRegistration findByConfirmationToken(String confirmationToken);
}
