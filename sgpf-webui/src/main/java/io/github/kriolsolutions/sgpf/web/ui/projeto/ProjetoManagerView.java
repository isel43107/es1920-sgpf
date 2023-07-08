/*
 * Copyright 2019 kriolSolutions.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kriolsolutions.sgpf.web.ui.projeto;

import com.vaadin.flow.component.AttachEvent;
import io.github.kriolsolutions.sgpf.web.ui.documentos.CandidaturaForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.kriolsolutions.sgpf.backend.bal.dto.PedidoReforcoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.SgpfServiceFacade;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.web.ui.MainLayout1;
import io.github.kriolsolutions.sgpf.web.ui.documentos.DespachoAberturaForm;
import io.github.kriolsolutions.sgpf.web.ui.documentos.DespachoFinBonificacaoForm;
import io.github.kriolsolutions.sgpf.web.ui.documentos.DespachoFinIncentivoForm;
import io.github.kriolsolutions.sgpf.web.ui.documentos.DespachoFinReforcoForm;
import io.github.kriolsolutions.sgpf.web.ui.documentos.PagamentoForm;
import io.github.kriolsolutions.sgpf.web.ui.documentos.ParecerTecnicoForm;
import jakarta.inject.Inject;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.springframework.data.domain.PageRequest;

/**
 * UI
 *
 * @author pauloborges
 */
@Route(value = "projetos", layout = MainLayout1.class)
@PageTitle("Projetos")
public class ProjetoManagerView extends VerticalLayout {

    public final static String VIEW_NAME = "Projetos";

    private final static Logger LOGGER = Logger.getLogger(ProjetoManagerView.class.getName());

    private final ProjetoRepository projetoRepository;

    private final SgpfServiceFacade sgpfacade;

    private final Grid<Projeto> projetoGrid = new Grid<>(Projeto.class, false);

    @Inject
    public ProjetoManagerView(ProjetoRepository projetoRepository, SgpfServiceFacade sgpfacade) {

        this.projetoRepository = projetoRepository;
        this.sgpfacade = sgpfacade;

        init();
    }

    private void init() {

        setupGrid(projetoGrid);

        final Component topLayout = createTopBar();

        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(projetoGrid);
        barAndGridLayout.setFlexGrow(1, projetoGrid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(projetoGrid);

        this.add(barAndGridLayout);

        // If the browser window size changes, check if all columns fit on screen
        // (e.g. switching from portrait to landscape mode)
        UI.getCurrent().getPage().addBrowserWindowResizeListener(
                e -> setColumnVisibility(projetoGrid, e.getWidth()));
    }

    private Component createProjetoEditor() {

        CandidaturaForm candidaturaForm = new CandidaturaForm(sgpfacade.getAceitacaoCandidaturaAcoes());

        return candidaturaForm;
    }

    private Component createTopBar() {

        TextField filterText = new TextField();
        filterText.setPlaceholder("Filtrar por n. projeto");
        filterText.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> listarProjetos(projetoGrid, e.getValue()));

        Button refreshButton = new Button("Refresh");
        //refreshButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refreshButton.setIcon(VaadinIcon.REFRESH.create());
        // CTRL+R will create a new window which is unavoidable
        refreshButton.addClickShortcut(Key.KEY_R, KeyModifier.ALT);
        refreshButton.addClickListener((t) -> {
            loadItems();
        });

        Button novaCandidaturaButton = new Button("Nova Candidatura");
        novaCandidaturaButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        novaCandidaturaButton.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        // CTRL+N will create a new window which is unavoidable
        novaCandidaturaButton.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        novaCandidaturaButton.addClickListener((t) -> {

            CandidaturaForm candForm = new CandidaturaForm(sgpfacade.getAceitacaoCandidaturaAcoes());
            Dialog candDialog = new Dialog(candForm);
            candDialog.open();

            candForm.getFecharButton().addClickListener((e) -> {
                candDialog.close();
            });
        });

        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.getStyle().set("marginRight", "10px");
        topLayout.add(filterText);
        topLayout.add(refreshButton);
        topLayout.add(novaCandidaturaButton);
        topLayout.setVerticalComponentAlignment(Alignment.START, filterText);
        topLayout.expand(filterText);

        return topLayout;
    }

    private void loadItems() {
        projetoGrid.getDataProvider().refreshAll();
    }

    private void listarProjetos(Grid<Projeto> grid, String filterText) {

        if (filterText != null && !filterText.isEmpty()) {
            //SingularAttribute<Projeto, ?>[] attributes = new SingularAttribute[] {};
            List<Projeto> projetos = this.projetoRepository.findByProjNumero(filterText);
            if (projetos != null) {

                AbstractBackEndDataProvider<Projeto, Object> dp = new AbstractBackEndDataProvider<Projeto, Object>() {
                    @Override
                    protected Stream<Projeto> fetchFromBackEnd(
                            Query<Projeto, Object> query) {
                        return projetos.stream();
                    }

                    @Override
                    protected int sizeInBackEnd(Query<Projeto, Object> query) {
                        return projetos.size();
                    }
                };

                grid.setDataProvider(dp);
            }
        } else {
            // List<Projeto> projetos = this.projetoRepository.findAll();
            //grid.setItems(projetos);
        }

    }

    private void setupGrid(Grid<Projeto> grid) {

        grid.removeAllColumns();

        grid.addColumn(Projeto::getProjNumero).setHeader("Numero projeto")
                .setFlexGrow(10).setSortable(false).setKey("projNumero");

        grid.addColumn(Projeto::getProjDesignacao).setHeader("Designação projeto")
                .setFlexGrow(20).setSortable(false).setKey("projDesignacao");

        grid.addColumn(Projeto::getProjEstado).setHeader("Estado projeto")
                .setFlexGrow(20).setSortable(false).setKey("projEstado");

        grid.addColumn(Projeto::getProjTipo).setHeader("Tipo projeto")
                .setFlexGrow(20).setSortable(false).setKey("projTipo");

        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

        grid.addColumn(product -> decimalFormat.format(product.getProjMontanteSolicitado()))
                .setHeader("Mnt. Solicitado").setTextAlign(ColumnTextAlign.END)
                .setComparator(Comparator.comparing(Projeto::getProjMontanteSolicitado))
                .setFlexGrow(3).setSortable(false).setKey("projMontanteSolicitado");

        grid.addColumn(Projeto::getPromotorDesignacao).setHeader("Promotor projeto")
                .setFlexGrow(20).setSortable(false).setKey("promotorDesignacao");

        grid.addColumn(Projeto::getContatoNome).setHeader("Contato projeto")
                .setFlexGrow(20).setSortable(false).setKey("contatoNome");

        grid.addComponentColumn(item -> this.createOptionsButton(item))
                .setHeader("Ações").setKey("acoesDisponiveis");

        /*
        Executors.newCachedThreadPool().submit(() -> {
            var lst = this.projetoRepository.findAll();
            //Dataprovider
            ListDataProvider<Projeto> dataProvider = new ListDataProvider<>(lst);

            UI.getCurrent().access(() -> {
                grid.setDataProvider(dataProvider);
            });

            return null;
        });*/
        
        //grid.setDataProvider(dataProvider(projetoRepository));

        /*
        DataProvider<Projeto, Void> dataProvider
                
                = DataProvider.fromCallbacks(
                        // First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            List<Projeto> projetos = this.projetoRepository.findAll();
                            return projetos.stream();
                        },
                        count -> (int)this.projetoRepository.count()
                );
         */
    }

    private DataProvider<Projeto, Void> dataProvider(ProjetoRepository hisRepository) {

        var Exec = Executors.newCachedThreadPool();

        return new AbstractBackEndDataProvider<Projeto, Void>() {
            @Override
            protected Stream<Projeto> fetchFromBackEnd(Query<Projeto, Void> query) {

                Stream<Projeto> res = Stream.empty();
                try {
                     res = Exec.submit(() -> {
                        var pag = PageRequest.of(query.getPage(), query.getPageSize());
                        return hisRepository.findAll(pag).stream();
                    }).get();
                    
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(ProjetoManagerView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return res;
            }

            @Override
            protected int sizeInBackEnd(Query<Projeto, Void> query) {
                
                int res = 0;
                try {
                     res = Exec.submit(() -> {
                        return (int) hisRepository.count();
                    }).get();
                    
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(ProjetoManagerView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return res;
            }
        };
    }

    private void setColumnVisibility(Grid<Projeto> grid, int width) {
        if (width > 800) {
            grid.getColumnByKey("projNumero").setVisible(true);
            grid.getColumnByKey("projDesignacao").setVisible(true);
            grid.getColumnByKey("projEstado").setVisible(true);
            grid.getColumnByKey("projTipo").setVisible(true);
            grid.getColumnByKey("projMontanteSolicitado").setVisible(false);
            grid.getColumnByKey("promotorDesignacao").setVisible(false);
            grid.getColumnByKey("contatoNome").setVisible(false);
            grid.getColumnByKey("acoesDisponiveis").setVisible(true);
        } else if (width > 550) {
            grid.getColumnByKey("projNumero").setVisible(true);
            grid.getColumnByKey("projDesignacao").setVisible(true);
            grid.getColumnByKey("projEstado").setVisible(true);
            grid.getColumnByKey("projTipo").setVisible(false);
            grid.getColumnByKey("projMontanteSolicitado").setVisible(false);
            grid.getColumnByKey("promotorDesignacao").setVisible(false);
            grid.getColumnByKey("contatoNome").setVisible(false);
            grid.getColumnByKey("acoesDisponiveis").setVisible(true);
        } else {
            grid.getColumnByKey("projNumero").setVisible(true);
            grid.getColumnByKey("projDesignacao").setVisible(true);
            grid.getColumnByKey("projEstado").setVisible(true);
            grid.getColumnByKey("projTipo").setVisible(false);
            grid.getColumnByKey("projMontanteSolicitado").setVisible(false);
            grid.getColumnByKey("promotorDesignacao").setVisible(false);
            grid.getColumnByKey("contatoNome").setVisible(false);
            grid.getColumnByKey("acoesDisponiveis").setVisible(true);
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // fetch browser width
        UI.getCurrent().getInternals().setExtendedClientDetails(null);
        UI.getCurrent().getPage().retrieveExtendedClientDetails(e -> {
            setColumnVisibility(projetoGrid, e.getBodyClientWidth());
        });
    }

    public Button createOptionsButton(Projeto projeto) {

        Button button = new Button(new Icon(VaadinIcon.OPTIONS));

        //ProjetoContextMenu<Projeto> contextMenu = projetoGrid.addContextMenu();
        ProjetoContextMenu<Projeto> contextMenu = new ProjetoContextMenu<>();
        contextMenu.setData(projeto);
        //ContextMenu contextMenu = new ContextMenu(button);
        contextMenu.setTarget(button);
        //contextMenu.setVisible(true);
        //contextMenu.removeAll();

        //OPCOES GERAIS
        contextMenu.addItem("Editar projeto", event -> {
            Notification.show("Editar projeto sera implementado brevemente");
        });

        //OPCOES BASEADO NO STATE
        switch (projeto.getProjEstado()) {

            case EM_CANDIDATURA:
                candidaturaOptions(contextMenu);
                break;

            case DESPACHO_ABERTURA:
                despachoAberturaOptions(contextMenu);
                break;

            case PARECER_TECNICO:
                parecerTecnicoOptions(contextMenu);
                break;

            case DESPACHO_FIN_INCENTIVO:
                despachoFinIncentivoOptions(contextMenu);
                break;
            case DESPACHO_FIN_BONIFICACAO:
                despachoFinBonificacaoOptions(contextMenu);
                break;
            case DESPACHO_REFORCO:
                despachoReforcoOptions(contextMenu);
                break;
            case EM_PAGAMENTO:
                projetoEmPagamentoOptions(contextMenu);
                break;
            case PROJETO_ARQUIVADO:
                projetoArquivadoOptions(contextMenu);
                break;
            case PROJETO_REJEITADO:
                projetoRejeitadoOptions(contextMenu);
                break;
            case PROJETO_SUSPENSO:
                projetoSuspensoOptions(contextMenu);
                break;
        }

        //TODO Depende do estado do projeto
        return button;
    }

    /* ACOES DE CONTEXTO - DE ACORDO COM O ESTADO DO PROJETO */
 /* */
    private Component candidaturaOptions(ProjetoContextMenu<Projeto> contextMenu) {
        contextMenu.addItem("Abrir projeto", event -> {
            Notification.show("Abrir projeto");
            contextMenu.getData().ifPresent(projeto -> {
                sgpfacade.getAceitacaoCandidaturaAcoes().abir(projeto);
            });
        });

        contextMenu.addItem("Arquivar projeto", event -> {
            Notification.show("Arquivar projeto");
            contextMenu.getData().ifPresent(projeto -> {
                sgpfacade.getAceitacaoCandidaturaAcoes().arquivar(projeto);
            });
        });

        return contextMenu;
    }

    private void despachoAberturaOptions(ProjetoContextMenu<Projeto> contextMenu) {

        contextMenu.addItem("Emissão despacho abertura", event -> {
            Notification.show("Despacho Abertura");

            contextMenu.getData().ifPresent(projeto -> {

                DespachoAberturaForm candForm = new DespachoAberturaForm(sgpfacade.getDespachoAberturaAcoes(), projeto);
                Dialog candDialog = new Dialog(candForm);
                candDialog.open();

                candForm.getFecharButton().addClickListener((e) -> {
                    candDialog.close();
                });
            });
        });
    }

    private void parecerTecnicoOptions(ProjetoContextMenu<Projeto> contextMenu) {
        contextMenu.addItem("Emissão Parecer Tecnico", event -> {
            Notification.show("Parecer tecnico");
            contextMenu.getData().ifPresent(projeto -> {
                ParecerTecnicoForm candForm = new ParecerTecnicoForm(sgpfacade.getParecerTecnicoAcoes(), projeto);
                Dialog candDialog = new Dialog(candForm);
                candDialog.open();

                //Button Fechar
                candForm.getFecharButton().addClickListener((e) -> {
                    candDialog.close();
                });
            });
        });
    }

    private void despachoFinIncentivoOptions(ProjetoContextMenu<Projeto> contextMenu) {
        contextMenu.addItem("Emissão Despacho Incentivo", event -> {
            Notification.show("Despacho Incentivo");
            contextMenu.getData().ifPresent(projeto -> {
                DespachoFinIncentivoForm candForm = new DespachoFinIncentivoForm(sgpfacade.getDespachoFinanciamentoIncentivoAcoes(), projeto);
                Dialog candDialog = new Dialog(candForm);
                candDialog.open();
                candForm.getFecharButton().addClickListener((e) -> {
                    candDialog.close();
                });
            });
        });
    }

    private void despachoFinBonificacaoOptions(ProjetoContextMenu<Projeto> contextMenu) {
        contextMenu.addItem("Emissão Despacho Bonificacao", event -> {
            Notification.show("Despacho Bonificacao");
            contextMenu.getData().ifPresent(projeto -> {
                DespachoFinBonificacaoForm form = new DespachoFinBonificacaoForm(sgpfacade.getDespachoBonificacaoAcoes(), projeto);
                Dialog candDialog = new Dialog(form);
                candDialog.open();

            });
        });
    }

    private void despachoReforcoOptions(ProjetoContextMenu<Projeto> contextMenu) {
        contextMenu.addItem("Emissão Despacho Reforço", event -> {
            Notification.show("Emissão Despacho Reforço");
            contextMenu.getData().ifPresent(projeto -> {
                DespachoFinReforcoForm form = new DespachoFinReforcoForm(sgpfacade.getDespachoFinanciamentoReforcoAcoes(), projeto);
                Dialog candDialog = new Dialog(form);
                candDialog.open();

                form.getFecharButton().addClickListener((e) -> {
                    candDialog.close();
                });
            });
        });
    }

    private void projetoEmPagamentoOptions(ProjetoContextMenu<Projeto> contextMenu) {
        contextMenu.addItem("Efetuar Pagamento", event -> {
            Notification.show("Efetuar Pagamento");
            contextMenu.getData().ifPresent(projeto -> {
                PagamentoForm form = new PagamentoForm(sgpfacade.getPagamentoAcoes(), projeto);
                Dialog candDialog = new Dialog(form);
                candDialog.open();

                form.getFecharButton().addClickListener((e) -> {
                    candDialog.close();
                });
            });
        });
        opcaoSolicitarReforco(contextMenu);
    }

    private void projetoFechadoOptions(ProjetoContextMenu<Projeto> contextMenu) {
        opcaoSolicitarReforco(contextMenu);
    }

    private void projetoArquivadoOptions(ProjetoContextMenu<Projeto> contextMenu) {
        contextMenu.addItem("Reenquadramento", event -> {
            Notification.show("NO IMPLEMENTED: Mudar os estado para Candidatura");
            contextMenu.getData().ifPresent(projeto -> {
                sgpfacade.getProjetoAcoes().reenquadrar(projeto);
            });
        });
    }

    private void projetoRejeitadoOptions(ProjetoContextMenu<Projeto> contextMenu) {
        contextMenu.addItem("Rejeitado", event -> {
            Notification.show("Este projeto não pode ser Alterado. Estado FINAL");
            contextMenu.getData().ifPresent(projeto -> {
            });
        });
    }

    private void projetoSuspensoOptions(ProjetoContextMenu<Projeto> contextMenu) {
        contextMenu.addItem("Reativar", event -> {
            Notification.show("Este projeto devera retornar ao estado precedente");
            contextMenu.getData().ifPresent(projeto -> {
                //DespachoFinReforco form = new DespachoFinReforco(sgpfacade.getDespachoBonificacaoAcoes(), projeto);
                //Dialog candDialog = new Dialog(form);
                //candDialog.open();
                sgpfacade.getProjetoAcoes().reativar(projeto);
            });
        });
    }

    private void opcaoSolicitarReforco(ProjetoContextMenu<Projeto> contextMenu) {
        contextMenu.addItem("Solicitar Reforço", event -> {
            Notification.show("Solicitar Reforço");
            contextMenu.getData().ifPresent(projeto -> {
                PedidoReforcoDto ref = new PedidoReforcoDto();
                ref.setProjetoId(projeto.getId());
                sgpfacade.getDespachoFinanciamentoReforcoAcoes().solicitar(ref);
            });
        });
    }
}
