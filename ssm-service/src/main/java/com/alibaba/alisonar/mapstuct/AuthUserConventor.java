/**
 * 
 */
package com.alibaba.alisonar.mapstuct;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.alibaba.alisonar.domain.AuthUser;
import com.alibaba.alisonar.dto.AuthUserDTO;

/**
 * @author wb-zxx263018
 *
 */
@Mapper(componentModel = "spring")
public interface AuthUserConventor {
	AuthUserConventor MAPPER = Mappers.getMapper(AuthUserConventor.class);

	//@Mapping(target = "birthformat", source = "birthdate",dateFormat = "yyyy-MM-dd HH:mm:ss")
	/*@Mappings({ @Mapping(source = "", target = "")

	})*/
	public AuthUserDTO entity2DTO(AuthUser entity);
	
	@InheritInverseConfiguration
	public AuthUser DTO2entity(AuthUserDTO dto);

	
	public List<AuthUserDTO> entities2DTOs(List<AuthUser> list);

}
