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

    public void assignUserRole(ObjectId userId, ObjectId roleId){
        User user  = userDAO.findById(userId).get();
        Role role = roleDAO.findById(roleId).get();
        //List<Role> userRoles = user.getRoles();
            user.getRoles().add(role);
            userDAO.save(user);
    }
    public void unassignUserRole(ObjectId userId, ObjectId roleId){
        User user  = userDAO.findById(userId).get();
        Role role = roleDAO.findById(roleId).get();
        user.getRoles().remove(role);
        userDAO.save(user);
    }

    public List<Role> getUserRoles(User user){
        return user.getRoles();
    }

    public List<Role> getUserNotRoles(ObjectId id)
    {
        return roleDAO.getUserNotRoles(id);
    }

    public List<Role> getUserNotRoles(User user)
    {
        List<Role> result = new ArrayList<Role>();
        List<Role> AllRoles=  roleDAO.findAll();
        if(user.getRoles()== null)
        {
            return AllRoles;

        }else{
            for(int i=0; i< AllRoles.size();i++)
            {
                if(!user.getRoles().contains(AllRoles.get(i)))
                {
                        result.add(AllRoles.get(i));
                }
            }
            return result;
        }
    }

    public Role getRoleByDescription(String description)
    {
        return  roleDAO.findByDescription(description);
    }

}
