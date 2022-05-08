package vuln.zsmart.ma.vulnnosql.Securityy.Services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuln.zsmart.ma.vulnnosql.Beans.User;
import vuln.zsmart.ma.vulnnosql.DAO.UserDAO;
import vuln.zsmart.ma.vulnnosql.Securityy.DAOs.RoleDAO;
import vuln.zsmart.ma.vulnnosql.Securityy.Models.Role;

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

        List<Role> userRoles = new ArrayList<Role>();
        if(user.getRoles() == null)
        {
            userRoles.add(role);
            user.setRoles(userRoles);
            userDAO.save(user);
        }else
        {
            user.getRoles().add(role);
            userDAO.save(user);
        }

    }
    public void unassignUserRole(ObjectId userId, ObjectId roleId){
        User user  = userDAO.findById(userId).get();
        Role role = roleDAO.findById(roleId).get();
        user.getRoles().remove(role);
        userDAO.save(user);
    }

    public List<Role> getUserRoles(User user){
        if (user.getRoles()== null)
        {
            return new ArrayList<Role>();
        }
        return user.getRoles();
    }

    public List<Role> getUserNotRoles(ObjectId userId)
    {
        User user =  userDAO.findById(userId).get();
        List<Role> listRoles = roleDAO.findAll();
        List<Role> newList = new ArrayList<Role>();

        if(user.getRoles()== null)
        {
            return listRoles;
        }
        else
        {
            for (Role role: listRoles)
            {
                if(!user.getRoles().contains(role))
                {
                    newList.add(role);
                }
            }
            return newList;
        }
    }

}
