//package com.example.demo.mappers;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.dtos.citoyenDTO;
//import com.example.demo.entity.citoyen;
//
//@Service
//public class citoyenMapperImp {
//   public citoyenDTO fromCitoyen(citoyen citoyen) {
//   citoyenDTO citoyenDTO =new citoyenDTO();
//   BeanUtils.copyProperties(citoyen,citoyenDTO);
//   return citoyenDTO;
//   }
//   public citoyenDTO fromCitoyenDTO(citoyenDTO citoyenDTO) {
//	   citoyen citoyen =new citoyen();
//	   BeanUtils.copyProperties(citoyenDTO,citoyen);
//	   return citoyenDTO;
//	   }
//   
//   
//}
