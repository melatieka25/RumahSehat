package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.RoleModel;
import TA_C_SHA_90.RumahSehatWeb.repository.RoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDb roleDb;

    @Override
    public List<RoleModel> findAll() {
        return roleDb.findAll();
    }

    @Override
    public RoleModel getById(Long id) {
		Optional<RoleModel> role = roleDb.findById(id);
		if(role.isPresent()) {
			return role.get();
		}
		return null;
    }
	
	@Override
    public RoleModel getByName(String name) {
		Optional<RoleModel> role = roleDb.findByRole(name);
		if(role.isPresent()) {
			return role.get();
		}
		return null;
    }
}
