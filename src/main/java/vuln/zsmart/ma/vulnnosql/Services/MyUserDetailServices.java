package vuln.zsmart.ma.vulnnosql.Services;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vuln.zsmart.ma.vulnnosql.Beans.Photo;
import vuln.zsmart.ma.vulnnosql.Beans.User;
import vuln.zsmart.ma.vulnnosql.Beans.UserPrincipal;
import vuln.zsmart.ma.vulnnosql.Beans.Vulnerbilite;
import vuln.zsmart.ma.vulnnosql.DAO.PhotoDAO;
import vuln.zsmart.ma.vulnnosql.DAO.UserDAO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailServices implements UserDetailsService {

    @Autowired
    UserDAO userDAO;
    @Autowired
    PhotoDAO photoDAO;
    @Autowired
    BCryptPasswordEncoder encoder;


    public void update(User user)
    {
        userDAO.save(user);

    }

    public void save(User user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    public Optional<User> getUserById(ObjectId id)
    {
        return userDAO.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("[-] User Not FOUND !!!");
        return new UserPrincipal(user);

    }

    public User findUserByUsername(String name)
    {
        return userDAO.findByUsername(name);
    }
    public List<User> getAllUsers()
    {
        return userDAO.findAll();
    }

    public void deleteUserById(ObjectId id)
    {
        userDAO.deleteById(id);
    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(email);
        if (user != null) {
            user.setToken(token);
            userDAO.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }
    }


    public User getByResetPasswordToken(String token) {
        return userDAO.findByToken(token);
    }

    public void updatePassword(User user, String newPassword) {
/*        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);*/
        List<Vulnerbilite> v = user.getVulnerbilites();

        user.setPassword(encoder.encode(newPassword));
        user.setVulnerbilites(v);
        user.setToken(null);
        userDAO.save(user);
    }


    public ObjectId addPhoto(String title, MultipartFile file) throws IOException {
        Photo photo = new Photo(title);
        photo.setImage( new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoDAO.insert(photo);
        return photo.getId();
    }

    public ObjectId addPhotoSansTitle( MultipartFile file) throws IOException {
        Photo photo = new Photo();
        photo.setImage( new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoDAO.insert(photo);
        return photo.getId();
    }

    public Photo getPhoto(ObjectId id) {
        return photoDAO.findById(id).get();
    }

}
