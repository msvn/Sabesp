package com.prime.app.agvirtual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabNoticiaDao;
import com.prime.app.agvirtual.service.NoticiaService;
import com.prime.app.agvirtual.to.NoticiaTO;

@Service
public class NoticiaServiceImpl implements NoticiaService {

    @Autowired
    private AgvTabNoticiaDao noticiaDao;

	@Transactional(readOnly = true)
	public List findAll() {
		return noticiaDao.findAll();
	}

	public boolean delete(NoticiaTO noticia) {
		return noticiaDao.delete(noticia);
	}

//    @Transactional(readOnly = false)
//    public List<Pedido> getPedidosPorCliente(String emailCliente) {
//        Cliente cliente = clienteDao.findByEmail(emailCliente);
//        return pedidoDao.findPedidosByCliente(cliente);
//    }

}
