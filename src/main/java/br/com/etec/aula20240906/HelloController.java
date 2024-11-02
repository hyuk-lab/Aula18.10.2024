package br.com.etec.aula20240906;

import br.com.etec.aula20240906.model.Cliente;
import br.com.etec.aula20240906.model.dao.ClienteDao;
import br.com.etec.aula20240906.model.database.Database;
import br.com.etec.aula20240906.model.database.DatabaseFactory;
import br.com.etec.aula20240906.model.database.DatabaseMySQL;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloController {
    //atributos para manipulção do banco de dados
    /*Static NÃO precisa instanciar E outra classe não pode herdar dela
    não pode ter classes finais
    */

    private final Database database =  DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ClienteDao clienteDao = new ClienteDao();



    @FXML
    private Button btnCadastrar;

    @FXML
    private CheckBox chkCasado;

    @FXML
    private ToggleGroup grpSexo;

    @FXML
    private RadioButton rbFeminino;

    @FXML
    private RadioButton rbMasculino;

    @FXML
    private TextArea txtAreaDados;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtBusca;

    private Cliente cliente;
    private List<Cliente> listaClientes = new ArrayList<>();

    @FXML
    protected void onClickCadastrar() {

        if (txtNome.getText().equals("")) {
           avisoBranco("Nome em Branco");
           txtNome.requestFocus();
           return;
        }

        if (txtTelefone.getText().equals("")) {
            avisoBranco("Telefone em Branco");
            txtTelefone.requestFocus();
            return;
        }

        if (txtEmail.getText().equals("")) {
            avisoBranco("Email em Branco");
            txtEmail.requestFocus();
            return;
        }

        String sexo = rbMasculino.isSelected()? "Masculino" : "Feminino";
        int id = listaClientes.size() + 1;
        cliente = new Cliente(id, txtNome.getText(), txtEmail.getText(),txtTelefone.getText(),sexo,chkCasado.isSelected());

        listaClientes.add(cliente);
        txtAreaDados.setText(listaClientes.toString());



        clienteDao.setConnection(connection);
        if (clienteDao.inserir(cliente)){
            aviso("salvar Registro","Cadastro de Cliente","Salvo com Sucesso!");
        } else {
            aviso("salvar Registro","Cadastro de Cliente","Erro ao Salvar!");
        }

        limparCampos();


    }

    private void limparCampos() {
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        rbMasculino.setSelected(false);
        rbFeminino.setSelected(false);
        chkCasado.setSelected(false);
        txtNome.requestFocus();
    }

    private void avisoBranco(String msg) {
        Alert alertaErro = new Alert(Alert.AlertType.ERROR);
        alertaErro.setTitle("Erro");
        alertaErro.setHeaderText("Campo em Branco");
        alertaErro.setContentText(msg);
        alertaErro.show();
    }

    @FXML
    protected void onBuscarCliente(){
        int idBusca;

        try {
           idBusca = Integer.parseInt(txtBusca.getText());
        } catch (Exception err) {
            Alert alertaErro = new Alert(Alert.AlertType.ERROR);
            alertaErro.setTitle("Erro");
            alertaErro.setHeaderText("Erro de Conversão");
            alertaErro.setContentText("O campo não é um número válido");
            alertaErro.show();
            return;
        }
        for (int i = 0; i < listaClientes.size(); i++) {
            if (listaClientes.get(i).getId() == idBusca) {
                 Cliente cliente1 = listaClientes.get(i);
                 populaCampos(cliente1);
            }
        }
    }

    private void populaCampos(Cliente cli) {
        txtNome.setText(cli.getNome());
        txtEmail.setText(cli.getEmail());
        txtTelefone.setText(cli.getTelefone());

        if (cli.getSexo().equals("Feminino")) {
            rbFeminino.setSelected(true);
            rbMasculino.setSelected(false);
        } else {
            rbFeminino.setSelected(false);
            rbMasculino.setSelected(true);
        }

        if (cli.getCasado()) {
            chkCasado.setSelected(true);
        } else {
            chkCasado.setSelected(false);
        }

    }
    private void aviso(String title, String header, String content){
        Alert alerta = new Alert (Alert.AlertType.INFORMATION);

        alerta.setTitle(title);
        alerta.setHeaderText(header);
        alerta.setContentText(content);
        alerta.show();

    }


    @FXML
    protected void onBuscarClienteBD(){
        int idbusca;


        try{
            idbusca = Integer.parseInt(txtBusca.getText());
        }catch (Exception error) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setContentText("Erro de Conversão");
            alerta.setContentText("O campo não é um numero válido");
            alerta.show();
            return;
        }
        clienteDao.setConnection(connection);
        cliente = clienteDao.getClienteById(idbusca);

        if (cliente.getId() != null){
            populaCampos(cliente);

        }else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Busca Cliente");
            alerta.setHeaderText("Busca de Cliente");
            alerta.setContentText("Não existe cliente com esse código");
            alerta.show();
        }

    }

    @FXML
    protected void onDeletarCliente(){
        int idBusca;

        try {
            idBusca = Integer.parseInt(txtBusca.getText());
            clienteDao.setConnection(connection);
            clienteDao.deletar(idBusca);
        } catch (Exception err) {
            Alert alertaErro = new Alert(Alert.AlertType.ERROR);
            alertaErro.setTitle("Erro");
            alertaErro.setHeaderText("Erro de Conversão");
            alertaErro.setContentText("O campo não é um número válido");
            alertaErro.show();
            return;
        }
    }




}






