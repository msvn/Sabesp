package com.prime.app.agvirtual.dao.impl.test;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.prime.app.agvirtual.dao.impl.ImovelDaoImpl;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.test.util.PropsUtil;

public class ImovelDaoImplCase extends ImovelDaoImpl {
	public Properties props = new PropsUtil().load("/testcase.properties");
	public SessionFactory sf = new AnnotationConfiguration().configure("/hibernate.cfg.xml").buildSessionFactory();
	public Session session = null;
	
	public Imovel findImovelByRGI(Imovel imovel) {
		session = sf.openSession();		
		Imovel ret = null;
		try {
			ret = this.findByImovel(imovel);	
		} catch (Exception e) {
		    e.printStackTrace();
		}		
		session.close();
		return ret;
	}
	
	@Override
	protected Session getHibernateSession() {
		return this.session;
	}
}
