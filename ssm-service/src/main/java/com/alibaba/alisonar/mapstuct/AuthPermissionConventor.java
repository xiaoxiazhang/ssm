/**
 * 
 */
package com.alibaba.alisonar.mapstuct;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.alibaba.alisonar.domain.AuthPermission;
import com.alibaba.alisonar.dto.AuthPermissionDTO;

/**
 * @author wb-zxx263018
 *
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthPermissionConventor {
	
	
	AuthPermissionConventor MAPPER = Mappers.getMapper(AuthPermissionConventor.class);

	
	
	public AuthPermissionDTO entity2DTO(AuthPermission entity);
	
	@InheritInverseConfiguration
	public AuthPermission DTO2entity(AuthPermissionDTO dto);

	
	public List<AuthPermissionDTO> entities2DTOs(List<AuthPermission> list);

}
