package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabSubservicoDao;
import com.prime.app.agvirtual.dao.CsiDao;
import com.prime.app.agvirtual.entity.AgvTabSubservico;
import com.prime.app.agvirtual.service.OrcamentoService;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.to.OrcamentoOferecidoTO;
import com.prime.app.agvirtual.to.SubServicoDTO;
import com.prime.app.agvirtual.to.SubServicoTO;
import com.prime.app.agvirtual.to.TarifaTO;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;
import com.prime.infra.util.WrapperUtils;

/**
 * Imterface DAO
 * @author gustavons
 *
 */
@Repository
public class AgvTabSubservicoDaoImpl extends GenericHibernateJpaDao<AgvTabSubservico, Long> implements AgvTabSubservicoDao {

	
	private static final Logger agvlogger = LoggerFactory.getLogger(AgvTabSubservicoDaoImpl.class);
	
	
	@Autowired
	CsiDao csiDao;
	
	@Autowired 
	OrcamentoService orcamentoService;
	
	public List<SubServicoTO> findAll(){
		List<AgvTabSubservico> listaResultado = findByQuery("select e from AgvTabSubservico e");
    	return parseTO(listaResultado);
	}
	
	/**
	 * Faz De Para do objeto entity para o TO
	 * @param listaResultado
	 * @return
	 */
	private List<SubServicoTO> parseTO(List<AgvTabSubservico> listaResultado) {
		
		ArrayList<SubServicoTO> lista =  new ArrayList<SubServicoTO>();
		
		for (Iterator<AgvTabSubservico> iterator = listaResultado.iterator(); iterator.hasNext();) {
			AgvTabSubservico element = (AgvTabSubservico) iterator.next();
			
			SubServicoTO to =  new SubServicoTO();
			to.setCdServicoCsi( (element.getCdServicoCsi() == null ? null : element.getCdServicoCsi().toString()));
			to.setCdSerCom(element.getCdSerCom());
			to.setCdServExe(element.getCdServExe());
			to.setCdSubservico(element.getCdSubservico());
			to.setDataAtualizacao(element.getDtAtualizacao());
			to.setDataPublicacao(element.getDtPublicacao());
			to.setDsCondExec(element.getDsCondExec());
			to.setDsFormaPgto(element.getDsFormaPgto());
			to.setDsLink(element.getDsLink());
			to.setDsPrazoAtend(element.getDsPrazoAtend());
			to.setDsPreco(element.getDsPreco());
			to.setDsSubservico(element.getDsSubservico());
			to.setFlagPublicGuia(element.getFlPublicGuia());
			to.setFlagPublicTabPrecos(element.getFlPublicTabPrecos());
			to.setServicoEntity(element.getServico());
			lista.add(to);
		}
		
		return lista;
	}

	/**
	 * Salva AgvTabSubservico
	 */
	public AgvTabSubservico save(AgvTabSubservico t) { 
		Session session = getHibernateSession();
 		AgvTabSubservico entityUpdated = getEntityManager().merge(t);
// 		getEntityManager().persist(t);
		return entityUpdated;
	}

	public void alterar(AgvTabSubservico entity) {
		Session session = getHibernateSession();
		getEntityManager().merge(entity);
	}

	public List<SubServicoTO> findAllServicoVazio() {
		List<AgvTabSubservico> listaResultado = findByQuery("select e from AgvTabSubservico e where e.servico = null order by e.dsSubservico");
    	return parseTO(listaResultado);
	}

	private List<String> findServicosCategorias() {
		return getEntityManager().createQuery("select e.servico.cdCategoria from AgvTabSubservico e group by e.servico.cdCategoria").getResultList();

	}
	
	public List<SubServicoTO> findAllWithTarifasByMunicipo(MunicipioTO municipioTO){
		List<SubServicoTO> listaRetorno = new ArrayList<SubServicoTO>();
		for(String categoria : findServicosCategorias()){
			SubServicoDTO subServicoDTO = new SubServicoDTO();
			List<AgvTabSubservico> listaSub = findByQuery("select e from AgvTabSubservico e where e.servico.cdCategoria=?1 order by e.servico.nmServico",categoria);
			if (WrapperUtils.isNotNull(listaSub) && listaSub.size()>0){
				subServicoDTO.setServicoEntity((listaSub.get(0).getServico()));
			}
			subServicoDTO.setListaSubServico(AddTarifasToSubServicos(parseTO(listaSub),municipioTO));
			listaRetorno.add(subServicoDTO);
		}
		return listaRetorno;
	}

	private List<SubServicoTO> AddTarifasToSubServicos(List<SubServicoTO> listaSub, MunicipioTO municipioTO) {
		List<SubServicoTO> listaRetorno = new ArrayList<SubServicoTO>();
		if(WrapperUtils.isNull(listaSub) || WrapperUtils.isNull(municipioTO)){
			return null;
		}
		
		String header = "";
		for(SubServicoTO sub : listaSub){
			// MUNICIPIO;CDGRPSERV;CDSERVCOM;CDSERVEXE
			try{
			OrcamentoOferecidoTO orcamento = orcamentoService.getTipoOrcamento(null, Integer.valueOf(sub.getCdServicoCsi()),Integer.valueOf(sub.getCdSerCom()) , municipioTO.getCodUf().toString());
			orcamento.setValorTotal(orcamentoService.getValorServico(null,Integer.valueOf(sub.getCdServicoCsi()),Integer.valueOf(sub.getCdSerCom()), Integer.valueOf(sub.getCdServExe()),municipioTO.getCodUf().toString()));
			if(orcamento.getValorTotal() != null)
				sub.setDsPreco(String.valueOf(orcamento.getValorTotal().doubleValue()));
			
			sub.setDsPrazoAtend(orcamento.getPrazoAtendimento());
			}catch (Exception e) {
				agvlogger.error("Erro ao consultar orcamento para preço de serviços");
			}
			String auxNmServico = WrapperUtils.isNotNull(sub.getServicoEntity()) && WrapperUtils.isNotNull(sub.getServicoEntity().getNmServico()) ? 
					  sub.getServicoEntity().getNmServico() : "";
					  
			if(!header.equalsIgnoreCase(auxNmServico)){
				header = auxNmServico;
				sub.setFlagPublicGuia(true);
			}else{
				sub.setFlagPublicGuia(false);
			}
			
			listaRetorno.add(sub);
			
		}
		return listaRetorno;
	}	
}
