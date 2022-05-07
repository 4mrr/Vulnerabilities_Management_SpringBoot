package vuln.zsmart.ma.vulnnosql.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuln.zsmart.ma.vulnnosql.Beans.SocialMedia;
import vuln.zsmart.ma.vulnnosql.Beans.User;
import vuln.zsmart.ma.vulnnosql.DAO.SocialMediaDAO;

@Service
public class SocialMediaService {

    @Autowired
    SocialMediaDAO socialMediaDAO;

    public void save(SocialMedia socialMedia)
    {
        socialMediaDAO.save(socialMedia);
    }

    public SocialMedia getUserBySM(User user)
    {
        return socialMediaDAO.findByUserProfie(user);
    }
}
