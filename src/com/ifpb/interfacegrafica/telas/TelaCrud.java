package com.ifpb.interfacegrafica.telas;

import com.ifpb.interfacegrafica.dao.UsuarioDaoArquivo;
import com.ifpb.interfacegrafica.dao.UsuarioDaoSet;
import com.ifpb.interfacegrafica.modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class TelaCrud extends JFrame{
    private JButton deletarContaButton;
    private JButton salvarAlteraçãoButton;
    private JTextField campoNovoNome;
    private JFormattedTextField campoNovoNascimento;
    private JPasswordField campoNovaSenhaRepetida;
    private JPasswordField campoNovaSenha;
    private JPanel painelcrud;
    private JTextField campoEmail;
    private JButton botaoPesquisar;
    private DateTimeFormatter formatter;

    public TelaCrud(){

        super("Tela de Crud");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(painelcrud);
        pack();

        // é dentro mesmo. Q bugado
        salvarAlteraçãoButton.addActionListener(e -> {

            String email = campoEmail.getText();
            String nome = campoNovoNome.getText();

            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate nascimento = LocalDate
                    .parse(campoNovoNascimento.getText(), formatter);

            String senha = new String(campoNovaSenha.getPassword());

            Usuario usuario = new Usuario(email, nome, nascimento, senha);

            UsuarioDaoArquivo usuarioDao = null;

            try {
                usuarioDao.atualizar(usuario);

            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this,
                        "Falha na conexão com o arquivo e na busca do email",
                        "Mensagem de erro",
                        JOptionPane.ERROR_MESSAGE);
            }

        });


        deletarContaButton.addActionListener(e -> {



        });

        botaoPesquisar.addActionListener(e -> {

            UsuarioDaoArquivo usuarioDaoArquivo = null;

            Usuario usuario;

            try {
                usuario = usuarioDaoArquivo.buscarPorEmail(campoEmail.getText());


                //System.out.println(usuario.getNome());


            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this,
                        "Falha na conexão com o arquivo e na busca do email",
                        "Mensagem de erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
