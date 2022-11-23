package TA_C_SHA_90.RumahSehatWeb.service;

import TA_C_SHA_90.RumahSehatWeb.model.RoleModel;

import java.util.List;

public interface RoleService {
    List<RoleModel> findAll();

    RoleModel getById(Long id);
	
	RoleModel getByName(String name);
}
