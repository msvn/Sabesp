//package com.prime.app.abc.service;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.prime.app.abc.dao.ClienteDao;
//import com.prime.app.abc.dao.PedidoDao;
//import com.prime.app.abc.entity.Cidade;
//import com.prime.app.abc.entity.Cliente;
//import com.prime.app.abc.entity.Estoque;
//import com.prime.app.abc.entity.EstoqueQuantidade;
//import com.prime.app.abc.entity.FormaPagamento;
//import com.prime.app.abc.entity.ItemPedido;
//import com.prime.app.abc.entity.Pedido;
//import com.prime.app.abc.entity.Produto;
//import com.prime.app.abc.entity.TipoFormaPagamento;
//import com.prime.app.abc.service.impl.PedidoServiceImpl;
//import com.prime.infra.dao.jpa.CrudJpaDao;
//
//import edu.emory.mathcs.backport.java.util.Collections;
//
//public class PedidoServiceTest {
//    private PedidoService pedidoService;
//    private Cliente cliente;
//    private CrudJpaDaoImpl crudJpaDaoImpl;
//
//    @Before
//    public void setUp() {
//        cliente = createCliente();
//        crudJpaDaoImpl = new CrudJpaDaoImpl();
//        PedidoServiceImpl pedidoServiceImpl = new PedidoServiceImpl();
//        pedidoServiceImpl.setCrudDao(crudJpaDaoImpl);
//        pedidoServiceImpl.setClienteDao(new ClienteDaoImpl(cliente));
//        pedidoServiceImpl.setPedidoDao(new PedidoDaoImpl());
//
//        pedidoService = pedidoServiceImpl;
//    }
//
//    @After
//    public void tearDown() {
//        pedidoService = null;
//        cliente = null;
//    }
//
//    @Test
//    public void testCriarPedido() throws Exception {
//
//        List<EstoqueQuantidade> listEstoqueQuantidade = createListEstoqueQuantidade();
//
//        Pedido pedido = pedidoService
//            .criarPedido(cliente, createFormaPagamento(cliente), createListEstoqueQuantidade());
//        Assert.assertNotNull(pedido.getData());
//        Assert.assertNotNull(pedido.getFormaPagamento());
//        Assert.assertNotNull(pedido.getItens());
//        Assert.assertNotNull(pedido.getNumeroNF());
//        Assert.assertNotNull(pedido.getStatus());
//        Assert.assertNotNull(pedido.getValor());
//        Assert.assertNotNull(pedido.getCodigo());
//
//        for (int i = 0; i < pedido.getItens().size(); i++) {
//            ItemPedido itemPedido = pedido.getItens().get(i);
//            Assert.assertTrue(itemPedido.getProduto().equals(listEstoqueQuantidade.get(i).getEstoque().getProduto()));
//        }
//
//        for (EstoqueQuantidade estQuant : listEstoqueQuantidade) {
//            Integer quantidade = estQuant.getEstoque().getQuantidade() - estQuant.getQuantidade();
//            Estoque estoque = crudJpaDaoImpl.findById(Estoque.class, estQuant.getEstoque().getCodigo(), false);
//            Assert.assertEquals(quantidade, estoque.getQuantidade());
//        }
//    }
//
//    @Test
//    public void testGetPedidosPorCliente() {
//        List<Pedido> pedidos = pedidoService.getPedidosPorCliente("test@email.com.br");
//        Assert.assertNotNull(pedidos);
//        Assert.assertEquals(pedidos.size(), 0);
//    }
//
//    protected Cliente createCliente() {
//        Cliente cliente = new Cliente();
//        cliente.setCodigo(1l);
//        cliente.setNome("Test");
//        cliente.setEmail("test@email.com.br");
//        cliente.setEndereco("Rua");
//        cliente.setSenha("123");
//        cliente.setTelefone(22223333);
//        cliente.setCidade(Cidade.SAO_PAULO);
//        cliente.setCep(223333333);
//        return cliente;
//    }
//
//    protected FormaPagamento createFormaPagamento(Cliente cliente) {
//        FormaPagamento formaPagamento = new FormaPagamento();
//        formaPagamento.setCodigo(1l);
//        formaPagamento.setCliente(cliente);
//        formaPagamento.setDescricao("123");
//        formaPagamento.setTipo(TipoFormaPagamento.CREDITO);
//        return formaPagamento;
//    }
//
//    protected List<EstoqueQuantidade> createListEstoqueQuantidade() {
//        List<EstoqueQuantidade> listEstoqueQuantidade = new ArrayList<EstoqueQuantidade>();
//        for (Produto produto : createListProdutos()) {
//            EstoqueQuantidade estoqueQuantidade = new EstoqueQuantidade(createEstoque(1l, 40, new BigDecimal("10.0"),
//                produto));
//            estoqueQuantidade.setQuantidade((short) 20);
//            listEstoqueQuantidade.add(estoqueQuantidade);
//        }
//        return listEstoqueQuantidade;
//    }
//
//    protected Estoque createEstoque(Long codigo, Integer quantidade, BigDecimal preco, Produto produto) {
//        return crudJpaDaoImpl.persist(new Estoque(codigo, quantidade, preco, produto));
//    }
//
//    protected List<Produto> createListProdutos() {
//        List<Produto> produtos = new ArrayList<Produto>();
//        for (int i = 0; i < 5; i++) {
//            produtos.add(new Produto(new Integer(i).longValue(), "nome-" + i, "nome-artista-" + i, new Date(), null,
//                null));
//        }
//        return produtos;
//    }
//
//    private static class CrudJpaDaoImpl implements CrudJpaDao {
//        private Map<Long, Pedido> pedidos = java.util.Collections.synchronizedMap(new HashMap<Long, Pedido>());
//        private Map<Long, Estoque> estoques = java.util.Collections.synchronizedMap(new HashMap<Long, Estoque>());
//
//        public <T> List<T> findAll(Class<?> entityType, int maxResults) {
//            return Collections.emptyList();
//        }
//
//        public <T> T findById(Class<?> entityType, Serializable id, boolean lock) {
//            if (entityType.equals(Pedido.class)) {
//                return (T) pedidos.get(id);
//            }
//            else if (entityType.equals(Estoque.class)) {
//                return (T) estoques.get(id);
//            }
//            return null;
//        }
//
//        public <T> T persist(T entity) {
//            if (entity instanceof Pedido) {
//                Pedido pedido = (Pedido) entity;
//                if (pedido.getCodigo() == null) {
//                    pedido.setCodigo(1l);
//                    pedidos.put(new Integer(pedidos.size() + 1).longValue(), pedido);
//                }
//                else {
//                    pedidos.put(pedido.getCodigo(), pedido);
//                }
//            }
//            else if (entity instanceof Estoque) {
//                Estoque estoque = (Estoque) entity;
//                if (estoque.getCodigo() == null) {
//                    estoque.setCodigo(1l);
//                    this.estoques.put(new Integer(estoques.size() + 1).longValue(), estoque);
//                }
//                else {
//                    this.estoques.put(estoque.getCodigo(), estoque);
//                }
//            }
//            return entity;
//        };
//
//        public <T> void remove(Class<?> entityType, Serializable id) {
//            pedidos.remove(id);
//        }
//    }
//
//    private static class ClienteDaoImpl implements ClienteDao {
//
//        private Cliente cliente;
//
//        public ClienteDaoImpl(Cliente cliente) {
//            this.cliente = cliente;
//        }
//
//        public Cliente findByEmail(String emailCliente) {
//            return cliente;
//        }
//    }
//
//    private static class PedidoDaoImpl implements PedidoDao {
//        public List<Pedido> findPedidosByCliente(Cliente cliente) {
//            return java.util.Collections.EMPTY_LIST;
//        }
//    }
//}
