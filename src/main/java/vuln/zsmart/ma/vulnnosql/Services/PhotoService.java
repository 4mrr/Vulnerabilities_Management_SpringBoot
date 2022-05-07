package vuln.zsmart.ma.vulnnosql.Services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuln.zsmart.ma.vulnnosql.Beans.Photo;
import vuln.zsmart.ma.vulnnosql.DAO.PhotoDAO;

@Service
public class PhotoService {

    @Autowired
    private PhotoDAO photoDAO;

    public Photo getPhotoById(ObjectId id)
    {
        return photoDAO.findById(id).get();
    }

    public void deletePhotoById(ObjectId id)
    {
        photoDAO.deleteById(id);
    }



}
