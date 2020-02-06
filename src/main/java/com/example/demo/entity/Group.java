package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@SuppressWarnings("unused")
@Entity
@Table(name = "groups")
public class Group implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "name", nullable = false, length = 45)
	private String name;

	@ManyToMany(mappedBy = "groups")
	private Collection<User> users;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "group_role",
			joinColumns = @JoinColumn(name = "group_id",
					referencedColumnName = "id",
					nullable = false
			),
			inverseJoinColumns = @JoinColumn(name = "role_id",
					referencedColumnName = "id",
					nullable = false
			)
	)
	private Collection<Role> roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@SuppressWarnings("WeakerAccess")
	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
}
