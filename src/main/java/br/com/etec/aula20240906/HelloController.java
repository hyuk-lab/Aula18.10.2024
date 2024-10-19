package br.com.etec.aula20240906;

import br.com.etec.aula20240906.model.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

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
}