package com.prime.app.agvirtual.service.impl;

import static com.prime.infra.util.WrapperUtils.toLong;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabSubservicoDao;
import com.prime.app.agvirtual.entity.AgvTabSubservico;
import com.prime.app.agvirtual.service.OrcamentoService;
import com.prime.app.agvirtual.service.SubServicoService;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.to.OrcamentoOferecidoTO;
import com.prime.app.agvirtual.to.SubServicoTO;
import com.prime.infra.util.WrapperUtils;

@Service
public class SubServicoServiceImpl implements SubServicoService {

	@Autowired
    private AgvTabSubservicoDao subServDao;
	
	@Autowired
	private OrcamentoService orcamentoService;
	

	@Transactional(readOnly = true)
	public List<SubServicoTO> findAll() {		
		return subServDao.findAll();
	}

	/*private ArrayList findCanaisByCodSubServ(
			Long cdSubservico) {
		ArrayList listaCanaisTo =  new ArrayList();
		List<AgvTabSubservicoCanalDeAtendimento> listaCanais = subServAtendimentoDao.findById(cdSubservico);
		if(!listaCanais.isEmpty()){
			for (Iterator ter = listaCanais.iterator(); ter.hasNext();) {
				AgvTabSubservicoCanalDeAtendimento canalEntity = (AgvTabSubservicoCanalDeAtendimento) ter.next();
				listaCanaisTo.add(canalEntity.getAgvTabCanalAtend().parseTO());
			}
		}
		return listaCanaisTo;
	}

	private ArrayList findDocumentosByCodSubServ(
			Long cdSubservico) {
		ArrayList listaDocTo =  new ArrayList();
		List<AgvTabSubservicoDocumento> listaDoc = subServDocumentoDao.findById(cdSubservico);
		if(!listaDoc.isEmpty()){
			for (Iterator iter2 = listaDoc.iterator();iter2.hasNext();) {
				AgvTabSubservicoDocumento docEntity = (AgvTabSubservicoDocumento) iter2.next();
				listaDocTo.add(docEntity.getAgvTabDocumento().parseTO());
			}
		}
		return listaDocTo;
	}*/
	
	@Transactional(readOnly = true)
	public List<SelectItem> findAllSelectedItems() {
		ArrayList<SelectItem> listaResultado =  new ArrayList<SelectItem>();
		List<SubServicoTO> listTemp = subServDao.findAllServicoVazio();
		if(listTemp != null){
			for (Iterator<SubServicoTO> iterator = listTemp.iterator(); iterator.hasNext();) {
				SubServicoTO docTO = (SubServicoTO) iterator.next();
				SelectItem item =  new SelectItem(docTO.getCdSubservico().toString(),docTO.getDsSubservico());
				listaResultado.add(item);	
			}
		}
		return listaResultado;
	}

	@Transactional(readOnly = true)
	public AgvTabSubservico findById(Long id, boolean lock) {
		AgvTabSubservico subServ = subServDao.findById(id,lock);
		
		return subServ;
	}
	
	@Transactional(readOnly = true)
	public void save(AgvTabSubservico t) {
		AgvTabSubservico servico = subServDao.save(t);
	}
	
	@Transactional
	public void alterar(AgvTabSubservico entity) {
		subServDao.alterar(entity);
	}

	@Transactional
	public void excluir(List sessionAttribute) {
		if(sessionAttribute != null){
			for (Iterator iterator = sessionAttribute.iterator(); iterator.hasNext();) {
				SubServicoTO object = (SubServicoTO) iterator.next();
				object.setServico(null);
				subServDao.alterar(object.toEntity());
			}
		}
	}
	
	@Transactional(readOnly = true)
	public List<SubServicoTO> findAllWithTarifasByMunicipo(MunicipioTO municipioTO) {
		ArrayList<SubServicoTO> lista = (ArrayList) subServDao.findAllWithTarifasByMunicipo(municipioTO);
		
//		String header = "";
//		for (SubServicoTO element : lista) {
//			List<AgvTabSubservico> listaTemp = element.getServicoEntity().getAgvListaServSubServ();
//				
//			String auxNmServico = WrapperUtils.isNotNull(element.getServicoEntity()) && WrapperUtils.isNotNull(element.getServicoEntity().getNmServico()) ? 
//					element.getServicoEntity().getNmServico() : "";
//					  
//			if(!header.equalsIgnoreCase(auxNmServico)){
//				header = auxNmServico;
//				element.setFlagPublicGuia(true);
//			}else{
//				element.setFlagPublicGuia(false);
//			}
//			OrcamentoOferecidoTO orcamento = null;    // Orcamento utilizado na cobrança de algum serviço		 
////			sub.getCdServicoCsi(), 	sub.getCdSerCom(),sub.getCdServExe()
//			orcamento = orcamentoService.getTipoOrcamento(null, Integer.valueOf(element.getCdServicoCsi()),Integer.valueOf(element.getCdSerCom()) , municipioTO.getCodUf().toString());
//			orcamento.setValorTotal(orcamentoService.getValorServico(null,Integer.valueOf(element.getCdServicoCsi()),Integer.valueOf(element.getCdSerCom()), Integer.valueOf(element.getCdServExe()),municipioTO.getCodUf().toString()));
//			element.setDsPreco(String.valueOf(orcamento.getValorTotal().doubleValue()));
//			element.setDsPrazoAtend(orcamento.getPrazoAtendimento());
//		}
		return lista;   
	}
}
