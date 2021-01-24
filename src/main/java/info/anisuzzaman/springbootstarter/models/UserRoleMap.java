package info.anisuzzaman.springbootstarter.models;

import javax.persistence.*;

/**
 * @author : anisuzzaman
 * @created : 1/17/21, Sunday
 **/

@Entity
@Table(	name = "user_role_map")
public class UserRoleMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long roleId;

    public UserRoleMap() {

    }

    public UserRoleMap(Long userId, Long roleId) {
        this.userId=userId;
        this.roleId=roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
