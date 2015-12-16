package com.telmex.wdm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.telmex.wdm.pojo.GeneralData;
import com.telmex.wdm.pojo.NodosWdm;
import com.telmex.wdm.pojo.Person;
import com.telmex.wdm.pojo.PersonWrapper;
import com.telmex.wdm.query.BuildApp;

@RestController
public class WdmRestController {
	
	
	@RequestMapping("/suppliers")
	public @ResponseBody List<String> providers(){
		BuildApp prov = new BuildApp();
		return prov.getSuppliers();
	}
	
	@RequestMapping(value = "/nameWdm/{supplier}/{wdm}", 
			method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<NodosWdm> getWdm(
			@PathVariable("supplier") String supplier,
			@PathVariable("wdm") String wdm){
		BuildApp prov = new BuildApp();
		return prov.getWdm(supplier, wdm);
	}
	
	@RequestMapping(value = "/nodes/{supplier}/{wdm}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<String> getNodes(
			@PathVariable("supplier") String supplier,
			@PathVariable("wdm") String wdm){
		BuildApp prov = new BuildApp();
		return prov.getNodes(supplier, wdm);
	}
	
	@RequestMapping(value = "/prueba",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	@ResponseBody
	public ArrayList<String> prueba(@RequestBody GeneralData gral)
			throws Exception{
		ArrayList<String> response = new ArrayList<String>();
		response.add(gral.getClli());
		return response;
	}
	
}
