package com.cmcglobal.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Table(name = "user_flattened")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFlattened implements Serializable {
    @Id
    private String id;

    @Column(name = "username")
    private String userName;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "department")
    private String departmentName;

    @Column(name = "group_name")
    private String parentDepartmentName;

    @Column(name = "is_active")
    private boolean status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFlattened)) return false;
        UserFlattened that = (UserFlattened) o;
        return status == that.status
                && Objects.equals(userName, that.userName)
                && Objects.equals(fullName, that.fullName)
                && Objects.equals(departmentName, that.departmentName)
                && Objects.equals(parentDepartmentName, that.parentDepartmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, fullName, departmentName, parentDepartmentName, status);
    }

    @Override
    public String toString() {
        return "{userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", parentDepartmentName='" + parentDepartmentName + '\'' +
                ", status=" + status +
                '}';
    }
}
