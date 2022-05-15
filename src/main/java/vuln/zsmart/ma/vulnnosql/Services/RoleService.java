package vuln.zsmart.ma.vulnnosql.Services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuln.zsmart.ma.vulnnosql.Beans.User;
import vuln.zsmart.ma.vulnnosql.DAO.UserDAO;
import vuln.zsmart.ma.vulnnosql.DAO.RoleDAO;
import vuln.zsmart.ma.vulnnosql.Beans.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    UserDAO userDAO;


    public List<Role> getAll()
    {
        return roleDAO.findAll();
    }

    public Optional<Role> getById(ObjectId id)
    {
        return roleDAO.findById(id);
    }

    public void deleteById(ObjectId id)
    {
        roleDAO.deleteById(id);
    }
    public void save(Role role)
    {
        roleDAO.save(role);
    }

    public void assignUserRole(String username, ObjectId roleId){
        User user  = userDAO.findByUsername(username);
        Role role = roleDAO.findById(roleId).get();
        List<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userDAO.save(user);
    }
    public void unassignUserRole(String username, ObjectId roleId){
        User user  = userDAO.findByUsername(username);
        Role role = roleDAO.findById(roleId).get();
        List<Role> roles = new ArrayList<>();

        if(user.getRoles() !=null)
        {
            roles.remove(role);
            user.setRoles(roles);
            userDAO.save(user);
        }
        else
        {
            roles.addAll(user.getRoles());
            roles.remove(role);
            user.setRoles(roles);
            userDAO.save(user);
        }
    }

    public List<Role> getUserRoles(User user){
        return user.getRoles();
    }

    public Role getRoleByDescription(String description)
    {
        return  roleDAO.findByDescription(description);
    }

}
