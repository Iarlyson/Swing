package com.ifpb.interfacegrafica.telas;

import com.ifpb.interfacegrafica.dao.UsuarioDaoArquivo;
import com.ifpb.interfacegrafica.dao.UsuarioDaoSet;
import com.ifpb.interfacegrafica.modelo.Usuario;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class TelaCadastroUsuario extends JFrame {
    private JTextField campoEmail;
    private JTextField campoNome;
    private JFormattedTextField campoNascimento;
    private JPasswordField campoSenha1;
    private JPasswordField campoSenha2;
    private JButton salvarButton;
    private JPanel painel;
    private UsuarioDaoArquivo usuarioDao;
    private DateTimeFormatter formatter;

    public TelaCadastroUsuario(){
        super("Cadastro de usuário");

        try {
            usuarioDao = new UsuarioDaoArquivo();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Falha na conexão com o arquivo",
                    "Mensagem de erro",
                    JOptionPane.ERROR_MESSAGE);
        }

        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        setContentPane(painel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        salvarButton.addActionListener(e -> {

            String email = campoEmail.getText();
            String nome = campoNome.getText();
            String dataNascimento = campoNascimento.getText().replace("/", "");
            //System.out.println(campoNascimento.getText().replace("/", "").length());
            //até tratei o campo nascimento para tirar o / /
            //System.out.println(campoNascimento.getText().replace("/", ""));

            //TODO validar os campos
            //Primeiro pelo EMail
            if(!email.isEmpty()) {

                if (!nome.isEmpty()) {
                    
                    //Verificando campo de data.
                    ArrayList <String> list = new ArrayList<String>();
                    list.addAll((Arrays.asList("0","1","2","3","4","5","6","7","8","9")));
                    boolean campoValido = true;
                    for (int i = 0; i < dataNascimento.length(); i++){
                        if (!list.contains(dataNascimento.substring(i, i+1))){
                            campoValido = false;
                        }
                    
                    }

                    if (campoValido){
                        //Validando data de nascimento

                        //Meses com 31 dias
                        ArrayList <Integer> list31 = new ArrayList<Integer>();
                        list31.addAll((Arrays.asList(1,3,5,7,8,10,12)));
                        //Meses com 30 dias
                        ArrayList <Integer> list30 = new ArrayList<Integer>();
                        list30.addAll((Arrays.asList(4,6,9,11)));

                        int dia = Integer. parseInt(dataNascimento.substring(0,2));
                        int mes = Integer. parseInt(dataNascimento.substring(2,4));
                        int ano = Integer. parseInt(dataNascimento.substring(4,8));
                        boolean dataValida = false;

                        if (ano < 2020){
                            if (mes > 0 && mes <= 12){
                                if (mes == 2){
                                    //Ano Bicesto
                                    if (ano % 4 == 0){
                                        if (dia > 0 && dia <=29) {
                                            dataValida = true;
                                        }
                                    }
                                    else{
                                        if (dia > 0 && dia <=28) {
                                            dataValida = true;
                                        }
                                    }
                                }
                                if (list30.contains(mes)){
                                    if (dia > 0 && dia <=30){
                                        dataValida = true;
                                    }
                                }
                                if (list31.contains(mes)){
                                    if (dia > 0 && dia <= 31){
                                        dataValida = true;
                                    }
                                }
                            }
                        }

                        //Esse ano
                        if (ano ==2020){
                            if(mes == 1){
                                if (dia > 0 && dia <= 31){
                                    dataValida = true;
                                }
                            }
                            if(mes == 2){
                                if (dia > 0 && dia <= 10){
                                    dataValida = true;
                                }
                            }
                        }

                        if(dataValida) {
                            if(campoSenha1.getPassword().length != 0) {

                                if(campoSenha2.getPassword().length != 0) {

                                    //senhas iguais
                                    if(Arrays.equals(campoSenha1.getPassword(), campoSenha2.getPassword())) {

                                        //Cadastrando usuario no banco

                                        LocalDate nascimento = LocalDate
                                                .parse(campoNascimento.getText(), formatter);

                                        String senha = new String(campoSenha1.getPassword());

                                        Usuario usuario = new Usuario(email, nome, nascimento, senha);

                                        try {
                                            if (usuarioDao.salvar(usuario)) {
                                                JOptionPane.showMessageDialog(this,
                                                        "Salvo com sucesso");
                                            } else {
                                                JOptionPane.showMessageDialog(this,
                                                        "Usuário já cadastrado",
                                                        "Mensagem de erro",
                                                        JOptionPane.ERROR_MESSAGE);
                                            }
                                        } catch (IOException | ClassNotFoundException ex) {
                                            JOptionPane.showMessageDialog(this,
                                                    "Falha na conexão com o arquivo",
                                                    "Mensagem de erro",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(this,
                                                "As senhas devem ser iguais");
                                    }

                                }
                                else{
                                    JOptionPane.showMessageDialog(this,
                                            "Campo repetição de senha não preenchido!");
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(this,
                                        "Campo senha não preenchido!");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(this,
                                    "Data invalida S2!");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(this,
                                "Campo nascimento não preenchido!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this,
                            "Campo nome não preenchido!");
                }
            }
            else{
                JOptionPane.showMessageDialog(this,
                        "Campo Email não preenchido!");
            }
                /*

                */

                /*
            else{
                //Segundo pelo nome
                if(nome.isEmpty()){
                        JOptionPane.showMessageDialog(this,
                                "Campo nome não preenchido!");
                    if(campoNascimento.getText() == null){
                        JOptionPane.showMessageDialog(this,
                                "Campo nascimento não preenchido!");
                    }

                    if(campoSenha1.getPassword().length == 0){
                        JOptionPane.showMessageDialog(this,
                                "Campo senha não preenchido!");
                    }

                    if(campoSenha2.getPassword().length == 0){
                        JOptionPane.showMessageDialog(this,
                                "Campo repetição de senha não preenchido!");
                    }
                }
                else{
                    //Terceiro pelo nascimento
                    //em todos os nascimento
                    if (campoNascimento.getText() == null){
                        JOptionPane.showMessageDialog(this,
                                "Campo nascimento não preenchido!");

                    if(campoSenha1.getPassword().length == 0){
                        JOptionPane.showMessageDialog(this,
                                "Campo senha não preenchido!");
                    }

                    if(campoSenha2.getPassword().length == 0){
                        JOptionPane.showMessageDialog(this,
                                "Campo repetição de senha não preenchido!");
                    }
                    }
                    else{
                        //quarto pela primeira senha
                        if(campoSenha1.getPassword().length == 0){
                            JOptionPane.showMessageDialog(this,
                                    "Campo senha não preenchido!");


                        if(campoSenha2.getPassword().length == 0){
                            JOptionPane.showMessageDialog(this,
                                    "Campo repetição de senha não preenchido!");
                        }

                        }
                        else{
                            //repetição de senha

                            if(campoSenha2.getPassword().length == 0){
                                JOptionPane.showMessageDialog(this,
                                        "Campo repetição de senha não preenchido!");
                            }
                            else{
                                //senhas iguais
                              if(!Arrays.equals(campoSenha1.getPassword(),
                                campoSenha2.getPassword())){
                                JOptionPane.showMessageDialog(this,
                                 "As senhas devem ser iguais");
                                }


                              else {
                                email = campoEmail.getText();
                                nome = campoNome.getText();
                                //TODO Tratar as exceções
                                LocalDate nascimento = LocalDate
                                    .parse(campoNascimento.getText(), formatter);
                                String senha = new String(campoSenha1.getPassword());

                                Usuario usuario = new Usuario(email, nome, nascimento, senha);

                            try {
                                if (usuarioDao.salvar(usuario)) {
                                    JOptionPane.showMessageDialog(this,
                                    "Salvo com sucesso");
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "Usuário já cadastrado",
                                    "Mensagem de erro",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Falha na conexão com o arquivo",
                                "Mensagem de erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                            }
                        }
                    }
                }
            }

                 */
        });


    }
//temos aqui a formatação
    private void createUIComponents() {
        MaskFormatter formatter = null;
        try {
             formatter = new MaskFormatter("##/##/####");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        campoNascimento = new JFormattedTextField();
        if(formatter!= null) formatter.install(campoNascimento);
    }
}
