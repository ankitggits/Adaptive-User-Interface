/**
 * 
 */
package com.aui.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.aui.dao.AuthenticationDao;
import com.aui.model.TBLAuthentication;
import com.aui.util.Constants;

/**
 * @author ANKIT
 *
 */
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	AuthenticationDao authenticationDao;
	
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		TBLAuthentication tblAuth = authenticationDao.getAuthenticationByUserName(userName);
		if(tblAuth==null) return null;
		Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(Constants.ROLE_USER);
		return new User(tblAuth.getUsername(), tblAuth.getPassword(), authorities);
	}
	
	/*private String[] getUserRoleList(TBLAuthentication tblAuth){
		String[] roleArray = new String[]{};
		if(tblAuth==null) return roleArray;
		
		List<TBLRole> tblRoleList = tblAuth.getTBLRoles();
		if(tblRoleList.size()>0){
			for(TBLRole role: tblRoleList){
				String temp = "ROLE_"+ role.getAuthority();
				roleArray = StringUtils.addStringToArray(roleArray, temp);
			}
		}
		return roleArray;
	}*/
	

}
