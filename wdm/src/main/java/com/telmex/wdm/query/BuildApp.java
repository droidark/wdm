package com.telmex.wdm.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.telmex.wdm.dao.HibernateUtil;
import com.telmex.wdm.model.CatWdm;
import com.telmex.wdm.model.SaibConsultaRequest;
import com.telmex.wdm.pojo.GeneralData;
import com.telmex.wdm.pojo.NodosWdm;

public class BuildApp {
	
	private List<String> providers;
	
	public ArrayList<String> getSuppliers(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(CatWdm.class)
				.setProjection(Projections.projectionList()
						.add(Projections.property("proveedorTx"))
						.add(Projections.groupProperty("proveedorTx"))
						);
		List<Object[]> obj = (List<Object[]>) cr.list();
		ArrayList<String> suppliers = new ArrayList<String>();
		for(Object[] k: obj){
			suppliers.add((String)k[0]);			
		}
		
		session.close();
		HibernateUtil.close();
		return suppliers;
	}
	
	public ArrayList<NodosWdm> getWdm(String supplier, String wdm){
		wdm = "%" + wdm + "%";
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Iterator queryWdm = session.createQuery("select c.wdm,c.tecnologia,"
				+ "c.topologia from CatWdm c where wdm like :wdm and "
				+ "proveedorTx = :supplier group by wdm order by wdm")
				.setParameter("supplier", supplier).setParameter("wdm", wdm)
				.list()
				.iterator();
		ArrayList<NodosWdm> datosWdm = new ArrayList<NodosWdm>();
		while(queryWdm.hasNext()){
			Object[] truple = (Object[]) queryWdm.next();
			datosWdm.add(new NodosWdm((String) truple[0], (String) truple[1],
					(String) truple[2]));
		}
		session.close();
		HibernateUtil.close();
		return datosWdm;
	}
	
	public ArrayList<String> getNodes(String supplier, String wdm){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(CatWdm.class)
			.setProjection(Projections.projectionList()
			.add(Projections.property("nodoAdmConexAdsl"))
			.add(Projections.groupProperty("nodoAdmConexAdsl"))
		);
		cr.add(Restrictions.eq("proveedorTx", supplier));
		cr.add(Restrictions.eq("wdm", wdm));
		cr.addOrder(Order.asc("nodoAdmConexAdsl"));
		 
		List<Object[]> obj = (List<Object[]>) cr.list();
		ArrayList<String> nodes = new ArrayList<String>();
		for(Object[] k: obj){
			nodes.add((String) k[0]);
		}
		session.close();
		HibernateUtil.close();
		return nodes;
	}
	
	public static void main(String[] args){
		String supplier = "HUAWEI", wdm = "WDM_OT40_122ZCEN", node = "AMECA";
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(CatWdm.class)
			.setProjection(Projections.projectionList()
				.add(Projections.property("id"))
				.add(Projections.property("repisa"))
				.add(Projections.property("nodoAdmConexAdsl"))
				.add(Projections.property("clliEquipo"))
				.add(Projections.property("siglasCentral"))
				.add(Projections.property("idNodo"))
				.add(Projections.property("repadmConxadsl"))
				.add(Projections.property("versionNodo"))
				.add(Projections.property("ubiNodoAdm"))
				.add(Projections.property("ipGestion"))
				.add(Projections.property("neid"))
			);
		cr.add(Restrictions.eq("proveedorTx", supplier));
		cr.add(Restrictions.eq("wdm", wdm));
		cr.add(Restrictions.eq("nodoAdmConexAdsl", node));
		
		List<Object[]> obj = (List<Object[]>) cr.list();
		
		session.close();
		HibernateUtil.close();
		
		GeneralData gral = new GeneralData();
		System.out.println(obj);
		for(Object[] k: obj){
			gral.setNodowdmid(1);
			gral.setRepisa((String)k[1]);
			gral.setNododos((String)k[2]);
			gral.setClli((String)k[3]);
			gral.setSiglas((String)k[4]);
			gral.setIdnodo((String)k[5]);
			gral.setModelo((String)k[6]);
			gral.setRelease((String)k[7]);
			gral.setUbicacion((String)k[8]);
			gral.setIp((String)k[9]);
			gral.setNeid((String)k[10]);
		}
		
		System.out.println(gral.getRepisa());		
	}
}