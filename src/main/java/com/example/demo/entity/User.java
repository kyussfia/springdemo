package com.example.demo.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "username", nullable = false, length = 45)
	@Length(min = 5, message = "*Your username must have at least 5 characters")
	@Length(max = 45, message = "*Your username must have les than 45 characters")
	@NotEmpty(message = "*Please provide a user name")
	private String username;

	@Column(name = "password")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	private String password;

	@Column(name = "login_failures", nullable = false)
	private Integer loginFailures = 0;

	@Column(name = "last_login")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_group",
			joinColumns = @JoinColumn(name = "user_id",
					referencedColumnName = "id",
					nullable = false
			),
			inverseJoinColumns = @JoinColumn(name = "group_id",
					referencedColumnName = "id",
					nullable = false
			)
	)
	private Collection<Group> groups = new HashSet<>();

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String encode) {
		this.password = encode;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date login) {
		this.lastLogin = login;
	}

	public Integer getLoginFailures() {
		return loginFailures;
	}

	public void setLoginFailures(Integer loginFailures) {
		this.loginFailures = loginFailures;
	}

	public String getLastLoginFormatted() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(this.lastLogin);
	}

	private Collection<Role> getRoles() {
		Set<Role> result = new HashSet<>();

		for (Group group : this.groups) {
			result.addAll(group.getRoles());
		}
		return result;
	}

	public void addGroup(Group group) {
		this.groups.add(group);
	}

	public boolean haveToAnswerCaptcha(Integer attemptsBeforeCaptcha) {
		return this.getLoginFailures() >= attemptsBeforeCaptcha;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRoles();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", loginFailures=" + loginFailures +
				", lastLogin=" + lastLogin +
				", groups=" + groups +
				'}';
	}
}

